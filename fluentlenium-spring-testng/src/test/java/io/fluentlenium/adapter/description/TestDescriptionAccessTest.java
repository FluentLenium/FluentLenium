package io.fluentlenium.adapter.description;

import io.fluentlenium.IntegrationFluentTestNg;import io.fluentlenium.IntegrationFluentTestNg;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestDescriptionAccessTest extends IntegrationFluentTestNg {

    private static final String TEST_METHOD = "shouldRetrieveNames";
    private static final Class<?> TEST_CLASS = TestDescriptionAccessTest.class;

    @BeforeMethod
    public void setUp() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

    @Test
    public void shouldRetrieveNames() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

    @AfterMethod
    public void cleanUp() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

}
