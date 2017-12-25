package com.konjex.util.string;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PaddedSplitterTest {

    @Test
    public void empty(){
        String input = "";
        List<Character> dividers = Lists.newArrayList(' ');

        //Exercise
        List<String> result = PaddedSplitter.split(input, dividers);

        //Verify
        assertEquals(0, result.size());
    }

    @Test
    public void one(){
        String input = "sdfsd";
        List<Character> dividers = Lists.newArrayList(' ', '\n', '\t');

        //Exercise
        List<String> result = PaddedSplitter.split(input, dividers);

        //Verify
        assertEquals(1, result.size());
        assertEquals(result.get(0), "sdfsd");
    }

    @Test
    public void complex(){
        String input = "             \n           \n   gererg efer ssadsdsds\nwd\n fdfsdf    sdf sdfsdf   sdfsdf                 sdfsd   ";
        List<Character> dividers = Lists.newArrayList(' ', '\n', '\t');

        //Exercise
        List<String> result = PaddedSplitter.split(input, dividers);

        //Verify
        assertEquals(9, result.size());
        assertEquals(result.get(0), "gererg");
        assertEquals(result.get(1), "efer");
        assertEquals(result.get(2), "ssadsdsds");
        assertEquals(result.get(3), "wd");
        assertEquals(result.get(4), "fdfsdf");
        assertEquals(result.get(5), "sdf");
        assertEquals(result.get(6), "sdfsdf");
        assertEquals(result.get(7), "sdfsdf");
        assertEquals(result.get(8), "sdfsd");
    }

}
