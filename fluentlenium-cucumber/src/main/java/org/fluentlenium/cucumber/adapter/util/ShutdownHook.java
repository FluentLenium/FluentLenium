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
package org.fluentlenium.cucumber.adapter.util;


import org.fluentlenium.cucumber.adapter.FluentCucumberAdapter;
import org.fluentlenium.cucumber.adapter.driver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author : Mathilde Lemee
 */
public class ShutdownHook extends Thread {
    private final FluentCucumberAdapter adapter;

    public ShutdownHook(final String s, final FluentCucumberAdapter adapter) {
        super(s);
        this.adapter = adapter;
    }

    @Override
    public synchronized void start() {
        adapter.forceQuit();
		if (!WebDriverFactory.webDriverInstances.isEmpty()) {

			for (WebDriver webDriver : WebDriverFactory.webDriverInstances.values()) {
				if (webDriver != null) {
					webDriver.close();
				}
			}
		}
    }
}
