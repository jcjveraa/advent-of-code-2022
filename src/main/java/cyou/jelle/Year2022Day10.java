package cyou.jelle;

import cyou.jelle.util.InputProcessor;
import cyou.jelle.util.Printer;

public class Year2022Day10 {

    public static void main(String[] args) {
        var input = InputProcessor.loadLines("Year2022Day10.txt", str -> str.split(" "));
        int signalStrength = 0;
        int currentCycle = 0;
        int xRegister = 1;

        Printer.println("Star 2: ");

        for (String[] line : input) {
            var isAdd = line[0].equals("addx");
            var cycles = isAdd ? 2 : 1;
            for (int cycle = 0; cycle < cycles; cycle++) {
                String charToDraw = getCharToDraw(currentCycle, xRegister);

                Printer.print(charToDraw);

                currentCycle++;
                if (currentCycle % 40 == 0) {
                    Printer.println("");
                }

                if ((currentCycle - 20) % 40 == 0 && currentCycle <= 220) {
                    signalStrength += currentCycle * xRegister;
                }
            }
            if (isAdd) {
                xRegister += Integer.parseInt(line[1]);
            }
        }

        Printer.println("Star 1: " + signalStrength);
    }

    private static String getCharToDraw(int currentCycle, int xRegister) {
        int currentCycleX = currentCycle % 40;
        boolean overlap = currentCycleX == xRegister || currentCycleX == (xRegister - 1) || currentCycleX == (xRegister + 1);
        return overlap ? "█" : " ";
    }


}
