package cyou.jelle;

import cyou.jelle.util.Printer;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Objects;

public class Year2016Day05 {
    public static void main(String[] args) {
        var input = "ugkcyxxp";
        StringBuilder passwordBuilder = star1(input);

        var counter = 0;

        String[] password = new String[8];
        while (Arrays.stream(password).anyMatch(Objects::isNull)) {
            var hash = DigestUtils.md5Hex((input + counter));
            if (hash.startsWith("00000")) {
                var pos = hash.charAt(5) - '0';
                if (pos >= 0 && pos < 8 && password[pos] == null)
                    password[pos] = String.valueOf(hash.charAt(6));
            }
            counter++;
        }

        Printer.println("1: " + passwordBuilder);
        Printer.println("2: " + String.join("", password) + "  - guesses: " + counter);
    }

    private static StringBuilder star1(String input) {
        var counter = 0;
        StringBuilder passwordBuilder = new StringBuilder(8);
        while (passwordBuilder.length() < 8) {
            var hash = DigestUtils.md5Hex((input + counter));
            if (hash.startsWith("00000")) {
                passwordBuilder.append(hash.charAt(5));
            }
            counter++;
        }
        return passwordBuilder;
    }
}
