package org.fluentlenium.test.page.isat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IsAtTest extends IntegrationFluentTest {
    @Page
    private PageIsAt pageOk;

    @Page
    private PageIsAtParameter pageOkParameter;

    @Page
    private PageIsAtMultipleParameters pageIsAtMultipleParameters;

    @Page
    private PageIsNotAt pageFail;

    @Test
    void testIsNotAt() {
        pageFail.go();

        assertThatThrownBy(() -> pageFail.isAt()).isInstanceOf(AssertionError.class);
    }

    @Test
    void testIsAt() {
        pageOk.go();
        pageOk.isAt();
    }

    @Test
    void testIsAtParametersPositive() {
        String parameter = "html";
        pageOkParameter.go(parameter);
        pageOkParameter.isAt(parameter);
    }

    @Test
    void testIsAtParametersNegative() {
        String parameter = "html";
        pageOkParameter.go(parameter);
        pageOk.go();
        assertThatThrownBy(() -> pageOkParameter.isAt(parameter))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void testIsAtParametersQuantifiersPositive() {
        String parameter = "+X2H2KPV_1a=FX2?U-8GJL-RSVA-VRIT*-EA1U#";
        pageOkParameter.go(parameter);
        pageOkParameter.isAt(parameter);
    }

    @Test
    void testIsAtParametersQuantifiersNegative() {
        String parameter = "?X2H2KPV_1a=FX2U-8GJL-RSVA-VRIT-EA1U#";
        pageOkParameter.go(parameter);
        pageOk.go();

        assertThatThrownBy(() -> pageOkParameter.isAt(parameter))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void testIsAtMultipleParametersPositive() {
        pageIsAtMultipleParameters.go("?X2H2KPV_1a=FX2U-8GJL-RSVA-VRIT-EA1U#", "String", "2");
        pageIsAtMultipleParameters.isAt("?X2H2KPV_1a=FX2U-8GJL-RSVA-VRIT-EA1U#", "String", "2");
    }

    @FindBy(css = "#oneline")
    public static class PageIsAt extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
        }
    }

    @FindBy(css = "#oneline")
    public static class PageIsAtParameter extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL.replace("-tests", "{extension}");
        }
    }

    @FindBy(css = "#oneline")
    public static class PageIsAtMultipleParameters extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL
                    .replace("-tests", "{?/extension1}")
                    .replace("target", "{extension2}")
                    .replace("index", "{extension3}");
        }
    }

    @FindBy(css = "#invalid")
    public static class PageIsNotAt extends FluentPage {
        @Override
        public String getUrl() {
            return IntegrationFluentTest.DEFAULT_URL;
        }
    }

}
