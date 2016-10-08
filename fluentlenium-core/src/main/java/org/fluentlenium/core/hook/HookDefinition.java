package org.fluentlenium.core.hook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Definition of a hook, containing class of the hook and defined options
 *
 * @param <T> type of the option class
 */
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class HookDefinition<T> {
    @NonNull
    private Class<? extends FluentHook<T>> hookClass;
    private T options;
}
