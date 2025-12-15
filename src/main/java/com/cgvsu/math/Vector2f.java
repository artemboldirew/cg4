package com.cgvsu.math;

public class Vector2f {
    private static final int n = 2;
    private static final int m = 1;
    private float[][] vector;

    public Vector2f(float x, float y) {
        this.vector = new float[][]{{x}, {y}};
    }

    public Vector2f(float[][] initialMatrix) {
        MathUtil.checkArray(initialMatrix, n, m);
        this.vector = initialMatrix;
    }

    public Vector2f multiply(float num) {
        MathUtil.multiplyByNum(this.vector, num);
        return this;
    }

    public Vector2f divide(float num) {
        MathUtil.checkArray(this.vector, n, m);
        MathUtil.divideByNum(this.vector, num);
        return this;
    }

    public Vector2f add(Vector2f vec) {
        MathUtil.checkArray(this.vector, n, m);
        MathUtil.addArrays(this.vector, vec.getVector());
        return this;
    }

    public Vector2f subtract(Vector2f vec) {
        MathUtil.checkArray(vec.getVector(), n, m);
        MathUtil.substractArrays(this.vector, vec.getVector());
        return this;
    }

    public float getLength() {
        return (float) Math.pow(Math.pow(vector[0][0], 2) + Math.pow(vector[1][0], 2) ,0.5);
    }

    public Vector2f normalize() {
        float len = getLength();
        this.vector[0][0] /= len;
        this.vector[1][0] /= len;
        return this;
    }


    public float scalarProduct(Vector2f vec) {
        MathUtil.checkArray(vec.getVector(), n, m);
        return MathUtil.scalarArrayProduct(this.vector, vec.getVector());
    }

    public float[][] getVector() {
        return vector;
    }
}
