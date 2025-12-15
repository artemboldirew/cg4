package com.cgvsu.math;

public class Vector3f {
    private static final int n = 3;
    private static final int m = 1;
    private float[][] vector;

    public Vector3f(float x, float y, float z) {
        this.vector = new float[][]{{x}, {y}, {z}};
    }

    public Vector3f(float[][] initialMatrix) {
        MathUtil.checkArray(initialMatrix, n, m);
        this.vector = initialMatrix;
    }

    public Vector3f multiply(float num) {
        MathUtil.checkArray(this.vector, n, m);
        return new Vector3f(MathUtil.multiplyByNum(getVector(), num));
    }

    public Vector3f divide(float num) {
        MathUtil.checkArray(this.vector, n, m);
        return new Vector3f(MathUtil.divideByNum(getVector(), num));
    }

    public Vector3f add(Vector3f vec) {
        MathUtil.checkArray(this.vector, n, m);
        return new Vector3f(MathUtil.addArrays(getVector(), vec.getVector()));
    }


    public Vector3f subtract(Vector3f vec) {
        return new Vector3f(MathUtil.substractArrays(getVector(), vec.getVector()));
    }

    public float getLength() {
        return (float) Math.pow(Math.pow(vector[0][0], 2) + Math.pow(vector[1][0], 2) + Math.pow(vector[2][0], 2) ,0.5);
    }

    public Vector3f normalize() {
        float len = getLength();
        this.vector[0][0] /= len;
        this.vector[1][0] /= len;
        this.vector[2][0] /= len;
        return this;
    }


    public float dot(Vector3f vec) {
        MathUtil.checkArray(vec.getVector(), n, m);
        return MathUtil.scalarArrayProduct(this.vector, vec.getVector());
    }

    public Vector3f cross(Vector3f vec) {
        float[] first = new float[]{this.vector[0][0], this.vector[1][0], this.vector[2][0]};
        float[] second = new float[]{vec.vector[0][0], vec.vector[1][0], vec.vector[2][0]};
        float[][] resVector = new float[3][1];
        resVector[0][0] = first[1]*second[2] - first[2]*second[1];
        resVector[1][0] = first[2]*second[0] - first[0]*second[2];
        resVector[2][0] = first[0]*second[1] - first[1]*second[0];

        return new Vector3f(resVector);
    }

    public Vector4f to4f() {
        return new Vector4f(new float[][] {{getX()} ,{getY()}, {getZ()}, {1}});
    }



    public float[][] getVector() {
        return new float[][] {{getX()} ,{getY()}, {getZ()}};
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
}
