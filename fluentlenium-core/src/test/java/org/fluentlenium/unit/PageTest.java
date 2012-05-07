package org.fluentlenium.unit;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;

public class PageTest extends FluentTest {
	public static class TestPage extends FluentPage {
		@Page
		private IncludedPage includedPage;
	}

	public static class IncludedPage extends FluentPage {
		private FluentWebElement element;
	}

	@Page
	private TestPage testPage;

	@Test
	public void testNull() {
		assertThat(testPage).isNotNull();
		assertThat(testPage.includedPage).isNotNull();
		assertThat(testPage.includedPage.element).isNotNull();
	}
}
