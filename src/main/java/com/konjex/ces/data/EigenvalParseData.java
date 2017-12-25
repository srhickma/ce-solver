package com.konjex.ces.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.konjex.ces.input.DFA;
import com.konjex.ces.input.Grammar;

public class EigenvalParseData {

    public static DFA dfa = new DFA(
            Sets.newHashSet('A', 'B', 'E', '1', '2', ' ', '+', 'u', 'g'),
            Sets.newHashSet("start", "CAP", "NSUB", "ASUB", "WS", "PLUS"),
            "start",
            Sets.newHashSet("CAP", "NSUB", "ASUB", "WS", "PLUS"),
            (state, input) -> {
                switch (state){
                    case("start"):
                        switch(input){
                            case('A'):
                            case('B'):
                            case('E'):
                                return "CAP";
                            case('1'):
                            case('2'):
                                return "NSUB";
                            case(' '):
                                return "WS";
                            case('+'):
                                return "PLUS";
                            case('u'):
                            case('g'):
                                return "ASUB";
                        }
                }
                return null;
            }
    );

    private Grammar grammar = Grammar.parse(Lists.newArrayList(
            "S cap WS PLUS WS cap",
            "cap CAP subsopt",
            "subsopt",
            "subsopt NSUB",
            "subsopt ASUB",
            "subsopt NSUB ASUB"
    ));

}
