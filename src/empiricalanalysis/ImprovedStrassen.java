/*
 * To change this license header, choose License Xeaders in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;

/**
 *
 * @author Abhishek Dongare
 */
public class ImprovedStrassen{
 //This method will remove the extra padding we added to make the any given matrix into a factor of 2
    public int[][] SendMatrixForImprovedStrassen(int[][] A, int[][] B, int orignalLength, int assumedBreakPoint) {
        int D[][] = new int[orignalLength][orignalLength];

        int C[][] = strassenImp(A, B, A.length / 2, assumedBreakPoint);
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D.length; j++) {
                D[i][j] = C[i][j];
            }
        }

        return D;
    }

    public int[][] strassenImp(int[][] M1, int[][] M2, int halvedLength, int asumedBreakPoint) {
        int C[][] = new int[M1.length][M1.length];

        if (halvedLength == 0) {
            C[0][0] = M1[0][0] * M2[0][0];
            return C;
        }
// this method recursively divides the matrix by a factor of /2
        int a[][] = new int[halvedLength][halvedLength];
        int d[][] = new int[halvedLength][halvedLength];

        int e[][] = new int[halvedLength][halvedLength];
        int h[][] = new int[halvedLength][halvedLength];
        //
        int X1[][] = new int[halvedLength][halvedLength];
        int X2[][] = new int[halvedLength][halvedLength];
        int X3[][] = new int[halvedLength][halvedLength];
        int X4[][] = new int[halvedLength][halvedLength];
        int X5[][] = new int[halvedLength][halvedLength];
        int X6[][] = new int[halvedLength][halvedLength];
        int X7[][] = new int[halvedLength][halvedLength];
        int X8[][] = new int[halvedLength][halvedLength];
        int X9[][] = new int[halvedLength][halvedLength];
        int X10[][] = new int[halvedLength][halvedLength];
   //Divide and conquer is initiated.
        for (int i = 0; i < halvedLength; i++) {
            for (int j = 0; j < halvedLength; j++) {
                a[i][j] = M1[i][j];
                d[i][j] = M1[halvedLength + i][halvedLength + j];

                e[i][j] = M2[i][j];
                h[i][j] = M2[halvedLength + i][halvedLength + j];

                X1[i][j] = M2[i][halvedLength + j] - M2[halvedLength + i][halvedLength + j];
                X2[i][j] = M1[i][j] + M1[i][halvedLength + j];
                X3[i][j] = M1[halvedLength + i][j] + M1[halvedLength + i][halvedLength + j];
                X4[i][j] = M2[halvedLength + i][j] - M2[i][j];//g-e
                X5[i][j] = M1[i][j] + M1[halvedLength + i][halvedLength + j];//a+d
                X6[i][j] = M2[i][j] + M2[halvedLength + i][halvedLength + j];//e + h
                X7[i][j] = M1[i][halvedLength + j] - M1[halvedLength + i][halvedLength + j];//b-d
                X8[i][j] = M2[halvedLength + i][j] + M2[halvedLength + i][halvedLength + j]; //g+h
                X9[i][j] = M1[i][j] - M1[halvedLength + i][j];//a-c
                X10[i][j] = M2[i][j] + M2[i][halvedLength + j];//e+f
            }
        }

        int Y1[][];// = strassen(a, X1, halvedLength/2);
        int Y2[][];// = strassen(X2, h, halvedLength/2);
        int Y3[][];// = strassen(X3, e, halvedLength/2);
        int Y4[][];// = strassen(d, X4, halvedLength/2);
        int Y5[][];// = strassen(X5, X6, halvedLength/2);
        int Y6[][];// = strassen(X7, X8, halvedLength/2);
        int Y7[][];// = strassen(X9, X10, halvedLength/2);
//This breakpoint is the size of the matrix after which the Strassen breakdown stops, and the matrices are multiplied using traditional multiplication method
        if (M1.length <= asumedBreakPoint) {
            TraditionalMatrixMultiplication tmm = new TraditionalMatrixMultiplication();
            Y1 = tmm.MultiplyMatrixTraditionally(a, X1);
            Y2 = tmm.MultiplyMatrixTraditionally(X2, h);
            Y3 = tmm.MultiplyMatrixTraditionally(X3, e);
            Y4 = tmm.MultiplyMatrixTraditionally(d, X4);
            Y5 = tmm.MultiplyMatrixTraditionally(X5, X6);
            Y6 = tmm.MultiplyMatrixTraditionally(X7, X8);
            Y7 = tmm.MultiplyMatrixTraditionally(X9, X10);
        } else {
            Y1 = strassenImp(a, X1, halvedLength / 2, asumedBreakPoint);
            Y2 = strassenImp(X2, h, halvedLength / 2, asumedBreakPoint);
            Y3 = strassenImp(X3, e, halvedLength / 2, asumedBreakPoint);
            Y4 = strassenImp(d, X4, halvedLength / 2, asumedBreakPoint);
            Y5 = strassenImp(X5, X6, halvedLength / 2, asumedBreakPoint);
            Y6 = strassenImp(X7, X8, halvedLength / 2, asumedBreakPoint);
            Y7 = strassenImp(X9, X10, halvedLength / 2, asumedBreakPoint);
        }

        for (int i = 0; i < Y1.length; i++) {
            for (int j = 0; j < Y1.length; j++) {
                C[i][j] = Y5[i][j] + Y4[i][j] - Y2[i][j] + Y6[i][j];//C11[i][j];
                C[i][Y1.length + j] = Y1[i][j] + Y2[i][j];//C12[i][j];
                C[Y1.length + i][j] = Y3[i][j] + Y4[i][j];//C21[i][j];
                C[Y1.length + i][Y1.length + j] = Y1[i][j] + Y5[i][j] - Y3[i][j] - Y7[i][j];//C22[i][j];
            }
        }

        return C;

    }

}
