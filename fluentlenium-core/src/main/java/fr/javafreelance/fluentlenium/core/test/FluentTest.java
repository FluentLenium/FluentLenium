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

package fr.javafreelance.fluentlenium.core.test;

import fr.javafreelance.fluentlenium.core.Fluent;
import fr.javafreelance.fluentlenium.core.FluentPage;
import fr.javafreelance.fluentlenium.core.annotation.Page;
import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;
import fr.javafreelance.fluentlenium.core.exception.ConstructionException;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.*;

/**
 * All Junit Test should extends this class. It provides default parameters.
 */
public abstract class FluentTest extends Fluent {
    @Before
    public final void beforeConstructTest() {
        this.setDriver(getDefaultDriver());
        Class cls = null;
        try {
            cls = Class.forName(this.getClass().getName());
            for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    Class clsField = field.getType();
                    cls = Class.forName(clsField.getName());
                    Object page = initClass(cls);
                    field.set(this, page);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
    }

    public FluentTest() {
        super();
    }

    public <T extends FluentPage> T createPage(Class<T> classOfPage) {
        return initClass(classOfPage);
    }

    private <T extends FluentPage> T initClass(Class<T> cls) {
        T page = null;
        try {
            Constructor construct = cls.getDeclaredConstructor();
            construct.setAccessible(true);
            page = (T) construct.newInstance();
            Class parent = Class.forName(Fluent.class.getName());
            Method m = parent.getDeclaredMethod("setDriver", WebDriver.class);
            m.setAccessible(true);
            m.invoke(page, getDriver());

            //init fields with default proxies
            Field[] fields = cls.getDeclaredFields();
            for (Field fieldFromPage : fields) {
                if (!FluentWebElement.class.isAssignableFrom(fieldFromPage.getType())) {
                    continue;
                }
                fieldFromPage.setAccessible(true);
                proxyElement(new DefaultElementLocatorFactory(getDriver()), page, fieldFromPage);
            }
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (NoSuchMethodException e) {
            throw new ConstructionException("No constructor found on class " + (cls != null ? cls.getName() : " null"), e);
        } catch (InstantiationException e) {
            throw new ConstructionException("Unable to instantiate " + (cls != null ? cls.getName() : " null"), e);
        } catch (InvocationTargetException e) {
            throw new ConstructionException("Cannot invoke method setDriver on " + (cls != null ? cls.getName() : " null"), e);
        }
        return page;
    }

    /**
     * Override this method to change the driver
     *
     * @return
     */
    public WebDriver getDefaultDriver() {
        return new FirefoxDriver();
    }

    public WebDriverWait getDefaultWait() {
        return new WebDriverWait(getDefaultDriver(), 5);
    }

    /**
     * Open the url page
     *
     * @param url
     */
    public void goTo(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Url is mandatory");
        }
        getDriver().get(url);
    }

    /**
     * Go To the  page
     *
     * @param page
     */
    public static void goTo(FluentPage page) {
        if (page == null) {
            throw new IllegalArgumentException("Page is mandatory");
        }
        page.go();
    }

    public static void assertAt(FluentPage fluent) {
          fluent.isAt();
      }




    @After
    public void after() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    private static void proxyElement(ElementLocatorFactory factory, Object page, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return;
        }

        InvocationHandler handler = new LocatingElementHandler(locator);
        WebElement proxy = (WebElement) Proxy.newProxyInstance(
                page.getClass().getClassLoader(), new Class[]{WebElement.class}, handler);
        try {
            field.setAccessible(true);
            field.set(page, new FluentWebElement(proxy));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
