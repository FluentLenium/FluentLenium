package org.fluentlenium.adapter.cucumber.unit;

import org.fluentlenium.adapter.cucumber.FluentCucumberTest;
import org.fluentlenium.adapter.cucumber.FluentObjectFactory;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FluentCucumberObjectFactoryTest {

    private FluentObjectFactory objectFactory;
    private FluentCucumberTest fluentCucumberTest;

    @Page
    private TestPage page;

    @Before
    public void before(){
        fluentCucumberTest = mock(FluentCucumberTest.class);
        objectFactory = new FluentObjectFactory(fluentCucumberTest);
    }

    @Test
    public void createInstanceTest() {
        when(fluentCucumberTest.newInstance(TestPage.class)).thenReturn(new TestPage());
        TestPage page = objectFactory.getInstance(TestPage.class);

        assertThat(page).isInstanceOf(TestPage.class);
    }


    private class TestPage extends FluentPage {
    }
}
