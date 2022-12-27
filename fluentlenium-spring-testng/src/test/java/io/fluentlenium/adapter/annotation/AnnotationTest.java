package io.fluentlenium.adapter.annotation;

import io.fluentlenium.IntegrationFluentTestNg;
import io.fluentlenium.IntegrationFluentTestNg;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
