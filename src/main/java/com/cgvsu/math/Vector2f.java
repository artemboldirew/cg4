package com.cgvsu.math;

import java.util.Objects;

public class Vector2f {
    private float[] vector = new float[2];

    public Vector2f(float[] initialMatrix) {
        setVector(initialMatrix);
    }

    public Vector2f(float x, float y) {
        setVector(new float[]{x, y});
    }

    public Vector2f(Vector2f vec) {
        setVector(vec.vector);
    }

    public Vector2f mulInPlace(float num) {
        MathUtil.vectorMultiplyByScalarInPlace(this.vector, num);
        return this;
    }

    public Vector2f mul(float num) {
        float[] arr = MathUtil.vectorMultiplyByScalar(this.vector, num);
        return new Vector2f(arr);
    }

    public Vector2f div(float num) {
        float[] arr = MathUtil.vectorDivideByScalar(this.vector, num);
        return new Vector2f(arr);
    }

    public Vector2f divInPlace(float num) {
        MathUtil.vectorDivideByScalarInPlace(this.vector, num);
        return this;
    }

    public Vector2f addInPlace(Vector2f vec) {
        MathUtil.vectorAddInPlace(this.vector, vec.vector);
        return this;
    }

    public Vector2f add(Vector2f vec) {
        float[] arr = MathUtil.vectorAdd(this.vector, vec.vector);
        return new Vector2f(arr);
    }

    public Vector2f subInPlace(Vector2f vec) {
        MathUtil.vectorSubtractInPlace(this.vector, vec.vector);
        return this;
    }

    public Vector2f sub(Vector2f vec) {
        float[] arr = MathUtil.vectorSubtract(this.vector, vec.vector);
        return new Vector2f(arr);
    }

    public float getLength() {
        float x = vector[0];
        float y = vector[1];
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2f normalize() {
        float len = getLength();
        if (MathUtil.floatEquals(len, 0.0f)) {
            return this;
        }
        this.vector[0] /= len;
        this.vector[1] /= len;
        return this;
    }

    public float dot(Vector2f vec) {
        return MathUtil.vectorScalarProduct(this.vector, vec.vector);
    }

    public float[] getVector() {
        float[] copy = new float[2];
        copy[0] = vector[0];
        copy[1] = vector[1];
        return copy;
    }

    public void setVector(float[] mat) {
        Objects.requireNonNull(mat, "Матрица не может быть null");
        if (mat.length != 2) {
            throw new IllegalArgumentException("Вектор должен иметь размерность 2");
        }
        vector[0] = mat[0];
        vector[1] = mat[1];
    }

    public float getX() {
        if (vector != null) {
            return vector[0];
        }
        throw new RuntimeException("Вектор пуст");
    }

    public float getY() {
        return vector[1];
    }
}