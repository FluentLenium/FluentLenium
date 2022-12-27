package io.fluentlenium.test.await.hook;

import io.fluentlenium.core.filter.FilterConstructor;
import io.fluentlenium.core.hook.wait.Wait;
import io.fluentlenium.test.IntegrationFluentTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

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
        $("table tr td", FilterConstructor.withText("anotherText")).now();
        long end = System.currentTimeMillis();
        assertThat(end - start).isLessThan(4000);
    }

    private void goToSource(String htmlSource) {
        try {
            File source = File.createTempFile("source", ".tmp.html");
            try (OutputStream fos = new FileOutputStream(source)) {
                IOUtils.write(htmlSource, fos, "UTF-8");
                source.deleteOnExit();
                goTo(source.toURI().toString());
            }
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

}
