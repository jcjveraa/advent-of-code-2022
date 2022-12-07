package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.Arrays;


public class Year2016Day08 {

    private static Character[][] screen;

    public static void main(String[] args) {
        var lines = InputProcessor.loadLines("Year2016Day08.dat");

        screen = new Character[50][6];
        for (Character[] chars : screen) {
            Arrays.fill(chars, '.');
        }

        for (var line : lines) {
            var input = line.split(" ");
            var command = input[0];
            if (command.equals("rect")) {
                fillRect(input[1]);
            } else if (input[1].equals("row")) {
                var row = Integer.parseInt(input[2].split("=")[1]);
                var amount = Integer.parseInt(input[4]);
                var rotated = rotate(getRow(row), amount);
                setRow(rotated, row);
            } else if (input[1].equals("column")) {
                var col = Integer.parseInt(input[2].split("=")[1]);
                var amount = Integer.parseInt(input[4]);
                var rotated = rotate(getColumn(col), amount);
                setColumn(rotated, col);
            } else {
                throw new IllegalStateException("Command not recognized");
            }
        }

        var counterStarOne = Arrays.stream(screen)
                .flatMap(Arrays::stream)
                .filter(character -> character == '#')
                .count();


        Printer.println("1: " + counterStarOne);
        Printer.println("2: ");
        for (int i = 0; i < 6; i++) {
            Printer.println(Arrays.toString(getRow(i))
                    .replace(", ", "")
                    .replace("#", "█")
                    .replace(".", " "));
        }
    }


    private static void fillRect(String rectDefinition) {
        var xy = Arrays.stream(rectDefinition.split("x")).map(Integer::parseInt).toList();
        for (int i = 0; i < xy.get(0); i++) {
            for (int j = 0; j < xy.get(1); j++) {
                screen[i][j] = '#';
            }
        }
    }

    private static Character[] rotate(Character[] input, int rotation) {
        int length = input.length;
        Character[] output = new Character[length];
        for (int i = 0; i < length; i++) {
            int outputIndex = (i + rotation) % length;
            output[outputIndex] = input[i];
        }
        return output;
    }

    private static Character[] getRow(int row) {
        Character[] out = new Character[screen.length];
        for (int i = 0; i < screen.length; i++) {
            out[i] = screen[i][row];
        }
        return out;
    }

    private static void setRow(Character[] updated, int row) {
        for (int i = 0; i < updated.length; i++) {
            screen[i][row] = updated[i];
        }
    }

    private static Character[] getColumn(int column) {
        return screen[column];
    }

    private static void setColumn(Character[] updated, int column) {
        screen[column] = updated;
    }
}

