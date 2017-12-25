package com.konjex.ces.input;

import com.konjex.util.Tree;

public interface Extraction<T> {

    T extract(Tree<Token> parse);

}
