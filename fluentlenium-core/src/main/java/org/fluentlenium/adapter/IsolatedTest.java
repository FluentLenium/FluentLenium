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

package org.fluentlenium.adapter;

import org.fluentlenium.core.FluentAdapter;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;

@Beta
public class IsolatedTest extends FluentAdapter {


    public IsolatedTest() {
        initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());
        initTest();
    }

    public IsolatedTest(WebDriver webDriver) {
        initFluent(webDriver).withDefaultUrl(getDefaultBaseUrl());
        initTest();
    }

    public void quit() {
        if (getDriver() != null) {
            super.quit();
        }
    }


}
