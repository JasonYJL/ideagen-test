package com.jason.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "1 + 1, 2",
            "2 * 2, 4",
            "1 + 2 + 3, 6",
            "6 / 2, 3",
            "11 + 23, 34",
            "11.1 + 23, 34.1",
            "1 + 1 * 3, 4",
            "( 11.5 + 15.4 ) + 10.1, 37",
            "23 - ( 29.3 - 12.5 ), 6.2",
            "10 - ( 2 + 3 * ( 7 - 5 ) ), 2"
    })
    void itShowCalculateCorrectValueFromEquation(String equation, double expected) {
        // given
        // when
        double value = CalculatorUtils.calculate(equation);

        // then
        Assertions.assertEquals(expected, value);
    }
}
