package com.konjex.ces.input;

import com.google.common.collect.Lists;
import com.konjex.ces.input.exceptions.FailedParseException;
import com.konjex.util.Tree;

import java.util.*;

public class CYKParser implements Parser {

    @Override
    public Tree<Token> parse(Grammar grammar, List<Token> scan) throws FailedParseException {
        Optional<List<Tree<Token>>> parse = recur(Lists.newArrayList(grammar.getStart()), 0, scan.size(), new HashMap<>(), grammar, Lists.newArrayList(scan));
        if(!parse.isPresent() || parse.get().size() != 1){
            throw new FailedParseException();
        }
        return parse.get().get(0);
    }

    private Optional<List<Tree<Token>>> recur(List<Symbol> lhs, int from, int length, HashMap<RecurInstance, Optional<List<Tree<Token>>>> memo, Grammar grammar, ArrayList<Token> input){
        final RecurInstance instance = new RecurInstance(lhs, from, length);
        if(memo.containsKey(instance)){
            return memo.get(instance);
        }
        memo.put(instance, Optional.empty());

        if(lhs.isEmpty()){
            if(length == 0) return returnCaptor(Optional.of(new ArrayList<>()), instance, memo);
        }
        else if(grammar.getTerminals().contains(lhs.get(0))){
            Symbol a = lhs.get(0);
            List<Symbol> beta = lhs.subList(1, lhs.size());
            Token fromToken = input.get(from);
            if(length == 0 || !fromToken.getKind().equals(a)) return returnCaptor(Optional.empty(), instance, memo);
            final Optional<List<Tree<Token>>> res = recur(beta, from + 1, length - 1, memo, grammar, input);
            if(res.isPresent()){
                List<Tree<Token>> newTreeList = Lists.newArrayList(new Tree<Token>(fromToken, new ArrayList<>()));
                newTreeList.addAll(res.get());
                return returnCaptor(Optional.of(newTreeList), instance, memo);
            }
        }
        else if(lhs.size() == 1 && grammar.getNonTerminals().contains(lhs.get(0))){
            List<Production> gammas = grammar.getExpanding(lhs.get(0));
            for(Production gamma: gammas){
                final Optional<List<Tree<Token>>> res = recur(gamma.getRhs(), from, length, memo, grammar, input);
                if(res.isPresent()){
                    return returnCaptor(Optional.of(Lists.newArrayList(new Tree<Token>(new Token(lhs.get(0), ""), res.get()))), instance, memo);
                }
            }
        }
        else{
            Symbol A = lhs.get(0);
            List<Symbol> beta = lhs.subList(1, lhs.size());
            for(int i = 0; i <= length; i ++){
                final Optional<List<Tree<Token>>> res1 = recur(Lists.newArrayList(A), from, i, memo, grammar, input);
                final Optional<List<Tree<Token>>> res2 = recur(beta, from + i, length - i, memo, grammar, input);
                if(res1.isPresent() && res2.isPresent()){
                    List<Tree<Token>> result = Lists.newArrayList(res1.get());
                    result.addAll(res2.get());
                    return returnCaptor(Optional.of(result), instance, memo);
                }
            }
        }

        return returnCaptor(Optional.empty(), instance, memo);
    }

    private Optional<List<Tree<Token>>> returnCaptor(Optional<List<Tree<Token>>> result, RecurInstance instance, HashMap<RecurInstance, Optional<List<Tree<Token>>>> memo){
        memo.put(instance, result);
        return result;
    }

    class RecurInstance {

        private List<Symbol> lhs;
        private int from;
        private int length;

        RecurInstance(List<Symbol> lhs, int from, int length){
            this.lhs = lhs;
            this.from = from;
            this.length = length;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RecurInstance that = (RecurInstance) o;

            return from == that.from && length == that.length && lhs.equals(that.lhs);
        }

        @Override
        public int hashCode() {
            int result = lhs.hashCode();
            result = 31 * result + from;
            result = 31 * result + length;
            return result;
        }
    }

}
