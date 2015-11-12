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

package org.fluentlenium.integration.localtest;

import org.fluentlenium.adapter.FluentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public abstract class LocalFluentCase extends FluentTest {
    public static final String BASE_URL = getPath();
    public static final String DEFAULT_URL = BASE_URL + "index.html";
    public static final String JAVASCRIPT_URL = BASE_URL + "javascript.html";

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(true);
    }

    public static String getPath() {
        String currentDir = System.getProperty("user.dir");
        if (!currentDir.endsWith("/fluentlenium-core")) {
            currentDir += "/fluentlenium-core";
        }
        String scheme = "file:/";
        if (currentDir.startsWith("/home")) {
            scheme = "file:";
        }
        return scheme + currentDir + "/src/test/html/";
    }
}

