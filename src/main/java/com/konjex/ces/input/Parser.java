package com.konjex.ces.input;

import com.konjex.ces.input.exceptions.FailedParseException;
import com.konjex.util.Tree;

import java.util.List;

public interface Parser {

    Tree<Token> parse(Grammar grammar, List<Token> scan) throws FailedParseException;

}
