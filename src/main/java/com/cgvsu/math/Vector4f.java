package com.cgvsu.math;

import java.util.Objects;

public class Vector4f {
    private float[] vector = new float[4];

    public Vector4f(float[] initialMatrix) {
        setVector(initialMatrix);
    }

    public Vector4f(Vector3f vec, float w) {
        Objects.requireNonNull(vec, "Матрица не может быть null");
        float[] f;
        if (vec.getVector().length != 4) {
            throw new IllegalArgumentException("Вектор должен иметь размерность 4");
        }
        f = vec.getVector();
        vector[0] = f[0];
        vector[1] = f[1];
        vector[2] = f[2];
        vector[3] = w;
    }

    public Vector4f(float x, float y, float z, float w) {
        setVector(new float[]{x, y, z, w});
    }

    public Vector4f(Vector4f vec) {
        setVector(vec.vector);
    }

    public Vector4f mulInPlace(float num) {
        MathUtil.vectorMultiplyByScalarInPlace(this.vector, num);
        return this;
    }

    public Vector4f mul(float num) {
        float[] arr = MathUtil.vectorMultiplyByScalar(this.vector, num);
        return new Vector4f(arr);
    }

    public Vector4f div(float num) {
        float[] arr = MathUtil.vectorDivideByScalar(this.vector, num);
        return new Vector4f(arr);
    }

    public Vector4f divInPlace(float num) {
        MathUtil.vectorDivideByScalarInPlace(this.vector, num);
        return this;
    }

    public Vector4f addInPlace(Vector4f vec) {
        MathUtil.vectorAddInPlace(this.vector, vec.vector);
        return this;
    }

    public Vector4f add(Vector4f vec) {
        float[] arr = MathUtil.vectorAdd(this.vector, vec.vector);
        return new Vector4f(arr);
    }

    public Vector4f subInPlace(Vector4f vec) {
        MathUtil.vectorSubtractInPlace(this.vector, vec.vector);
        return this;
    }

    public Vector4f sub(Vector4f vec) {
        float[] arr = MathUtil.vectorSubtract(this.vector, vec.vector);
        return new Vector4f(arr);
    }

    public float getLength() {
        float x = vector[0];
        float y = vector[1];
        float z = vector[2];
        float w = vector[3];
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Vector4f normalize() {
        float len = getLength();
        if (MathUtil.floatEquals(len, 0.0f)) {
            return this;
        }
        this.vector[0] /= len;
        this.vector[1] /= len;
        this.vector[2] /= len;
        this.vector[3] /= len;
        return this;
    }

    public float dot(Vector4f vec) {
        return MathUtil.vectorScalarProduct(this.vector, vec.vector);
    }

    public float[] getVector() {
        float[] copy = new float[4];
        copy[0] = vector[0];
        copy[1] = vector[1];
        copy[2] = vector[2];
        copy[3] = vector[3];
        return copy;
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

    public float getW() {
        return vector[3];
    }

    public Vector3f ndc() {
        float w = vector[3];
        if (MathUtil.floatEquals(w, 0.0f)) {
            return new Vector3f(getVector());
        }

        float[] ndcArray = new float[3];
        ndcArray[0] = vector[0] / w;
        ndcArray[1] = vector[1] / w;
        ndcArray[2] = vector[2] / w;

        return new Vector3f(ndcArray);
    }

    public Vector4f ndcInPlace() {
        float w = vector[3];
        if (w == 0.0f) {
            throw new RuntimeException("W = 0, приведение к ndc невозможно");
        }

        this.vector[0] /= w;
        this.vector[1] /= w;
        this.vector[2] /= w;
        this.vector[3] /= w;

        return this;
    }

    public Vector3f ndcAndModify() {
        float w = vector[3];
        if (w == 0.0f) {
            throw new RuntimeException("W = 0, приведение к ndc невозможно");
        }

        float[] ndcArray = new float[3];
        ndcArray[0] = vector[0] / w;
        ndcArray[1] = vector[1] / w;
        ndcArray[2] = vector[2] / w;

        this.vector[0] = ndcArray[0];
        this.vector[1] = ndcArray[1];
        this.vector[2] = ndcArray[2];

        return new Vector3f(ndcArray);
    }

    public void setVector(float[] mat) {
        Objects.requireNonNull(mat, "Матрица не может быть null");
        if (mat.length != 4) {
            throw new IllegalArgumentException("Вектор должен иметь размерность 4");
        }
        vector[0] = mat[0];
        vector[1] = mat[1];
        vector[2] = mat[2];
        vector[3] = mat[3];
    }

}