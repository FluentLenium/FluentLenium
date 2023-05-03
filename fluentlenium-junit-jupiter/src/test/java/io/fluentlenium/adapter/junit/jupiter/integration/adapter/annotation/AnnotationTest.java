package io.fluentlenium.adapter.junit.jupiter.integration.adapter.annotation;

import io.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@CustomAnnotation("AwesomeTestClass")
class AnnotationTest extends IntegrationFluentTest {

    @Test
    void shouldBeAbleToAccesClassAnnotationViaFluentAdapter() {
        CustomAnnotation classAnnotation = getClassAnnotation(CustomAnnotation.class);
        assertThat(classAnnotation).isNotNull();
        assertThat(classAnnotation.value()).isEqualTo("AwesomeTestClass");
    }

    @CustomAnnotation("AwesomeTestMethod")
    @Test
    void shouldBeAbleToAccessMethodAnnotationViaFluentAdapter() {
        CustomAnnotation methodAnnotation = getMethodAnnotation(CustomAnnotation.class);
        assertThat(methodAnnotation).isNotNull();
        assertThat(methodAnnotation.value()).isEqualTo("AwesomeTestMethod");
    }

}
