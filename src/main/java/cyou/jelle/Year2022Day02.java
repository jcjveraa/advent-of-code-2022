package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Year2022Day02 {
    public static void main(String[] args) {
        Function<String, List<Character>> stringToCharListMapper =
                inputLine -> Arrays.stream(inputLine.split(" "))
                        .map(singleLetterString -> singleLetterString.charAt(0)).toList();

        var charPairs = InputProcessor.loadLines("Year2022Day02.input", stringToCharListMapper);

        int counter = 0;
        int counterStar2 = 0;
        for (var charPair : charPairs) {
            char theirs = charPair.get(0);
            char mineOrSecondStarOutcome = charPair.get(1);
            counter += myValue(mineOrSecondStarOutcome) + 1;
            counter += firstMatchPoints(mineOrSecondStarOutcome, theirs);

            counterStar2 += secondMatchPoints(mineOrSecondStarOutcome);
            counterStar2 += myValueSecondStar(theirs, mineOrSecondStarOutcome) + 1;
        }

        Printer.println("1: " + counter);
        Printer.println("2: " + counterStar2);
    }

    static int myValue(char mySelection) {
        return mySelection - 'X';
    }

    static int theirValue(char theirSelection) {
        return theirSelection - 'A';
    }

    static int firstMatchPoints(char mine, char theirs) {
        int myValue = myValue(mine);
        int theirValue = theirValue(theirs);

        if (myValue == theirValue) return 3;
        if (myValue == (theirValue + 1) % 3) return 6;
        return 0;
    }

    static int secondMatchPoints(char outcome) {
        return myValue(outcome) * 3;
    }

    static int myValueSecondStar(char theirs, char roundResult) {
        int theirValue = theirValue(theirs);

        if (roundResult == 'Y') return theirValue;
        if (roundResult == 'Z') return ((theirValue + 1) % 3);
        return Math.floorMod(theirValue - 1, 3);
    }
}
