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
