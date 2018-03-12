package org.fluentlenium.adapter.junit5;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Parameter;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MockitoExtension implements TestInstancePostProcessor, ParameterResolver {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        MockitoAnnotations.initMocks(testInstance);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().isAnnotationPresent(Mock.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return getMock(parameterContext.getParameter(), extensionContext);
    }

    private Object getMock(Parameter parameter, ExtensionContext extensionContext) {
        Class<?> mockType = parameter.getType();
        ExtensionContext.Store mocks = extensionContext.getStore(ExtensionContext.Namespace.create(MockitoExtension.class, mockType));
        String mockName = getMockName(parameter);

        if (mockName != null) {
            return mocks.getOrComputeIfAbsent(mockName, key -> mock(mockType, mockName));
        }
        else {
            return mocks.getOrComputeIfAbsent(mockType.getCanonicalName(), key -> mock(mockType));
        }
    }

    private String getMockName(Parameter parameter) {
        String explicitMockName = parameter.getAnnotation(Mock.class).name().trim();
        if (!explicitMockName.isEmpty()) {
            return explicitMockName;
        }
        else if (parameter.isNamePresent()) {
            return parameter.getName();
        }
        return null;
    }
}
