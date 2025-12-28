package com.cgvsu.math;

import java.util.Objects;

public class Vector3f {
    private float[] vector = new float[3];

    public Vector3f(float[] initialMatrix) {
        setVector(initialMatrix);
    }

    public Vector3f(float x, float y, float z) {
        setVector(new float[]{x, y, z});
    }

    public Vector3f(Vector3f vec) {
        setVector(vec.vector);
    }

    public Vector3f mulInPlace(float num) {
        MathUtil.vectorMultiplyByScalarInPlace(this.vector, num);
        return this;
    }

    public Vector3f mul(float num) {
        float[] arr = MathUtil.vectorMultiplyByScalar(this.vector, num);
        return new Vector3f(arr);
    }

    public Vector3f div(float num) {
        float[] arr = MathUtil.vectorDivideByScalar(this.vector, num);
        return new Vector3f(arr);
    }

    public Vector3f divInPlace(float num) {
        MathUtil.vectorDivideByScalarInPlace(this.vector, num);
        return this;
    }

    public Vector3f addInPlace(Vector3f vec) {
        MathUtil.vectorAddInPlace(this.vector, vec.vector);
        return this;
    }

    public Vector3f add(Vector3f vec) {
        float[] arr = MathUtil.vectorAdd(this.vector, vec.vector);
        return new Vector3f(arr);
    }

    public Vector3f subInPlace(Vector3f vec) {
        MathUtil.vectorSubtractInPlace(this.vector, vec.vector);
        return this;
    }

    public Vector3f sub(Vector3f vec) {
        float[] arr = MathUtil.vectorSubtract(this.vector, vec.vector);
        return new Vector3f(arr);
    }

    public float getLength() {
        float x = vector[0];
        float y = vector[1];
        float z = vector[2];
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Vector3f normalize() {
        float len = getLength();
        if (MathUtil.floatEquals(len, 0.0f)) {
            return this;
        }
        this.vector[0] /= len;
        this.vector[1] /= len;
        this.vector[2] /= len;
        return this;
    }

    public float dot(Vector3f vec) {
        return MathUtil.vectorScalarProduct(this.vector, vec.vector);
    }

    public Vector3f cross(Vector3f vec) {
        float x1 = this.vector[0];
        float y1 = this.vector[1];
        float z1 = this.vector[2];

        float x2 = vec.vector[0];
        float y2 = vec.vector[1];
        float z2 = vec.vector[2];

        float[] result = new float[3];
        result[0] = y1 * z2 - z1 * y2;
        result[1] = z1 * x2 - x1 * z2;
        result[2] = x1 * y2 - y1 * x2;

        return new Vector3f(result);
    }

    public float getX() {
        return vector[0];
    }

    public float getY() {
        return vector[1];
    }

    public float getZ() {
        return vector[2];
    }

    public float[] getVector() {
        float[] copy = new float[3];
        copy[0] = vector[0];
        copy[1] = vector[1];
        copy[2] = vector[2];
        return copy;
    }

    public void setVector(float[] mat) {
        Objects.requireNonNull(mat, "Матрица не может быть null");
        if (mat.length != 3) {
            throw new IllegalArgumentException("Вектор должен иметь размерность 3");
        }
        vector[0] = mat[0];
        vector[1] = mat[1];
        vector[2] = mat[2];
    }

    public Vector4f to4f() {
        return new Vector4f(getX(), getY(), getZ(), 1);
    }
}