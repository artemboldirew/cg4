package com.cgvsu.math;

public class Quaternion {
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3f axis, float angle) {
        axis = axis.normalize();
        float halfAngle = angle * 0.5f;
        float sin = (float) Math.sin(halfAngle);
        float cos = (float) Math.cos(halfAngle);

        this.x = axis.getX() * sin;
        this.y = axis.getY() * sin;
        this.z = axis.getZ() * sin;
        this.w = cos;
    }

    public static Quaternion identity() {
        return new Quaternion(0, 0, 0, 1);
    }

    public static Quaternion fromAxisAngle(Vector3f axis, float angle) {
        return new Quaternion(axis, angle);
    }

    public Quaternion multiply(Quaternion q) {
        float newX = w * q.x + x * q.w + y * q.z - z * q.y;
        float newY = w * q.y - x * q.z + y * q.w + z * q.x;
        float newZ = w * q.z + x * q.y - y * q.x + z * q.w;
        float newW = w * q.w - x * q.x - y * q.y - z * q.z;

        return new Quaternion(newX, newY, newZ, newW);
    }

    public Vector3f rotateVector(Vector3f v) {
        Quaternion vecQuat = new Quaternion(v.getX(), v.getY(), v.getZ(), 0);
        Quaternion conjugate = new Quaternion(-x, -y, -z, w);
        Quaternion result = this.multiply(vecQuat).multiply(conjugate);

        return new Vector3f(result.x, result.y, result.z);
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }

    public Quaternion normalize() {
        float len = (float) Math.sqrt(x*x + y*y + z*z + w*w);
        if (len > 0.0001f) {
            return new Quaternion(x/len, y/len, z/len, w/len);
        }
        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    public static Quaternion fromEuler(float yaw, float pitch, float roll) {
        float cy = (float) Math.cos(yaw * 0.5);
        float sy = (float) Math.sin(yaw * 0.5);
        float cp = (float) Math.cos(pitch * 0.5);
        float sp = (float) Math.sin(pitch * 0.5);
        float cr = (float) Math.cos(roll * 0.5);
        float sr = (float) Math.sin(roll * 0.5);

        float qw = cr * cp * cy + sr * sp * sy;
        float qx = sr * cp * cy - cr * sp * sy;
        float qy = cr * sp * cy + sr * cp * sy;
        float qz = cr * cp * sy - sr * sp * cy;

        return new Quaternion(qx, qy, qz, qw);
    }

    public Matrix4f toRotationMatrix() {
        float xx = x * x;
        float xy = x * y;
        float xz = x * z;
        float xw = x * w;
        float yy = y * y;
        float yz = y * z;
        float yw = y * w;
        float zz = z * z;
        float zw = z * w;

        float[][] m = new float[4][4];
        m[0][0] = 1 - 2 * (yy + zz);
        m[0][1] = 2 * (xy - zw);
        m[0][2] = 2 * (xz + yw);
        m[0][3] = 0;

        m[1][0] = 2 * (xy + zw);
        m[1][1] = 1 - 2 * (xx + zz);
        m[1][2] = 2 * (yz - xw);
        m[1][3] = 0;

        m[2][0] = 2 * (xz - yw);
        m[2][1] = 2 * (yz + xw);
        m[2][2] = 1 - 2 * (xx + yy);
        m[2][3] = 0;

        m[3][0] = 0;
        m[3][1] = 0;
        m[3][2] = 0;
        m[3][3] = 1;

        return new Matrix4f(m);
    }
}