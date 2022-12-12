package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

import java.util.*;
import java.util.function.Function;

public class Year2022Day12 {

    private static Node startNode = null;
    private static Node endNode = null;
    private static final List<Node> aNodes = new ArrayList<>();

    public static void main(String[] args) {
        Function<String, Node[]> mapper = s -> {
            var chars = s.toCharArray();
            var result = new Node[s.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = new Node(chars[i]);
            }
            return result;
        };
        var input = InputProcessor.loadLines("Year2022Day12.txt", mapper);
        Node[][] nodes = new Node[input.size()][];
        input.toArray(nodes);
        initializeNodes(nodes);

        Printer.println("1: " + shortestPathSteps());

        List<Integer> star2Values = new ArrayList<>();
        for (var node : aNodes) {
            startNode = node;
            star2Values.add(shortestPathSteps());
        }

        Printer.println("2: " + star2Values.stream().filter(Objects::nonNull).min(Integer::compareTo).orElse(-1));

    }

    private static Integer shortestPathSteps() {
        LinkedList<Node> toVisit = new LinkedList<>();
        toVisit.add(startNode);
        Map<Node, Integer> stepsToStart = new HashMap<>();
        stepsToStart.put(startNode, 0);


        while (!toVisit.isEmpty()) {
            var node = toVisit.removeFirst();
            int stepsPlusOne = stepsToStart.get(node) + 1;

            for (int i = 0; i < node.neighbors.length; i++) {
                var neighbor = node.neighbors[i];
                if (neighbor != null) {

                    if (stepsToStart.containsKey(neighbor)) {
                        var currentValue = stepsToStart.get(neighbor);
                        if (currentValue > stepsPlusOne) {
                            neighbor.closestToStart = node;
                            stepsToStart.put(neighbor, stepsPlusOne);
                            toVisit.add(neighbor);
                        }
                    } else {
                        stepsToStart.put(neighbor, stepsPlusOne);
                        neighbor.closestToStart = node;
                        toVisit.add(neighbor);
                    }
                }
            }
        }

        return stepsToStart.get(endNode);
    }

    private static void initializeNodes(Node[][] nodes) {
        int height = nodes.length;
        int width = nodes[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = nodes[i][j];
                node.x = j;
                node.y = i;
                storeSpecialNodes(node);
                setNodeNeighbors(nodes, i, j, node);
            }
        }
    }

    private static void setNodeNeighbors(Node[][] nodes, int i, int j, Node node) {
        int height = nodes.length;
        int width = nodes[0].length;

        if (isWithinBounds(i - 1, height)) {
            Node node1 = nodes[i - 1][j];
            if (isReachable(node, node1)) node.neighbors[0] = node1;
        }
        if (isWithinBounds(j + 1, width)) {
            Node node1 = nodes[i][j + 1];
            if (isReachable(node, node1)) node.neighbors[1] = node1;
        }
        if (isWithinBounds(i + 1, height)) {
            Node node1 = nodes[i + 1][j];
            if (isReachable(node, node1)) node.neighbors[2] = node1;
        }
        if (isWithinBounds(j - 1, width)) {
            Node node1 = nodes[i][j - 1];
            if (isReachable(node, node1)) node.neighbors[3] = node1;
        }
    }

    private static void storeSpecialNodes(Node node) {
        if (node.value == 'S') startNode = node;
        if (node.value == 'E') endNode = node;
        if (node.value == 'a') aNodes.add(node);
    }

    private static boolean isWithinBounds(int value, int maxValueExclusive) {
        return value >= 0 && value < maxValueExclusive;
    }

    private static boolean isReachable(Node thisNode, Node otherNode) {
        if (otherNode.value == 'E') {
            return thisNode.value == 'z' || thisNode.value == 'y';
        }

        return thisNode.value + 1 >= otherNode.value
                || otherNode.value == 'S' || thisNode.value == 'S';
    }

    private static class Node {
        Node[] neighbors = new Node[4];
        Node closestToStart = null;
        int x;
        int y;

        char value;

        public Node(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", value=" + value +
                    ", closestToStart=" + closestToStart +
                    '}';
        }
    }
}
