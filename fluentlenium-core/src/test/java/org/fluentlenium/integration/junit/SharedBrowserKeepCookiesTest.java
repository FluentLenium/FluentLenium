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

package org.fluentlenium.integration.junit;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.adapter.util.SharedBrowser;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.Cookie;

import java.lang.reflect.Field;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withName;

@SharedBrowser(deleteCookies = false)
public class SharedBrowserKeepCookiesTest extends FluentTest {

  @Test
  public void firstMethod() {
    goTo(LocalFluentCase.DEFAULT_URL);
    assertThat($(".small", withName("name"))).hasSize(1);
    this.getDriver().manage().addCookie(new Cookie("cookie", "fluent"));
  }


  @Test
  public void secondMethod() {
    assertThat($(".small", withName("name"))).hasSize(1);
    assertThat(this.getCookie("cookie").getValue()).isEqualTo("fluent");
  }


  @Test
  public void when_once_mode_then_shutdown_hook() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
    Class clazz = Class.forName("java.lang.ApplicationShutdownHooks");
    Field field = clazz.getDeclaredField("hooks");
    field.setAccessible(true);
    Object hooks = field.get(null);
    System.out.println(hooks); //hooks is a Map<Thread, Thread>

  }


}
