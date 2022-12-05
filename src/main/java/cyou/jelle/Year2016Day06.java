package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Year2016Day06 {
    public static void main(String[] args) {
        var columnFrequencies = new ArrayList<Map<Character, Integer>>();
        var lines = InputProcessor.loadLines("2016_06.input");
        int outputLength = lines.get(0).length();
        for (int i = 0; i < outputLength; i++) {
            columnFrequencies.add(new HashMap<>());
        }

        for (var line : lines) {
            for (int i = 0; i < outputLength; i++) {
                char c = line.charAt(i);
                var map = columnFrequencies.get(i);
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }

        Printer.println("1: " + star1Builder(columnFrequencies));
        Printer.println("2: " + star2Builder(columnFrequencies));
    }

    private static StringBuilder star1Builder(ArrayList<Map<Character, Integer>> columnFrequencies) {
        var result = new StringBuilder();
        columnFrequencies.stream().map(map -> map.entrySet()
                        .stream()
                        // why is this so complicated? Would expect 'a' to be recognized as an Entry without casting
                        .sorted(Comparator.comparingInt(a -> (int) ((Map.Entry<?, ?>) a).getValue())
                                .reversed())
                        .toList().get(0))
                .forEach(characterIntegerEntry -> result.append(characterIntegerEntry.getKey()));
        return result;
    }

    private static StringBuilder star2Builder(ArrayList<Map<Character, Integer>> columnFrequencies) {
        var result = new StringBuilder();
        columnFrequencies.stream().map(map -> map.entrySet()
                        .stream()
                        .sorted(Comparator.comparingInt(a -> (int) ((Map.Entry<?, ?>) a).getValue())
                                // exactly the same as 1 but not reversed
                        )
                        .toList().get(0))
                .forEach(characterIntegerEntry -> result.append(characterIntegerEntry.getKey()));
        return result;
    }


}
