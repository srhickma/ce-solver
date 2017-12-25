package com.konjex.ces.data;

import com.google.common.collect.Lists;

import java.util.List;

public class EigenvalQuad {

    private int a, b, c, d;

    public EigenvalQuad(int a, int b, int c, int d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public List<Integer> values(){
        return Lists.newArrayList(a, b, c, d);
    }

}
