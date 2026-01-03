package com.cgvsu.math;

public final class Quaternionf {

    public float x, y, z, w;

    /* =========================
       КОНСТРУКТОРЫ
       ========================= */

    public Quaternionf() {
        // единичный кватернион
        this.w = 1f;
    }

    public Quaternionf(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /* =========================
       СОЗДАНИЕ
       ========================= */

    // Ось–угол (ось должна быть нормализована)
    public static Quaternionf fromAxisAngle(Vector3f axis, float angleRad) {
        axis = axis.normalize();

        float half = angleRad * 0.5f;
        float sin = (float) Math.sin(half);

        return new Quaternionf(
                axis.getX() * sin,
                axis.getY() * sin,
                axis.getZ() * sin,
                (float) Math.cos(half)
        );
    }

    // Из Эйлеровых углов (yaw, pitch, roll)
    // yaw   — вокруг Y
    // pitch — вокруг X
    // roll  — вокруг Z
    public static Quaternionf fromEuler(float yaw, float pitch, float roll) {

        float cy = (float) Math.cos(yaw * 0.5f);
        float sy = (float) Math.sin(yaw * 0.5f);
        float cp = (float) Math.cos(pitch * 0.5f);
        float sp = (float) Math.sin(pitch * 0.5f);
        float cr = (float) Math.cos(roll * 0.5f);
        float sr = (float) Math.sin(roll * 0.5f);

        return new Quaternionf(
                sr * cp * cy - cr * sp * sy,
                cr * sp * cy + sr * cp * sy,
                cr * cp * sy - sr * sp * cy,
                cr * cp * cy + sr * sp * sy
        ).normalize();
    }

    /* =========================
       ОПЕРАЦИИ
       ========================= */

    // Умножение (this * r)
    public Quaternionf mul(Quaternionf r) {
        return new Quaternionf(
                w * r.x + x * r.w + y * r.z - z * r.y,
                w * r.y - x * r.z + y * r.w + z * r.x,
                w * r.z + x * r.y - y * r.x + z * r.w,
                w * r.w - x * r.x - y * r.y - z * r.z
        );
    }

    public Quaternionf normalize() {
        float len = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        if (len == 0) return this;
        x /= len;
        y /= len;
        z /= len;
        w /= len;
        return this;
    }

    public Quaternionf conjugate() {
        return new Quaternionf(-x, -y, -z, w);
    }

    /* =========================
       ВРАЩЕНИЕ ВЕКТОРОВ
       ========================= */

    public Vector3f rotate(Vector3f v) {
        Quaternionf qv = new Quaternionf(v.getX(), v.getY(), v.getZ(), 0);
        Quaternionf res = this.mul(qv).mul(this.conjugate());
        return new Vector3f(res.x, res.y, res.z);
    }

    /* =========================
       ОСИ ОРИЕНТАЦИИ
       ========================= */

    public Vector3f getForward() {
        return rotate(new Vector3f(0, 0, -1));
    }

    public Vector3f getUp() {
        return rotate(new Vector3f(0, 1, 0));
    }

    public Vector3f getRight() {
        return rotate(new Vector3f(1, 0, 0));
    }

    /* =========================
       МАТРИЦА ПОВОРОТА
       ========================= */

    public Matrix4f toRotationMatrix() {

        float xx = x * x;
        float yy = y * y;
        float zz = z * z;
        float xy = x * y;
        float xz = x * z;
        float yz = y * z;
        float wx = w * x;
        float wy = w * y;
        float wz = w * z;

        return new Matrix4f(new float[][]{
                {1 - 2 * (yy + zz),     2 * (xy - wz),         2 * (xz + wy),         0},
                {2 * (xy + wz),         1 - 2 * (xx + zz),     2 * (yz - wx),         0},
                {2 * (xz - wy),         2 * (yz + wx),         1 - 2 * (xx + yy),     0},
                {0,                     0,                     0,                     1}
        });
    }

    /* =========================
       ОБРАТНО В ЭЙЛЕРЫ (осторожно!)
       ========================= */

    public Vector3f toEuler() {
        float pitch;
        float sinp = 2 * (w * x - y * z);
        if (Math.abs(sinp) >= 1)
            pitch = (float) Math.copySign(Math.PI / 2, sinp);
        else
            pitch = (float) Math.asin(sinp);

        float yaw = (float) Math.atan2(
                2 * (w * y + z * x),
                1 - 2 * (x * x + y * y)
        );

        float roll = (float) Math.atan2(
                2 * (w * z + x * y),
                1 - 2 * (x * x + z * z)
        );

        return new Vector3f(yaw, pitch, roll);
    }
}
