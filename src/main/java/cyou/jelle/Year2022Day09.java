package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Year2022Day09 {

    public static void main(String[] args) {
        var input = InputProcessor.loadLines("Year2022Day09.txt");

        var headPosition = new Position(0, 0);
        var tailPosition = new Position(0, 0);

        Map<Position, Integer> positionsVisitedTimes = new HashMap<>();
        positionsVisitedTimes.put(tailPosition, 1);

        for (var line : input) {
            var command = line.split(" ");
            var direction = command[0];
            var moves = Integer.parseInt(command[1]);

            for (int i = 0; i < moves; i++) {
                var previousHeadPosition = new Position(headPosition);
                headPosition.move(direction);

                if (headPosition.distanceTo0Large(tailPosition)) {
                    tailPosition = previousHeadPosition;
                    var currentTimeVisited = positionsVisitedTimes.getOrDefault(tailPosition, 0);
                    positionsVisitedTimes.put(tailPosition, currentTimeVisited + 1);
                }
            }
        }


        Printer.println("Star 1: " + positionsVisitedTimes.keySet().size());
        Printer.println("Star 2: " + -1);
    }


}

class Position {
    int x, y;
    Position trailer;

    public void move(String direction) {
        switch (direction) {
            case "D" -> this.y = this.y - 1;
            case "U" -> this.y = this.y + 1;
            case "L" -> this.x = this.x - 1;
            case "R" -> this.x = this.x + 1;
        }

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
    }

    public boolean distanceTo0Large(Position otherPosition) {
        var xDiff = Math.abs(this.x - otherPosition.x);
        var yDiff = Math.abs(this.y - otherPosition.y);
        return xDiff > 1 || yDiff > 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
