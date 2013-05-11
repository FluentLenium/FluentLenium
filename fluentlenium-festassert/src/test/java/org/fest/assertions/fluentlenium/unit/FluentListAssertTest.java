/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package org.fest.assertions.fluentlenium.unit;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.fest.assertions.fluentlenium.FluentLeniumAssertions;
import org.fest.assertions.fluentlenium.custom.FluentListAssert;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FluentListAssertTest<E extends FluentWebElement> {
	
	@Mock
    FluentList<E> fluentList;
    
    @InjectMocks
    FluentListAssert listAssert = FluentLeniumAssertions.assertThat(fluentList);

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
	
    @Test
    public void testHasText() {
    	when(fluentList.getTexts()).thenReturn( Arrays.asList("some text") );
        assertNotNull( listAssert.hasText("some text") );
    }
    
    @Test
    public void testHasNotText() {
    	when(fluentList.getTexts()).thenReturn( Arrays.asList("other text") );
        assertNotNull( listAssert.hasNotText("some text") );
    }
    
    @Test
    public void testHasSizeOk() {
    	when(fluentList.size()).thenReturn(7);
        assertNotNull( listAssert.hasSize(7) );
    }
    
    @Test(expected=AssertionError.class)
    public void testHasSizeKo() {
    	when(fluentList.size()).thenReturn(7);
        listAssert.hasSize(5);
    }
    
    @Test
    public void testHasSizeLessThanOk() {
    	when(fluentList.size()).thenReturn(7);
        assertNotNull( listAssert.hasSize().lessThan(9) );
    }
    
    @Test(expected=AssertionError.class)
    public void testHasSizeLessThanKo() {
    	when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().lessThan(7);
        listAssert.hasSize().lessThan(6);
    }
    
    @Test
    public void testHasSizeLessThanOrEqualToOk() {
    	when(fluentList.size()).thenReturn(7);
        assertNotNull( listAssert.hasSize().lessThanOrEqualTo(7) );
        assertNotNull( listAssert.hasSize().lessThanOrEqualTo(8) );
    }
    
    @Test(expected=AssertionError.class)
    public void testHasSizeLessThanOrEqualToKo() {
    	when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().lessThanOrEqualTo(6);
    }
    
    @Test
    public void testHasSizeGreaterThanOk() {
    	when(fluentList.size()).thenReturn(7);
        assertNotNull( listAssert.hasSize().greaterThan(6) );
    }
    
    @Test(expected=AssertionError.class)
    public void testHasSizeGreaterThanKo() {
    	when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().greaterThan(7);
        listAssert.hasSize().greaterThan(8);
    }
    
    @Test
    public void testHasSizeGreaterThanOrEqualToOk() {
    	when(fluentList.size()).thenReturn(7);
        assertNotNull( listAssert.hasSize().greaterThanOrEqualTo(7) );
        assertNotNull( listAssert.hasSize().greaterThanOrEqualTo(6) );
    }
    
    @Test(expected=AssertionError.class)
    public void testHasSizeGreaterThanOrEqualToKo() {
    	when(fluentList.size()).thenReturn(7);
        listAssert.hasSize().greaterThanOrEqualTo(8);
    }
    
}
