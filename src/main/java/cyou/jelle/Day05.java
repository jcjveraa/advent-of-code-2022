package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.*;
import java.util.stream.Collectors;

public class Day05 {
    private static List<Deque<Character>> stacks;
    private static ArrayList<List<Integer>> instructions;

    public static void main(String[] args) {
        initializeStacks();
        for (var instruction : instructions) {
            crateMover9000(instruction.get(0), instruction.get(1), instruction.get(2));
        }
        var starOneAnswer = collectTopCrates();

        initializeStacks();
        for (var instruction : instructions) {
            crateMover9001(instruction.get(0), instruction.get(1), instruction.get(2));
        }
        var starTwoAnswer = collectTopCrates();


        Printer.println("1: " + starOneAnswer);
        Printer.println("2: " + starTwoAnswer);
    }

    private static String collectTopCrates() {
        return stacks.stream().map(deque -> deque.peekLast().toString()).collect(Collectors.joining());
    }

    private static void initializeStacks() {
        stacks = new ArrayList<>();
        ArrayList<String> crateInputLines = new ArrayList<>();
        instructions = new ArrayList<>();
        var lineIterator = InputProcessor.loadLines("Day05.input").iterator();

        buildStacks(lineIterator, crateInputLines);
        fillStacks(crateInputLines);
        lineIterator.next(); // skip empty line
        while (lineIterator.hasNext()) {
            var instruction = Arrays.stream(lineIterator.next()
                            .replaceAll("[a-zA-Z ]+", " ").trim()
                            .split(" "))
                    .map(Integer::parseInt).toList();
            instructions.add(instruction);
        }
    }

    private static void fillStacks(List<String> crateInputLines) {
        for (int i = crateInputLines.size() - 1; i >= 0; i--) {
            var line = crateInputLines.get(i);
            for (int j = 0; j < stacks.size(); j++) {
                var stack = stacks.get(j);
                int index = 1 + j * 4;
                char crate = line.length() - 1 >= index ? line.charAt(index) : ' ';
                if (crate != ' ') {
                    stack.addLast(crate);
                }
            }
        }
    }

    private static void buildStacks(Iterator<String> lineIterator, List<String> crateInputLines) {
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

    private static void crateMover9000(int numCrates, int fromStack, int toStack) {
        for (int i = 0; i < numCrates; i++) {
            stacks.get(toStack - 1).addLast(
                    stacks.get(fromStack - 1).removeLast()
            );
        }
    }

    private static void crateMover9001(int numCrates, int fromStack, int toStack) {
        var craneCargo = new ArrayDeque<Character>();
        for (int i = 0; i < numCrates; i++) {
            craneCargo.addLast(stacks.get(fromStack - 1).removeLast());
        }
        for (int i = 0; i < numCrates; i++) {
            stacks.get(toStack - 1).addLast(craneCargo.removeLast());
        }
    }

}
