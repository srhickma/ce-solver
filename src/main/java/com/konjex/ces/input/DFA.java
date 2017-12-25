package com.konjex.ces.input;

import java.util.Set;
import java.util.function.BiFunction;

public class DFA {

    private Set<Character> alphabet;
    private Set<String> states;
    private String start;
    private Set<String> accepting;
    private BiFunction<String, Character, String> transitions;

    public DFA(Set<Character> alphabet,
               Set<String> states,
               String start,
               Set<String> accepting,
               BiFunction<String, Character, String> transitions){
        this.alphabet = alphabet;
        this.states = states;
        this.start = start;
        this.accepting = accepting;
        this.transitions = transitions;
    }

    boolean alphabetContains(char c){
        return alphabet.contains(c);
    }

    boolean hasTransition(String state, char input){
        return transitions.apply(state, input) != null;
    }

    String getTransition(String state, char input){
        return transitions.apply(state, input);
    }

    boolean isAccepting(String state){
        return accepting.contains(state);
    }

    String getStart(){
        return start;
    }

}
