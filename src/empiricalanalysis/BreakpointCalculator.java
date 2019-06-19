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
import javafx.util.Pair;

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
        StrassenMatrixMutliplication smm = new StrassenMatrixMutliplication();
        DifferentStrassen ds = new DifferentStrassen();
        int size = 1;
        Random rand = new Random();

        String header1 = String.format("%16s", "N") + String.format("%13s", "Trad") + String.format("%13s", "16") + String.format("%13s", "32") + String.format("%13s", "64") + String.format("%13s", "128") + String.format("%13s", "256") + String.format("%13s", "512");
        String header2 = String.format("%16s", "N") + String.format("%13s", "Trad") + String.format("%18s", "PureStrassen");
        System.out.println(String.format("%140s\n", " ").replace(" ", "_"));
        System.out.println(header2);
        System.out.println(String.format("%140s\n", " ").replace(" ", "_"));

        while (size <= 256) {

            size *= 2;

            int A[][] = new int[size][size];
            int B[][] = new int[size][size];
            int C[][];
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A.length; j++) {
                    A[i][j] = rand.nextInt(200) + 100;
                    B[i][j] = rand.nextInt(200) + 100;
                }
            }

            String readings = String.format("%10d units", size);
            long starttime1 = System.currentTimeMillis();
            if (size <= 2048) {

                C = tmm.MultiplyMatrixTraditionally(A, B);

                long stoptime1 = System.currentTimeMillis() - starttime1;
                String tradTime = String.format("%10d ms", stoptime1);

                tradmap.put(size, stoptime1);

                readings += tradTime;
            }

            //int assumedBreakPoint = 16;
            int requiredLength = extraLength(A.length);
            if (requiredLength > A.length) {
                A = applyPadding(A, requiredLength);
                B = applyPadding(B, requiredLength);
            }

            long starttime2 = System.currentTimeMillis();
            C = ds.SendMatrixForStrassen(A, B, size);
            long stoptime2 = System.currentTimeMillis() - starttime2;
            String time = String.format("%10d ms", stoptime2);
            pureStrasmap.put(size, stoptime2);
            readings += time;
            System.out.println(readings);
        }
        int breakPoint = 0;
        return breakPoint / 5;

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

        while (size <= 512) {

            size *= 2;
            int A[][] = new int[size][size];
            int B[][] = new int[size][size];
            int C[][];
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A.length; j++) {
                    A[i][j] = rand.nextInt(200) + 100;
                    B[i][j] = rand.nextInt(200) + 100;
                }
            }

            String readings = String.format("%10d units", size);
            long nanos = System.currentTimeMillis();
            if (size <= 2048) {
                C = tmm.MultiplyMatrixTraditionally(A, B);
                long t1 = System.currentTimeMillis() - nanos;
                String tradTime = String.format("%10d ms", t1);
                tradmap2.put(size, t1);
                readings += tradTime;
            } else {
                String tradTime = String.format("%10s   ", "NA");
                readings += tradTime;
            }

            int assumedBreakPoint = 16;
            int requiredLength = extraLength(A.length);
            if (requiredLength > A.length) {
                A = applyPadding(A, requiredLength);
                B = applyPadding(B, requiredLength);
            }
            while (assumedBreakPoint < 1024) {
                nanos = System.currentTimeMillis();
                C = imp.SendMatrixForImprovedStrassen(A, B, size, assumedBreakPoint);
                long t2 = System.currentTimeMillis() - nanos;
                String time = String.format("%10d ms", t2);
                switch (assumedBreakPoint) {
                    
                    case 16:
                        map16.put(size, t2);
                        break; // break is optional

                    case 32:
                         map32.put(size, t2);
                        break; // break is optional

                    case 64:
                         map64.put(size, t2);
                        break; // break is optional

                    case 128:
                         map128.put(size, t2);
                        break; // break is optional

                    case 256:
                         map256.put(size, t2);
                        break; // break is optional

                    case 512:
                         map512.put(size, t2);
                        break; // break is optional

                    default:
                       
                }

                readings += time;
                assumedBreakPoint *= 2;
            }
            System.out.println(readings);
        }

        int breakPoint = 0;
        return breakPoint / 5;

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
