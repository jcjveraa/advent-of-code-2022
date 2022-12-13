package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.*;

public class Year2022Day13 {
    public static void main(String[] args) {
        var input = InputProcessor.loadLines("Year2022Day13.txt");
        List<List> listPairs = parseInput(input);

        int starOne = starOne(listPairs);
        Printer.println("1: " + starOne);

        int starTwo = starTwo(listPairs);
        Printer.println("2: " + starTwo);
    }

    private static int starTwo(List<List> listPairs) {
        var allItems = new ArrayList<>(listPairs);
        List<List<Integer>> six = List.of(List.of(6));
        allItems.add(six);
        List<List<Integer>> two = List.of(List.of(2));
        allItems.add(two);

        allItems.sort(Year2022Day13::compare);
        var twoIndex = allItems.indexOf(two) + 1;
        var sixIndex = allItems.indexOf(six) + 1;

        int starTwo = twoIndex * sixIndex;
        return starTwo;
    }

    private static int starOne(List<List> listPairs) {
        int pairIndex = 1;
        int pairIndexSum = 0;

        for (int i = 0; i < listPairs.size(); i += 2) {
            var list1 = listPairs.get(i);
            var list2 = listPairs.get(i + 1);

            var compareResult = compare(list1, list2);
            if (compareResult == -1) {
                pairIndexSum += pairIndex;
            }

            pairIndex++;
        }
        return pairIndexSum;
    }

    private static List<List> parseInput(List<String> input) {
        List<List> listPairs = new ArrayList<>();
        var iterator = input.iterator();
        while (iterator.hasNext()) {
            var line = iterator.next();
            if (line.isBlank()) {
                line = iterator.next();
                Printer.println("");
            }

            var list = new ArrayList<>();
            var topLevelList = list;
            listPairs.add(list);

            var listStack = new ArrayDeque<ArrayList>();
            listStack.addLast(list);
            var chars = line.toCharArray();
            // i=1 to skip first list opening bracket
            for (int i = 1; i < chars.length; i++) {
                var c = chars[i];
                if (c == '[') {
                    // previousListLevel = list;
                    listStack.addLast(list);
                    var newList = new ArrayList<>();
                    list.add(newList);
                    list = newList;
                } else if (c == ']') {
                    list = listStack.removeLast();
                } else if (c == ',') {
                    continue;
                } else {
                    // now we must have a number character
                    String number = "" + c;
                    while (arrayHasNextNumber(chars, i)) {
                        i++;
                        c = chars[i];
                        number += c;
                    }

                    list.add(Integer.parseInt(number));
                }
            }

            Printer.println(topLevelList);
        }
        return listPairs;
    }

    private static int compare(List list1, List list2) {
        int returnValue = 0;
        for (int j = 0; j < list1.size() && j < list2.size() && returnValue == 0; j++) {
            var val1 = list1.get(j);
            var val2 = list2.get(j);

            if (val1 instanceof Integer && val2 instanceof Integer) {
                returnValue = Integer.compare((int) val1, (int) val2);
            } else if (val1 instanceof Integer && val2 instanceof List) {
                var val1AsList = List.of(val1);
                returnValue = compare(val1AsList, (List) val2);
            } else if (val1 instanceof List && val2 instanceof Integer) {
                var val2AsList = List.of(val2);
                returnValue = compare((List) val1, val2AsList);
            } else if (val1 instanceof List && val2 instanceof List) { // should always be true if we reach here
                returnValue = compare((List) val1, (List) val2);
            } else {
                throw new IllegalStateException("Value was neither a List or an Integer");
            }
        }

        if (returnValue == 0) {
            // one of the lists ran out of numbers
            returnValue = Integer.compare(list1.size(), list2.size());
        }

        return returnValue;
    }

    private static boolean arrayHasNextNumber(char[] arr, int i) {
        return i + 1 < arr.length && Character.isDigit(arr[i + 1]);
    }
}
