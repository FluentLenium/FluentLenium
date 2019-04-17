package org.fluentlenium.core.events;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Unit test for {@link ListenerPriorityComparator}.
 */
public class ListenerPriorityComparatorTest {

    @Test
    public void shouldReturnPositiveNumberWhenSecondArgumentPriorityIsGreaterThanFirst() {
        String aString = "a_string";
        TestListener testListener = new TestListener();

        ListenerPriorityComparator comparator = new ListenerPriorityComparator();

        assertThat(comparator.compare(aString, testListener)).isEqualTo(1);
    }

    @Test
    public void shouldReturnNegativeNumberWhenFirstArgumentPriorityIsGreaterThanSecond() {
        String aString = "a_string";
        TestListener testListener = new TestListener();

        ListenerPriorityComparator comparator = new ListenerPriorityComparator();

        assertThat(comparator.compare(testListener, aString)).isEqualTo(-1);
    }

    @Test
    public void shouldReturnZeroArgumentsPriorityEqual() {
        String aString = "a_string";
        String anotherString = "another_string";

        ListenerPriorityComparator comparator = new ListenerPriorityComparator();

        assertThat(comparator.compare(aString, anotherString)).isEqualTo(0);
    }

    private static final class TestListener implements ListenerPriority {

        @Override
        public int getPriority() {
            return 10;
        }
    }
}
