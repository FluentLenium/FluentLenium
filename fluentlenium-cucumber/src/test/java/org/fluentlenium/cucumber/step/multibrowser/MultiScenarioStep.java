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
package org.fluentlenium.cucumber.step.multibrowser;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.cucumber.adapter.FluentCucumberAdapter;
import org.fluentlenium.cucumber.page.LocalPage;

public class MultiScenarioStep extends FluentCucumberAdapter {

    @Page
    LocalPage page;

    @Page
    LocalPage page2;

    private MultiScenarioInitialStep initialStep;

    public MultiScenarioStep(MultiScenarioInitialStep initialStep) {
        this.initialStep = initialStep;
    }

    @Given(value = "multiscenario I am on the first page")
    public void step1() {
        this.initFluentWithWebDriver(initialStep);
        this.initTest();

        goTo(page);
//        assertThat($(".small", withName("name"))).hasSize(1);
    }

    @When(value = "multiscenario I click on next page")
    public void step2() {
        this.initFluentWithWebDriver(initialStep);
        this.initTest();

        click(find("a"));
    }

    @Then(value = "multiscenario I am on the second page")
    public void step3() {
        this.initFluentWithWebDriver(initialStep);
        this.initTest();

        page2.isAt();
    }

    @After
    public void after() {
        this.quit();
    }
}
