package cyou.jelle.util;

public class Printer {
    private Printer(){}
    // This exists just to trick my IDE to stop complaining all the time use of the System.out, without
    // actually using an external logging library.
    public static void println(Object o) {
        System.out.println(o);
    }
    public static void print(Object o) {
        System.out.print(o);
    }
}
