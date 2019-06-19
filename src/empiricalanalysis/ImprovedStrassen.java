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
public class ImprovedStrassen {
    
    
     public int[][] SendMatrixForImprovedStrassen(int[][] A, int[][] B, int orignalLength, int assumedBreakPoint){
		int D[][] = new int[orignalLength][orignalLength];
		
		int C[][] = strassenImp(A, B, A.length/2, assumedBreakPoint);
		for(int i = 0; i < D.length; i++){
			for(int j = 0; j < D.length; j++){
				D[i][j] = C[i][j];
			}
		}
		
		return D;
	}
    
    public int[][] strassenImp(int[][] M1, int[][] M2, int halfLength, int asumedBreakPoint){
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
		
		if(M1.length <= asumedBreakPoint){
                    TraditionalMatrixMultiplication tmm = new TraditionalMatrixMultiplication();
			P1 = tmm.MultiplyMatrixTraditionally(a, S1); 
			P2 = tmm.MultiplyMatrixTraditionally(S2, h);
			P3 = tmm.MultiplyMatrixTraditionally(S3, e);
			P4 = tmm.MultiplyMatrixTraditionally(d, S4);
			P5 = tmm.MultiplyMatrixTraditionally(S5, S6);
			P6 = tmm.MultiplyMatrixTraditionally(S7, S8);
			P7 = tmm.MultiplyMatrixTraditionally(S9, S10);
		}else{
			 P1 = strassenImp(a, S1, halfLength/2, asumedBreakPoint);
			 P2 = strassenImp(S2, h, halfLength/2, asumedBreakPoint);
			 P3 = strassenImp(S3, e, halfLength/2, asumedBreakPoint);
			 P4 = strassenImp(d, S4, halfLength/2, asumedBreakPoint);
			 P5 = strassenImp(S5, S6, halfLength/2, asumedBreakPoint);
			 P6 = strassenImp(S7, S8, halfLength/2, asumedBreakPoint);
			 P7 = strassenImp(S9, S10, halfLength/2, asumedBreakPoint);
		}

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
