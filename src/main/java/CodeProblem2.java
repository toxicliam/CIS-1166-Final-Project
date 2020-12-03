import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.MAX_VALUE;

public class CodeProblem2 {
    public static void main(String[] args) { //main ui
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please input a binary code, separated by , with no spaces.");
        String unformattedCode = keyboard.next();

        int minDist = hammingDistance(unformattedCode.split(",")); //calculating minimum distance for use later
        if (minDist - 1 <= 0) { //ensures codes are large enough to actuall perform analysis on
            System.out.println("Codewords are too similar to detect any errors, or the code you entered contained input errors (inconsistent length, letters, spaces, etc).");
        } else {
            String[][] code = format(unformattedCode); //formats input

            System.out.println("Now, please input a codeword that is received.");
            String[] inputCode = keyboard.next().split("");

            String[][][] values = positionalErrors(code, inputCode, minDist); //finds the positions of any errors in the code, up to d(C) - 1

            assert values != null;
            if (values[0][0][0].equals("3")) { //valid codeword was entered
                System.out.println("That is a valid codeword!");
            } else if (values[0][0][0].equals("1")) { //outputs the positions that have errors in them
                System.out.println("The code you entered contained errors. Here is the corrected code: ");
                for (String[] corrected : values[1]) {
                    for (String correctedBits : corrected)
                        System.out.print(" " + correctedBits);
                }
            } else { //outputs the possible positions that errors have occured
                System.out.println("The code you entered contains errors in these positions: ");
                for (String[] bitstrings : values[1]) {
                    for (String position : bitstrings) {
                        System.out.print(" " + position);
                    }
                    System.out.println(" ");
                }
            }
        }
    }

    private static String[][][] positionalErrors(String[][] code, String[] inputCode, int minDist) { //supposed to find the positions of d(C) - 1 errors but is currently correcting (d(C) - 1)/2 errors
        int minWeight = MAX_VALUE;
        List<String[]> nearest = new ArrayList<>();
        String[] nearestNeighbor = new String[0]; //nearest codeword to the input code

        for (String[] word : code) { //iterates through whole inputted code, finding the minimum weight
            int temp = weight(word, inputCode);
            if (temp < minWeight) {
                minWeight = temp;
                nearestNeighbor = word;
            }
        }

        for (String[] neighbor : code) { //finds all of the nearest neighbors
            int temp = weight(neighbor, inputCode);
            if (temp == minWeight)
                nearest.add(neighbor);
        }

        String[][] errors = new String[nearest.size()][];//potential return value
        if (minWeight == 0) { //check for validity
            System.out.println("That is a valid codeword!");
            return null;
        } else if (minWeight <= (int) Math.floor((minDist - 1.0) / 2.0)) { //ensures we are within nearest neighbor thresholds via the sphere-packing bound
            return new String[][][]{{{"1"}}, {nearestNeighbor}};
        } else if (minWeight <= minDist - 1) { //ensures we are within error detecting thresholds
            for (int i = 0; i < nearest.size(); i++) {
                String[] errorPos = new String[code[0].length]; //temporary array
                for (int j = 0; j < code[0].length; j++) {
                    errorPos[j] = "" + ((Integer.parseInt(nearest.get(i)[j]) + Integer.parseInt(inputCode[i])) % 2); //mod 2 arithmetic instead of XOR
                }
                errors[i] = errorPos;
            }
            return new String[][][]{{{"0"}}, errors};
        }

        return new String[][][]{{{"3"}}, {{""}}};
    }

    private static int weight(String[] input1, String[] input2) { //finds the weight of two codewords
        int weight = 0;
        for (int i = 0; i < input1.length; i++) {
            if (Integer.parseInt(input1[i]) + Integer.parseInt(input2[i]) % 2 == 1)
                weight++;
        }
        return weight;
    }

    private static String[][] format(String a) { //formats our input code as a 2-d array so we can easily perform operations on it
        String[] codes = a.split(",");
        String[][] formattedCode = new String[codes.length][codes[0].length()];
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes[0].length(); j++) {
                formattedCode[i][j] = "" + codes[i].charAt(j);
            }
        }
        return formattedCode;
    }

    public static int hammingDistance(String[] a) { //finds the minimum distance, could be done more efficiently now that the input is formatted but im lazy
        int b = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                int c = 0;
                if (j > i) {
                    for (int k = 0; k < a[j].length(); k++) {
                        if (a[j].charAt(k) != a[i].charAt(k))
                            c++;
                    }
                }
                if ((c < b && c != 0) || b == 0)
                    b = c;
            }
        }
        return b;
    }
}
