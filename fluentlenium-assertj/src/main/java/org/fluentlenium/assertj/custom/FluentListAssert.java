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

package org.fluentlenium.assertj.custom;

import java.util.List;

import org.assertj.core.api.AbstractAssert;
import org.fluentlenium.core.domain.FluentList;

public class FluentListAssert extends AbstractAssert<FluentListAssert, FluentList> {
    public FluentListAssert(FluentList<?> actual) {
        super(actual, FluentListAssert.class);
    }

   /**
     * check if at least one element of the FluentList contains the text
     *
     * @return
     */
    public FluentListAssert hasText(String textToFind) {
        List<String> actualTexts = actual.getTexts();
        for(String text : actualTexts) {
            if(text.contains(textToFind)){
                return this;
            }
        }
        super.failWithMessage("No selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
        return this;
    }

   /**
     * check if at no element of the FluentList contains the text
     *
     * @return
     */
    public FluentListAssert hasNotText(String textToFind) {
        List<String> actualTexts = actual.getTexts();
        for(String text : actualTexts) {
            if(text.contains(textToFind)){
                super.failWithMessage("At least one selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
            }
        }
        return this;
    }

	public FluentListAssert hasSize(int expectedSize) {
		if(actual.size() != expectedSize) {
			super.failWithMessage("Expected size: " + expectedSize + ". Actual size: " + actual.size() + ".");
		}
		return this;
	}
	
	public FluentListSizeBuilder hasSize() {
		return new FluentListSizeBuilder(actual.size(), this);
	}
	
   /**
     * check if an element of the FluentList has the id
     *
	 * @param idToFind
     * @return
     */
	public FluentListAssert hasId(String idToFind) {
		List actualIds = actual.getIds();
		if (!actualIds.contains(idToFind)) {
			super.failWithMessage("No selected elements has id: " + idToFind + " . Actual texts found : " + actualIds);
		}
		return this;
	}

   /**
     * check if at least one element of the FluentList contains the text
     *
     * @return
     */
    public FluentListAssert hasClass(String classToFind) {
        List<String> actualClasses = actual.getAttributes("class");
        if (!actualClasses.contains(classToFind)) {
			super.failWithMessage("No selected elements has class: " + classToFind + " . Actual classes found : " + actualClasses);
        }
        return this;
    }

	/*
	 * Used in FluentListSizeBuilder to raise AssertionError
	 */
	void internalFail(String reason) {
		super.failWithMessage(reason);
	}

}
