package com.konjex.ces.input;

import com.google.common.collect.Lists;
import com.konjex.ces.input.exceptions.FailedParseException;
import com.konjex.util.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EarleyParser implements Parser {

    @Override
    public Tree<Token> parse(Grammar grammar, List<Token> scan) throws FailedParseException {
        List<List<Item>> S = Lists.newArrayList();
        S.add(Lists.newArrayList(
                grammar.getProductions().stream()
                        .filter(prod -> prod.getLhs().equals(grammar.getStart()))
                        .map(prod -> new Item(prod, 0, 0))
                        .collect(Collectors.toList())
        ));

        int i = 0;
        while(i < S.size()){
            int j = 0;
            while(j < S.get(i).size()){
                Symbol symbol = nextToken(S.get(i).get(j));
                if(symbol == null){
                    complete(i, j, S);
                }
                else if(grammar.getTerminals().contains(symbol)){
                    scan(i, j, symbol, S, scan);
                }
                else{
                    predict(i, symbol, S, grammar);
                }
            }
        }

        return null;
    }

    private Symbol nextToken(Item item){
        if(item.next >= item.rule.getRhs().size()) return null;
        return item.rule.getRhs().get(item.next);
    }

    private Symbol name(Item item){
        return item.rule.getLhs();
    }

    private boolean equal(Item item1, Item item2){
        return item1.rule.getLhs().equals(item2.rule.getLhs());
    }

    private void append(int i, Item item, List<List<Item>> S){
        for(int j = 0; j < S.get(i).size(); j ++){
            if(equal(S.get(i).get(j), item)) return;
        }
        unsafeAppend(i, item, S);
    }

    private void unsafeAppend(int i, Item item, List<List<Item>> S){
        S.get(i).add(item);
    }

    private void predict(int i, Symbol symbol, List<List<Item>> S, Grammar grammar){
        grammar.getProductions().stream()
                .filter(prod -> prod.getLhs().equals(symbol))
                .forEachOrdered(prod -> append(i, new Item(prod, i, 0), S));
    }

    private void scan(int i, int j, Symbol symbol, List<List<Item>> S, List<Token> input){
        Item item = S.get(i).get(j);
        if(i < input.size() && input.get(i).getKind().equals(symbol)){
            if(S.size() <= i + 1) S.add(new ArrayList<>());
            unsafeAppend(i + 1, new Item(item.rule, item.start, item.next + 1), S);
        }
    }

    private void complete(int i, int j, List<List<Item>> S){
        Item item = S.get(i).get(j);
        S.get(item.start).stream()
            .filter(oldItem -> nextToken(oldItem).equals(name(item)))
            .forEachOrdered(oldItem -> append(i, new Item(oldItem.rule, oldItem.start, oldItem.next + 1), S));
    }

    private boolean hasPartialParse(int i, List<List<Item>> S, Grammar grammar){
        for(int j = 0; j < S.get(i).size(); i ++){
            Item item = S.get(i).get(j);
            if(item.rule.getLhs().equals(grammar.getStart()) && item.next >= item.rule.getRhs().size() && item.start == 0) return true;
        }
        return false;
    }

    class Item {

        private Production rule;
        private int start;
        private int next;

        public Item(Production rule, int start, int next){
            this.rule = rule;
            this.start = start;
            this.next = next;
        }

        public Production getRule(){
            return rule;
        }

        public int getStart(){
            return start;
        }

        public int getNext(){
            return next;
        }

    }

}
