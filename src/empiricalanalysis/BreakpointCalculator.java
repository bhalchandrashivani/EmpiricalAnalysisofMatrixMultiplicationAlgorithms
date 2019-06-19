/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author shivani bhalchandra
 */
public class BreakpointCalculator {

    Map<Integer, Long> tradmap = new HashMap<Integer, Long>();
    Map<Integer, Long> pureStrasmap = new HashMap<Integer, Long>();

    Map<Integer, Long> tradmap2 = new HashMap<Integer, Long>();
    Map<Integer, Long> map16 = new HashMap<Integer, Long>();
    Map<Integer, Long> map32 = new HashMap<Integer, Long>();
    Map<Integer, Long> map64 = new HashMap<Integer, Long>();
    Map<Integer, Long> map128 = new HashMap<Integer, Long>();
    Map<Integer, Long> map256 = new HashMap<Integer, Long>();
    Map<Integer, Long> map512 = new HashMap<Integer, Long>();

    public Map<Integer, Long> getTradmap() {
        return tradmap;
    }

    public Map<Integer, Long> getTradmap2() {
        return tradmap2;
    }
    public Map<Integer, Long> getPureStrasmap() {
        return pureStrasmap;
    }

    //calculateBreakPoint
    public int calculateBreakPoint() {

        TraditionalMatrixMultiplication tmm = new TraditionalMatrixMultiplication();
        
        OriginalStrassen ds = new OriginalStrassen();
        int size = 1;
        Random rand = new Random();

        String header2 = String.format("%13s", "N") + String.format("%13s", "Trad") + String.format("%18s", "PureStrassen");
        System.out.println(String.format("%140s\n", " ").replace(" ", "_"));
        System.out.println(header2);
        System.out.println(String.format("%140s\n", " ").replace(" ", "_"));

        while (size <= 256) {

            size *= 2;

            int M1[][] = new int[size][size];
            int M2[][] = new int[size][size];
            int res[][];
            for (int i = 0; i < M1.length; i++) {
                for (int j = 0; j < M1.length; j++) {
                    M1[i][j] = rand.nextInt(200) + 100;
                    M2[i][j] = rand.nextInt(200) + 100;
                }
            }

            String OutputValues = String.format("%10d units", size);
            long starttime1 = System.currentTimeMillis();
            if (size <= 2048) {

               res = tmm.MultiplyMatrixTraditionally(M1, M2);
               long stoptime1 = System.currentTimeMillis() - starttime1;
                String tradTime = String.format("%10d ms", stoptime1);
                tradmap.put(size, stoptime1);
                OutputValues += tradTime;
            }

          
            int requiredLength = extraLength(M1.length);
            if (requiredLength > M1.length) {
                M1 = applyPadding(M1, requiredLength);
                M2 = applyPadding(M2, requiredLength);
            }

            long starttime2 = System.currentTimeMillis();
            res = ds.SendMatrixForStrassen(M1, M2, size);
            long stoptime2 = System.currentTimeMillis() - starttime2;
            String time = String.format("%10d ms", stoptime2);
            pureStrasmap.put(size, stoptime2);
            OutputValues += time;
            System.out.println(OutputValues);
        }
        int breakPoint = 0;
        return breakPoint;

    }

    //table for pure strassen 
    public int impStrassenBP() {
        TraditionalMatrixMultiplication tmm = new TraditionalMatrixMultiplication();
        ImprovedStrassen imp = new ImprovedStrassen();

        Queue<Integer> bpQueue = new LinkedList<Integer>();
        int size = 1;
        Random rand = new Random();

        String header = String.format("%16s", "N") + String.format("%13s", "Trad") + String.format("%13s", "16") + String.format("%13s", "32") + String.format("%13s", "64") + String.format("%13s", "128") + String.format("%13s", "256") + String.format("%13s", "512");
        System.out.println(String.format("%140s\n", " ").replace(" ", "_"));
        System.out.println(header);
        System.out.println(String.format("%140s\n", " ").replace(" ", "_"));

        while (size <= 1024) {

            size *= 2;
            int M1[][] = new int[size][size];
            int M2[][] = new int[size][size];
            int res[][];
            for (int i = 0; i < M1.length; i++) {
                for (int j = 0; j < M1.length; j++) {
                    M1[i][j] = rand.nextInt(200) + 100;
                    M2[i][j] = rand.nextInt(200) + 100;
                }
            }

            String OutputValues = String.format("%10d units", size);
            long nanos = System.currentTimeMillis();
            if (size <= 2048) {
                res = tmm.MultiplyMatrixTraditionally(M1, M2);
                long t1 = System.currentTimeMillis() - nanos;
                String tradTime = String.format("%10d ms", t1);
                tradmap2.put(size, t1);
                OutputValues += tradTime;
            } else {
                String tradTime = String.format("%10s   ", "NM1");
                OutputValues += tradTime;
            }

            int assumedBreakPoint = 16;
            int requiredLength = extraLength(M1.length);
            if (requiredLength > M1.length) {
                M1 = applyPadding(M1, requiredLength);
                M2 = applyPadding(M2, requiredLength);
            }
            while (assumedBreakPoint < 1024) {
                nanos = System.currentTimeMillis();
                res = imp.SendMatrixForImprovedStrassen(M1, M2, size, assumedBreakPoint);
                long timeRequired = System.currentTimeMillis() - nanos;
                String time = String.format("%10d ms", timeRequired);

                switch (assumedBreakPoint) {
                   
                    case 16:
                        map16.put(size, timeRequired);
                        break;
                    case 32:
                         map32.put(size, timeRequired);
                        break; 
                    case 64:
                         map64.put(size, timeRequired);
                        break; 
                    case 128:
                         map128.put(size, timeRequired);
                        break; 
                    case 256:
                         map256.put(size, timeRequired);
                        break; 

                    case 512:
                         map512.put(size, timeRequired);
                        break; 
                    default:                       
                }
                OutputValues += time;
                assumedBreakPoint *= 2;
            }
            System.out.println(OutputValues);
        }
        int breakPoint = 0;
        return breakPoint;

    }

    // table for improved stassen
    //to make a matrix into even x even
    public int[][] applyPadding(int[][] Given, int size) {
        int NewM[][] = new int[size][size];

        for (int i = 0; i < NewM.length; i++) {
            for (int j = 0; j < NewM.length; j++) {
                if (i < Given.length && j < Given.length) {
                    NewM[i][j] = Given[i][j];
                } else {
                    NewM[i][j] = 0;
                }
            }
        }
        return NewM;
    }

    public int extraLength(int currentLength) {
        int requiredLength = 0;
        int divisor = 1;
        while (currentLength % divisor != currentLength) {
            divisor *= 2;
            if (divisor == currentLength) {
                return divisor;
            }
        }
        requiredLength = divisor;
        return requiredLength;
    }

    public Map<Integer, Long> getMap16() {
        return map16;
    }

    public Map<Integer, Long> getMap32() {
        return map32;
    }

    public Map<Integer, Long> getMap64() {
        return map64;
    }

    public Map<Integer, Long> getMap128() {
        return map128;
    }

    public Map<Integer, Long> getMap256() {
        return map256;
    }

    public Map<Integer, Long> getMap512() {
        return map512;
    }

}
