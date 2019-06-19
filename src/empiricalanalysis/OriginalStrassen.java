package empiricalanalysis;

/**
 *
 * @author prathyusha
 */
public class OriginalStrassen {

    //This method will remove the extra padding we added to make the any given matrix into a factor of 2
    public int[][] SendMatrixForStrassen(int[][] A, int[][] B, int givenLength) {
        int D[][] = new int[givenLength][givenLength];

        int C[][] = strassenMultiplication(A, B);
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D.length; j++) {
                D[i][j] = C[i][j];
            }
        }

        return D;
    }

    // this method recursively divides the matrix by a factor of /2
    private static int[][] strassenMultiplication(int[][] A, int[][] B) {

        int halflen = A.length;

        int[][] result = new int[halflen][halflen];

        // if the input matrix is 1x1
        if (halflen == 1) {
            result[0][0] = A[0][0] * B[0][0];
        } else {

            // Dividing the length of first matrix into half recursively
            int[][] a = new int[halflen / 2][halflen / 2];
            int[][] b = new int[halflen / 2][halflen / 2];
            int[][] c = new int[halflen / 2][halflen / 2];
            int[][] d = new int[halflen / 2][halflen / 2];

            // Dividing the length of second matrix into half recursively 
            int[][] e = new int[halflen / 2][halflen / 2];
            int[][] f = new int[halflen / 2][halflen / 2];
            int[][] g = new int[halflen / 2][halflen / 2];
            int[][] h = new int[halflen / 2][halflen / 2];

            // Dividing matrix A into 4 parts
            arrayDivision(A, a, 0, 0);
            arrayDivision(A, b, 0, halflen / 2);
            arrayDivision(A, c, halflen / 2, 0);
            arrayDivision(A, d, halflen / 2, halflen / 2);

            // Dividing matrix B into 4 parts
            arrayDivision(B, e, 0, 0);
            arrayDivision(B, f, 0, halflen / 2);
            arrayDivision(B, g, halflen / 2, 0);
            arrayDivision(B, h, halflen / 2, halflen / 2);

            /**
             * Equations to be formed: p1 = (a + d)(e + h) p2 = (c + d)e p3 =
             * a(f - h) p4 = d(g - e) p5 = (a + b)h p6 = (c - a) (e + f) p7 = (b
             * - d) (g + h)
            *
             */
            int[][] p1 = strassenMultiplication(matricesAddition(a, d), matricesAddition(e, h));
            int[][] p2 = strassenMultiplication(matricesAddition(c, d), e);
            int[][] p3 = strassenMultiplication(a, subMatrices(f, h));
            int[][] p4 = strassenMultiplication(d, subMatrices(g, e));
            int[][] p5 = strassenMultiplication(matricesAddition(a, b), h);
            int[][] p6 = strassenMultiplication(subMatrices(c, a), matricesAddition(e, f));
            int[][] p7 = strassenMultiplication(subMatrices(b, d), matricesAddition(g, h));

            /**
             * Operations to perform to get resultant matrix C11 = p1 + p4 - p5
             * + p7 C12 = p3 + p5 C21 = p2 + p4 C22 = p1 - p2 + p3 + p6
            *
             */
            int[][] C11 = matricesAddition(subMatrices(matricesAddition(p1, p4), p5), p7);
            int[][] C12 = matricesAddition(p3, p5);
            int[][] C21 = matricesAddition(p2, p4);
            int[][] C22 = matricesAddition(subMatrices(matricesAddition(p1, p3), p2), p6);

            // adding all subarray back into one
            copySubArray(C11, result, 0, 0);
            copySubArray(C12, result, 0, halflen / 2);
            copySubArray(C21, result, halflen / 2, 0);
            copySubArray(C22, result, halflen / 2, halflen / 2);
        }
        return result;
    }

    // Helper methods
    // Adding 2 matrices
    public static int[][] matricesAddition(int[][] a, int[][] b) {
        int n = a.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    // Subtracting 2 matrices
    public static int[][] subMatrices(int[][] a, int[][] b) {
        int len = a.length;
        int[][] result = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    // Divides the array
    public static void arrayDivision(int[][] P, int[][] C, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
                C[i1][j1] = P[i2][j2];
            }
        }
    }

    // copies
    public static void copySubArray(int[][] C, int[][] P, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
                P[i2][j2] = C[i1][j1];
            }
        }
    }

}
