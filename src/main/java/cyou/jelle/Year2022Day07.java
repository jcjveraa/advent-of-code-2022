package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;
import org.apache.commons.collections4.iterators.PeekingIterator;

import java.util.*;

public class Year2022Day07 {

    public static void main(String[] args) {
        var inputLine = InputProcessor.loadLines("Year2022Day07.input");
        var iterator = PeekingIterator.peekingIterator(inputLine.iterator());
        // skip first line with cd /
        iterator.next();

        Directory root = new Directory();
        Directory currentDirectory = root;
        List<Directory> allDirs = new ArrayList<>();
        while (iterator.hasNext()) {
            var command = iterator.next();
            if (command.equals("$ ls")) {
                while (iterator.hasNext() && !iterator.peek().startsWith("$")) {
                    var dirOrFile = iterator.next().split(" ");
                    if (dirOrFile[0].equals("dir")) {
                        var d = new Directory();
                        d.parent = currentDirectory;
                        allDirs.add(d);
                        currentDirectory.subDirs.put(dirOrFile[1], d);
                    } else {
                        currentDirectory.files.put(dirOrFile[1], Integer.parseInt(dirOrFile[0]));
                    }
                }
            } else if (command.equals("$ cd ..")) {
                currentDirectory = currentDirectory.parent;
            } else if (command.startsWith("$ cd")) {
                currentDirectory = currentDirectory.subDirs.get(command.split(" ")[2]);
            } else {
                throw new IllegalStateException("Command not recognized: " + command);
            }
        }

        var allSmallDirs = allDirs.stream()
                .map(Directory::getDirSize)
                .filter(dirSize -> dirSize <= 100_000)
                .reduce(Integer::sum).orElse(-1);


        int totalSpace = 70000000;
        int requiredSpace = 30000000;
        int rootSize = root.getDirSize();
        int unused = totalSpace - rootSize;
        int toBeFreed = requiredSpace - unused;

        var dirToDelete = allDirs.stream()
                .sorted(Comparator.comparingInt(Directory::getDirSize))
                .filter(dir -> dir.getDirSize() >= toBeFreed)
                .toList()
                .get(0);

        Printer.println("1: " + allSmallDirs);
        Printer.println("2: " + dirToDelete.getDirSize());
    }


    private static class Directory {
        Map<String, Directory> subDirs = new HashMap<>();
        Directory parent;
        Map<String, Integer> files = new HashMap<>();
        private int dirSize = -1;

        int getDirSize() {
            if (dirSize == -1) {
                var thisDirSize = files.values().stream().reduce(Integer::sum).orElse(0);
                var subDirsSize = subDirs.values().stream().map(Directory::getDirSize).reduce(Integer::sum).orElse(0);
                this.dirSize = thisDirSize + subDirsSize;
            }
            return this.dirSize;
        }
    }
}
