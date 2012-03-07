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

import java.util.List;

import org.fest.assertions.GenericAssert;
import org.fluentlenium.core.domain.FluentList;

public class FluentListAssert extends GenericAssert<FluentListAssert, FluentList> {
    public FluentListAssert(FluentList<?> actual) {
        super(FluentListAssert.class, actual);
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
        super.fail("No selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
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
                super.fail("At least one selected elements contains text: " + textToFind + " . Actual texts found : " + actualTexts);
            }
        }
        return this;
    }

	public FluentListAssert hasSize(int expectedSize) {
		if(actual.size() != expectedSize) {
			super.fail("Expected size: " + expectedSize + ". Actual size: " + actual.size() + ".");
		}
		return this;
	}
	
	public FluentListSizeBuilder hasSize() {
		return new FluentListSizeBuilder(actual.size(), this);
	}
	
	/*
	 * Used in FluentListSizeBuilder to raise AssertionError
	 */
	void internalFail(String reason) {
		super.fail(reason);
	}
	
}
