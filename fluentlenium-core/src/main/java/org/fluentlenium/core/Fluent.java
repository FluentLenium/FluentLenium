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

package org.fluentlenium.core;

import org.apache.commons.io.FileUtils;
import org.fluentlenium.core.action.FillConstructor;
import org.fluentlenium.core.action.FillSelectConstructor;
import org.fluentlenium.core.action.FluentDefaultActions;
import org.fluentlenium.core.annotation.AjaxElement;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.exception.ConstructionException;
import org.fluentlenium.core.filter.Filter;
import org.fluentlenium.core.search.Search;
import org.fluentlenium.core.search.SearchActions;
import org.fluentlenium.core.wait.FluentWait;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Util Class which offers some shortcut to webdriver methods
 */
public abstract class Fluent implements SearchActions {
    private WebDriver driver;
    private String baseUrl;
    private Search search;

    public Fluent(WebDriver driver) {
        this.driver = driver;
        this.search = new Search(driver);
        FluentThread.set(this);
    }

    /**
     * Defined the default url that will be used in the test and in the relative pages
     *
     * @param baseUrl
     * @return
     */
    public Fluent withDefaultUrl(String baseUrl) {
        if (baseUrl != null) {
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            this.baseUrl = baseUrl;
        }
        return this;
    }

    /**
     * Define an implicit time to wait for a page to be loaded
     *
     * @param l
     * @param timeUnit
     * @return
     */
    public Fluent withDefaultPageWait(long l, TimeUnit timeUnit) {
        this.getDriver().manage().timeouts().pageLoadTimeout(l, timeUnit);
        return this;
    }

    /**
     * Define an implicit time to wait when searching an element
     *
     * @param l
     * @param timeUnit
     * @return
     */
    public Fluent withDefaultSearchWait(long l, TimeUnit timeUnit) {
        this.getDriver().manage().timeouts().implicitlyWait(l, timeUnit);
        return this;
    }

    public Fluent() {
        FluentThread.set(this);
    }

    /**
     * Take a snapshot of the browser. By default the file will be a png named by the current timestamp.
     */
    public Fluent takeScreenShot() {
        takeScreenShot(new Date().getTime() + ".png");
        return this;
    }

    /**
     * Take a snapshot of the browser into a file given by the fileName param.
     *
     * @param fileName
     */
    public Fluent takeScreenShot(String fileName) {
        if (!(getDriver() instanceof TakesScreenshot)) {
            throw new WebDriverException("Current browser doesn't allow taking screenshot.");
        }
        File scrFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File(fileName);
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error when taking the snapshot", e);
        }
        return this;
    }

    protected Fluent initFluent(WebDriver driver) {
        this.driver = driver;
        this.search = new Search(driver);
        return this;
    }

    public WebDriver getDriver() {
        return driver;
    }


    protected void initTest() {
        Class cls = null;
        try {
	    for (cls = Class.forName(this.getClass().getName()); FluentAdapter.class.isAssignableFrom(cls); cls = cls.getSuperclass()) {
	        for (Field field : cls.getDeclaredFields()) {
                if (field.isAnnotationPresent(Page.class)) {
                    field.setAccessible(true);
                    Class clsField = field.getType();
                    Class clsPage = Class.forName(clsField.getName());
                    Object page = initClass(clsPage);
                    field.set(this, page);
                }
            }
	    }
        } catch (ClassNotFoundException e) {
            throw new ConstructionException("Class " + (cls != null ? cls.getName() : " null") + "not found", e);
        } catch (IllegalAccessException e) {
            throw new ConstructionException("IllegalAccessException on class " + (cls != null ? cls.getName() : " null"), e);
        }
    }

    public <T extends FluentPage> T createPage(Class<T> classOfPage) {
        return initClass(classOfPage);
    }


    protected <T extends FluentPage> T initClass(Class<T> cls) {
        T page = null;
        try {
            Constructor construct = cls.getDeclaredConstructor();
            construct.setAccessible(true);
            page = (T) construct.newInstance();
            Class parent = Class.forName(Fluent.class.getName());
            initDriver(page, parent);
            initBaseUrl(page, parent);

            //init fields with default proxies
            Field[] fields = cls.getDeclaredFields();
            for (Field fieldFromPage : fields) {
                if (!FluentWebElement.class.isAssignableFrom(fieldFromPage.getType())) {
                    continue;
                }
                fieldFromPage.setAccessible(true);
                AjaxElement elem = fieldFromPage.getAnnotation(AjaxElement.class);
                if (elem == null) {
                    proxyElement(new DefaultElementLocatorFactory(getDriver()), page, fieldFromPage);
                } else {
                    proxyElement(new AjaxElementLocatorFactory(getDriver(), elem.timeOutInSeconds()), page, fieldFromPage);
                }
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

    private <T extends FluentPage> void initBaseUrl(T page, Class parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m;
        if (getBaseUrl() != null) {
            m = parent.getDeclaredMethod("withDefaultUrl", String.class);
            m.setAccessible(true);
            m.invoke(page, getBaseUrl());
        }
    }

    private <T extends FluentPage> void initDriver(T page, Class parent) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method m = parent.getDeclaredMethod("initFluent", WebDriver.class);
        m.setAccessible(true);
        m.invoke(page, getDriver());

    }

    private static void proxyElement(ElementLocatorFactory factory, Object page, Field field) {
        ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return;
        }

        InvocationHandler handler = new LocatingElementHandler(locator);
        WebElement proxy = (WebElement) java.lang.reflect.Proxy.newProxyInstance(
                page.getClass().getClassLoader(), new Class[]{WebElement.class}, handler);
        try {
            field.setAccessible(true);
            field.set(page, new FluentWebElement(proxy));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the base URL to use when visiting relative URLs, if one is configured
     *
     * @return The base URL, or null if none configured
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * wait for an asynchronous call
     *
     * @return
     */
    public FluentWait await() {
        return new FluentWait(driver, search);
    }


    /**
     * Return the title of the page
     *
     * @return
     */
    public String title() {
        return driver.getTitle();
    }

    /**
     * return the cookies as a set
     *
     * @return
     */
    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    /**
     * return the corresponding cookie given a name
     *
     * @param name
     * @return
     */
    public Cookie getCookie(String name) {
        return driver.manage().getCookieNamed(name);
    }

    /**
     * Return the url of the page. If a base url is provided, the current url will be relative to that base url.
     *
     * @return
     */
    public String url() {
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl != null && baseUrl != null && currentUrl.startsWith(baseUrl)) {
            currentUrl = currentUrl.substring(baseUrl.length());
        }

        return currentUrl;
    }

    /**
     * Return the source of the page
     *
     * @return
     */
    public String pageSource() {
        return driver.getPageSource();
    }



    public <P extends FluentPage> P goTo(P page) {
        if (page == null) {
            throw new IllegalArgumentException("Page is mandatory");
        }
        page.go();
        return page;
    }

    /**
     * Open the url page
     *
     * @param url
     */
    public Fluent goTo(String url) {
        if (url == null) {
            throw new IllegalArgumentException("Url is mandatory");
        }
        if (baseUrl != null) {
            URI uri = URI.create(url);
            if (!uri.isAbsolute()) {
                url = baseUrl + url;
            }
        }
        getDriver().get(url);
        return this;
    }


    public Fluent executeScript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
        return this;
    }

    /**
     * Central methods to find elements on the page. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList<FluentWebElement> $(String name, final Filter... filters) {
        return search.find(name, filters);
    }


    /**
     * Central methods a find element on the page, the number indicat the index of the desired element on the list. Can provide some filters. Able to use css1, css2, css3, see WebDriver  restrictions
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement $(String name, Integer number, final Filter... filters) {
        return search.find(name, number, filters);
    }


    /**
     * return the lists corresponding to the cssSelector with it filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentList<FluentWebElement> find(String name, final Filter... filters) {
        return search.find(name, filters);
    }

    /**
     * Return the elements at the numner position into the the lists corresponding to the cssSelector with it filters
     *
     * @param name
     * @param number
     * @param filters
     * @return
     */
    public FluentWebElement find(String name, Integer number, final Filter... filters) {
        return search.find(name, number, filters);
    }

    /**
     * Return the first elements corresponding to the name and the filters
     *
     * @param name
     * @param filters
     * @return
     */
    public FluentWebElement findFirst(String name, final Filter... filters) {
        return search.findFirst(name, filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector
     */
    public FillConstructor fill(String cssSelector, Filter... filters) {
        return new FillConstructor(cssSelector, getDriver(), filters);
    }

    /**
     * Construct a FillConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param list
     */
    public FillConstructor fill(FluentDefaultActions list, Filter... filters) {
        return new FillConstructor(list, getDriver(), filters);
    }

    /**
     * Construct a FillSelectConstructor in order to allow easy fill
     * Be careful - only the visible elements are filled
     *
     * @param cssSelector
     */
    public FillSelectConstructor fillSelect(String cssSelector, Filter... filters) {
        return new FillSelectConstructor(cssSelector, getDriver(), filters);
    }

    /**
     * click all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are clicked
     *
     * @param cssSelector
     */
    public Fluent click(String cssSelector, Filter... filters) {
        $(cssSelector, filters).click();
        return this;
    }

    /**
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are cleared
     *
     * @param cssSelector
     */
    public Fluent clear(String cssSelector, Filter... filters) {
        $(cssSelector, filters).clear();
        return this;
    }

    /**
     * Submit all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     *
     * @param cssSelector
     */
    public Fluent submit(String cssSelector, Filter... filters) {
        $(cssSelector, filters).submit();
        return this;
    }

    /**
     * get a list all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are submitted
     * //TODO UTILITY ? Deprecated ?
     *
     * @param cssSelector
     */
    public List<String> text(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getTexts();
    }

    /**
     * Value all elements that are in cssSelector with its filters
     * Be careful - only the visible elements are returned
     * //TODO UTILITY ? Deprecated ?
     *
     * @param cssSelector
     */
    public List<String> value(String cssSelector, Filter... filters) {
        return $(cssSelector, filters).getValues();
    }


    /**
     * click all elements that are in the list
     * Be careful - only the visible elements are clicked
     *
     * @param fluentObject
     */
    public Fluent click(FluentDefaultActions fluentObject) {
        fluentObject.click();
        return this;
    }

    /**
     * Clear all elements that are in the list
     * Be careful - only the visible elements are cleared
     *
     * @param fluentObject
     */
    public Fluent clear(FluentList<FluentWebElement> fluentObject) {
        fluentObject.clear();
        return this;
    }

    /**
     * Clear the given parameters elements that are in the list
     * Be careful - only the visible elements are cleared
     *
     * @param fluentObject
     */
    public Fluent clear(FluentWebElement fluentObject) {
        fluentObject.clear();
        return this;
    }

    /**
     * Submit all elements that are in the list
     * Be careful - only the visible elements are submitted
     *
     * @param fluentObject
     */
    public Fluent submit(FluentDefaultActions fluentObject) {
        fluentObject.submit();
        return this;
    }

  /**
   * Swith to the selected Element (if element is null or not an iframe, or haven't an id then
   * switch to the default)
   *
   * @param element
   */
  public Fluent switchTo(FluentWebElement element) {
    if (null == element ||
        null == element.getTagName() ||
        null == element.getId() ||
        !"iframe".equals(element.getTagName())) {
      driver.switchTo().defaultContent();
    } else {
      driver.switchTo().frame(element.getId());
    }
    return this;
  }

  /**
   * Swith to the default element
   */
  public Fluent switchTo() {
    this.switchTo(null);
    return this;
  }


  /**
   * Swith to the default element
   */
  public Fluent switchToDefault() {
    this.switchTo(null);
    return this;
  }

  /**
   * When an alert box pops up, click on "OK"
   */
  public Fluent acceptAlert(){
    getDriver().switchTo().alert().accept();
    return this;
  }

  /**
   * When an alert box pops up, click on "Cancel"
   */
  public Fluent dismissAlert(){
    getDriver().switchTo().alert().dismiss();
    return this;
  }

  /**
   * Entering an input value
   * @param s field to enter
   */
  public Fluent promptAlert(String s){
    getDriver().switchTo().alert().sendKeys(s);
    acceptAlert();
    return this;
  }

  /**
   * Maximize browser window using webdriver
   */
  public Fluent maximizeWindow(){
      getDriver().manage().window().maximize();
      return this;
  }

}
