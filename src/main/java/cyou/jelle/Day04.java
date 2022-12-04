package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day04 {
    public static void main(String[] args) {
        var starOneAnswer = getInputAsSortedPairsList().filter(Day04::starOneFilter).count();
        var starTwoAnswer = getInputAsSortedPairsList().filter(Day04::starTwoFilter).count();

        Printer.println("1: " + starOneAnswer);
        Printer.println("2: " + starTwoAnswer);
    }

    private static Stream<List<Integer>> getInputAsSortedPairsList() {
        return InputProcessor.loadLines("Day04.input").stream()
                .map(s -> s.split(","))
                .map(strings -> {
                    var one = getIntegerList(strings, 0);
                    var two = getIntegerList(strings, 1);
                    // Put the larger pair first
                    if (one.get(1) - one.get(0) > two.get(1) - two.get(0)) {
                        one.addAll(two);
                        return one;
                    } else {
                        two.addAll(one);
                        return two;
                    }
                });
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
