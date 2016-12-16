package org.fluentlenium.core.hook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NanoHookOptions {
    private String value; // NOPMD ImmutableField

    public NanoHookOptions(NanoHookAnnotation annotation) {
        value = annotation.value();
    }
}
