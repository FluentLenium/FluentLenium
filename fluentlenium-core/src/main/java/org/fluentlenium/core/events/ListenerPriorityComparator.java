package org.fluentlenium.core.events;

import java.util.Comparator;

/**
 * Compare listeners implementing {@link ListenerPriority}.
 */
public class ListenerPriorityComparator implements Comparator<Object> {
    @Override
    public int compare(final Object o1, final Object o2) {
        final int priority1 = getPriority(o1);
        final int priority2 = getPriority(o2);

        return Integer.compare(priority2, priority1);
    }

    public int getPriority(final Object obj) {
        if (obj instanceof ListenerPriority) {
            return ((ListenerPriority) obj).getPriority();
        }
        return 0;
    }
}
