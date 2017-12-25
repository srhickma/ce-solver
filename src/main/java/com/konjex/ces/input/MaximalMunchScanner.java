package com.konjex.ces.input;

import com.konjex.ces.input.exceptions.FailedScanException;

import java.util.LinkedList;

public class MaximalMunchScanner implements Scanner {

    @Override
    public LinkedList<Token> scan(String input, DFA dfa) throws FailedScanException {
        return recur(input, new LinkedList<>(), dfa);
    }

    private ScanAttempt scanOne(String input, String state, ScanAttempt backtrack, DFA dfa){
        if(input.length() == 0 || !dfa.alphabetContains(input.charAt(0)) || !dfa.hasTransition(state, input.charAt(0))){
            return dfa.isAccepting(state) ? new ScanAttempt(input, state) : backtrack;
        }
        String nextState = dfa.getTransition(state, input.charAt(0));
        ScanAttempt nextAttempt = scanOne(input.substring(1), nextState, new ScanAttempt(input, state), dfa);
        return dfa.isAccepting(nextAttempt.state) ? nextAttempt : backtrack;
    }

    private LinkedList<Token> recur(String input, LinkedList<Token> accumulator, DFA dfa) throws FailedScanException {
        if(input.length() == 0){
            return accumulator;
        }
        ScanAttempt scanAttempt = scanOne(input, dfa.getStart(), null, dfa);
        if(scanAttempt == null){
            throw new FailedScanException();
        }
        Token scannedToken = new Token(
                new Symbol(scanAttempt.state),
                input.substring(0, input.length() - scanAttempt.input.length())
        );
        accumulator.add(scannedToken);
        return recur(scanAttempt.input, accumulator, dfa);
    }

    class ScanAttempt {

        private String input;
        private String state;

        ScanAttempt(String input, String state){
            this.input = input;
            this.state = state;
        }

    }
}
