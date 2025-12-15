package com.cgvsu.math;

public class MathUtil {
    public static float[][] multiplyMatrices(float[][] a, float[][] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Матрицы не могут быть null");
        }

        if (a.length == 0 || a[0].length == 0 || b.length == 0 || b[0].length == 0) {
            throw new IllegalArgumentException("Матрицы не могут быть пустыми");
        }

        int aRows = a.length;
        int aCols = a[0].length;
        int bRows = b.length;
        int bCols = b[0].length;

        if (aCols != bRows) {
            throw new IllegalArgumentException(
                    String.format("Несовместимые размеры матриц: %dx%d и %dx%d. " +
                                    "Количество столбцов первой матрицы должно равняться количеству строк второй матрицы",
                            aRows, aCols, bRows, bCols)
            );
        }

        for (int i = 1; i < aRows; i++) {
            if (a[i].length != aCols) {
                throw new IllegalArgumentException("Первая матрица должна быть прямоугольной");
            }
        }

        for (int i = 1; i < bRows; i++) {
            if (b[i].length != bCols) {
                throw new IllegalArgumentException("Вторая матрица должна быть прямоугольной");
            }
        }

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

    public static float[][] transposeMatrix(float[][] matrix) {
        // Проверка на null
        if (matrix == null) {
            throw new IllegalArgumentException("Матрица не может быть null");
        }

        // Проверка, что матрица не пустая
        if (matrix.length == 0) {
            return new float[0][0];
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        // Проверка, что матрица прямоугольная
        for (int i = 1; i < rows; i++) {
            if (matrix[i].length != cols) {
                throw new IllegalArgumentException("Матрица должна быть прямоугольной");
            }
        }

        // Создание транспонированной матрицы
        float[][] transposed = new float[cols][rows];

        // Транспонирование
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }

        return transposed;
    }

    public static float[][] addArrays(float[][] arr1, float[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] = arr1[i][j] + arr2[i][j];
            }
        }
        return arr1;
    }

    public static float[][] substractArrays(float[][] arr1, float[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr1[i][j] = arr1[i][j] - arr2[i][j];
            }
        }
        return arr1;
    }

    public static float[][] multiplyByNum(float[][] arr, float num) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = arr[i][j] * num;
            }
        }
        return arr;
    }

    public static float[][] divideByNum(float[][] arr, float num) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = arr[i][j] / num;
            }
        }
        return arr;
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
            throw new IllegalArgumentException("Incorrect matrix");
        }
    }
}
