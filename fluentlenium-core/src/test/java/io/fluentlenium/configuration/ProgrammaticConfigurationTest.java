package io.fluentlenium.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import org.junit.Before;
import org.junit.Test;

public class ProgrammaticConfigurationTest {

    private static final String PARAM = "param";
    private static final String VALUE = "value";

    private Class<?> clazz;

    @Before
    public void setUp() {
        clazz = new ByteBuddy()
                .subclass(ProgrammaticConfiguration.class)
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
    }

    @Test
    public void testDefaultConfiguration() throws IllegalAccessException,
            InstantiationException, NoSuchMethodException, InvocationTargetException {

        Object programmaticConfInstance = clazz.getDeclaredConstructor().newInstance();
        assertThat(programmaticConfInstance).isInstanceOf(ProgrammaticConfiguration.class);
    }

    @Test
    public void testSetAndGetProperty() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

        Object programmaticConfInstance = clazz.getDeclaredConstructor().newInstance();
        Method setCustomProperty = clazz.getMethod("setCustomProperty", String.class, String.class);
        Method getCustomProperty = clazz.getMethod("getCustomProperty", String.class);

        setCustomProperty.invoke(programmaticConfInstance, new String[]{PARAM, VALUE});
        Object param = getCustomProperty.invoke(programmaticConfInstance, PARAM);

        assertThat(param).isEqualTo(VALUE);
    }
}
