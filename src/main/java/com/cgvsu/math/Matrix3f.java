package com.cgvsu.math;

import java.util.Objects;

public class Matrix3f {
    private static final int n = 3;
    private static final int m = 3;
    private float[][] matrix = new float[3][3];

    public Matrix3f(float[][] initialMatrix) {
        setMatrix(initialMatrix);
    }

    public Matrix3f transposeInPlace() {
        setMatrix(MathUtil.transposeMatrix(this.matrix));
        return this;
    }

    public Matrix3f transpose() {
        return new Matrix3f(MathUtil.transposeMatrix(this.matrix));
    }

    public Matrix3f mulInPlace(Matrix3f mat) {
        setMatrix(MathUtil.multiplyMatrices (this.matrix, mat.matrix));
        return this;
    }

    public Matrix3f mul(Matrix3f mat) {
        return new Matrix3f(MathUtil.multiplyMatrices(this.matrix, mat.matrix));
    }

    public Vector3f mul(Vector3f mat) {
        return new Vector3f(MathUtil.matrixMultiplyVector(this.matrix, mat.getVector()));
    }

    public Matrix3f addInPlace(Matrix3f mat) {
        MathUtil.addArraysInPlace(this.matrix, mat.matrix);
        return this;
    }

    public Matrix3f add(Matrix3f mat) {
        return new Matrix3f(MathUtil.addArrays(this.matrix, mat.matrix));
    }

    public Matrix3f div(float scalar) {
        return new Matrix3f(MathUtil.matrixDivideByScalar(this.matrix, scalar));
    }

    public void divInPlace(float scalar) {
        MathUtil.matrixDivideByScalarInPlace(this.matrix, scalar);
    }

    public Matrix3f mulScalar(float scalar) {
        return new Matrix3f(MathUtil.matrixMulByScalar(this.matrix, scalar));
    }

    public void mulScalarInPlace(float scalar) {
        MathUtil.matrixMulByScalarInPlace(this.matrix, scalar);
    }

    public Matrix3f subInPlace(Matrix3f mat) {
        MathUtil.subArraysInPlace(this.matrix, mat.matrix);
        return this;
    }

    public Matrix3f sub(Matrix3f mat) {
        return new Matrix3f(MathUtil.subArrays(this.matrix, mat.matrix));
    }

    public static Matrix3f getE() {
        return new Matrix3f(new float[][]{{1.0F, 0.0F, 0.0F}, {0.0F, 1.0F, 0.0F}, {0.0F, 0.0F, 1.0F}});
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

    public void setMatrix(float[][] mat) {
        Objects.requireNonNull(mat, "Матрица не может быть null");
        MathUtil.checkArray(mat, n, m);
        matrix[0][0] = mat[0][0]; matrix[0][1] = mat[0][1]; matrix[0][2] = mat[0][2];
        matrix[1][0] = mat[1][0]; matrix[1][1] = mat[1][1]; matrix[1][2] = mat[1][2];
        matrix[2][0] = mat[2][0]; matrix[2][1] = mat[2][1]; matrix[2][2] = mat[2][2];
    }
}
