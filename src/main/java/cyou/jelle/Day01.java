package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.Comparator;
import java.util.List;

public class Day01 {
    public static void main(String[] args) {
        List<List<String>> groups = InputProcessor.loadLinesSplitGroupsOnEmptyString("Day01.input.txt");
        List<Integer> highToLowSums = groups.stream()
                .map(numberStrings ->
                        numberStrings.stream()
                                .map(Integer::parseInt)
                                .reduce(Integer::sum).orElseThrow())
                .sorted(Comparator.comparing(Integer::intValue).reversed())
                .toList();


        Integer maxValue = highToLowSums.get(0);
        Printer.println("1: " + maxValue);

        var maxThree = highToLowSums.get(0) + highToLowSums.get(1) + highToLowSums.get(2);
        Printer.println("2: " + maxThree);
    }
}
