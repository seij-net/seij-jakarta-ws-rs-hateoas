package jakarta.ws.rs.ext.hateoas.jackson.serializers;

import io.restassured.path.json.JsonPath;
import jakarta.ws.rs.ext.hateoas.Links;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

import static jakarta.ws.rs.ext.hateoas.jackson.Fixtures.writeForTest;

class LinkSerializerTest {

    @Test
    public void testSimpleLink() {
        Link link = Links.fromUriBuilder("myOperationName", UriBuilder.fromUri("http://localhost:8080/my/path"))
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
        Link link = Links.fromUriBuilder("myOperationName", UriBuilder.fromUri("http://localhost:8080/my/path")).build();
        JsonPath json = JsonPath.from(writeForTest(link));
        Assertions.assertThat(json.getString("href")).isEqualTo("http://localhost:8080/my/path");
        Assertions.assertThat(json.getString("params")).isNull();
    }

}