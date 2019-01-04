package org.fluentlenium.adapter.sharedwebdriver;

import java.util.Objects;

class ClassAndTestName {
    private final Class<?> testClass;
    private final String testName;

    ClassAndTestName(Class<?> testClass, String testName) {
        this.testClass = testClass;
        this.testName = testName;
    }

    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ClassAndTestName)) {
            return false;
        }

        final ClassAndTestName other = (ClassAndTestName) obj;

        if (!other.canEqual(this)) {
            return false;
        }

        if (!Objects.equals(this.testClass, other.testClass)) {
            return false;
        }

        return Objects.equals(this.testName, other.testName);
    }

    boolean canEqual(final Object other) {
        return other instanceof ClassAndTestName;
    }

    public int hashCode() {
        final int prime = 59;
        int result = 1;
        final Object testClazz = this.testClass;
        result = result * prime + (testClazz == null ? 43 : testClazz.hashCode());
        final Object nameOfTest = this.testName;
        result = result * prime + (nameOfTest == null ? 43 : nameOfTest.hashCode());
        return result;
    }
}
