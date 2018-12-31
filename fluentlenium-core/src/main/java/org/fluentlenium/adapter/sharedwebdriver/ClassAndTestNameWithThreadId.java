package org.fluentlenium.adapter.sharedwebdriver;

import java.util.Objects;

class ClassAndTestNameWithThreadId {
    protected final Class<?> testClass;
    protected final String testName;
    private final Long threadId;

    ClassAndTestNameWithThreadId(Class<?> testClass, String testName, Long threadId) {
        this.testClass = testClass;
        this.testName = testName;
        this.threadId = threadId;
    }

    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ClassAndTestNameWithThreadId)) {
            return false;
        }

        final ClassAndTestNameWithThreadId other = (ClassAndTestNameWithThreadId) obj;

        if (!other.canEqual(this)) {
            return false;
        }

        if (!Objects.equals(this.testClass, other.testClass)) {
            return false;
        }

        if (!Objects.equals(this.testName, other.testName)) {
            return false;
        }

        return Objects.equals(this.threadId, other.threadId);
    }

    boolean canEqual(final Object other) {
        return other instanceof ClassAndTestNameWithThreadId;
    }

    public int hashCode() {
        final int prime = 59;
        int result = 1;
        final Object testClazz = this.testClass;
        result = result * prime + (testClazz == null ? 43 : testClazz.hashCode());
        final Object nameOfTest = this.testName;
        result = result * prime + (nameOfTest == null ? 43 : nameOfTest.hashCode());
        final Object idOfThread = this.threadId;
        result = result * prime + (idOfThread == null ? 43 : idOfThread.hashCode());
        return result;
    }
}
