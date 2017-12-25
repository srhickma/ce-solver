package com.konjex.ces.input;

import com.konjex.ces.input.exceptions.FailedScanException;

import java.util.List;

interface Scanner {

    List<Token> scan(String input, DFA dfa) throws FailedScanException;

}
