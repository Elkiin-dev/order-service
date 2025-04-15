package com.order.domain.models;

import com.order.domain.exceptions.InvalidSeatLengthException;
import com.order.domain.exceptions.InvalidSeatLetterException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Seat {
    private final String letter;
    private final Integer number;

    public Seat(String letter, Integer number) {
        this.ensureSeatLetterExist(letter);
        this.ensureSeatNumberIsGreaterThanZero(number);
        this.letter = letter;
        this.number = number;
    }

    private void ensureSeatNumberIsGreaterThanZero(Integer number) {
        if (number == null || number <= 0) {
            throw new InvalidSeatLengthException();
        }
    }

    private void ensureSeatLetterExist(String letter) {
        if (letter == null || letter.isBlank()) {
            throw new InvalidSeatLetterException();
        }
    }

    @Override
    public String toString() {
        return letter + number;
    }
}
