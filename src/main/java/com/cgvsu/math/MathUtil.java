package com.cgvsu.math;

public class MathUtil {
    public static final float DELTA = 0.0001f;
    //создается новая
    public static float[][] multiplyMatrices(float[][] a, float[][] b) {
        int aRows = a.length;
        int aCols = a[0].length;
        int bRows = b.length;
        int bCols = b[0].length;

        float[][] result = new float[aRows][bCols];

        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bCols; j++) {
                float sum = 0.0f;
                for (int k = 0; k < aCols; k++) {
                    sum += a[i][k] * b[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    //создается новая
    public static float[][] transposeMatrix(float[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        float[][] transposed = new float[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    public static float[][] addArrays(float[][] arr1, float[][] arr2) {
        int n = arr1.length;;
        int m = arr1[0].length;
        float[][] arr = new float[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = arr1[i][j] + arr2[i][j];
            }
        }
        return arr;
    }

    public static void addArraysInPlace(float[][] arr1, float[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] = arr1[i][j] + arr2[i][j];
            }
        }
    }

    public static void subArraysInPlace(float[][] arr1, float[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] = arr1[i][j] - arr2[i][j];
            }
        }
    }

    public static float[][] subArrays(float[][] arr1, float[][] arr2) {
        int n = arr1.length;;
        int m = arr1[0].length;
        float[][] arr = new float[n][m];
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr[i][j] = arr1[i][j] - arr2[i][j];
            }
        }
        return arr;
    }

    public static void multiplyByNumInPlace(float[][] arr, float num) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = arr[i][j] * num;
            }
        }
    }

    public static float[][] multiplyByNum(float[][] arr, float num) {
        int n = arr.length;
        int m = arr[0].length;
        float[][] res = new float[n][m];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                res[i][j] = arr[i][j] * num;
            }
        }
        return res;
    }

    public static void divideByNumInPlace(float[][] arr, float num) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = arr[i][j] / num;
            }
        }
    }

    public static float[][] divideByNum(float[][] arr, float num) {
        int n = arr.length;
        int m = arr[0].length;
        float[][] res = new float[n][m];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                res[i][j] = arr[i][j] / num;
            }
        }
        return res;
    }

    public static float scalarArrayProduct(float[][] arr1, float[][] arr2) {
        float res = 0;
        for (int i = 0; i < arr1.length; i++) {
            res += (arr1[i][0] * arr2[i][0]);
        }
        return res;
    }

    public static void checkArray(float[][] matrix, int rows, int cols) {
        if (matrix.length != rows || matrix[0].length != cols) {
            throw new IllegalArgumentException("Неправильные размеры матрицы");
        }
    }


    public static float[] vectorAdd(float[] a, float[] b) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }

    public static float[] vectorSubtract(float[] a, float[] b) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    public static float[] vectorMultiplyByScalar(float[] vector, float scalar) {
        float[] result = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * scalar;
        }
        return result;
    }

    public static float[] vectorDivideByScalar(float[] vector, float scalar) {
        float[] result = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] / scalar;
        }
        return result;
    }

    public static float[] vectorElementwiseMultiply(float[] a, float[] b) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b[i];
        }
        return result;
    }

    public static float[] vectorElementwiseDivide(float[] a, float[] b) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] / b[i];
        }
        return result;
    }

    public static float vectorDotProduct(float[] a, float[] b) {
        float result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    public static void vectorAddInPlace(float[] target, float[] source) {
        for (int i = 0; i < target.length; i++) {
            target[i] += source[i];
        }
    }

    public static void vectorSubtractInPlace(float[] target, float[] source) {
        for (int i = 0; i < target.length; i++) {
            target[i] -= source[i];
        }
    }

    public static void vectorMultiplyByScalarInPlace(float[] vector, float scalar) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] *= scalar;
        }
    }

    public static void vectorDivideByScalarInPlace(float[] vector, float scalar) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] /= scalar;
        }
    }


    public static float vectorScalarProduct(float[] a, float[] b) {
        float result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i] * b[i];
        }
        return result;
    }

    public static float[] matrixMultiplyVector(float[][] matrix, float[] vector) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        float[] result = new float[rows];

        for (int i = 0; i < rows; i++) {
            float sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }


    public static float[][] matrixDivideByScalar(float[][] matrix, float scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        float[][] result = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] / scalar;
            }
        }

        return result;
    }

    public static void matrixDivideByScalarInPlace(float[][] matrix, float scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] /= scalar;
            }
        }
    }

    public static float[][] matrixMulByScalar(float[][] matrix, float scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        float[][] result = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] * scalar;
            }
        }

        return result;
    }

    public static void matrixMulByScalarInPlace(float[][] matrix, float scalar) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] *= scalar;
            }
        }
    }

    public static boolean floatEquals(float a, float b) {
        return Math.abs(a - b) <= DELTA;
    }
}
