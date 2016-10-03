package org.fluentlenium.core.events;

import java.util.Comparator;

/**
 * Compare listeners implementing {@link ListenerPriority}.
 */
public class ListenerPriorityComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        int priority1 = getPriority(o1);
        int priority2 = getPriority(o2);

        return Integer.compare(priority2, priority1);
    }

    public int getPriority(Object o) {
        if (o instanceof ListenerPriority) {
            return ((ListenerPriority) o).getPriority();
        }
        return 0;
    }
}
