import java.util.Scanner;

public class CodeProblem1 {

    public static void main(String[] args) {
        CodeProblem1 errors = new CodeProblem1();
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please input a binary code for potential error detections and correction, with each bit string separated by commas with no space. Make sure each bit string is the same length.");
        String input = keyboard.next();
        String[] binaryCode = input.split(","); //formats the input code
        System.out.println("Errors that can be detected: " + errors.numErrors(binaryCode));
        System.out.println("Errors that can be corrected: " + errors.numErrorsCorrected(binaryCode));
    }

    public int hammingDistance(String[] a) { // iterates through the array and compares each codeword to each other
        int b = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                int c = 0;
                if (j > i) { // ensures each pair of codewords is compared exactly once
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

    public int numErrors(String[] a) { // k - 1 function, not necessary but it looks clean

        return hammingDistance(a) - 1;
    }

    public int numErrorsCorrected(String[] a) { // (k - 1) / 2 function, not necessary but it looks clean pt. 2

        return (hammingDistance(a) - 1) / 2;
    }
}
