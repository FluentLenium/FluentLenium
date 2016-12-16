package org.fluentlenium.utils;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReflectionUtilsTest {
    @Test
    public void testToClass() {
        Object[] objects = {"test", 1, 2L, true, Integer.valueOf(3)};

        Class<?>[] classes = ReflectionUtils.toClass(objects);

        assertThat(classes).containsExactly(String.class, Integer.class, Long.class, Boolean.class, Integer.class);

        assertThat(ReflectionUtils.toClass((Object[]) null)).isNull();
        assertThat(ReflectionUtils.toClass(new Object[] {})).isEmpty();
    }

    public static class SuperClass {
        private String param1;
        private Integer param2;
        private boolean param3;
        private Object param4;

        public SuperClass() {
            // Default constructor
        }

        public SuperClass(String param1, Integer param2, boolean param3, Object param4) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
            this.param4 = param4;
        }
    }

    public static class TestClass {

    }

    @Test
    public void testGetDeclaredConstructor()
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<TestClass> testClassConstructor = ReflectionUtils.getConstructor(TestClass.class);
        assertThat(testClassConstructor.getParameterTypes()).isEmpty();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                ReflectionUtils.getConstructor(TestClass.class, "1", 2, true, "object");
            }
        }).isExactlyInstanceOf(NoSuchMethodException.class);

        Constructor<SuperClass> superClassConstructor = ReflectionUtils
                .getConstructor(SuperClass.class, "1", 2, true, "object");
        assertThat(superClassConstructor.getParameterTypes())
                .isEqualTo(new Class<?>[] {String.class, Integer.class, boolean.class, Object.class});

        SuperClass object = ReflectionUtils.newInstance(SuperClass.class, "1", 2, true, "object");
        assertThat(object.param1).isEqualTo("1");
        assertThat(object.param2).isEqualTo(2);
        assertThat(object.param3).isTrue();
        assertThat(object.param4).isEqualTo("object");

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                ReflectionUtils.newInstance(SuperClass.class, "1", 2);
            }
        }).isExactlyInstanceOf(NoSuchMethodException.class);

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                ReflectionUtils.newInstance(SuperClass.class, "1", 2, "true", "object");
            }
        }).isExactlyInstanceOf(NoSuchMethodException.class);

    }

    @Test
    public void testNewInstance()
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);

        assertThat(testClass).isExactlyInstanceOf(TestClass.class);
    }
}
