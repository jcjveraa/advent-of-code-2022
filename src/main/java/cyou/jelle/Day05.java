package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.*;
import java.util.stream.Collectors;

public class Day05 {
    private static final List<Deque<Character>> stacks = new ArrayList<>();
    private static final ArrayList<String> crateInputLines = new ArrayList<>();

    public static void main(String[] args) {
        var lineIterator = InputProcessor.loadLines("Day05.input").iterator();

        buildStacks(lineIterator);
        fillStacks();

        lineIterator.next(); // skip emty line
        while (lineIterator.hasNext()) {
            var instruction = Arrays.stream(lineIterator.next()
                            .replaceAll("[a-zA-Z ]+", " ").trim().split(" "))
                    .map(Integer::parseInt).toList();
            moveCrate(instruction.get(0), instruction.get(1), instruction.get(2));
        }

        var starOneAnswer = stacks.stream().map(deque -> deque.peekLast().toString()).collect(Collectors.joining());

        Printer.println("1: " + starOneAnswer);
//        Printer.println("2: " + starTwoAnswer);
    }

    private static void fillStacks() {
        for (int i = crateInputLines.size() - 1; i >= 0; i--) {
            var line = crateInputLines.get(i);
            for (int j = 0; j < stacks.size(); j++) {
                var stack = stacks.get(j);
                int index = 1 + j * 4;
                char crate = line.length() >= index ? line.charAt(index) : ' ';
                if (crate != ' ') {
                    stack.addLast(crate);
                }
            }
        }
    }

    private static void buildStacks(Iterator<String> lineIterator) {
        while (true) {
            var line = lineIterator.next();
            if (line.charAt(1) == '1') {
                var stackNumbers = line.trim().split("\\s+");
                for (int i = 0; i < stackNumbers.length; i++) {
                    stacks.add(new ArrayDeque<>());
                }
                break;
            } else {
                crateInputLines.add(line);
            }
        }
    }

    private static void moveCrate(int numCrates, int fromStack, int toStack) {
        for (int i = 0; i < numCrates; i++) {
            stacks.get(toStack - 1).addLast(
                    stacks.get(fromStack - 1).removeLast()
            );
        }
    }

}
