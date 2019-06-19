/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;

/**
 *
 * @author kaustubhspande
 */
public class StrassenMatrixMutliplication {
    
    public int[][] SendMatrixForStrassen(int[][] A, int[][] B, int orignalLength, int assumedBreakPoint){
		int D[][] = new int[orignalLength][orignalLength];
		
		int C[][] = strassen(A, B, A.length/2, assumedBreakPoint);
		for(int i = 0; i < D.length; i++){
			for(int j = 0; j < D.length; j++){
				D[i][j] = C[i][j];
			}
		}
		
		return D;
	}
    
    public int[][] strassen(int[][] M1, int[][] M2, int halfLength, int asumedBreakPoint){
		int C[][] = new int[M1.length][M1.length];
		
		if(halfLength == 0){
			C[0][0] = M1[0][0]*M2[0][0];
			return C;
		}
		
		int a[][] = new int[halfLength][halfLength];
		int d[][] = new int[halfLength][halfLength];
		
		int e[][] = new int[halfLength][halfLength];
		int h[][] = new int[halfLength][halfLength];
		
		int S1[][] = new int[halfLength][halfLength];
		int S2[][] = new int[halfLength][halfLength];
		int S3[][] = new int[halfLength][halfLength];
		int S4[][] = new int[halfLength][halfLength];
		int S5[][] = new int[halfLength][halfLength];
		int S6[][] = new int[halfLength][halfLength];
		int S7[][] = new int[halfLength][halfLength];
		int S8[][] = new int[halfLength][halfLength];
		int S9[][] = new int[halfLength][halfLength];
		int S10[][] = new int[halfLength][halfLength];

		for(int i = 0; i < halfLength; i++){
			for(int j = 0; j < halfLength; j++){
				a[i][j] = M1[i][j];
				d[i][j] = M1[halfLength + i][halfLength + j];
				
				e[i][j] = M2[i][j];
				h[i][j] = M2[halfLength + i][halfLength + j];
				
				 S1[i][j] = M2[i][halfLength + j] - M2[halfLength + i][halfLength + j];
				 S2[i][j] = M1[i][j] + M1[i][halfLength + j];
				 S3[i][j] = M1[halfLength + i][j] + M1[halfLength + i][halfLength + j];
				 S4[i][j] = M2[halfLength + i][j] - M2[i][j];//g-e
				 S5[i][j] = M1[i][j] + M1[halfLength + i][halfLength + j];//a+d
				 S6[i][j] = M2[i][j] + M2[halfLength + i][halfLength + j];//e + h
				 S7[i][j] = M1[i][halfLength + j] - M1[halfLength + i][halfLength + j];//b-d
				 S8[i][j] = M2[halfLength + i][j] + M2[halfLength + i][halfLength + j]; //g+h
				 S9[i][j] = M1[i][j] - M1[halfLength + i][j];//a-c
				 S10[i][j] = M2[i][j] + M2[i][halfLength + j];//e+f
			}
		}
		
		int P1[][];// = strassen(a, S1, halfLength/2);
		int P2[][];// = strassen(S2, h, halfLength/2);
		int P3[][];// = strassen(S3, e, halfLength/2);
		int P4[][];// = strassen(d, S4, halfLength/2);
		int P5[][];// = strassen(S5, S6, halfLength/2);
		int P6[][];// = strassen(S7, S8, halfLength/2);
		int P7[][];// = strassen(S9, S10, halfLength/2);
		
		/*if(M1.length <= asumedBreakPoint){
                //    TraditionalMatrixMultiplication tmm = new TraditionalMatrixMultiplication();
		//	P1 = tmm.MultiplyMatrixTraditionally(a, S1); 
			P2 = tmm.MultiplyMatrixTraditionally(S2, h);
			P3 = tmm.MultiplyMatrixTraditionally(S3, e);
			P4 = tmm.MultiplyMatrixTraditionally(d, S4);
			P5 = tmm.MultiplyMatrixTraditionally(S5, S6);
			P6 = tmm.MultiplyMatrixTraditionally(S7, S8);
			P7 = tmm.MultiplyMatrixTraditionally(S9, S10);
		}else{*/
			 P1 = strassen(a, S1, halfLength/2, asumedBreakPoint);
			 P2 = strassen(S2, h, halfLength/2, asumedBreakPoint);
			 P3 = strassen(S3, e, halfLength/2, asumedBreakPoint);
			 P4 = strassen(d, S4, halfLength/2, asumedBreakPoint);
			 P5 = strassen(S5, S6, halfLength/2, asumedBreakPoint);
			 P6 = strassen(S7, S8, halfLength/2, asumedBreakPoint);
			 P7 = strassen(S9, S10, halfLength/2, asumedBreakPoint);
		//}

		for(int i = 0; i < P1.length; i++){
			for(int j = 0; j < P1.length; j++){
				C[i][j] = P5[i][j] + P4[i][j] - P2[i][j] + P6[i][j];//C11[i][j];
				C[i][P1.length + j] = P1[i][j] + P2[i][j];//C12[i][j];
				C[P1.length + i][j] = P3[i][j] + P4[i][j];//C21[i][j];
				C[P1.length + i][P1.length + j] = P1[i][j] + P5[i][j] - P3[i][j] - P7[i][j];//C22[i][j];
			}
		}
                
		return C;
                
    }  
    
    
    
    
    
}               
                
                    
    /*public int[][] strassen(int[][] M1, int[][] M2 ){
        
        int n = M1.length;

        int[][] res = new int[n][n];

        // if the input matrix is 1x1
        if (n == 1) {
            res[0][0] = M1[0][0] * M2[0][0];
        } else {

            // first matrix
            int[][] a = new int[n/2][n/2];
            int[][] b = new int[n/2 ][n /2];
            int[][] c = new int[n /2][n /2];
            int[][] d = new int[n/2][n/2];
            
            // second matrix
            int[][] e = new int[n/2][n/2];
            int[][] f = new int[n/2][n/2];
            int[][] g = new int[n/2][n/2];
            int[][] h = new int[n/2][n/2];

            // dividing matrix M1 into 4 parts
            divideMatrix(M1, a, 0, 0);
            divideMatrix(M1, b, 0, n/2);
            divideMatrix(M1, c, n/2, 0);
            divideMatrix(M1, d, n/2, n/2);

            // dividing matrix M2 into 4 parts
            divideMatrix(M2, e, 0, 0);
            divideMatrix(M2, f, 0, n/2);
            divideMatrix(M2, g, n/2, 0);
            divideMatrix(M2, h, n/2, n/2);
            
            /** 
              p1 = (a + d)(e + h)
              p2 = (c + d)e
              p3 = a(f - h)
              p4 = d(g - e)
              p5 = (a + b)h
              p6 = (c - a) (e + f)
              p7 = (b - d) (g + h)
           
           
            int[][] p1 = strassen(addMatrix(a, d), addMatrix(e, h));
            int[][] p2 = strassen(addMatrix(c,d),e);
            int[][] p3 = strassen(a, subtractMatrix(f, h));           
            int[][] p4 = strassen(d, subtractMatrix(g, e));
            int[][] p5 = strassen(addMatrix(a,b), h);
            int[][] p6 = strassen(subtractMatrix(c, a), addMatrix(e, f));
            int[][] p7 = strassen(subtractMatrix(b, d), addMatrix(g, h));

            
           /**
              C11 = p1 + p4 - p5 + p7
              C12 = p3 + p5
              C21 = p2 + p4
              C22 = p1 - p2 + p3 + p6
          
           
            int[][] C11 = addMatrix(subtractMatrix(addMatrix(p1, p4), p5), p7);
            int[][] C12 = addMatrix(p3, p5);
            int[][] C21 = addMatrix(p2, p4);
            int[][] C22 = addMatrix(subtractMatrix(addMatrix(p1, p3), p2), p6);

            // adding all subarray back into one
            
            putBackMatrix(C11, res, 0, 0);
            putBackMatrix(C12, res, 0, n/2);
            putBackMatrix(C21, res, n/2 , 0);
            putBackMatrix(C22, res, n/2, n/2 );
        }
        return res;
        
        
        
    }
    
    public static int[][] addMatrix(int[][] A, int[][] B) {
        int n = A.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = A[i][j] + B[i][j];
            }
        }
        return res;
    }
    
    // Subtraction of 2 matrices
    public static int[][] subtractMatrix(int[][] A, int[][] B) {
        int n = A.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = A[i][j] - B[i][j];
            }
        }
        return res;
    }
    
   // divides matrix
    public static void divideMatrix(int[][] A, int[][] B, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < B.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < B.length; j1++, j2++)
                B[i1][j1] = A[i2][j2];
    }

    // put back given array into required slot by copying array
    public static void putBackMatrix(int[][] A, int[][] B, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < A.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < A.length; j1++, j2++)
                B[i2][j2] = A[i1][j1];
    }   

    */
