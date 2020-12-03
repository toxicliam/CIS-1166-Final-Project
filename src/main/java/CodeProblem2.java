import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.MAX_VALUE;

public class CodeProblem2 {
    public static void main(String[] args) { //main ui
        CodeProblem2 var = new CodeProblem2();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please input a binary code, separated by , with no spaces.");
        String unformattedCode = keyboard.next();

        int minDist = var.hammingDistance(unformattedCode.split(",")); //calculating minimum distance for use later
        if (minDist - 1 <= 0) { //ensures codes are large enough to actually perform analysis on
            System.out.println("Codewords are too similar to detect any errors, or the code you entered contained input errors (inconsistent length, letters, spaces, etc).");
        } else {
            String[][] code = var.format(unformattedCode); //formats input

            System.out.println("Now, please input a codeword that is received.");
            String[] inputCode = keyboard.next().split("");

            String[][][] values = var.positionalErrors(code, inputCode, minDist); //finds the positions of any errors in the code, up to d(C) - 1

            switch (values[0][0][0]) {
                case "0":  //valid codeword was entered
                    System.out.println("That is a valid codeword!");
                    break;
                case "1":  //outputs the possible positions that errors could have occured
                    System.out.println("The code you entered contains errors in these positions: ");
                    for (String[] bitstrings : values[1]) {
                        for (String position : bitstrings) {
                            System.out.print(" " + position);
                        }
                        System.out.println(" ");
                    }
                    break;
                case "2":  //outputs the positions that have errors in them
                    System.out.println("The code you entered contained errors. Here is the corrected code: ");
                    for (String[] corrected : values[1]) {
                        for (String correctedBits : corrected)
                            System.out.print(" " + correctedBits);
                    }
                    break;
                case "3":
                    System.out.print("That codeword is not part of the original code C and also has errors > k - 1 where k is the minimum distance of code C");
                    break;
            }
        }
    }

    public String[][][] positionalErrors(String[][] code, String[] inputCode, int minDist) { //finds the positions of up to d(C) - 1 errors or corrects (d(C) - 1)/2 errors, whichever is applicable
        int minWeight = MAX_VALUE;
        CodeProblem2 var = new CodeProblem2();
        List<String[]> nearest = new ArrayList<>(); //used to store all the possible error positions
        String[] nearestNeighbor = new String[0]; //nearest codeword to the input code

        for (String[] word : code) { //iterates through whole inputted code, finding the minimum weight
            int temp = var.weight(word, inputCode);
            if (temp < minWeight) {
                minWeight = temp;
                nearestNeighbor = word;
            }
        }

        for (String[] neighbor : code) { //finds all of the nearest neighbors
            int temp = var.weight(neighbor, inputCode);
            if (temp == minWeight)
                nearest.add(neighbor);
        }

//        Quick reminder of code values: returning values[0] = 0 means a valid codeword was entered,
//        1 means the positions of errors can be detected, 2 means errors can be corrected,
//        and 3 means the errors are out of the scope of the code.

        String[][] errors = new String[nearest.size()][]; //potential return value
        if (minWeight == 0) { //check for validity
            return new String[][][]{{{"0"}}, {{""}}};
        } else if (minWeight <= (int) Math.floor((minDist - 1.0) / 2.0)) { //ensures we are within nearest neighbor thresholds via the sphere-packing bound
            return new String[][][]{{{"2"}}, {nearestNeighbor}};
        } else if (minWeight <= minDist - 1) { //ensures we are within error detecting thresholds
            for (int i = 0; i < nearest.size(); i++) {
                String[] errorPos = new String[code[0].length]; //temporary array
                for (int j = 0; j < code[0].length; j++) {
                    errorPos[j] = "" + ((Integer.parseInt(nearest.get(i)[j]) + Integer.parseInt(inputCode[j])) % 2); //mod 2 arithmetic instead of XOR
                }
                errors[i] = errorPos;
            }
            return new String[][][]{{{"1"}}, errors};
        }

        return new String[][][]{{{"3"}}, {{""}}};
    }
    //in hindsight, using 3d arrays in order to return an array and a string was very unecessary, but it works so im keeping it

    public int weight(String[] input1, String[] input2) { //finds the weight of two codewords
        int weight = 0;
        for (int i = 0; i < input1.length; i++) {
            if (Integer.parseInt(input1[i]) + Integer.parseInt(input2[i]) % 2 == 1)
                weight++;
        }
        return weight;
    }

    public String[][] format(String a) { //formats our input code as a 2-d array so we can easily perform operations on it
        String[] codes = a.split(",");
        String[][] formattedCode = new String[codes.length][codes[0].length()];
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes[0].length(); j++) {
                formattedCode[i][j] = "" + codes[i].charAt(j);
            }
        }
        return formattedCode;
    }

    public int hammingDistance(String[] a) { //finds the minimum distance, could be done more efficiently now that the input is formatted but im lazy
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
