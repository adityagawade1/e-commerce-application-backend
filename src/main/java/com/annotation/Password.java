package com.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.constraints.Pattern;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Pattern.List({
        @Pattern(regexp = "^[a-zA-Z0-9]{10}$", message = "Password should be 10 characters."),
        @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "Password should contain at least one uppercase letter."),
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z\\d!@#$%^&*()_+]{10,}$" ,message="password contain atleast one special character")
})
public @interface Password {
    String message() default "Invalid password.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}