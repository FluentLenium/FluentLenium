/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.unit.initialization;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.adapter.FluentTest;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class ConstructorInitialization extends FluentTest {
    public WebDriver webDriver = new HtmlUnitDriver();


    @Test
    public void do_not_use_overridable_methods_in_a_constructor() {
        assertThat(webDriver).isEqualTo(this.getDriver());
    }


    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }
}
