package org.genomesmanager.bioprograms.execute;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LucyTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLucy() {
        System.out.println("testLucy");
        Lucy instance = new Lucy();
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testLucyStringStringStringString() {
        System.out.println("testLucyStringStringStringString");
        String output1 = "seq.fa.lucy";
        String output2 = "seq.qual.lucy";
        String input1 = "seq.fa";
        String input2 = "seq.qual";
        Lucy instance = new Lucy(output1, output2, input1, input2);
        if (instance == null) {
            fail();
        }
    }

    @Test
    public void testSetOutput1() {
        System.out.println("testSetOutput1");
        String output1 = "seq.fa.lucy";
        Lucy instance = new Lucy();
        instance.setOutput1(output1);
    }

    @Test
    public void testSetOutput2() {
        System.out.println("testSetOutput2");
        String output2 = "seq.qual.lucy";
        Lucy instance = new Lucy();
        instance.setOutput2(output2);
    }

    @Test
    public void testSetInput1() {
        System.out.println("testSetInput1");
        String input1 = "seq.fa";
        Lucy instance = new Lucy();
        instance.setOutput1(input1);
    }

    @Test
    public void testSetInput2() {
        System.out.println("testSetInput2");
        String input2 = "seq.qual";
        Lucy instance = new Lucy();
        instance.setOutput1(input2);
    }

    @Test
    public void testRun() {
        System.out.println("testRun");
        String output1 = "seq.fa.lucy";
        String output2 = "seq.qual.lucy";
        String input1 = "seq.fa";
        String input2 = "seq.qual";
        Lucy instance = new Lucy(output1, output2, input1, input2);
        Boolean ret = instance.run();
        if (ret == false) {
            fail();
        }
        System.out.println("Lucy output:" + instance.getLastRunOutput());
        System.out.println("Lucy error:" + instance.getLastRunError());
        System.out.println("lucy exit value: " + instance.getLastExitValue());
    }
}
