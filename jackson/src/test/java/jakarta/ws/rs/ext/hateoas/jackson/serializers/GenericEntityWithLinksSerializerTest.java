package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import io.restassured.path.json.JsonPath;
import jakarta.ws.rs.ext.hateoas.GenericEntityWithLinks;
import jakarta.ws.rs.ext.hateoas.Links;
import jakarta.ws.rs.ext.hateoas.jackson.HALTestOperationResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static io.restassured.path.json.JsonPath.from;
import static jakarta.ws.rs.ext.hateoas.jackson.Fixtures.writeForTest;

class GenericEntityWithLinksSerializerTest {
    @Test
    public void testHalNoLinks() {
        HALTestBean bean = new HALTestBean("title_a", "content_a");
        GenericEntityWithLinks<HALTestBean> content = GenericEntityWithLinks.build(bean, Collections.emptyList());
        String result = writeForTest(content);
        Assertions.assertThat(JsonPath.from(result).getString("title")).isEqualTo("title_a");
        Assertions.assertThat(JsonPath.from(result).getString("content")).isEqualTo("content_a");
    }

    @Test
    public void testHalStandardLinks() {
        HALTestBean bean = new HALTestBean("title_a", "content_a");
        Link simpleLink1 = Links.fromUriBuilder("myOperationName1", UriBuilder.fromUri("http://localhost:8080/my/path"))
                .param("myparam1_1", "myvalue1_1")
                .param("myparam1_2", "myvalue1_2")
                .build();
        Link simpleLink2 = Links.fromUriBuilder("myOperationName2", UriBuilder.fromUri("http://localhost:8080/my/path"))
                .param("myparam2_1", "myvalue2_1")
                .param("myparam2_2", "myvalue2_2")
                .build();
        Link simpleLink3 = Links.fromUriBuilder("myOperationName3", UriBuilder.fromUri("http://localhost:8080/my/path"))
                .build();
        GenericEntityWithLinks<HALTestBean> content = GenericEntityWithLinks.build(bean, Arrays.asList(simpleLink1, simpleLink2, simpleLink3));
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
        Link opNotResolved = Links.builder("myOperationName1")
                .uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path"))
                .param("myparam1_1", "myvalue1_1")
                .param("myparam1_2", "myvalue1_2")
                .build();
        UUID uid1 = UUID.randomUUID();
        Link opResolved = Links.builder("myOperationName2", () -> new HALTestOperationResult(uid1, "operation result 2"))
                .uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path"))
                .build();
        Link opResolvedAsNull = Links.builder("myOperationName3", () -> null)
                .uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path"))
                .build();
        Link opResolvedAsList = Links.builder("myOperationName4", () -> Arrays.asList(new HALTestOperationResult(UUID.randomUUID(), "operation result 4 1"), new HALTestOperationResult(UUID.randomUUID(), "operation result 4 2")))
                .uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path"))
                .build();
        GenericEntityWithLinks<HALTestBean> content = GenericEntityWithLinks.build(bean, Arrays.asList(opNotResolved, opResolved, opResolvedAsNull, opResolvedAsList));
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
    void testEmptyEntity() {
        String text = writeForTest(GenericEntityWithLinks.buildEmpty());
        Map<String, Object> str = from(text).get();
        Assertions.assertThat(str).isEmpty();
    }


    @Test
    void testEmptyEntityWithSimpleLink() {
        Link link = Links.fromUriBuilder("myOperationName", UriBuilder.fromUri("http://localhost:8080/my/path")).build();
        String text = writeForTest(GenericEntityWithLinks.buildEmpty(Arrays.asList(link)));
        Map<String, Object> str = from(text).get();
        Assertions.assertThat(str).containsKey("_links");
    }

    @Test
    void testEmptyEntityWithEmbeddedLink() {
        Link link = Links.builder("myOperationName", () -> new HALTestOperationResult(UUID.randomUUID(), "name"))
                .uriBuilder(UriBuilder.fromUri("http://localhost:8080/my/path"))
                .build();
        String text = writeForTest(GenericEntityWithLinks.buildEmpty(Arrays.asList(link)));
        Map<String, Object> str = from(text).get();
        Assertions.assertThat(str).containsKey("_embedded");
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

}