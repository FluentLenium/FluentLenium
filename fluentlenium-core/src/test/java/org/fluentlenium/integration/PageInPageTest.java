/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fluentlenium.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.exception.ConstructionException;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 */
public class PageInPageTest extends LocalFluentCase {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Page
    private TestPage testPage;
    @Page
    private SubSubTestPage subTestPage;

    @Test
    public void pages_should_be_injected() {
        assertThat(testPage).isNotNull();
        assertThat(testPage).isInstanceOf(TestPage.class);
        assertThat(testPage.includedPage).isNotNull();
        assertThat(testPage.includedPage).isInstanceOf(IncludedPage.class);
        assertThat(testPage.includedPage.element).isNotNull();
        assertThat(subTestPage).isNotNull();
        assertThat(subTestPage).isInstanceOf(SubTestPage.class);
        assertThat(subTestPage.includedPage).isNotNull();
        assertThat(subTestPage.anotherIncludedPage).isNotNull();

        SubTestPageWithParameter subTestPageWithParameter = createPage(SubTestPageWithParameter.class, "buttonId");
        assertThat(subTestPageWithParameter).isNotNull();
        assertThat(subTestPageWithParameter).isInstanceOf(SubTestPageWithParameter.class);
        assertThat(subTestPageWithParameter.buttonId).isEqualTo("buttonId");
    }

    @Test
    public void pages_should_throw_an_exception_when_constructor_with_params_not_found() {
        expectedException.expect(ConstructionException.class);
        expectedException.expectMessage("You provided the wrong arguments to the createPage method, if you just want to use a page with a default constructor, use @Page or createPage(SubTestPageWithParameter.class)");
        createPage(SubTestPageWithParameter.class, "buttonId", "unkownConstructorField");
    }
}

class TestPage extends FluentPage {
    @Page
    IncludedPage includedPage;
}

class SubSubTestPage extends SubTestPage {
}

class SubTestPage extends TestPage {
    @Page
    IncludedPage anotherIncludedPage;
}

class SubTestPageWithParameter extends TestPage {
    final String buttonId;

    public SubTestPageWithParameter(String buttonId) {
        this.buttonId = buttonId;
    }
}

class SubTestPageWithCreate extends FluentPage {
    public IncludedPage pageWithCreatePage;

    SubTestPageWithCreate() {
        pageWithCreatePage = createPage(IncludedPage.class);
    }

}

class IncludedPage extends FluentPage {
    FluentWebElement element;
}