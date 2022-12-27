package io.fluentlenium.adapter.junit.integration.adapter.annotation;

import io.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

@CustomAnnotation("AwesomeTestClass")
public class AnnotationTest extends IntegrationFluentTest {

    @Test
    public void shouldBeAbleToAccesClassAnnotationViaFluentAdapter() {
        CustomAnnotation classAnnotation = getClassAnnotation(CustomAnnotation.class);
        Assertions.assertThat(classAnnotation).isNotNull();
        Assertions.assertThat(classAnnotation.value()).isEqualTo("AwesomeTestClass");
    }

    @CustomAnnotation("AwesomeTestMethod")
    @Test
    public void shouldBeAbleToAccessMethodAnnotationViaFluentAdapter() {
        CustomAnnotation methodAnnotation = getMethodAnnotation(CustomAnnotation.class);
        Assertions.assertThat(methodAnnotation).isNotNull();
        Assertions.assertThat(methodAnnotation.value()).isEqualTo("AwesomeTestMethod");
    }

}
