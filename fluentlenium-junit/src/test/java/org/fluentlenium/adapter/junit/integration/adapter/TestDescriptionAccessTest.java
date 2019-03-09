package org.fluentlenium.adapter.junit.integration.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.adapter.junit.integration.IntegrationFluentTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestDescriptionAccessTest extends IntegrationFluentTest {

    private static final Class<?> TEST_CLASS = TestDescriptionAccessTest.class;
    private static final String TEST_METHOD = "shouldRetrieveNames";

    @Before
    public void setUp() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

    @Test
    public void shouldRetrieveNames() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

    @After
    public void cleanUp() {
        assertThat(getTestClass()).isEqualTo(TEST_CLASS);
        assertThat(getTestMethodName()).isEqualTo(TEST_METHOD);
    }

}