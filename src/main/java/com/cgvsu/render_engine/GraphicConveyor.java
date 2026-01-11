package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Point2f;
import com.cgvsu.math.Quaternion;
import com.cgvsu.math.Vector3f;

public class GraphicConveyor {


    public static Matrix4f getModelMatrix(Placement placement) {
        Vector3f r = placement.getR();
        Vector3f s = placement.getS();
        Vector3f t = placement.getT();
        Matrix4f S = new Matrix4f(new float[][]{
                {s.getX(), 0,  0,  0},
                {0,  s.getY(), 0,  0},
                {0,  0,  s.getZ(), 0},
                {0,  0,  0,  1}
        });

        Quaternion q = Quaternion.fromEuler(r.getX(), r.getY(), r.getZ());
        Matrix4f rotateMatrix = q.toRotationMatrix();


        Matrix4f T = new Matrix4f(new float[][]{
                {1, 0, 0, t.getX()},
                {0, 1, 0, t.getY()},
                {0, 0, 1, t.getZ()},
                {0, 0, 0, 1}
        });

        return T.mul(rotateMatrix.mul(S));
    }



    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f eye = camera.getPosition();
        Vector3f target = camera.getTarget();
        Vector3f forward = eye.sub(target).normalize();
        Vector3f up = camera.getUp();
        Vector3f right = up.cross(forward).normalize();
        if (right.getLength() < 1e-6f) {
            Vector3f alternativeUp = Math.abs(forward.getY()) < 0.99f
                    ? new Vector3f(0, 1, 0)
                    : new Vector3f(0, 0, 1);
            right = alternativeUp.cross(forward).normalize();
        }

        up = forward.cross(right).normalize();
        float[][] m = new float[4][4];

        m[0][0] = right.getX();   m[0][1] = right.getY();   m[0][2] = right.getZ();   m[0][3] = -right.dot(eye);
        m[1][0] = up.getX();      m[1][1] = up.getY();      m[1][2] = up.getZ();      m[1][3] = -up.dot(eye);
        m[2][0] = forward.getX(); m[2][1] = forward.getY(); m[2][2] = forward.getZ(); m[2][3] = -forward.dot(eye);
        m[3][0] = 0f;             m[3][1] = 0f;             m[3][2] = 0f;             m[3][3] = 1f;

        camera.setUp(up);

        return new Matrix4f(m);
    }



    public static Matrix4f getProjectionMatrix(Camera camera) {
        float fov = camera.getFov();
        float aspect = camera.getAspectRatio();
        float near = camera.getNearPlane();
        float far = camera.getFarPlane();

        float f = (float)(1.0 / Math.tan(fov * 0.5));

        float[][] m = new float[4][4];

        m[0][0] = f / aspect;
        m[1][1] = f;

        m[2][2] = (far + near) / (near - far);
        m[2][3] = (2 * far * near) / (near - far);

        m[3][2] = -1.0f;
        m[3][3] = 0.0f;

        return new Matrix4f(m);
    }



    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {
        return new Point2f(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
}