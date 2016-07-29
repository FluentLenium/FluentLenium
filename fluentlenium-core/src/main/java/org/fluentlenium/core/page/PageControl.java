package org.fluentlenium.core.page;

import org.fluentlenium.core.FluentPage;

public interface PageControl {
    <T extends FluentPage> T createPage(Class<T> cls, Object... params);
}
