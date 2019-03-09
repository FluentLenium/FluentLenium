package org.fluentlenium.adapter.junit.jupiter.integration.adapter;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

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
