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

package org.fluentlenium.unit;


import org.fluentlenium.core.FluentAdapter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Set;

public class ScreenshotTest {

    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Test(expected = WebDriverException.class)
    public void when_browser_doesnt_accept_screenshot_then_custom_error() {
        new FluentAdapter(new CustomWebDriverNoScreenshot()).takeScreenShot();
    }
    @Test
    public void when_browser_does_accept_screenshot_then_no_exception() {
        new FluentAdapter(new CustomWebDriverScreenshot()).takeScreenShot(folder+"/test.jpg");
    }
}

 class CustomWebDriverNoScreenshot implements WebDriver {

     public void get(String s) {
         
     }

     public String getCurrentUrl() {
         return null;  
     }

     public String getTitle() {
         return null;  
     }

     public List<WebElement> findElements(By by) {
         return null;  
     }

     public WebElement findElement(By by) {
         return null;  
     }

     public String getPageSource() {
         return null;  
     }

     public void close() {
         
     }

     public void quit() {
         
     }

     public Set<String> getWindowHandles() {
         return null;  
     }

     public String getWindowHandle() {
         return null;  
     }

     public TargetLocator switchTo() {
         return null;  
     }

     public Navigation navigate() {
         return null;  
     }

     public Options manage() {
         return null;  
     }
 }


class CustomWebDriverScreenshot implements WebDriver , TakesScreenshot {

    public void get(String s) {
        
    }

    public String getCurrentUrl() {
        return null;  
    }

    public String getTitle() {
        return null;  
    }

    public List<WebElement> findElements(By by) {
        return null;  
    }

    public WebElement findElement(By by) {
        return null;  
    }

    public String getPageSource() {
        return null;  
    }

    public void close() {
        
    }

    public void quit() {
        
    }

    public Set<String> getWindowHandles() {
        return null;  
    }

    public String getWindowHandle() {
        return null;  
    }

    public TargetLocator switchTo() {
        return null;  
    }

    public Navigation navigate() {
        return null;  
    }

    public Options manage() {
        return null;  
    }

    public <X> X getScreenshotAs(OutputType<X> xOutputType) throws WebDriverException {
        return xOutputType.convertFromBase64Png("test");
    }
}
