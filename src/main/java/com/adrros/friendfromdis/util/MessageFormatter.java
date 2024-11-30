
package com.adrros.friendfromdis.util;

import com.adrros.friendfromdis.BotConfigVariables;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageFormatter {
    public MessageFormatter() {
    }

    public static List<String> prepareMessage(String message) {
        String formatted = message.length() > 0 && containsAnyPrefix(message) ? message.substring(1) : message;
        return (List)Arrays.stream(formatted.split("\\s+")).collect(Collectors.toList());
    }

    private static boolean containsAnyPrefix(final String message) {
        Stream<String> var10000 = BotConfigVariables.prodPrefixes.stream();
        Objects.requireNonNull(message);
        boolean var2;
        if (!var10000.anyMatch(n -> n.startsWith(n))) {
            Stream<String> var1 = BotConfigVariables.devPrefixes.stream();
            Objects.requireNonNull(message);
            if (!var1.anyMatch(n -> n.startsWith(n))) {
                var2 = false;
                return var2;
            }
        }

        var2 = true;
        return var2;
    }
}
