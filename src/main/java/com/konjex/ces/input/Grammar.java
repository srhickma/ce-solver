package com.konjex.ces.input;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grammar {

    private List<Production> productions;
    private HashSet<Symbol> nonTerminals = new HashSet<>();
    private HashSet<Symbol> terminals = new HashSet<>();
    private Map<Symbol, List<Production>> productionsExpanding;

    private Symbol start;

    private Grammar(List<Production> productions){
        this.productions = productions;
        start = productions.get(0).getLhs();
        HashSet<Symbol> symbols = new HashSet<>();
        productions.forEach(prod -> {
            nonTerminals.add(prod.getLhs());
            symbols.add(prod.getLhs());
            symbols.addAll(prod.getRhs());
        });
        productionsExpanding = productions.stream().collect(Collectors.groupingBy(Production::getLhs));
        symbols.stream()
                .filter(sym -> !nonTerminals.contains(sym))
                .forEach(sym -> terminals.add(sym));
    }

    public static Grammar parse(List<String> productions){
        return new Grammar(
                productions.stream()
                        .map(Production::parse)
                        .collect(Collectors.toList())
        );
    }

    Symbol getStart(){
        return start;
    }

    HashSet<Symbol> getNonTerminals(){
        return nonTerminals;
    }

    HashSet<Symbol> getTerminals(){
        return terminals;
    }

    List<Production> getProductions(){
        return productions;
    }

    List<Production> getExpanding(Symbol symbol){
        return productionsExpanding.get(symbol);
    }

}
