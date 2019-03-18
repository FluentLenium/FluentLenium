package org.fluentlenium.adapter.testng.integration.adapter.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import org.testng.annotations.Test;

@CustomAnnotation("AwesomeTestClass")
public class AnnotationTest extends IntegrationFluentTestNg {

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
