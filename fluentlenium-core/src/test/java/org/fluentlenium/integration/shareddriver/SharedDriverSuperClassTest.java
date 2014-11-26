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

package org.fluentlenium.integration.shareddriver;

import org.fluentlenium.adapter.util.SharedDriver;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Cookie;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@SharedDriver
class SharedDriverSuperClass extends LocalFluentCase {
}

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SharedDriverSuperClassTest extends SharedDriverSuperClass {
  @Test
  public void firstMethod() {
    goTo(LocalFluentCase.DEFAULT_URL);
    this.getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
    assertThat($(".small", withName("name"))).hasSize(1);
  }

  @Test
  public void secondMethod() {
    assertThat($(".small", withName("name"))).hasSize(1);
    assertThat(this.getCookie("cookie")).isNull();
  }


}
