package io.fluentlenium.core.events;

import java.util.Comparator;

/**
 * Compare listeners implementing {@link ListenerPriority}.
 */
public class ListenerPriorityComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        return Integer.compare(getPriority(o2), getPriority(o1));
    }

    /**
     * Get the priority of the object.
     *
     * @param obj object to get the priority of
     * @return priority value if the argument implements {@link ListenerPriority}, otherwise 0
     */
    protected int getPriority(Object obj) {
        if (obj instanceof ListenerPriority) {
            return ((ListenerPriority) obj).getPriority();
        }
        return 0;
    }
}
