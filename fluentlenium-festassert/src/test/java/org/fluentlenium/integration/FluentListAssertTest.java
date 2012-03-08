package org.fluentlenium.integration;

import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;

import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

public class FluentListAssertTest extends LocalFluentCase {
	
    @Test
    public void testHasTextOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasText("Paul"); 
    }

    @Test(expected = AssertionError.class)
    public void testHasTextKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasText("John");
    }
	
    @Test
    public void testHasNotTextOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasNotText("John");
    }

    @Test(expected = AssertionError.class)
    public void testHasNotTextKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasNotText("Paul");
    }
    
    @Test
    public void testHasSizeOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize(9); 
    }
    
    @Test(expected = AssertionError.class)
    public void testHasSizeKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize(10); 
    }
    
    @Test
    public void testHasSizeLessThanOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThan(10); 
    }
    
    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThan(8);
        assertThat(find("span")).hasSize().lessThan(9);
    }
    
    @Test
    public void testHasSizeLessThanOrEqualToOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThanOrEqualTo(10);
        assertThat(find("span")).hasSize().lessThanOrEqualTo(9);
    }
    
    @Test(expected = AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().lessThanOrEqualTo(8);
    }
    
    @Test
    public void testHasSizeGreaterThanOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThan(8); 
    }
    
    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThan(9);
        assertThat(find("span")).hasSize().greaterThan(10); 
    }
    
    @Test
    public void testHasSizeGreaterThanOrEqualToOk() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThanOrEqualTo(9);
        assertThat(find("span")).hasSize().greaterThanOrEqualTo(8);
    }
    
    @Test(expected = AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() throws Exception {
        goTo(DEFAULT_URL);
        assertThat(find("span")).hasSize().greaterThanOrEqualTo(10);
    }
    
}
