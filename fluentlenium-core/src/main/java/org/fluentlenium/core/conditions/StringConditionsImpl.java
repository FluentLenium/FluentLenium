package org.fluentlenium.core.conditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Conditions for string
 */
public class StringConditionsImpl extends AbstractObjectConditions<String> implements StringConditions {
    /**
     * Creates a new conditions object on string.
     *
     * @param string underlying string
     */
    public StringConditionsImpl(String string) {
        super(string);
    }

    /**
     * Creates a new conditions object on string.
     *
     * @param string   underlying string
     * @param negation negation value
     */
    public StringConditionsImpl(String string, boolean negation) {
        super(string, negation);
    }

    @Override
    protected StringConditionsImpl newInstance(boolean negationValue) {
        return new StringConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public StringConditionsImpl not() {
        return (StringConditionsImpl) super.not();
    }

    @Override
    public boolean contains(CharSequence charSequence) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.contains(charSequence);
        });
    }

    @Override
    public boolean startsWith(String prefix) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.startsWith(prefix);
        });
    }

    @Override
    public boolean endsWith(String suffix) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.endsWith(suffix);
        });
    }

    @Override
    public boolean equalTo(String anotherString) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.equals(anotherString);
        });
    }

    @Override
    public boolean equalToIgnoreCase(String anotherString) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.equalsIgnoreCase(anotherString);
        });
    }

    @Override
    public boolean matches(String regex) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            return input.matches(regex);
        });
    }

    @Override
    public boolean matches(Pattern pattern) {
        return verify(input -> {
            if (input == null) {
                return false;
            }
            Matcher matcher = pattern.matcher(input);
            return matcher.matches();
        });
    }

}
