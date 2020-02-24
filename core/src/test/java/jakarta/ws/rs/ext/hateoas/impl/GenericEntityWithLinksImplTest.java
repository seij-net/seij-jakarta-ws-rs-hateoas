package jakarta.ws.rs.ext.hateoas.impl;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.GenericEntity;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GenericEntityWithLinksImplTest {
    static class TestModel {
        private final String name;

        TestModel(String name) {
            this.name = name;
        }
    }

    @Test
    void given_basicType_when_built_typeIsRetained() {
        GenericEntityWithLinksImpl<TestModel> entity = new GenericEntityWithLinksImpl<>(new TestModel("name"), Collections.emptyList());
        assertThat(entity.getType()).isEqualTo(TestModel.class);
    }

    @Test
    void given_listType_when_built_typeIsRetained() {
        // First test is to ensure we write test correctly with Generic Entity (standard JaxRS)
        GenericEntity<List<TestModel>> entity1 = new GenericEntity<List<TestModel>>(
                Collections.singletonList(new TestModel("name"))) {
        };
        assertThat(GenericEntitySupport.getEnclosedType(entity1)).isEqualTo(TestModel.class);

        // Now we can test that we didn't broke inheritance with our own type. We must have same result
        GenericEntityWithLinksImpl<List<TestModel>> entity = new GenericEntityWithLinksImpl<List<TestModel>>(
                Collections.singletonList(new TestModel("name")),
                Collections.emptyList()) {
        };
        assertThat(GenericEntitySupport.isEntityInstanceOf(entity, List.class)).isTrue();
        assertThat(GenericEntitySupport.getEnclosedType(entity)).isEqualTo(TestModel.class);
    }
}