package org.fluentlenium.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for {@link ResourceUtils}.
 */
public class ResourceUtilsTest {
    private static final String NON_EXISTET_RESOURCE_PATH = "/org/fluentlenium/utils/non-existent";
    private static final String EXISTING_RESOURCE_PATH = "org/fluentlenium/utils/dummy_resource.json";

    //getResourceAsFile

    @Test
    public void shouldReturnNullFileIfTheResourceIsNotFound() throws IOException {
        assertThat(ResourceUtils.getResourceAsFile(NON_EXISTET_RESOURCE_PATH)).isNull();
    }

    @Test
    public void shouldReturnCopyOfOfResourceAsFile() throws IOException {
        String expectedResourceContent = "{\n  \"dummy\": \"resource\"\n}\n";

        assertThat(ResourceUtils.getResourceAsFile(EXISTING_RESOURCE_PATH)).usingCharset("UTF-8")
                                                                           .hasContent(expectedResourceContent);
    }

    @Test
    @Ignore("Find a way to mock IOUtils.copy to throw IOException")
    public void shouldThrowIOExceptionWhenAProblemOccursDuringReadingResource() {
    }

    //getResourceAsURL

    @Test
    public void shouldReturnNullURLIfTheResourceIsNotFound() throws IOException {
        assertThat(ResourceUtils.getResourceAsURL(NON_EXISTET_RESOURCE_PATH)).isNull();
    }

    @Test
    public void shouldReturnURLFromResource() throws IOException {
        assertThat(ResourceUtils.getResourceAsURL(EXISTING_RESOURCE_PATH))
            .matches(url -> url.getPath().matches("^.*(?<=\\w).tmp$"));
    }
}
