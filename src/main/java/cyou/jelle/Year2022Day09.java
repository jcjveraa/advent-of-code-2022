package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;
import lombok.Getter;

import java.util.*;

public class Year2022Day09 {
    protected static final Map<String, Integer> starOneMoves = new HashMap<>();
    protected static final Map<String, Integer> starTwoMoves = new HashMap<>();

    public static void main(String[] args) {
        var input = InputProcessor.loadLines("Year2022Day09.txt");

        var headPosition = new Position(0, 0, 0);
        var rope = new ArrayList<Position>();
        rope.add(headPosition);


        var currentPosition = headPosition;
        for (int i = 0; i < 9; i++) {
            currentPosition.trailer = new Position(0, 0, i + 1);
            rope.add(currentPosition.trailer);
            currentPosition = currentPosition.trailer;
        }
        starOneMoves.put(headPosition.toString(), 1);
        starTwoMoves.put(headPosition.toString(), 1);

        for (var line : input) {
            var command = line.split(" ");
            var direction = command[0];
            var moves = Integer.parseInt(command[1]);

            for (int i = 0; i < moves; i++) {
                headPosition.move(direction);
//                render(rope);
            }
        }

//        render(starOneMoves);

        Printer.println("Star 1: " + starOneMoves.keySet().size());
        Printer.println("Star 2: " + starTwoMoves.keySet().size());
//        Printer.println("Star 2: " + -1);
    }

    private static void render(List<Position> positions) {

        int maxX = positions.stream().map(Position::getX).max(Integer::compareTo).orElseThrow();
        int minX = positions.stream().map(Position::getX).min(Integer::compareTo).orElseThrow();
        int maxY = positions.stream().map(Position::getY).max(Integer::compareTo).orElseThrow();
        int minY = positions.stream().map(Position::getY).min(Integer::compareTo).orElseThrow();

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                var posInList = positions.stream().filter(p -> p.toString().equals(finalX + "," + finalY)).findFirst();
                Character c = posInList.map(position -> Character.forDigit(position.id, 10)).orElse('.');
                if (x == 0 && y == 0) c = 's';
                System.out.print(c);

            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    private static void render(Map<String, Integer> map) {
        var keys = map.keySet();
        var positions = keys.stream().map(str -> {
            var xy = Arrays.stream(str.split(",")).map(Integer::parseInt).toList();
            return new Position(xy.get(0), xy.get(1), -1);
        }).toList();
        int maxX = positions.stream().map(Position::getX).max(Integer::compareTo).orElseThrow();
        int minX = positions.stream().map(Position::getX).min(Integer::compareTo).orElseThrow();
        int maxY = positions.stream().map(Position::getY).max(Integer::compareTo).orElseThrow();
        int minY = positions.stream().map(Position::getY).min(Integer::compareTo).orElseThrow();

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                int finalX = x;
                int finalY = y;
                var posInList = positions.stream().anyMatch(p -> p.toString().equals(finalX + "," + finalY));
                var c = posInList ? '#' : '.';
                if (x == 0 && y == 0) c = 's';
                System.out.print(c);

            }
            System.out.print("\n");
        }
    }


}

class Position {
    @Getter
    int x;
    @Getter
    int y;
    Position trailer;
    int id;

    public void move(String direction) {
        switch (direction) {
            case "D" -> this.y = this.y - 1;
            case "U" -> this.y = this.y + 1;
            case "L" -> this.x = this.x - 1;
            case "R" -> this.x = this.x + 1;
            default -> throw new IllegalStateException();
        }
        moveTrailer();
    }

    private void moveTrailer() {
        int xDiff = this.xDiff(trailer);
        int yDiff = this.yDiff(trailer);

        if (yDiff > 1 || xDiff > 1) {
            if (xDiff > 1 && yDiff == 0) {
                if (this.x > trailer.x) trailer.x++;
                else trailer.x--;
            } else if (yDiff > 1 && xDiff == 0) {
                if (this.y > trailer.y) trailer.y++;
                else trailer.y--;
            } else { //diagonal
                if (this.y > trailer.y) trailer.y++;
                else trailer.y--;
                if (this.x > trailer.x) trailer.x++;
                else trailer.x--;
            }
            updateMoveCounters();
            if (trailer.trailer != null) trailer.moveTrailer();
        }

    }

    private void updateMoveCounters() {
        if (trailer.id == 1) {
            Year2022Day09.starOneMoves.put(trailer.toString(), 0);
        }

        if (trailer.id == 9) {
            Year2022Day09.starTwoMoves.put(trailer.toString(), 0);
        }
    }

    public Position(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    private int xDiff(Position otherPosition) {
        return Math.abs(this.x - otherPosition.x);
    }

    private int yDiff(Position otherPosition) {
        return Math.abs(this.y - otherPosition.y);
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

}
