package com.cgvsu.math;

public class Matrix3f {
    private static final int n = 3;
    private static final int m = 3;
    private float[][] matrix;

    public Matrix3f(float[][] initialMatrix) {
        MathUtil.checkArray(initialMatrix, n, m);
        this.matrix = initialMatrix;
    }

    public Matrix3f multiply(Matrix3f mat) {
        MathUtil.checkArray(mat.getMatrix(), n, m);
        float[][] res = MathUtil.multiplyMatrices(getMatrix(), mat.matrix);
        return new Matrix3f(res);
    }

    public Matrix3f transpose() {
        this.matrix = MathUtil.transposeMatrix(this.matrix);
        return this;
    }

    public Matrix3f multiply(Vector3f vec) {
        MathUtil.checkArray(vec.getVector(), 3, 1);
        this.matrix = MathUtil.multiplyMatrices(this.matrix, vec.getVector());
        return this;
    }

    public Matrix3f add(Matrix3f mat) {
        MathUtil.checkArray(mat.getMatrix(), n, m);
        MathUtil.addArrays(this.matrix, mat.getMatrix());
        return this;
    }

    public Matrix3f subtract(Matrix3f mat) {
        MathUtil.checkArray(mat.getMatrix(), n, m);
        MathUtil.substractArrays(this.matrix, mat.getMatrix());
        return this;
    }

    public Matrix4f to4f() {
        float[][] arr4 = new float[4][4];
        float[][] arr3 = matrix;
        arr4[0][0] = arr3[0][0];
        arr4[0][1] = arr3[0][1];
        arr4[0][2] = arr3[0][2];
        arr4[1][0] = arr3[1][0];
        arr4[1][1] = arr3[1][1];
        arr4[1][2] = arr3[1][2];
        arr4[2][0] = arr3[2][0];
        arr4[2][1] = arr3[2][1];
        arr4[2][2] = arr3[2][2];
        arr4[3][3] = 1;
        return new Matrix4f(arr4);
    }

    public static Matrix3f getE() {
        return new Matrix3f(new float[][]{{1.0F, 1.0F, 1.0F}, {1.0F, 1.0F, 1.0F}, {1.0F, 1.0F, 1.0F}});
    }

    public static Matrix3f getZ() {
        return new Matrix3f(new float[][]{
                {0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f}
        });
    }

    public float[][] getMatrix() {
        float[][] copy = new float[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, 3);
        }
        return copy;
    }
}
