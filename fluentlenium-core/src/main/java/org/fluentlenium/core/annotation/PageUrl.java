package org.fluentlenium.core.annotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>PageUrl</b> is a class annotation used instead of <b>getUrl</b> method of {@link org.fluentlenium.core.FluentPage} object.
 * If <b>PageUrl</b> annotation is used the page class may not override the <b>getUrl</b> method.
 * <p>
 * It’s possible to define parameters using {@code {[?][/path/]parameter}} syntax.
 * If it starts with {@code ?}, it means that the parameter is optional.
 * Path can be included in the braces so it is removed when parameter value is not defined:
 * <pre>
 * &#064;PageUrl("/document/{document}{?/page/page}{?/format}")
 * public class DocumentPage extends FluentPage {
 *     public DocumentPage customPageMethod(){
 *             ...
 *             return this;
 *         }
 *         ...
 * }
 * </pre>
 * Referencing local files in the test resources directory can be achieved by defining the file name of the resource in the
 * annotation's {@code file} attribute:
 * <pre>
 * &#064;PageUrl(file = "page2url.html", value = "?param1={param1}&param2={param2}")
 * class Page2DynamicP2P1 extends FluentPage {
 * }
 * </pre>
 * In case you don't specify the {@code file} attribute but you override either {@link org.fluentlenium.core.FluentPage#getUrl()}
 * or {@link org.fluentlenium.core.FluentPage#getUrl(Object...)} in a way that it retrieves a local test resource you need to
 * also override {@link org.fluentlenium.core.FluentPage#isAtUsingUrl(String)} and leave its body empty to skip URL check
 * because PageUrl is not able to get local file path relatively.
 * <pre>
 * &#064;PageUrl(value = "?param1={param1}&param2={param2}")
 * class Page2DynamicP2P1 extends FluentPage {
 *     &#064;Override
 *     protected String getUrl() {
 *          return someLocalResource;
 *     }
 *
 *     &#064;Override
 *     public void isAtUsingUrl(String urlTemplate) {
 *     }
 * }
 * </pre>
 * In case local files depending on the value of the {@code value} attribute you can use additional URL query parameters
 * attached to the path. If no query parameters are used the value attribute can be left empty.
 * <p>
 * You can find further examples at <a href="https://fluentlenium.com/docs/key_features/#pages">FluentLenium Key features</a>.
 */
@Retention(RUNTIME)
public @interface PageUrl {
    /**
     * The page URL can be relative or absolute, if the URL is not recognized as absolute will be treated as relative.
     * <p>
     * For example:
     * <ul>
     * <li><code>@PageUrl("/")</code>                  should redirect to the homepage of the website (baseUrl + "/")</li>
     * <li><code>@PageUrl("/index.html")</code>        should redirect to baseUrl + "/index.html"</li>
     * <li><code>@PageUrl("http://example.com")</code> should redirect to "http://example.com"</li>
     * <li><code>@PageUrl(file = "index.html" value="")</code> should redirect to
     * * "file://{testResourcesDirectory}/index.html"</li>
     * <li><code>@PageUrl(file = "index.html" value="?param={param}")</code> should
     * redirect to "file://{testResourcesDirectory}/index.html?param=&lt;value of param>"</li>
     * </ul>
     *
     * @return page url
     */
    String value();

    /**
     * Defines the file name without a path located in the test resources directory on the local file system, e.g.
     * <p>
     * {@code @PageUrl(file = "index.html", value = "")}
     * <p>
     * Default value is empty.
     *
     * @return the file name
     */
    String file() default "";
}
