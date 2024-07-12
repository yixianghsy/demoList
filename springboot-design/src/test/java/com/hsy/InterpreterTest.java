package com.hsy;

import com.hsy.interpreter.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InterpreterTest {
        @Test
        void  contextLoads() {
            String statement = "3 * 2 * 4 / 6 % 5";

            Calculator calculator = new Calculator();

            calculator.build(statement);

            int result = calculator.compute();

            System.out.println(statement + " = " + result);
        }
}
