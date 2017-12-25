package com.konjex.util.string;

import java.util.ArrayList;
import java.util.List;

public class PaddedSplitter {

    public static List<String> split(String input, List<Character> dividers){
        List<String> result = new ArrayList<>();
        int splitStart = 0;
        boolean splitNeeded = false;
        for(int i = 0; i < input.length(); i ++){
            if(!dividers.contains(input.charAt(i))){
                if(!splitNeeded){
                  splitStart = i;
                }
                splitNeeded = true;
            }
            else if(splitNeeded){
                splitNeeded = false;
                result.add(input.substring(splitStart, i));
            }
        }
        if(splitNeeded){
            result.add(input.substring(splitStart));
        }
        return result;
    }

}
