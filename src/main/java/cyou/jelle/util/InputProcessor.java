package cyou.jelle.util;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class InputProcessor {

    // regex (?m)^\R: (?m) = 'enable multiline mode', ^ = start of string (already split by multiline), \R = (?>\r\n|\n|\x0b|\f|\r|\x85)
    public static final String NEWLINE = "\\R";
    public static final String MULTILINE_MODE = "(?m)";
    private static final String START_OF_STRING = "^";

    private InputProcessor() {
    }

    @SneakyThrows // IOException
    public static List<String> loadLines(String filename) {
        var path = Path.of(filename);
        return Files.readAllLines(path);
    }

    public static List<List<String>> loadLinesSplitGroupsOnEmptyString(String filename) {
        return InputProcessor.loadLinesSplitGroups(filename, MULTILINE_MODE + START_OF_STRING + NEWLINE);
    }

    @SneakyThrows // IOException
    public static List<List<String>> loadLinesSplitGroups(String filename, String splitterRegex) {
        var path = Path.of(filename);

        String input = Files.readString(path);
        String[] split = input.split(splitterRegex);
        return Arrays.stream(split)
                .map(str -> Arrays.stream(str.split(NEWLINE)).toList())
                .toList();

    }
}
