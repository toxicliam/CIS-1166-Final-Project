import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CodeProblem2Test {
    @Test
    public void weightTest1() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals(1, code.weight("010".split(""), "000".split("")));
    }

    @Test
    public void weightTest2() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals(0, code.weight("000".split(""), "000".split("")));
    }

    @Test
    public void weightTest3() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals(3, code.weight("01001".split(""), "10101".split("")));
    }

    @Test
    public void weightTest4() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals(7, code.weight("0000000".split(""), "1111111".split("")));
    }

    @Test
    public void validCodeTest1() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals("0", code.positionalErrors(new String[][]{"111,000,101".split(",")}, "000".split(""), code.hammingDistance("111,000,101".split(",")))[0][0][0]);
    }

    @Test
    public void validCodeTest2() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals("0", code.positionalErrors(new String[][]{"11111,00000,10011".split(",")}, "10011".split(""), code.hammingDistance("11111,00000,10011".split(",")))[0][0][0]);
    }

    @Test
    public void posErrorTest1() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertArrayEquals(new String[][]{{"0", "0", "0", "1"}, {"0", "0", "1", "0"}}, code.positionalErrors(new String[][]{"0000".split(""), "1111".split(""), "0011".split("")}, "0001".split(""), code.hammingDistance("0000,1111,0011".split(",")))[1]);
    }

    @Test
    public void posErrorTest2() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertEquals("3", code.positionalErrors(new String[][]{"000000".split(""), "011100".split(""), "101010".split(""), "000011".split("")}, "111111".split(""), code.hammingDistance("000000,011100,101010,001011".split(",")))[0][0][0]);
    }

    @Test
    public void posErrorTest3() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertArrayEquals(new String[][]{{"0", "0", "0", "1", "1", "1"}, {"1", "1", "1", "0", "0", "0"}}, code.positionalErrors(new String[][]{"000000".split(""), "111111".split("")}, "000111".split(""), code.hammingDistance("000000,111111".split(",")))[1]);
    }

    @Test
    public void posErrorCorrectedTest1() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertArrayEquals(new String[]{"0", "0", "0", "0"}, code.positionalErrors(new String[][]{"0000".split(""), "1111".split("")}, "0001".split(""), code.hammingDistance("0000,1111".split(",")))[1][0]);
    }

    @Test
    public void posErrorCorrectedTest2() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertArrayEquals(new String[]{"0", "0", "1", "0", "0", "0"}, code.positionalErrors(new String[][]{"000000".split(""), "011100".split(""), "101010".split(""), "001011".split("")}, "000011".split(""), code.hammingDistance("000000,011100,101010,001011".split(",")))[1][0]);
    }

    @Test
    public void posErrorCorrectedTest3() {
        CodeProblem2 code = new CodeProblem2();
        Assertions.assertArrayEquals(new String[]{"0", "0", "0", "0", "0", "0"}, code.positionalErrors(new String[][]{"000000".split(""), "111111".split("")}, "000011".split(""), code.hammingDistance("000000,111111".split(",")))[1][0]);
    }
}
