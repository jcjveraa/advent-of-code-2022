package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.function.Function;

public class Year2022Day08 {

    private static Integer[][] integers;
    private static int width;
    private static int height;

    public static void main(String[] args) {
        loadData();

        var treecounter = 0;
        width = integers.length;
        height = integers[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == integers.length - 1 || j == integers[0].length - 1 || treeSticksOut(i, j))  {
                    treecounter++;
                }

            }
        }
        Printer.println(treecounter);

    }

    private static boolean treeSticksOut(int i, int j) {
        var tree = integers[i][j];
        var left = ArrayUtils.subarray(getRow(i), 0, j);
        var right = ArrayUtils.subarray(getRow(i), j + 1, width);
        var leftRightVisible = isVisible(tree, left) || isVisible(tree, right);

        var up = ArrayUtils.subarray(getCol(j), 0, i);
        var down = ArrayUtils.subarray(getCol(j), i + 1, height);
        var upDownVisible = isVisible(tree, up) || isVisible(tree, down);
        return leftRightVisible || upDownVisible;
    }

    private static boolean isVisible(Integer tree, Integer[] left) {
        return Arrays.stream(left).allMatch(otherTree -> otherTree < tree);
    }

    private static void loadData() {
        Function<String, Integer[]> mapper = str -> {
            Integer[] result = new Integer[str.length()];
            for (int i = 0; i < str.length(); i++) {
                result[i] = Integer.parseInt("" + str.charAt(i));
            }
            return result;
        };

        var inputLines = InputProcessor.loadLines("Year2022Day08.input", mapper);
        integers = new Integer[inputLines.get(0).length][inputLines.size()];
        for (int i = 0; i < inputLines.size(); i++) {
            integers[i] = inputLines.get(i);
        }
    }

    private static Integer[] getRow(int column) {
        return integers[column];
    }

    private static Integer[] getCol(int row) {
        Integer[] out = new Integer[integers.length];
        for (int i = 0; i < integers.length; i++) {
            out[i] = integers[i][row];
        }
        return out;
    }

}
