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
            counter += myValue(mine) + 1;
            counter += firstMatchPoints(mine, theirs);

            char secondStarOutcome = mine;
            counterStar2 += secondMatchPoints(secondStarOutcome);
            counterStar2 += myValueSecondStar(theirs, secondStarOutcome) + 1;
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
