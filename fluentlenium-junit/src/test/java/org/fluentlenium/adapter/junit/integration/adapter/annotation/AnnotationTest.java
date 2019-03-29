package org.fluentlenium.adapter.junit.integration.adapter.annotation;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.Test;

@CustomAnnotation("AwesomeTestClass")
public class AnnotationTest extends IntegrationFluentTest {

    @Test
    public void shouldBeAbleToAccesClassAnnotationViaFluentAdapter() {
        CustomAnnotation classAnnotation = getClassAnnotation(CustomAnnotation.class);
        assertThat(classAnnotation).isNotNull();
        assertThat(classAnnotation.value()).isEqualTo("AwesomeTestClass");
    }

    @CustomAnnotation("AwesomeTestMethod")
    @Test
    public void shouldBeAbleToAccessMethodAnnotationViaFluentAdapter() {
        CustomAnnotation methodAnnotation = getMethodAnnotation(CustomAnnotation.class);
        assertThat(methodAnnotation).isNotNull();
        assertThat(methodAnnotation.value()).isEqualTo("AwesomeTestMethod");
    }

}
