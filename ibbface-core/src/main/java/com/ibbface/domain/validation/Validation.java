/*
 * Copyright (c) 2013. ibbface.com All rights reserved.
 * @(#) Validation.java 2013-07-28 18:33
 */

package com.ibbface.domain.validation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Fuchun
 * @since 1.0
 */
public class Validation {

    private final List<ValidationError> errors;

    public static Validation newValidation() {
        return new Validation();
    }

    public Validation() {
        errors = Lists.newLinkedList();
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    /**
     * Clear all error.
     */
    public void clear() {
        errors.clear();
    }

    /**
     * Returns {@code true} if current context has error, otherwise {@code false}.
     */
    public boolean hasError() {
        return errors.size() > 0;
    }

    public Map<String, ValidationError> asMap() {
        Map<String, ValidationError> map = Maps.newHashMap();
        for (ValidationError error : errors) {
            if (!map.containsKey(error.getKey())) {
                map.put(error.getKey(), error);
            }
        }
        return map;
    }

    /**
     * Add an error to the validation context.
     *
     * @param message the error message.
     * @param args    the error message args.
     * @return Returns validated result (never {@code null}).
     */
    @Nonnull
    public ValidationResult error(String message, Object... args) {
        return new ValidationResult(new ValidationError(), false)
                .message(message, args);
    }

    /**
     * Use given {@link Validator} validate the {@code value}, returns
     * validated result.
     *
     * @param check the {@link Validator} object.
     * @param value the validated value.
     * @return Returns validated result.
     */
    @Nonnull
    public ValidationResult apply(@Nonnull Validator check, Object value) {
        if (check.isSatisfied(value)) {
            return ValidationResult.ok();
        }

        ValidationError error = ValidationError.newError(
                check.getDefaultMessage());
        errors.add(error);
        return ValidationResult.error(error);
    }

    /**
     * Test that the argument is non-null and non-empty (if string or list)
     *
     * @param value the checked value.
     * @return Returns validated result.
     */
    public ValidationResult required(Object value) {
        return apply(Validator.required(), value);
    }

    public <T extends Number> ValidationResult min(T value, T min) {
        return apply(Validator.min(min), value);
    }

    public <T extends Number> ValidationResult max(T value, T max) {
        return apply(Validator.max(max), value);
    }

    public ValidationResult minSize(Object value, int min) {
        return apply(Validator.minSize(min), value);
    }

    public ValidationResult maxSize(Object value, int max) {
        return apply(Validator.maxSize(max), value);
    }

    public <T extends Number> ValidationResult range(
            T value, T min, T max) {
        return apply(Validator.range(min, max), value);
    }

    public <T extends CharSequence> ValidationResult match(
            T value, Pattern pattern) {
        return apply(Validator.match(pattern), value);
    }

    public <T extends CharSequence> ValidationResult match(
            T value, String regexp) {
        return apply(Validator.match(regexp), value);
    }

    public ValidationResult email(String email) {
        return apply(Validator.email(), email);
    }

    /**
     * Apply a group of {@link Validator validator}s to a field, in order,
     * and return the {@link ValidationResult} from the first one that fails,
     * or the last one that succeeds.
     *
     * @param value  the checked value。
     * @param checks the validators。
     * @return Returns validate result (never {@code null})。
     */
    @Nonnull
    public ValidationResult check(Object value, Validator... checks) {
        ValidationResult result = null;
        if (checks != null && checks.length > 0) {
            for (Validator check : checks) {
                result = apply(check, value);
                if (!result.isOk()) {
                    return result;
                }
            }
        }
        if (result == null) {
            result = ValidationResult.ok();
        }
        return result;
    }
}
