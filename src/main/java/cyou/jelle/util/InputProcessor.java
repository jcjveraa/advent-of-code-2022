package cyou.jelle.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class InputProcessor {
    private InputProcessor(){}

    public static List<String> loadLines(String filename) {
        var path = Path.of(filename);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> loadLinesSplitGroupsOnEmptyString(String filename) {
        // regex (?m)^\R: (?m) = 'enable multiline mode', ^ = start of string (already split by multiline), \R = (?>\r\n|\n|\x0b|\f|\r|\x85)
        return InputProcessor.loadLinesSplitGroupsOnEmptyString(filename, "(?m)^\\R");
    }

    public static List<List<String>> loadLinesSplitGroupsOnEmptyString(String filename, String regex) {
        var path = Path.of(filename);
        try {
            String input = Files.readString(path);
            String[] split = input.split(regex);
            return Arrays.stream(split)
                    .map(str -> Arrays.stream(str.split("\\R")).toList())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
