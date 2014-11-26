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

package org.fluentlenium.integration;

import com.google.common.base.Predicate;
import org.fluentlenium.core.Fluent;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AwaitWithStaleElementReferenceException extends LocalFluentCase {
    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test(expected = TimeoutException.class)
    public void given_default_when_state_element_reference_exception_is_thrown_then_it_is_ignored() {
        await().atMost(3, SECONDS).until(new Predicate<Fluent>() {
            @Override
            public boolean apply(Fluent fluent) {
                find(".small").clear();
                throw new StaleElementReferenceException("test");
            }
        });
    }


    @Test(expected = StaleElementReferenceException.class)
        public void given_no_defaults_when_state_element_reference_exception_is_thrown_then_it_is_not_ignored() {
            await().atMost(3, SECONDS).withNoDefaultsException().until(new Predicate<Fluent>() {
                @Override
                public boolean apply(Fluent fluent) {
                    find(".small").clear();
                    throw new StaleElementReferenceException("test");
                }
            });
        }

}
