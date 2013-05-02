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
package org.fluentlenium.cucumber.step.monobrowser;

import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberAdapter;
import org.fluentlenium.cucumber.adapter.util.SharedDriver;
import org.fluentlenium.cucumber.page.LocalPage;

import static org.fest.assertions.Assertions.assertThat;

@SharedDriver(type = SharedDriver.SharedType.PER_SCENARIO)
public class SimpleScenarioMultiStep2 extends FluentCucumberAdapter {

    @Page
    LocalPage page;

    @Page
    LocalPage page2;

    @When(value = "scenario multi2 I click on next page")
    public void step2() {
//        this.initFluent(SimpleScenarioMultiStep2.class);
        this.initFluent();
        this.initTest();

		click(find("a"));
    }

    @Then(value = "scenario multi2 I am on the second page")
    public void step3() {
//        this.initFluent(SimpleScenarioMultiStep2.class);
        this.initFluent();
        this.initTest();

		page2.isAt();
    }

    @After
    public void after() {
		this.quit();
    }

}
