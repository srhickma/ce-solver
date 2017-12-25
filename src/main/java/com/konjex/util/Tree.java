package com.konjex.util;

import java.util.List;

public class Tree<T> {

    private T root;
    private List<Tree<T>> children;

    public Tree(T root, List<Tree<T>> children){
        this.root = root;
        this.children = children;
    }

    public T getRoot(){
        return root;
    }

    public List<Tree<T>> getChildren(){
        return children;
    }
}
