package com.konjex.ces.input;

import com.konjex.ces.input.exceptions.FailedParseException;
import com.konjex.ces.input.exceptions.FailedScanException;
import com.konjex.util.Tree;

import java.util.List;

public class Extractor {

    private static final Scanner scanner = new MaximalMunchScanner();
    private static final Parser parser = new CYKParser();

    public static <T> T extract(String input, DFA dfa, Grammar grammar, Extraction<T> extraction) throws FailedScanException, FailedParseException {
        List<Token> scan = scanner.scan(input, dfa);
        Tree<Token> parse = parser.parse(grammar, scan);
        return extraction.extract(parse);
    }

}
