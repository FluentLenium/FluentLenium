package org.fluentlenium.core.conditions;

import com.google.common.base.Predicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringConditionsImpl extends AbstractObjectConditions<String> implements StringConditions {
    public StringConditionsImpl(final String string) {
        super(string);
    }

    public StringConditionsImpl(final String object, final boolean negation) {
        super(object, negation);
    }

    @Override
    protected StringConditionsImpl newInstance(final boolean negationValue) {
        return new StringConditionsImpl(object, negationValue);
    }

    @Override
    @Negation
    public StringConditionsImpl not() {
        return (StringConditionsImpl) super.not();
    }

    @Override
    public boolean contains(final CharSequence charSequence) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                return input.contains(charSequence);
            }
        });
    }

    @Override
    public boolean startsWith(final String prefix) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                return input.startsWith(prefix);
            }
        });
    }

    @Override
    public boolean endsWith(final String suffix) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                return input.endsWith(suffix);
            }
        });
    }

    @Override
    public boolean equalTo(final String anotherString) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                return input.equals(anotherString);
            }
        });
    }

    @Override
    public boolean equalToIgnoreCase(final String anotherString) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                return input.equalsIgnoreCase(anotherString);
            }
        });
    }

    @Override
    public boolean matches(final String regex) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                return input.matches(regex);
            }
        });
    }

    @Override
    public boolean matches(final Pattern pattern) {
        return verify(new Predicate<String>() {
            @Override
            public boolean apply(final String input) {
                if (input == null) {
                    return false;
                }
                final Matcher matcher = pattern.matcher(input);
                return matcher.matches();
            }
        });
    }

}
