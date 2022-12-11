package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

public class Year2022Day11 {
    private static List<Monkey> monkeys = new ArrayList<>();

    public static void main(String[] args) {
        var inputLines = InputProcessor.loadLines("Year2022Day11.txt").iterator();
        var inputScanner = InputProcessor.loadLines("Year2022Day11.txt").iterator();

        int id = 0;
        while (inputScanner.hasNext()) {
            var line = inputScanner.next();
            if(line.startsWith("Monkey")) {
                Monkey monkey = new Monkey();
                monkey.id = id;
                id++;
                monkeys.add(monkey);
            }
        }

        id = 0;
        while (inputLines.hasNext()) {
            var line = inputLines.next();
            while (!line.startsWith("Monkey")) line = inputLines.next();

            var monkey = monkeys.get(id);
            id++;
            var items = inputLines.next().trim().replace("Starting items: ", "").split(", ");
            Arrays.stream(items).map(Long::parseLong).forEach(i -> monkey.items.add(i));
            var op = inputLines.next().trim().replace("Operation: new = old ", "").split(" ");
            monkey.operation = getOperation(op[0], op[1]);
            var resultHandler = inputLines.next().trim().replace("Test: divisible by ", "");
            monkey.resultHandler = i -> i % Integer.parseInt(resultHandler) == 0;
            var trueMonkeyId = inputLines.next().trim().replace("If true: throw to monkey ", "");
            monkey.ifTrue = monkeys.get(Integer.parseInt(trueMonkeyId));
            var falseMonkeyId = inputLines.next().trim().replace("If false: throw to monkey ", "");
            monkey.ifFalse = monkeys.get(Integer.parseInt(falseMonkeyId));

        }



        for (int i = 0; i < 20; i++) {
            for (var monkey : monkeys) {
                monkey.inspect();
            }
        }

        var sortedMonkeys = monkeys.stream().sorted(Comparator.comparingInt(m -> m.inspectionCounter)).toList();
        var sortedMonkeysSize = sortedMonkeys.size();

        Printer.println("1: " + sortedMonkeys.get(sortedMonkeysSize - 2).inspectionCounter
                * sortedMonkeys.get(sortedMonkeysSize - 1).inspectionCounter);

    }

    private static LongFunction<Long> getOperation(String operator, String value) {
        boolean isSelfReference = value.equals("old");
        if (operator.equals("+")) {
            if(isSelfReference) return i -> i + i;
            return i -> i + Integer.parseInt(value);
        } else if (operator.equals("*")) {
            if(isSelfReference) return i -> i * i;
            return i -> i * Integer.parseInt(value);
        }
        throw new IllegalArgumentException();
    }


    private static class Monkey {
        int id;
        int inspectionCounter = 0;
        List<Long> items = new ArrayList<>();
        LongFunction<Long> operation;
        LongFunction<Boolean> resultHandler;

        Monkey ifTrue;
        Monkey ifFalse;

        void inspect() {
            while (!items.isEmpty()) {
                var item = items.remove(0);
                item = operation.apply(item);
                item = item / 3;
                var targetMonkey = Boolean.TRUE.equals(resultHandler.apply(item)) ? ifTrue : ifFalse;
                targetMonkey.items.add(item);
                inspectionCounter++;
            }
        }

        @Override
        public String toString() {
            return "" + id;
        }
    }
}
