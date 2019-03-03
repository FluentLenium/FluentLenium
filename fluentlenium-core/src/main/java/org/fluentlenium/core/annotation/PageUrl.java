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
 * <p>
 * In case of referencing local files in the resources directory you need to override the {@code isAtUsingUrl} method
 * to skip URL check because PageUrl is not able to get local file path relatively.
 * <pre>
 * &#064;PageUrl(file = "page2url.html", value = "?param1={param1}&param2={param2}", isLocalFile = true)
 * class Page2DynamicP2P1 extends FluentPage {
 *     &#064;Override
 *     protected void isAtUsingUrl(String urlTemplate) {
 *     }
 * }
 * </pre>
 * <p>
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
     * <li><code>@PageUrl(file = "index.html" value="", isLocalFile = true)</code> should redirect to
     * * "file://{resourcesDirectory}/index.html"</li>
     * <li><code>@PageUrl(file = "index.html" value="?param={param}", isLocalFile = true)</code> should
     * redirect to "file://{resourcesDirectory}/index.html?param=&lt;value of param>"</li>
     * </ul>
     *
     * @return page url
     */
    String value();

    /**
     * Defines the file name without a path located in the resources directory on the local file system, e.g.
     * <p>
     * {@code @PageUrl(file = "index.html", value = "", isLocalFile = true)}
     * <p>
     * Default value is empty.
     *
     * @return the file name
     */
    String file() default "";

    /**
     * Defines whether during page URL generation FluentLenium should look for a local file system resource.
     * <p>
     * Default value is false.
     *
     * @return true if the file is local, false otherwise
     */
    boolean isLocalFile() default false;
}
