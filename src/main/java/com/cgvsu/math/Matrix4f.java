package com.cgvsu.math;

public class Matrix4f {
    private static final int n = 4;
    private static final int m = 4;
    private float[][] matrix;

    public Matrix4f(float[][] initialMatrix) {
        MathUtil.checkArray(initialMatrix, n, m);
        this.matrix = initialMatrix;
    }

    public Matrix4f multiply(Matrix4f mat) {
        MathUtil.checkArray(mat.getMatrix(), n, m);
        float[][] res = MathUtil.multiplyMatrices(getMatrix(), mat.matrix);
        return new Matrix4f(res);
    }

    public Matrix4f transpose() {
        this.matrix = MathUtil.transposeMatrix(this.matrix);
        return this;
    }

    public Vector4f multiply(Vector4f m) {
        MathUtil.checkArray(m.getVector(), 4, 1);
        return new Vector4f(MathUtil.multiplyMatrices(this.matrix, m.getVector()));
    }

    public Matrix4f add(Matrix4f mat) {
        MathUtil.checkArray(mat.getMatrix(), n, m);
        MathUtil.addArrays(this.matrix, mat.getMatrix());
        return this;
    }

    public Matrix4f subtract(Matrix4f mat) {
        MathUtil.checkArray(mat.getMatrix(), n, m);
        MathUtil.substractArrays(this.matrix, mat.getMatrix());
        return this;
    }

    public static Matrix4f getE() {
        return new Matrix4f(new float[][]{{1.0F, 1.0F, 1.0F, 1.0F}, {1.0F, 1.0F, 1.0F, 1.0F}, {1.0F, 1.0F, 1.0F, 1.0F},{1.0F, 1.0F, 1.0F, 1.0F}});
    }

    public static Matrix4f getZ() {
        return new Matrix4f(new float[][]{
                {0.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 0.0f}
        });
    }

    public float[][] getMatrix() {
        float[][] copy = new float[4][4];
        for (int i = 0; i < 4; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, 4);
        }
        return copy;
    }
}
