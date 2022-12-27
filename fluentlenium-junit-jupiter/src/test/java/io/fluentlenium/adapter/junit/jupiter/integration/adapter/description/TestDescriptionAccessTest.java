package io.fluentlenium.adapter.junit.jupiter.integration.adapter.description;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import io.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;
import io.fluentlenium.adapter.junit.jupiter.integration.IntegrationFluentTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDescriptionAccessTest extends IntegrationFluentTest {

    private static final String TEST_METHOD = "shouldRetrieveNames";
    private static final Class<?> TEST_CLASS = TestDescriptionAccessTest.class;

    @BeforeEach
    void setUp() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

    @Test
    void shouldRetrieveNames() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

    @AfterEach
    void cleanUp() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

}
