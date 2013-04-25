package org.fluentlenium.integration;

import static org.fest.assertions.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

/**
 *
 */
public class PageInPageTest extends LocalFluentCase
{

	@Page
	private TestPage testPage;
	@Page
	private SubTestPage subTestPage;

	@Test
	public void pages_should_be_injected() {
		assertThat(testPage).isNotNull();
		assertThat(testPage).isInstanceOf(TestPage.class);
		assertThat(testPage.includedPage).isNotNull();
		assertThat(testPage.includedPage).isInstanceOf(IncludedPage.class);
		assertThat(testPage.includedPage.element).isNotNull();
		assertThat(subTestPage).isNotNull();
		assertThat(subTestPage).isInstanceOf(SubTestPage.class);
		assertThat(subTestPage.includedPage).isNotNull();
		assertThat(subTestPage.anotherIncludedPage).isNotNull();
	}
}

class TestPage extends FluentPage {
	@Page
	IncludedPage includedPage;
}

class SubTestPage extends TestPage {
	@Page
	IncludedPage anotherIncludedPage;
}

class IncludedPage extends FluentPage {
	FluentWebElement element;
}
