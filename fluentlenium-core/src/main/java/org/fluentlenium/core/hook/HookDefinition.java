package org.fluentlenium.core.hook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class HookDefinition<T> {
    @NonNull
    private Class<? extends FluentHook<T>> hookClass;
    private T options;
}
