package com.cgvsu.math;

import java.util.Objects;

public class Matrix4f {
    private static final int n = 4;
    private static final int m = 4;
    private float[][] matrix = new float[4][4];

    public Matrix4f(float[][] initialMatrix) {
        setMatrix(initialMatrix);
    }

    public Matrix4f transposeInPlace() {
        setMatrix(MathUtil.transposeMatrix(this.matrix));
        return this;
    }

    public Matrix4f transpose() {
        return new Matrix4f(MathUtil.transposeMatrix(this.matrix));
    }

    public Matrix4f mulInPlace(Matrix4f mat) {
        setMatrix(MathUtil.multiplyMatrices(this.matrix, mat.matrix));
        return this;
    }

    public Matrix4f mul(Matrix4f mat) {
        return new Matrix4f(MathUtil.multiplyMatrices(this.matrix, mat.matrix));
    }

    public Vector4f mul(Vector4f mat) {
        return new Vector4f(MathUtil.matrixMultiplyVector(this.matrix, mat.getVector()));
    }

    public Matrix4f addInPlace(Matrix4f mat) {
        MathUtil.addArraysInPlace(this.matrix, mat.matrix);
        return this;
    }

    public Matrix4f add(Matrix4f mat) {
        return new Matrix4f(MathUtil.addArrays(this.matrix, mat.matrix));
    }

    public Matrix4f subInPlace(Matrix4f mat) {
        MathUtil.subArraysInPlace(this.matrix, mat.matrix);
        return this;
    }

    public Matrix4f sub(Matrix4f mat) {
        return new Matrix4f(MathUtil.subArrays(this.matrix, mat.matrix));
    }

    public Matrix4f div(float scalar) {
        return new Matrix4f(MathUtil.matrixDivideByScalar(this.matrix, scalar));
    }

    public void divInPlace(float scalar) {
        MathUtil.matrixDivideByScalarInPlace(this.matrix, scalar);
    }

    public Matrix4f mulScalar(float scalar) {
        return new Matrix4f(MathUtil.matrixMulByScalar(this.matrix, scalar));
    }

    public void mulScalarInPlace(float scalar) {
        MathUtil.matrixMulByScalarInPlace(this.matrix, scalar);
    }

    public static Matrix4f getE() {
        return new Matrix4f(new float[][]{{1.0F, 0.0F, 0.0F, 0.0F}, {0.0F, 1.0F, 0.0F, 0.0F}, {0.0F, 0.0F, 1.0F, 0.0F},{0.0F, 0.0F, 0.0F, 1.0F}});
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

    public void setMatrix(float[][] mat) {
        Objects.requireNonNull(mat, "Матрица не может быть null");
        MathUtil.checkArray(mat, n, m);
        matrix[0][0] = mat[0][0]; matrix[0][1] = mat[0][1]; matrix[0][2] = mat[0][2]; matrix[0][3] = mat[0][3];
        matrix[1][0] = mat[1][0]; matrix[1][1] = mat[1][1]; matrix[1][2] = mat[1][2]; matrix[1][3] = mat[1][3];
        matrix[2][0] = mat[2][0]; matrix[2][1] = mat[2][1]; matrix[2][2] = mat[2][2]; matrix[2][3] = mat[2][3];
        matrix[3][0] = mat[3][0]; matrix[3][1] = mat[3][1]; matrix[3][2] = mat[3][2]; matrix[3][3] = mat[3][3];
    }
}
