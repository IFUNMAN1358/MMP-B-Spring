package com.nagornov.multimicroserviceproject.authservice.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@UtilityClass
public class CodeUtils {

    private final SecureRandom random = new SecureRandom();
    private final String digits = "123456789";
    private final String letters = "ABCDEFGHIJKLMNPQRSTUVWXYZ";

    public static String generateRandomCodeDigit(Integer digitCount) {
        return generateRandomCharacters(digitCount);
    }

    public static String generateRandomCodeDigitChars(Integer digitCount, Integer letterCount) {
        return generateMixedCode(digitCount, letterCount);
    }

    private String generateRandomCharacters(int length) {
        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(CodeUtils.digits.charAt(random.nextInt(CodeUtils.digits.length())));
        }
        return code.toString();
    }

    private String generateMixedCode(int digitCount, int letterCount) {
        List<Character> codeList = new ArrayList<>();
        for (int i = 0; i < digitCount; i++) {
            codeList.add(digits.charAt(random.nextInt(digits.length())));
        }
        for (int i = 0; i < letterCount; i++) {
            codeList.add(letters.charAt(random.nextInt(letters.length())));
        }
        Collections.shuffle(codeList, random);
        StringBuilder code = new StringBuilder();
        for (char c : codeList) {
            code.append(c);
        }
        return code.toString();
    }

}
