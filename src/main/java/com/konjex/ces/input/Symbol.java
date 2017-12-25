package com.konjex.ces.input;

public class Symbol {

    private String symbol;

    Symbol(String symbol){
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Symbol)){
            return false;
        }
        Symbol s = (Symbol)o;
        return symbol.equals(s.symbol);
    }

    @Override
    public int hashCode(){
        return symbol.hashCode();
    }


    @Override
    public String toString(){
        return symbol;
    }

}
