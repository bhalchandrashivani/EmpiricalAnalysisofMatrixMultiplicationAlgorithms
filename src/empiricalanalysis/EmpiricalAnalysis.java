/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author shivani bhalchandra
 */
public class EmpiricalAnalysis {

    /**
     * @param args the command line arguments
     */
    Map<Integer, Long> tradmap = new HashMap<>();

    Map<Integer, Long> pureStrasmap = new HashMap<Integer, Long>();
    
    Map<Integer, Long> tradmap2 = new HashMap<Integer, Long>();
    Map<Integer, Long> map16 = new HashMap<Integer, Long>();
    Map<Integer, Long> map32 = new HashMap<Integer, Long>();
    Map<Integer, Long> map64 = new HashMap<Integer, Long>();
    Map<Integer, Long> map128 = new HashMap<Integer, Long>();
    Map<Integer, Long> map256 = new HashMap<Integer, Long>();
    Map<Integer, Long> map512 = new HashMap<Integer, Long>();
    List<Map> maplist = new ArrayList<>();

    public Map<Integer, Long> getTradmap() {
        return tradmap;
    }

    public Map<Integer, Long> getPureStrasmap() {
        return pureStrasmap;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        EmpiricalAnalysis empAn = new EmpiricalAnalysis();
        //Arrays.fill(timeLead, 0);
        try {
            empAn.start();
        } catch (FileNotFoundException e) {

        }
    }

    public void start() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File("MatrixInputExample.txt"));

        //accepted the first integer from the file for calculation of matrix size
        int size = scanner.nextInt();

        //create two n*n matrix
        int M1[][] = new int[size][size];
        int M2[][] = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                M1[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                M2[i][j] = scanner.nextInt();
            }
        }
        scanner.close();

        //printing two input matrix
        System.out.println("first matrix");
        printMatrix(M1);
        System.out.println("\n second  matrix");
        printMatrix(M2);

        //calculate the result by traditional method and print output
        TraditionalMatrixMultiplication tmm = new TraditionalMatrixMultiplication();
        long startTime = System.currentTimeMillis();
        int result[][] = tmm.MultiplyMatrixTraditionally(M1, M2);
        System.out.println("\nMatrix multiplication result using traditional method:\nTime Taken: " + (System.currentTimeMillis() - startTime) + " ns\n");
        printMatrix(result);

        int required = extraLength(M1.length);
        //System.out.println("test " + M1.length);

        if (required > M1.length) {
            M1 = applyPadding(M1, required);
            M2 = applyPadding(M2, required);
        }

        //calculate pure strassen
        long startTime2 = System.currentTimeMillis();
        OriginalStrassen Ds = new OriginalStrassen();
        int res2[][] = Ds.SendMatrixForStrassen(M1, M2, result.length);
        long stoptime = System.currentTimeMillis() - startTime2;
        System.out.println("\n Matrix multiplication using Strassen's Algorithm: \n Time Taken: " + stoptime + " ns\n");
        printMatrix(res2);

        //improved strassen
        long startTime3 = System.currentTimeMillis();
        ImprovedStrassen imp = new ImprovedStrassen();
        int res3[][] = imp.SendMatrixForImprovedStrassen(M1, M2, result.length, -1);
        long stoptime3 = System.currentTimeMillis() - startTime3;
        System.out.println("\n Matrix multiplication using Improved Strassen's Algorithm: \n Time Taken: " + stoptime3 + " ns\n");
        printMatrix(res3);

        //calculating breakpoint for pure vs traditional
        BreakpointCalculator bpc = new BreakpointCalculator();
        int calulatedbreakPoint = bpc.calculateBreakPoint();
        tradmap = bpc.getTradmap();
        pureStrasmap = bpc.getPureStrasmap();
        System.out.println("\n The evaluation for pure vs traditional completed" + calulatedbreakPoint);

        //calculating breakpoint for improved vs traditional
        System.out.println("\n Improved Strassen calculations");
        int impbpc = bpc.impStrassenBP();
        maplist.add(tradmap2 = bpc.getTradmap2());
        maplist.add(map16 = bpc.getMap16());
        maplist.add(map32 = bpc.getMap32());
        maplist.add(map64 = bpc.getMap64());
        maplist.add(map128 = bpc.getMap128());
        maplist.add(map256 = bpc.getMap256());
        maplist.add(map512 = bpc.getMap512());            
        System.out.println("\n The evaluation for pure vs traditional completed ");
        System.out.println("\n Based on the analysis of the chart ,the final break point needs to be 64");
    }

    //method to print the given matrix
    public void printMatrix(int[][] M) {
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                System.out.print(String.format(" %4d", M[i][j]));
            }
            System.out.print("\n");
        }
    }

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

    public Map<Integer, Long> getTradmap2() {
        return tradmap2;
    }

    public void setTradmap2(Map<Integer, Long> tradmap2) {
        this.tradmap2 = tradmap2;
    }

    public Map<Integer, Long> getMap16() {
        return map16;
    }

    public void setMap16(Map<Integer, Long> map16) {
        this.map16 = map16;
    }

    public Map<Integer, Long> getMap32() {
        return map32;
    }

    public void setMap32(Map<Integer, Long> map32) {
        this.map32 = map32;
    }

    public Map<Integer, Long> getMap64() {
        return map64;
    }

    public void setMap64(Map<Integer, Long> map64) {
        this.map64 = map64;
    }

    public Map<Integer, Long> getMap128() {
        return map128;
    }

    public void setMap128(Map<Integer, Long> map128) {
        this.map128 = map128;
    }

    public Map<Integer, Long> getMap256() {
        return map256;
    }

    public void setMap256(Map<Integer, Long> map256) {
        this.map256 = map256;
    }

    public Map<Integer, Long> getMap512() {
        return map512;
    }

    public void setMap512(Map<Integer, Long> map512) {
        this.map512 = map512;
    }

    public List<Map> getMaplist() {
        return maplist;
    }

    public void setMaplist(List<Map> maplist) {
        this.maplist = maplist;
    }
    
    

}
