package org.fluentlenium.integration;

import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

/**
 * Test related to <a href=https://github.com/FluentLenium/FluentLenium/issues/425>issue #425</a>.
 */
@Wait
class WaitHookFilteringTest extends IntegrationFluentTest {

    @Test
    void test() {
        StringBuilder sourceBuilder = new StringBuilder();

        sourceBuilder.append("<html><body>");
        sourceBuilder.append("<table>");

        sourceBuilder.append("<tr><td>anotherText</td></tr>");
        sourceBuilder.append("<tr><td>someText</td></tr>");

        sourceBuilder.append("</table>");
        sourceBuilder.append("</body></html>");

        goToSource(sourceBuilder.toString());

        long start = System.currentTimeMillis();
        $("table tr td", withText("anotherText")).now();
        long end = System.currentTimeMillis();
        assertThat(end - start).isLessThan(4000);
    }

}
