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

package org.fest.assertions.fluentlenium;

import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;
import org.fest.assertions.GenericAssert;

public class FluentWebElementAssert extends GenericAssert<FluentWebElementAssert, FluentWebElement> {


    /**
     * Creates a new <code>{@link org.fest.assertions.GenericAssert}</code>.
     *
     * @param actual the actual value to verify.
     */
    protected FluentWebElementAssert(FluentWebElement actual) {
        super(FluentWebElementAssert.class, actual);
    }

    public static FluentWebElementAssert assertThat(FluentWebElement actual) {
        return new FluentWebElementAssert(actual);
    }


    private void failIsEnabled() {
        super.fail("Object not enabled");
    }

    private void failIsNotEnabled() {
        super.fail("Object is enabled");
    }


    public FluentWebElementAssert isEnabled() {
        if (!actual.isEnabled()) failIsEnabled();
        return this;
    }

    public FluentWebElementAssert isNotEnabled() {
        if (actual.isEnabled()) failIsNotEnabled();
        return this;
    }

    public FluentWebElementAssert isDisplayed() {
        if (!actual.isDisplayed()) failIsNotDisplayed();

        return this;
    }

    public FluentWebElementAssert isNotDisplayed() {
        if (actual.isDisplayed()) failIsDisplayed();
        return this;
    }

    private void failIsDisplayed() {
        super.fail("Object not displayed");
    }

    private void failIsNotDisplayed() {
        super.fail("Object is displayed");
    }

    public FluentWebElementAssert isSelected() {
        if (!actual.isSelected()) failIsSelected();
        return this;
    }

    public FluentWebElementAssert isNotSelected() {
        if (actual.isSelected()) failIsNotSelected();
        return this;
    }

    private void failIsSelected() {
        super.fail("Object not selected");
    }

    private void failIsNotSelected() {
        super.fail("Object is selected");
    }

}
