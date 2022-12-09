package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cyou.jelle.Year2022Day09.positionsVisitedTimes;

public class Year2022Day09 {

    public static Map<Position, Integer> positionsVisitedTimes;

    public static void main(String[] args) {
        var input = InputProcessor.loadLines("Year2022Day09.txt");

        var tailPosition = new Position(0, 0, null);
        var headPosition = new Position(0, 0, tailPosition);

        positionsVisitedTimes = new HashMap<>();
        positionsVisitedTimes.put(tailPosition, 1);

        for (var line : input) {
            var command = line.split(" ");
            var direction = command[0];
            var moves = Integer.parseInt(command[1]);

            for (int i = 0; i < moves; i++) {
                headPosition.move(direction);
            }
        }


        Printer.println("Star 1: " + positionsVisitedTimes.keySet().size());
        Printer.println("Star 2: " + -1);
    }


}

class Position {
    int x, y;
    Position trailer;
    private Position previousPosition;

    public void move(String direction) {
        previousPosition = new Position(this);
        switch (direction) {
            case "D" -> this.y = this.y - 1;
            case "U" -> this.y = this.y + 1;
            case "L" -> this.x = this.x - 1;
            case "R" -> this.x = this.x + 1;
        }

        moveTrailer(previousPosition);
    }

    private void moveTrailer(Position previousPosition) {
        if (trailer != null && this.distanceTooLarge(trailer)) {
            trailer.previousPosition = new Position(trailer);
            trailer.x = previousPosition.x;
            trailer.y = previousPosition.y;
            trailer.moveTrailer(previousPosition);

            var currentTimeVisited = positionsVisitedTimes.getOrDefault(trailer, 0);
            positionsVisitedTimes.put(trailer, currentTimeVisited + 1);
        }
    }

    public Position(int x, int y, Position trailer) {
        this.x = x;
        this.y = y;
        this.trailer = trailer;
    }

    public Position(Position other) {
        this.x = other.x;
        this.y = other.y;
        this.trailer = null;
    }

    public boolean distanceTooLarge(Position otherPosition) {
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
