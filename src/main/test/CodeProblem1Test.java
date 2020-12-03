import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeProblem1Test {
    @Test
    public void minDistTest() {
        CodeProblem1 code = new CodeProblem1();
        Assertions.assertEquals(5, code.hammingDistance("00000,11111".split(",")));
    }

    @Test
    public void numErrorsTest() {
        CodeProblem1 code = new CodeProblem1();
        Assertions.assertEquals(4, code.numErrors("00000,11111".split(",")));
    }

    @Test
    public void numErrorsCorrectTest() {
        CodeProblem1 code = new CodeProblem1();
        Assertions.assertEquals(2, code.numErrorsCorrected("00000,11111".split(",")));
    }

}