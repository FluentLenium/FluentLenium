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

    /**
     * Get the priority of the object.
     *
     * @param obj object to get the priority
     * @return priority value
     */
    protected int getPriority(Object obj) {
        if (obj instanceof ListenerPriority) {
            return ((ListenerPriority) obj).getPriority();
        }
        return 0;
    }
}
