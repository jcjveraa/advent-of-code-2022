package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Day03 {
    public static void main(String[] args) {

        var starOneAnswer = InputProcessor.loadLines("Day03.input", starOneMapper())
                .stream().reduce(Integer::sum).orElseThrow();

        var starTwoInput = InputProcessor.loadLines("Day03.input");
        int starTwoAnswer = getStarTwoAnswer(starTwoInput);

        Printer.println("1: " + starOneAnswer);
        Printer.println("2: " + starTwoAnswer);
    }

    private static int getStarTwoAnswer(List<String> starTwoInput) {
        int answer = 0;
        for (int i = 0; i < starTwoInput.size(); i += 3) {
            var packOne = addAllToSet(starTwoInput.get(i).toCharArray());
            var packTwo = addAllToSet(starTwoInput.get(i+1).toCharArray());
            var packThree = addAllToSet(starTwoInput.get(i+2).toCharArray());
            packOne.retainAll(packTwo);
            packOne.retainAll(packThree);

            Character remainingChar = (Character) packOne.toArray()[0];
            answer += charToValue(remainingChar);
        }
        return answer;
    }

    static Function<String, Integer> starOneMapper() {
        return str -> {
            var length = str.length();
            var firstPart = str.substring(0, length / 2).toCharArray();
            var secondPart = str.substring(length / 2).toCharArray();
            var setOne = addAllToSet(firstPart);
            var setTwo = addAllToSet(secondPart);

            setOne.retainAll(setTwo);

            Character remainingChar = (Character) setOne.toArray()[0];
            return charToValue(remainingChar);
        };
    }

    private static int charToValue(Character remainingChar) {
        if (Character.isLowerCase(remainingChar)) return remainingChar - 'a' + 1;
        else return remainingChar - 'A' + 27;
    }

    private static Set<Character> addAllToSet(char[] chars) {
        return new HashSet<>(Arrays.stream(ArrayUtils.toObject(chars)).toList());
    }
}
