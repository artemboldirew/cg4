package com.cgvsu.math;

public class Vector4f {
    private static final int n = 4;
    private static final int m = 1;
    private float[][] vector;

    public Vector4f(float x, float y, float z, float w) {
        this.vector = new float[][]{{x}, {y}, {z}, {w}};
    }

    public Vector4f(float[][] initialMatrix) {
        MathUtil.checkArray(initialMatrix, n, m);
        this.vector = initialMatrix;
    }

    public Vector4f multiply(float num) {
        MathUtil.multiplyByNum(this.vector, num);
        return this;
    }

    public Vector4f divide(float num) {
        MathUtil.checkArray(this.vector, n, m);
        MathUtil.divideByNum(this.vector, num);
        return this;
    }

    public Vector4f add(Vector4f vec) {
        MathUtil.checkArray(this.vector, n, m);
        MathUtil.addArrays(this.vector, vec.getVector());
        return this;
    }

    public Vector4f subtract(Vector4f vec) {
        MathUtil.checkArray(vec.getVector(), n, m);
        MathUtil.substractArrays(this.vector, vec.getVector());
        return this;
    }

    public float getLength() {
        return (float) Math.pow(Math.pow(vector[0][0], 2) + Math.pow(vector[1][0], 2) + Math.pow(vector[2][0], 2) ,0.5);
    }


    public Vector4f normalize() {
        float len = getLength();
        this.vector[0][0] /= len;
        this.vector[1][0] /= len;
        this.vector[2][0] /= len;
        return this;
    }

    public Vector3f ndc() {
        float len = vector[3][0];
        this.vector[0][0] /= len;
        this.vector[1][0] /= len;
        this.vector[2][0] /= len;
        return new Vector3f(new float[][]{{getX()} ,{getY()}, {getZ()}});
    }


    public float scalarProduct(Vector4f vec) {
        MathUtil.checkArray(vec.getVector(), n, m);
        return MathUtil.scalarArrayProduct(this.vector, vec.getVector());
    }

    public float[][] getVector() {
        return new float[][] {{getX()} ,{getY()}, {getZ()}, {getW()}};
    }

    public float getX() {
        if (vector != null) {
            return vector[0][0];
        }
        throw new RuntimeException("Вектор пуст");
    }

    public float getY() {
        if (vector != null) {
            return vector[1][0];
        }
        throw new RuntimeException("Вектор пуст");
    }

    public float getZ() {
        if (vector != null) {
            return vector[2][0];
        }
        throw new RuntimeException("Вектор пуст");
    }

    public float getW() {
        if (vector != null) {
            return vector[3][0];
        }
        throw new RuntimeException("Вектор пуст");
    }
}
