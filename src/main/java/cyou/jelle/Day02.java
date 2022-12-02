package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

public class Day02 {
    public static void main(String[] args) {
        var lines = InputProcessor.loadLines("Day02.input").stream()
                .map(str -> str.split(" "))
                .toList();

        int counter = 0;
        int counterStar2 = 0;
        for (var line : lines) {
            char theirs = line[0].charAt(0);
            char mine = line[1].charAt(0);
            counter += myValue(mine)+1;
            counter += matchPoints(mine, theirs);

            char roundOutcome = mine;
            counterStar2 += roundScoreSecondStar(roundOutcome);
            counterStar2 += myPickSecondStar(theirs, roundOutcome);

        }

        Printer.println("1: " + counter);
        Printer.println("2: " + counterStar2);
    }

    static int myValue(char mySelection) {
        return mySelection - 'X' + 1;
    }

    static int theirValue(char theirSelection) {
        return theirSelection - 'A' + 1;
    }

    static int matchPoints(char mine, char theirs) {
        int myValue = myValue(mine);
        int theirValue = theirValue(theirs);

        if (myValue == theirValue) return 3;
        if (myValue == (theirValue % 3 + 1)) return 6;

        return 0;
    }

    static int roundScoreSecondStar(char outcome) {
        return (myValue(outcome) - 1) * 3;
    }

    static int myPickSecondStar(char theirs, char roundResult) {
        int theirValue = theirValue(theirs);
        int outcome = roundScoreSecondStar(roundResult);
        if (outcome == 3) {
            return theirValue;
        }

        if (outcome == 6) {
            return (theirValue % 3) + 1;
        }

        if (theirValue != 1) return theirValue - 1;
        return 3;
    }
}
