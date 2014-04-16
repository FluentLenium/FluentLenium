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


public class AttributeAssert extends GenericAssert<AttributeAssert, String> {

    public AttributeAssert(String actual) {
        super(AttributeAssert.class, actual);
    }

    public AttributeAssert withValue(String expectedValue) {
        if (!actual.equals(expectedValue)) {
            super.fail("The attribute does not have the value: " + expectedValue + ". Actual value is : " + actual);
        }
        return this;
    }
}
