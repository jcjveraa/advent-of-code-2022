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
    private static Integer[] down;
    private static Integer[] up;
    private static Integer[] right;
    private static Integer[] left;
    private static Integer tree;

    public static void main(String[] args) {
        loadData();

        var treecounter = 0;
        var maxScore = 0;
        width = integers.length;
        height = integers[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                setNeighbours(i, j);

                if (i == 0 || j == 0 || i == integers.length - 1 || j == integers[0].length - 1 || treeSticksOut()) {
                    treecounter++;
                }

                int score = treesVisibleScore();
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }
        Printer.println("Star 1: " + treecounter);
        Printer.println("Star 2: " + maxScore);
    }

    private static int treesVisibleScore() {
        var score = 1;
        score *= countVisibleTrees(left, tree);
        score *= countVisibleTrees(right, tree);
        score *= countVisibleTrees(up, tree);
        score *= countVisibleTrees(down, tree);
        return score;
    }

    private static void setNeighbours(int i, int j) {
        tree = integers[i][j];
        left = getLeft(i, j);
        ArrayUtils.reverse(left); // for countVisibleTrees in star 2, star 1 unaffected
        right = getRight(i, j);
        up = getUp(i, j);
        ArrayUtils.reverse(up); // for countVisibleTrees in star 2, star 1 unaffected
        down = getDown(i, j);
    }

    private static int countVisibleTrees(Integer[] otherTrees, int tree) {
        if (otherTrees.length == 0) return 0;

        int result = 0;
        int otherTree = 0;
        while (otherTree < tree && result < otherTrees.length) {
            otherTree = otherTrees[result];
            result++;
        }
        return result;
    }

    private static boolean treeSticksOut() {
        return isVisible(tree, left) || isVisible(tree, right) || isVisible(tree, up) || isVisible(tree, down);
    }

    private static Integer[] getDown(int i, int j) {
        return ArrayUtils.subarray(getCol(j), i + 1, height);
    }

    private static Integer[] getUp(int i, int j) {
        return ArrayUtils.subarray(getCol(j), 0, i);
    }

    private static Integer[] getRight(int i, int j) {
        return ArrayUtils.subarray(getRow(i), j + 1, width);
    }

    private static Integer[] getLeft(int i, int j) {
        return ArrayUtils.subarray(getRow(i), 0, j);
    }

    private static boolean isVisible(Integer tree, Integer[] otherTrees) {
        return Arrays.stream(otherTrees).allMatch(otherTree -> otherTree < tree);
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
