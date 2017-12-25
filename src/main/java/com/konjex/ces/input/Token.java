package com.konjex.ces.input;

public class Token {

    private Symbol kind;
    private String lexeme;

    Token(Symbol kind, String lexeme){
        this.kind = kind;
        this.lexeme = lexeme;
    }

    Symbol getKind(){
        return kind;
    }

    @Override
    public String toString(){
        return kind + " " + lexeme;
    }

}
