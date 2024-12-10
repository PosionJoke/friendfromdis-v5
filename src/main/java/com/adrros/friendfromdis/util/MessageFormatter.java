
package com.adrros.friendfromdis.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class MessageFormatter {
    public static List<String> prepareMessage(String message) {
        String formatted = !message.isEmpty() && IsCorrectPrefix.isPrefixCorrect(message) ? message.substring(1) : message;
        return Arrays.stream(formatted.split("\\s+")).toList();
    }
}
