package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.ArrayList;
import java.util.List;

public class Year2016Day07 {
    public static void main(String[] args) {
        var lines = InputProcessor.loadLines("2016_07.input");

        int counterStarOne = 0;
        int counterStarTwo = 0;

        final String hypernetSelector = "\\[[^]]++]"; //
        final String regularSelector = "([a-z]+)[\\[\\n\\r]+";
        for (var line : lines) {
            line += "\n"; // to ensure the last section of the line gets selected by regularselector
            String regular = line.replaceAll(hypernetSelector, "_");
            String hypernets = line.replaceAll(regularSelector, "_");

            boolean matchOutsideOfBrackets = containsAbba(regular);
            boolean matchInBrackets = containsAbba(hypernets);

            if (!matchInBrackets && matchOutsideOfBrackets) {
                counterStarOne++;
            }

            if (containsValidXYX(regular, hypernets)) {
                counterStarTwo++;
            }
        }

        Printer.println("1: " + counterStarOne);
        Printer.println("2: " + counterStarTwo);
    }

    private static boolean containsValidXYX(String regular, String hypernet) {
        var regularXYX = allXYX(regular);
        var hypernetYXY = allXYX(hypernet);

        return regularXYX.stream().anyMatch(xyx -> hypernetYXY.contains(inverseXYX(xyx)));
    }

    private static String inverseXYX(String yxy) {
        return "" + yxy.charAt(1) + yxy.charAt(0) + yxy.charAt(1);
    }

    private static List<String> allXYX(String in) {
        var out = new ArrayList<String>();
        for (int i = 3; i < in.length(); i++) {
            String substring = in.substring(i - 3, i);
            var charArray = substring.toCharArray();
            if (isXYX(charArray)) {
                out.add(substring);
            }
        }
        return out;
    }

    private static boolean isXYX(char[] chars) {
        if (chars.length != 3) throw new IllegalArgumentException("Chars length should be 3");
        return chars[0] == chars[2] && chars[0] != chars[1];
    }

    private static boolean containsAbba(String in) {
        for (int i = 4; i < in.length(); i++) {
            var subStr = in.substring(i - 4, i).toCharArray();
            if (isABBA(subStr)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isABBA(char[] chars) {
        if (chars.length != 4) throw new IllegalArgumentException("Chars length should be 4");
        return chars[0] == chars[3] && chars[1] == chars[2] && chars[0] != chars[1];
    }

}

