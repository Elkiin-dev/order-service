package com.order.domain.models;

import com.order.domain.exceptions.InvalidEmailLengthException;
import com.order.domain.exceptions.InvalidEmailSpecialChartException;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class BuyerEmail {
    private final String value;

    public static final Pattern SPECIAL_EMAIL_CHAR = Pattern.compile("@");

    public BuyerEmail(String value) {
        this.ensureIsValidEmail(value);
        this.value = value;
    }

    private void ensureIsValidEmail(final String value) {
        if (value.isBlank()) {
            throw new InvalidEmailLengthException();
        }
        if (!SPECIAL_EMAIL_CHAR.matcher(value).find()) {
            throw new InvalidEmailSpecialChartException(SPECIAL_EMAIL_CHAR.pattern(), value);
        }
    }
}
