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

package org.fluentlenium.unit.initialization;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AnnotationInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();

    @Page
    public TestExternalPage page2;

    @Page
    public TestPrivatePage page;

    @Test
    public void test_no_exception() {
        page2.go();
    }

    @Test
    public void test_no_exception_when_inner_class() {
        page2.go();
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

}

class TestPrivatePage extends FluentPage {

    @Override
    public String getUrl() {
        return "http://www.google.fr";
    }
}
