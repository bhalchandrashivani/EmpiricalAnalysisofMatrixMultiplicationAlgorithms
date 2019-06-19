/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Prathyusha
*/
package empiricalanalysis;

public class TraditionalMatrixMultiplication {
    
    //method to multiply two matrix using traditional method
    //O(N^2)
    public int[][] MultiplyMatrixTraditionally(int[][] m1, int[][] m2){
        int out[][] = new int[m1.length][m1.length];
        for(int i = 0; i < m1.length; i++){
            for(int j = 0; j < m1.length; j ++){
                for(int k = 0; k < m1.length; k++){
                    out[i][j] += m1[i][k]*m2[k][j];
                }
            }
        }		
        return out;
    } 
}
