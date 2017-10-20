### Quick example:

```java
@Wait
public class DuckDuckGoTest extends FluentTest {
    @Test
    public void title_of_duck_duck_go_should_contain_search_query_name() {
        goTo("https://duckduckgo.com");
        $("#search_form_input_homepage").fill().with("FluentLenium");
        $("#search_button_homepage").submit();
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present();
        assertThat(window().title()).contains("FluentLenium");
    }
}
```

### Well written FluentLenium code:

#### Page component

```java
@PageUrl("https://duckduckgo.com")
public class MainPage extends FluentPage {
    @FindBy(id = "search_form_input_homepage")
    private FluentWebElement searchFormInput;
    @FindBy(id = "search_button_homepage")
    private FluentWebElement searchInput;
    
    public FluentWebElement getSearchFormInput() {
        return searchFormInput;
    }
    
    public FluentWebElement getSearchInput() {
        return searchInput;
    }
}
```
#### Test body

```java
public class First extends FluentTest {
    @Page
    MainPage mainPage;

    @Test
    public void title_of_duck_duck_go_should_contain_search_query_name() {
        MainPage mainPage = goTo(this.mainPage);
        mainPage.getSearchFormInput().fill().with("FluentLenium");
        mainPage.getSearchInput().submit();
        await().atMost(5, TimeUnit.SECONDS).until(el("#search_form_homepage")).not().present();
        assertThat(window().title()).contains("FluentLenium");
    }
    
    @Override
    public WebDriver newWebDriver() {
        return new ChromeDriver();
    }
}
```

#### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>DuckDuckGoTest</groupId>
    <artifactId>DuckDuckGoTest</artifactId>
    <version>1.0-SNAPSHOT</version>

    <build>
    <plugins>
    <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.0</version>
        <configuration>
            <source>1.8</source>
            <target>1.8</target>
        </configuration>
    </plugin>
    </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.fluentlenium</groupId>
            <artifactId>fluentlenium-core</artifactId>
            <version>3.4.1</version>
        </dependency>
        <dependency>
            <groupId>org.fluentlenium</groupId>
            <artifactId>fluentlenium-junit</artifactId>
            <version>3.4.1</version>
        </dependency>
        <dependency>
            <groupId>org.fluentlenium</groupId>
            <artifactId>fluentlenium-assertj</artifactId>
            <version>3.4.1</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-chrome-driver</artifactId>
            <version>3.6.0</version>
        </dependency>
    </dependencies>
</project>
```