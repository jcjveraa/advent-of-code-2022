package cyou.jelle;

import cyou.jelle.util.Printer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;

public class Day01 {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("src/main/resources/Day01.input.txt"))) {

            var integers = lines
                    .map(line -> (line.length() > 0) ? Integer.parseInt(line) : Integer.MIN_VALUE)
                    .toList();

            var sums = new ArrayList<Integer>();
            int currentSum = 0;
            for (var value : integers) {
                if (value == Integer.MIN_VALUE) {
                    sums.add(currentSum);
                    currentSum = 0;
                } else {
                    currentSum += value;
                }
            }

            Integer maxValue = sums.stream().max(Integer::compareTo).orElseGet(() -> -1);
            Printer.println(maxValue);

            var maxThree = sums.stream()
                    .sorted(Comparator.comparing(Integer::intValue).reversed())
                    .limit(3)
                    .reduce(Integer::sum).orElseGet(() -> -1);
           Printer.println(maxThree);

        }
    }
}
