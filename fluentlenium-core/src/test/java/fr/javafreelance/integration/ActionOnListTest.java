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

package fr.javafreelance.integration;

import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ActionOnListTest extends LocalFluentCase {

    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        System.out.println(this.pageSource());
        $("input").text("zzz");
        assertThat($("input").getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        $("#name").clear();
        assertThat($("#name").getValues()).contains("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").click();
        assertThat(title()).isEqualTo("Page 2");
    }


    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").getTexts()).contains("Small 1", "Small 2", "Small 3");
    }
}
