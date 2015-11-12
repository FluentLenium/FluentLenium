/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.fluentlenium.integration;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import org.fluentlenium.integration.localtest.SauceLabsFluentCase;
import org.junit.Before;
import org.junit.Test;

public class FluentLeniumWaitOnLabsTest extends SauceLabsFluentCase {

    @Before
    public void before() {
        goTo(DEFAULT_URL);

    }

    @Test
    public void checkAwaitContainsNameWithNameMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").with("name").contains("name").isPresent();
    }

    @Test
    public void checkAwaitContainsIdWithIdMatcher() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").contains("id2").isPresent();
    }

    @Test
    public void checkAwaitStartWith() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").startsWith("id").hasSize(2);
    }

    @Test
    public void checkAwaitEndsWith() {

        await().atMost(1, NANOSECONDS).until(".small").with("id").endsWith("2").hasSize(1);
    }
}
