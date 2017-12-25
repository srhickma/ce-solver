package com.konjex.ces.input;

import com.google.common.collect.Sets;
import com.konjex.ces.input.exceptions.FailedScanException;
import org.junit.Test;

import java.util.List;

public class MaximalMunchScannerTest {

    private MaximalMunchScanner scanner = new MaximalMunchScanner();

    @Test
    public void defaultConstructor() throws FailedScanException {
        //Setup
        DFA binaryNumbers = new DFA(
                Sets.newHashSet('0', '1'),
                Sets.newHashSet("start", "0", "not0"),
                "start",
                Sets.newHashSet("0", "not0"),
                (state, input) -> {
                    switch (state){
                        case("start"):
                            switch(input){
                                case('0'):
                                    return "0";
                                case('1'):
                                    return "not0";
                            }
                        case("not0"):
                            switch(input){
                                default: return "not0";
                            }
                    }
                    return null;
                }
        );

        String input = "0000000110101010010";

        //Exercise

        List<Token> result = scanner.scan(input, binaryNumbers);

        //Verify
        System.out.println(result);
    }


}
