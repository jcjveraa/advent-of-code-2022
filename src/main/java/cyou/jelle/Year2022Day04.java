package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Year2022Day04 {
    public static void main(String[] args) {
        var starOneAnswer = getInputAsListOfPairs().filter(Year2022Day04::starOneFilter).count();
        var starTwoAnswer = getInputAsListOfPairs().filter(Year2022Day04::starTwoFilter).count();

        Printer.println("1: " + starOneAnswer);
        Printer.println("2: " + starTwoAnswer);
    }

    private static Stream<List<Integer>> getInputAsListOfPairs() {
        return InputProcessor.loadLines("Year2022Day04.input").stream()
                .map(s -> s.split(","))
                .map(strings -> {
                    var firstJobIntPair = getIntegerList(strings, 0);
                    var secondJobIntPair = getIntegerList(strings, 1);
                    if (range(firstJobIntPair) > range(secondJobIntPair))
                        return concatLists(firstJobIntPair, secondJobIntPair);
                    else
                        return concatLists(secondJobIntPair, firstJobIntPair);
                });
    }

    private static List<Integer> concatLists(List<Integer> first, List<Integer> second) {
        first.addAll(second);
        return first;
    }

    private static int range(List<Integer> integers) {
        return integers.get(1) - integers.get(0);
    }


    private static boolean starOneFilter(List<Integer> ints) {
        // full overlap
        return ints.get(0) <= ints.get(2) && ints.get(1) >= ints.get(3);
    }

    private static boolean starTwoFilter(List<Integer> ints) {
        return starOneFilter(ints)
                // Overlap at beginning of shorter list (index 2 twice)
                || ints.get(0) <= ints.get(2) && ints.get(1) >= ints.get(2)
                // Overlap at end of shorter list (index 3 twice)
                || ints.get(0) <= ints.get(3) && ints.get(1) >= ints.get(3);
    }

    private static List<Integer> getIntegerList(String[] strings, int index) {
        return new ArrayList<>(Arrays.stream(strings[index].split("-")).map(Integer::parseInt).toList());
    }

}
