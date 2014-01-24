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

package org.fest.assertions.fluentlenium.custom;

import org.fest.assertions.GenericAssert;
import org.fluentlenium.core.domain.FluentWebElement;

public class FluentWebElementAssert extends GenericAssert<FluentWebElementAssert, FluentWebElement> {


    public FluentWebElementAssert(FluentWebElement actual) {
        super(FluentWebElementAssert.class, actual);
    }


    private void failIsEnabled() {
        super.fail("Object not enabled");
    }

    private void failIsNotEnabled() {
        super.fail("Object is enabled");
    }

    /**
     * check if the element is enabled
     *
     * @return
     */
    public FluentWebElementAssert isEnabled() {
        if (!actual.isEnabled()) {
            failIsEnabled();
        }
        return this;
    }

    /**
     * check if the element is not enabled
     *
     * @return
     */
    public FluentWebElementAssert isNotEnabled() {
        if (actual.isEnabled()) {
            failIsNotEnabled();
        }
        return this;
    }

    /**
     * check if the element is displayed
     *
     * @return
     */
    public FluentWebElementAssert isDisplayed() {
        if (!actual.isDisplayed()) {
            failIsNotDisplayed();
        }

        return this;
    }

    /**
     * check if the element is not displayed
     *
     * @return
     */
    public FluentWebElementAssert isNotDisplayed() {
        if (actual.isDisplayed()) {
            failIsDisplayed();
        }
        return this;
    }

    private void failIsDisplayed() {
        super.fail("Object not displayed");
    }

    private void failIsNotDisplayed() {
        super.fail("Object is displayed");
    }

    /**
     * check if the element is selected
     *
     * @return
     */
    public FluentWebElementAssert isSelected() {
        if (!actual.isSelected()) {
            failIsSelected();
        }
        return this;
    }

    /**
     * check if the element is not selected
     *
     * @return
     */
    public FluentWebElementAssert isNotSelected() {
        if (actual.isSelected()) {
            failIsNotSelected();
        }
        return this;
    }

    /**
     * check if the element contains the text
     *
     * @return
     */
    public FluentWebElementAssert hasText(String textToFind) {
        if(!actual.getText().contains(textToFind)){
            super.fail("The element does not contain the text: " + textToFind+" . Actual text found : " + actual.getText());

        }

       return this;
    }

   /**
     * check if the element does not contain the text
     *
     * @return
     */
    public FluentWebElementAssert hasNotText(String textToFind) {
        if(actual.getText().contains(textToFind)){
            super.fail("The element contain the text: " + textToFind);
        }

       return this;
    }

    private void failIsSelected() {
        super.fail("Object not selected");
    }

    private void failIsNotSelected() {
        super.fail("Object is selected");
    }

    /**
     * check if the element has the given id
     *
	 * @param id to check
     * @return
     */
	public FluentWebElementAssert hasId(String id) {
		if(!actual.getId().equals(id)) {
			super.fail("The element does not have the id: " + id + " . Actual id found : " + actual.getId());
		}
		return this;
	}

    /**
     * check if the element has the class
     *
	 * @param classToFind
     * @return
     */
    public FluentWebElementAssert hasClass(String classToFind) {
        if(!actual.getAttribute("class").contains(classToFind)){
            super.fail("The element does not have the class: " + classToFind + " . Actual class found : " + actual.getAttribute("class"));
        }

       return this;
    }
}
