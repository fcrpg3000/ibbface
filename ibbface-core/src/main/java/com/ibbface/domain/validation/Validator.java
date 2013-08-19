/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Validator.java 2013-07-28 18:46
 */

package com.ibbface.domain.validation;

import com.google.common.collect.Iterables;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A validator interface.
 *
 * @author Fuchun
 * @since 1.0
 */
public abstract class Validator {

    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*" +
                    "@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[a-zA-Z0-9](?:[\\w-]*[\\w])?");

    private static final MatchPattern EMAIL_MATCH_PATTERN =
            new MatchPattern(EMAIL_PATTERN);

    /**
     * Validates the value is satisfied conditions.
     *
     * @param value the value.
     * @return Returns {@code true} if the value satisfied conditions, otherwise {@code false}.
     */
    public abstract boolean isSatisfied(Object value);

    /**
     * Returns this validator default message.
     */
    public abstract String getDefaultMessage();

    public static Validator required() {
        return Required.INSTANCE;
    }

    public static <T extends Number> Validator min(T minValue) {
        return new Min<T>(minValue);
    }

    public static Validator minSize(int minSize) {
        return new MinSize(minSize);
    }

    public static <T extends Number> Validator max(T maxValue) {
        return new Max<T>(maxValue);
    }

    public static Validator maxSize(int maxSize) {
        return new MaxSize(maxSize);
    }

    public static <T extends Number> Validator range(T minValue, T maxValue) {
        return new Range<T>(new Min<T>(minValue), new Max<T>(maxValue));
    }

    public static Validator match(String regexp) {
        return new Match(regexp);
    }

    public static Validator match(Pattern pattern) {
        return new MatchPattern(pattern);
    }

    public static Validator email() {
        return new Email(EMAIL_MATCH_PATTERN);
    }

    /**
     * The required implementation.
     */
    static class Required extends Validator {

        private static final Required INSTANCE = new Required();

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return ((String) value).length() > 0;
            } else if (value instanceof CharSequence) {
                return ((CharSequence) value).length() > 0;
            } else if (value instanceof Number) {
                if (value instanceof Long) {
                    return ((Number) value).longValue() != 0;
                } else if (value instanceof Float) {
                    return ((Float) value) != 0.0f;
                } else if (value instanceof Double) {
                    return ((Double) value) != 0.0d;
                } else {
                    return ((Number) value).intValue() != 0;
                }
            } else if (value instanceof Boolean) {
                return (Boolean) value;
            } else if (value.getClass().isArray()) {
                return ((Object[]) value).length > 0;
            } else if (value instanceof Iterable) {
                return Iterables.size((Iterable<?>) value) > 0;
            } else if (value instanceof Map) {
                return ((Map<?, ?>) value).size() > 0;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return "Required";
        }
    }

    static class Min<T extends Number> extends Validator {

        final T min;

        Min(T min) {
            this.min = min;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof Number) {
                if (value instanceof Long) {
                    return ((Long) value) >= min.longValue();
                } else if (value instanceof BigDecimal) {
                    BigDecimal minDecimal = min instanceof BigDecimal ?
                            (BigDecimal) min : BigDecimal.valueOf(min.longValue());
                    return ((BigDecimal) value).compareTo(minDecimal) >= 0;
                } else if (value instanceof BigInteger) {
                    BigInteger minInteger = min instanceof BigInteger ?
                            (BigInteger) min : BigInteger.valueOf(min.longValue());
                    return ((BigInteger) value).compareTo(minInteger) >= 0;
                } else {
                    return ((Number) value).intValue() >= min.intValue();
                }
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return String.format("Minimum(%s)", min);
        }
    }

    static class MinSize extends Validator {

        final int min;

        MinSize(int min) {
            this.min = min;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return ((String) value).length() >= min;
            } else if (value instanceof CharSequence) {
                return value.toString().length() >= min;
            } else if (value.getClass().isArray()) {
                return ((Object[]) value).length >= min;
            } else if (value instanceof Iterable) {
                return Iterables.size((Iterable<?>) value) >= min;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return String.format("Minimum size is %s", min);
        }
    }

    static class Max<T extends Number> extends Validator {

        final T max;

        Max(T max) {
            this.max = max;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof Number) {
                if (value instanceof Long) {
                    return ((Long) value) <= max.longValue();
                } else if (value instanceof BigDecimal) {
                    BigDecimal maxDecimal = max instanceof BigDecimal ?
                            (BigDecimal) max : BigDecimal.valueOf(max.longValue());
                    return ((BigDecimal) value).compareTo(maxDecimal) <= 0;
                } else if (value instanceof BigInteger) {
                    BigInteger maxInteger = max instanceof BigInteger ?
                            (BigInteger) max : BigInteger.valueOf(max.longValue());
                    return ((BigInteger) value).compareTo(maxInteger) <= 0;
                } else {
                    return ((Number) value).intValue() <= max.intValue();
                }
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return String.format("Maximum(%s)", max);
        }
    }

    static class MaxSize extends Validator {

        final int max;

        MaxSize(int max) {
            this.max = max;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof String) {
                return ((String) value).length() <= max;
            } else if (value instanceof CharSequence) {
                return value.toString().length() <= max;
            } else if (value.getClass().isArray()) {
                return ((Object[]) value).length <= max;
            } else if (value instanceof Iterable) {
                return Iterables.size((Iterable<?>) value) <= max;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return String.format("Maximum size is %s", max);
        }
    }

    static class Range<T extends Number> extends Validator {

        final Min<T> min;
        final Max<T> max;

        Range(Min<T> min, Max<T> max) {
            this.min = min;
            this.max = max;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            return min.isSatisfied(value) && max.isSatisfied(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return String.format("Range[%s,%s]", min.min, max.max);
        }
    }

    static class Match extends Validator {

        final MatchPattern matchPattern;

        Match(String regexp) {
            matchPattern = new MatchPattern(Pattern.compile(regexp));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isSatisfied(Object value) {
            return matchPattern.isSatisfied(value);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getDefaultMessage() {
            return matchPattern.getDefaultMessage();
        }
    }

    static class MatchPattern extends Validator {

        final Pattern pattern;

        MatchPattern(Pattern pattern) {
            this.pattern = pattern;
        }

        /**
         * Validates the value is satisfied conditions.
         *
         * @param value the value.
         * @return Returns {@code true} if the value satisfied conditions, otherwise {@code false}.
         */
        @Override
        public boolean isSatisfied(Object value) {
            String target = null;
            if (value instanceof String) {
                target = (String) value;
            } else if (value instanceof CharSequence) {
                target = value.toString();
            }
            return target != null && pattern.matcher(target).find();
        }

        /**
         * Returns this validator default message.
         */
        @Override
        public String getDefaultMessage() {
            return String.format("Must match `%s`", pattern.pattern());
        }
    }

    static class Email extends Validator {

        final MatchPattern pattern;

        Email(MatchPattern pattern) {
            this.pattern = pattern;
        }

        /**
         * Validates the value is satisfied conditions.
         *
         * @param value the value.
         * @return Returns {@code true} if the value satisfied conditions, otherwise {@code false}.
         */
        @Override
        public boolean isSatisfied(Object value) {
            return pattern.isSatisfied(value);
        }

        /**
         * Returns this validator default message.
         */
        @Override
        public String getDefaultMessage() {
            return "Must be a valid email";
        }
    }
}
