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

package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

//TODO : Problem here - 1 instance by test when 1 instance for test suite is sufficient ...
public abstract class LocalFluentCase extends FluentTest {
    public static final String DEFAULT_URL = "http://localhost:8787/static/";
    public static final String JAVASCRIPT_URL = "http://localhost:8787/static/javascript.html";

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }
}

