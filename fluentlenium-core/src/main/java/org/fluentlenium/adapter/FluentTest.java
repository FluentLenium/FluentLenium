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

package org.fluentlenium.adapter;

import org.fluentlenium.adapter.util.SharedBrowser;
import org.fluentlenium.adapter.util.ShutdownHook;
import org.fluentlenium.core.FluentAdapter;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.FluentThread;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.openqa.selenium.Beta;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends FluentAdapter {
  protected enum Mode {TAKE_SNAPSHOT_ON_FAIL, NEVER_TAKE_SNAPSHOT;}


  private static WebDriver browser;

  private Mode snapshotMode = Mode.NEVER_TAKE_SNAPSHOT;
  private String snapshotPath;
  public Class classe = this.getClass();

  public void setSnapshotPath(String path) {
    this.snapshotPath = path;
  }


  public void setSnapshotMode(Mode mode) {
    this.snapshotMode = mode;
  }

  @Rule
  public TestName name = new TestName();
  @Rule
  public MethodRule watchman = new TestWatchman() {

    @Override
    public void starting(FrameworkMethod method) {
      super.starting(method);

      if (isSharedBrowser(method)) {
        synchronized (this) {
          if (browser == null) {
            initFluentFromDefaultDriver();
            browser = FluentThread.get().getDriver();
            killTheBrowserOnShutdown();
          } else {
            initFluentWithExistingBrowser();
          }
        }
      } else {
        initFluentFromDefaultDriver();
      }
      initTest();
      setDefaultConfig();
    }

    private SharedBrowser getSharedBrowser(final FrameworkMethod method) {
      Class<?> cls;
      for (cls = method.getMethod()
          .getDeclaringClass(); FluentTest.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
        if (cls.isAnnotationPresent(SharedBrowser.class)) {
          return cls.getAnnotation(SharedBrowser.class);
        }
      }
      return null;
    }

    private boolean isSharedBrowser(final FrameworkMethod method) {
      return (getSharedBrowser(method) != null);
    }


    private boolean isDeleteCookies(final FrameworkMethod method) {
      SharedBrowser sharedBrowser = getSharedBrowser(method);
      return (sharedBrowser != null && sharedBrowser.deleteCookies());
    }

    @Override
    public void finished(FrameworkMethod method) {
      super.finished(method);
      if (!isSharedBrowser(method)) {
        quit();
      } else if (isDeleteCookies(method)) {
        browser.manage().deleteAllCookies();
      }
    }

    @Override
    public void failed(Throwable e, FrameworkMethod method) {
      if (snapshotMode == Mode.TAKE_SNAPSHOT_ON_FAIL) {
        takeScreenShot(snapshotPath + "/" + classe.getSimpleName() + "_" + method.getName() + ".png");
      }
    }

  };

  private void killTheBrowserOnShutdown() {
    Runtime.getRuntime().addShutdownHook(new ShutdownHook("fluentlenium", this));
  }

  private void initFluentWithExistingBrowser() {initFluent(browser).withDefaultUrl(getDefaultBaseUrl());}

  private void initFluentFromDefaultDriver() {initFluent(getDefaultDriver()).withDefaultUrl(getDefaultBaseUrl());}


  public FluentTest() {
    super();
  }

  /**
   * Override this method to change the driver
   *
   * @return
   */
  public WebDriver getDefaultDriver() {
    return new FirefoxDriver();
  }

  /**
   * Override this method to change the default time to wait for a page to be loaded
   */
  @Beta
  public void setDefaultConfig() {
  }

  public static void assertAt(FluentPage fluent) {
    fluent.isAt();
  }


}
