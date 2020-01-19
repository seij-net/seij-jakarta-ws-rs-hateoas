package jakarta.ws.rs.ext.hateoas.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import jakarta.ws.rs.ext.hateoas.EntityLinked;
import jakarta.ws.rs.ext.hateoas.LinkEmbeddableBuilderImpl;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.util.*;

import static io.restassured.path.json.JsonPath.from;


public class JakartaWsRsHateoasModuleTest {
    @Test
    public void testHalNoLinks() {
        HALTestBean bean = new HALTestBean("title_a", "content_a");
        EntityLinked<HALTestBean> content = EntityLinked.build(bean, Collections.emptyList());
        String result = writeForTest(content);
        Assertions.assertThat(JsonPath.from(result).getString("title")).isEqualTo("title_a");
        Assertions.assertThat(JsonPath.from(result).getString("content")).isEqualTo("content_a");
    }

    @Test
    public void testHalStandardLinks() {
        HALTestBean bean = new HALTestBean("title_a", "content_a");
        Link simpleLink1 = Link.fromUriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName1")
                .param("myparam1_1", "myvalue1_1")
                .param("myparam1_2", "myvalue1_2")
                .build();
        Link simpleLink2 = Link.fromUriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName2")
                .param("myparam2_1", "myvalue2_1")
                .param("myparam2_2", "myvalue2_2")
                .build();
        Link simpleLink3 = Link.fromUriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName3")
                .build();
        EntityLinked<HALTestBean> content = EntityLinked.build(bean, Arrays.asList(simpleLink1, simpleLink2, simpleLink3));
        JsonPath json = JsonPath.from(writeForTest(content));
        Assertions.assertThat(json.getString("title")).isEqualTo("title_a");
        Assertions.assertThat(json.getString("content")).isEqualTo("content_a");
        Assertions.assertThat(json.getString("_links.myOperationName1.params.myparam1_1")).isEqualTo("myvalue1_1");
        Assertions.assertThat(json.getString("_links.myOperationName1.params.myparam1_2")).isEqualTo("myvalue1_2");
        Assertions.assertThat(json.getString("_links.myOperationName2.params.myparam2_1")).isEqualTo("myvalue2_1");
        Assertions.assertThat(json.getString("_links.myOperationName2.params.myparam2_2")).isEqualTo("myvalue2_2");
        Assertions.assertThat(json.getString("_links.myOperationName3.params")).isNull();
        Assertions.assertThat(json.getString("_embedded")).isNull();
    }

    @Test
    public void testHalEmbeddedLinks() {
        HALTestBean bean = new HALTestBean("title_a", "content_a");
        Link opNotResolved = new LinkEmbeddableBuilderImpl<>().uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName1")
                .param("myparam1_1", "myvalue1_1")
                .param("myparam1_2", "myvalue1_2")
                .build();
        UUID uid1 = UUID.randomUUID();
        Link opResolved = new LinkEmbeddableBuilderImpl<HALTestOperationResult>().uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName2")
                .embedded(true)
                .resolve(() -> new HALTestOperationResult(uid1, "operation result 2"))
                .build();
        Link opResolvedAsNull = new LinkEmbeddableBuilderImpl<HALTestOperationResult>().uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName3")
                .embedded(true)
                .resolve(() -> null)
                .build();
        Link opResolvedAsList = new LinkEmbeddableBuilderImpl<List<HALTestOperationResult>>().uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName4")
                .embedded(true)
                .resolve(() -> Arrays.asList(new HALTestOperationResult(UUID.randomUUID(), "operation result 4 1"), new HALTestOperationResult(UUID.randomUUID(), "operation result 4 2")))
                .build();
        EntityLinked<HALTestBean> content = EntityLinked.build(bean, Arrays.asList(opNotResolved, opResolved, opResolvedAsNull, opResolvedAsList));
        JsonPath json = JsonPath.from(writeForTest(content));
        Assertions.assertThat(json.getString("title")).isEqualTo("title_a");
        Assertions.assertThat(json.getString("content")).isEqualTo("content_a");
        Assertions.assertThat(json.getString("content")).isEqualTo("content_a");
        Assertions.assertThat(json.getString("_links.myOperationName1.params.myparam1_1")).isEqualTo("myvalue1_1");
        Assertions.assertThat(json.getString("_links.myOperationName1.params.myparam1_2")).isEqualTo("myvalue1_2");
        Assertions.assertThat(json.getString("_links.myOperationName2")).isNull();
        Assertions.assertThat(json.getString("_embedded.myOperationName2.uid")).isEqualTo(uid1.toString());
        Assertions.assertThat(json.getString("_embedded.myOperationName2.name")).isEqualTo("operation result 2");
        Assertions.assertThat(json.getMap("_embedded")).containsKey("myOperationName3");
        Assertions.assertThat(json.getMap("_embedded")).containsKey("myOperationName4");
        Assertions.assertThat(json.getString("_embedded.myOperationName4[0].name")).isEqualTo("operation result 4 1");
        Assertions.assertThat(json.getString("_embedded.myOperationName4[1].name")).isEqualTo("operation result 4 2");
    }

    @Test
    public void testSimpleLink() {
        Link link = Link.fromUriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName")
                .param("myparam1", "myvalue1")
                .param("myparam2", "myvalue2")
                .build();
        JsonPath json = JsonPath.from(writeForTest(link));
        Assertions.assertThat(json.getString("href")).isEqualTo("http://localhost:8080/my/path");
        Assertions.assertThat(json.getString("params.myparam1")).isEqualTo("myvalue1");
        Assertions.assertThat(json.getString("params.myparam2")).isEqualTo("myvalue2");
        Assertions.assertThat(json.getString("params.rel")).isNull();
    }

    @Test
    public void testSimpleLinkNotParams() {
        Link link = Link.fromUriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName").build();
        JsonPath json = JsonPath.from(writeForTest(link));
        Assertions.assertThat(json.getString("href")).isEqualTo("http://localhost:8080/my/path");
        Assertions.assertThat(json.getString("params")).isNull();
    }

    @Test
    public void testEmptyEntity() {
        String text = writeForTest(EntityLinked.build());
        Map<String, Object> str = from(text).get();
        Assertions.assertThat(str).isEmpty();
    }

    @Test
    public void testEmptyEntityWithSimpleLink() {
        Link link = Link.fromUriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path")).rel("myOperationName").build();
        String text = writeForTest(EntityLinked.build(Arrays.asList(link)));
        Map<String, Object> str = from(text).get();
        Assertions.assertThat(str).containsKey("_links");
    }

    @Test
    public void testEmptyEntityWithEmbeddedLink() {
        Link link = new LinkEmbeddableBuilderImpl<HALTestOperationResult>()
                .embedded(true)
                .resolve(() -> new HALTestOperationResult(UUID.randomUUID(), "name"))
                .uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path"))
                .rel("myOperationName")
                .build();
        String text = writeForTest(EntityLinked.build(Arrays.asList(link)));
        Map<String, Object> str = from(text).get();
        Assertions.assertThat(str).containsKey("_embedded");
    }

    private String writeForTest(Object anything) {
        try {
            return configuredOm().writerWithDefaultPrettyPrinter().writeValueAsString(anything);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Configures an instance of Object mapper for tests
     */
    @NotNull
    private ObjectMapper configuredOm() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(JakartaWsRsHateoasModule.Instance);
        return om;
    }

    /**
     * Simple class for testing a bean
     */
    static class HALTestBean {
        private String title;
        private String content;

        HALTestBean(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }
    }

    static class HALTestOperationResult {
        private final UUID uid;
        private final String name;

        HALTestOperationResult(UUID uid, String name) {
            this.uid = uid;
            this.name = name;
        }

        public UUID getUid() {
            return uid;
        }

        public String getName() {
            return name;
        }
    }
}
