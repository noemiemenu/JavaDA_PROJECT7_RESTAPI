package com.nnk.springboot;

import com.nnk.springboot.config.PasswordValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

public class PasswordValidatorTest {

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("validPasswordProvider")
    void test_password_regex_valid(String password) {
        Assertions.assertTrue(PasswordValidator.isValid(password));
    }

    @ParameterizedTest(name = "#{index} - Run test with password = {0}")
    @MethodSource("invalidPasswordProvider")
    void test_password_regex_invalid(String password) {
        Assertions.assertFalse(PasswordValidator.isValid(password));
    }

     static Stream<String> validPasswordProvider() {
        return Stream.of(
                "AAAbbbccc@123",
                "Hello world$123",
                "A!@#&()–a1",               // test punctuation part 1
                "A[{}]:;',?/*a1",           // test punctuation part 2
                "A~$^+=<>a1",               // test symbols
                "0123456789$abcdefgAB",     // test 20 chars
                "123Aa$Aa"                  // test 8 chars
        );
    }

    // At least
    // one lowercase character,
    // one uppercase character,
    // one digit,
    // one special character
    // and length between 8 to 20.
    static Stream<String> invalidPasswordProvider() {
        return Stream.of(
                "12345678",                 // invalid, only digit
                "abcdefgh",                 // invalid, only lowercase
                "ABCDEFGH",                 // invalid, only uppercase
                "abc123$$$",                // invalid, at least one uppercase
                "ABC123$$$",                // invalid, at least one lowercase
                "ABC$$$$$$",                // invalid, at least one digit
                "java REGEX 123",           // invalid, at least one special character
                "java REGEX 123 %",         // invalid, % is not in the special character group []
                "________",                 // invalid
                "--------",                 // invalid
                " ",                        // empty
                "");                        // empty
    }
}
