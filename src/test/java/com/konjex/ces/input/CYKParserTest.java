package com.konjex.ces.input;

import com.google.common.collect.Lists;
import com.konjex.ces.input.exceptions.FailedParseException;
import com.konjex.util.Tree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CYKParserTest {

    private CYKParser parser = new CYKParser();

    private Grammar simpleGrammar = Grammar.parse(Lists.newArrayList(
            "S BOF A EOF",
            "A x",
            "A A x"
    ));

    @Test
    public void simpleGrammar_oneX() throws FailedParseException {
        //Setup
        ArrayList<Token> input = Lists.newArrayList("BOF", "x", "EOF").stream()
                .map(s -> new Token(new Symbol(s), ""))
                .collect(Collectors.toCollection(ArrayList::new));

        //Exercise
        Tree<Token> result = parser.parse(simpleGrammar, input);

        //Verify
        System.out.println(result);
    }

    @Test
    public void simpleGrammar_twoXes() throws FailedParseException {
        //Setup
        ArrayList<Token> input = Lists.newArrayList("BOF", "x", "x", "EOF").stream()
                .map(s -> new Token(new Symbol(s), ""))
                .collect(Collectors.toCollection(ArrayList::new));

        //Exercise
        Tree<Token> result = parser.parse(simpleGrammar, input);

        //Verify
        System.out.println(result);
    }

    @Test
    public void simpleGrammar_threeXes() throws FailedParseException {
        //Setup
        ArrayList<Token> input = Lists.newArrayList("BOF", "x", "x", "x", "EOF").stream()
                .map(s -> new Token(new Symbol(s), ""))
                .collect(Collectors.toCollection(ArrayList::new));

        //Exercise
        Tree<Token> result = parser.parse(simpleGrammar, input);

        //Verify
        System.out.println(result);
    }

}
