package com.konjex.ces.input;

import com.google.common.collect.Lists;
import com.konjex.util.string.PaddedSplitter;

import java.util.ArrayList;
import java.util.List;

class Production {

    private static List<Character> splitDividers = Lists.newArrayList(' ', '\n', '\t');

    private Symbol lhs;
    private List<Symbol> rhs;

    private Production(Symbol lhs, List<Symbol> rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }

    Symbol getLhs(){
        return lhs;
    }

    List<Symbol> getRhs(){
        return rhs;
    }

    static Production parse(String prod){
        List<String> split = PaddedSplitter.split(prod, splitDividers);
        List<Symbol> rhs = new ArrayList<>(split.size() - 1);
        for(int i = 1; i < split.size(); i ++){
            rhs.add(new Symbol(split.get(i)));
        }
        return new Production(new Symbol(split.get(0)), rhs);
    }

}
