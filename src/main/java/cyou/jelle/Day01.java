package cyou.jelle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Day01 {
    public static void main(String[] args) throws IOException {
        try (var lines = Files.lines(Path.of("src/main/resources/Day01.input.txt"))) {

            var integers = lines.map(line -> (line.length() > 0) ? Integer.parseInt(line) : null).toList();

            var sums = new ArrayList<Integer>();
            Integer currentSum = 0;
            for(var value: integers) {
                if(value == null) {
                    sums.add(currentSum);
                    currentSum = 0;
                }
                 else {
                     currentSum += value;
                }
            }

            System.out.println(sums.stream().max(Integer::compareTo).get());

            var maxThree = sums.stream()
                    .sorted(Comparator.comparing(Integer::intValue).reversed())
                    .toList().subList(0,3)
                    .stream().reduce(Integer::sum).get();
            System.out.println(maxThree);

        }
    }
}
