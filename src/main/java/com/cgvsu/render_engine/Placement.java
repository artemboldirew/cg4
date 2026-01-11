package com.cgvsu.render_engine;

import com.cgvsu.math.Vector3f;

public class Placement {
    private Vector3f s;
    private Vector3f r;
    private Vector3f t;


    public Placement(float sx, float sy, float sz, float rx, float ry, float rz, float tx, float ty, float tz) {
        this.s = new Vector3f(sx, sy, sz);
        this.r = new Vector3f(rx, ry, rz);
        this.t = new Vector3f(tx, ty, tz);
    }


    public void setRotation(float rx, float ry, float rz) {
        r.setVector(new float[]{rx, ry, rz});
    }


    public void setScale(float sx, float sy, float sz) {
        s.setVector(new float[]{sx, sy, sz});
    }

    public void setTranslate(float tx, float ty, float tz) {
        t.setVector(new float[]{tx, ty, tz});
    }

    public void rotateX(float rx) {

    }


    public Vector3f getS() {
        return s;
    }

    public Vector3f getR() {
        return r;
    }

    public Vector3f getT() {
        return t;
    }
}
