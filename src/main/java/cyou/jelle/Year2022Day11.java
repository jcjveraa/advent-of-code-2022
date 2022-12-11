package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Year2022Day11 {
    private static List<Monkey> monkeys;
    private static Long divisor = 1L;

    public static void main(String[] args) {
        monkeys = makemeSomeMonkeys(true);
        int rounds = 20;
        long result = getResult(rounds);
        Printer.println("1: " + result);

        monkeys = makemeSomeMonkeys(false);
        rounds = 10_000;
        result = getResult(rounds);
        Printer.println("2: " + result);

    }

    private static long getResult(int rounds) {
        for (int i = 0; i < rounds; i++) {

            for (var monkey : monkeys) {
                monkey.inspect();
            }

        }

        var sortedMonkeys = monkeys.stream().sorted(Comparator.comparingLong(m -> m.inspectionCounter)).toList();
        var sortedMonkeysSize = sortedMonkeys.size();

        return sortedMonkeys.get(sortedMonkeysSize - 2).inspectionCounter
                * sortedMonkeys.get(sortedMonkeysSize - 1).inspectionCounter;
    }

    private static List<Monkey> makemeSomeMonkeys(boolean divideByThree) {
        divisor = 1L;
        var inputLines = InputProcessor.loadLines("Year2022Day11.txt").iterator();
        var inputScanner = InputProcessor.loadLines("Year2022Day11.txt").iterator();
        int id = 0;
        var apes = new ArrayList<Monkey>();
        while (inputScanner.hasNext()) {
            var line = inputScanner.next();
            if (line.startsWith("Monkey")) {
                Monkey monkey = new Monkey();
                monkey.id = id;
                id++;
                apes.add(monkey);
            }
        }

        id = 0;
        while (inputLines.hasNext()) {
            var line = inputLines.next();
            while (!line.startsWith("Monkey")) line = inputLines.next();

            var monkey = apes.get(id);
            id++;
            var items = inputLines.next().trim().replace("Starting items: ", "").split(", ");
            Arrays.stream(items).map(Long::parseLong).forEach(i -> monkey.items.add(i));
            var op = inputLines.next().trim().replace("Operation: new = old ", "").split(" ");
            monkey.doDivideByThree = divideByThree;
            monkey.operation = getOperation(op[0], op[1]);
            var resultHandler = inputLines.next().trim().replace("Test: divisible by ", "");
            long divideBy = Long.parseLong(resultHandler);
            monkey.resultHandler = i -> i % divideBy;
            divisor *= divideBy;
            var trueMonkeyId = inputLines.next().trim().replace("If true: throw to monkey ", "");
            monkey.ifTrue = apes.get(Integer.parseInt(trueMonkeyId));
            var falseMonkeyId = inputLines.next().trim().replace("If false: throw to monkey ", "");
            monkey.ifFalse = apes.get(Integer.parseInt(falseMonkeyId));

        }

        return apes;
    }

    private static Function<Long, Long> getOperation(String operator, String value) {
        boolean isSelfReference = value.equals("old");
        if (operator.equals("+")) {
            if (isSelfReference) return i -> i + i;
            return i -> i + Integer.parseInt(value);
        } else if (operator.equals("*")) {
            if (isSelfReference) return i -> i * i;
            return i -> i * Integer.parseInt(value);
        }
        throw new IllegalArgumentException();
    }


    private static class Monkey {
        int id;
        long inspectionCounter = 0;
        List<Long> items = new ArrayList<>();
        Function<Long, Long> operation;
        Function<Long, Long> resultHandler;

        Monkey ifTrue;
        Monkey ifFalse;
        boolean doDivideByThree;

        void inspect() {
            while (!items.isEmpty()) {
                var item = items.remove(0);
                item = operation.apply(item) ;
                if (doDivideByThree) {
                    item = item / 3;
                }
                else {
                    item = item % divisor;
                }
                Long test = resultHandler.apply(item);
                var targetMonkey = test.equals(0L) ? ifTrue : ifFalse;
                targetMonkey.items.add(item);

                inspectionCounter++;
            }
        }

        @Override
        public String toString() {
            return "id=" + id + ", ictr=" + inspectionCounter + ", itmCount=" + items.size();
        }
    }
}
