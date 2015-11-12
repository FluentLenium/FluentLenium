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

import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

/**
 *
 */
public class PageInPageWithAnnotationPageTest extends LocalFluentCase {

    @Page
    private SubSubTestPage subTestPage;

    @Test
    public void pages_should_be_injected() {
        TestPage testPage = createPage(SubSubTestPage.class);
        assertThat(testPage).isNotNull();
        assertThat(testPage).isInstanceOf(TestPage.class);
        assertThat(testPage.includedPage).isNotNull();
        assertThat(testPage.includedPage).isInstanceOf(IncludedPage.class);
        assertThat(testPage.includedPage.element).isNotNull();
        assertThat(subTestPage).isNotNull();
        assertThat(subTestPage).isInstanceOf(SubTestPage.class);
        assertThat(subTestPage.includedPage).isNotNull();
        assertThat(subTestPage.anotherIncludedPage).isNotNull();
    }
}
