package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashSet;

public class Year2022Day06 {

    public static void main(String[] args) {
        String inputLine = InputProcessor.loadLines("Year2022Day06.input").get(0);

        Printer.println("1: " + getMarkerPosition(inputLine, 4));
        Printer.println("2: " + getMarkerPosition(inputLine, 14));
    }

    private static int getMarkerPosition(String inputLine, int markerLength) {
        var inputAsList = Arrays.stream(ArrayUtils.toObject(inputLine.toCharArray())).toList();
        int i = markerLength;
        int found = -1;
        while (found == -1) {
            var set = new HashSet<>(inputAsList.subList(i - markerLength, i));
            if (set.size() == markerLength) {
                found = i;
            }
            i++;
        }
        return found;
    }
}
