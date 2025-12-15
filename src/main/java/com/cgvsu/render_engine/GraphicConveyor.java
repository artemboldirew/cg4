package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix3f;
import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Point2f;
import com.cgvsu.math.Vector3f;

public class GraphicConveyor {


    public static Matrix4f getModelMatrix(float sx, float sy, float sz, float rx, float ry, float rz, float tx, float ty, float tz) {
        float sinx = (float) Math.sin(rx);
        float cosx = (float) Math.cos(rx);
        float siny = (float) Math.sin(ry);
        float cosy = (float) Math.cos(ry);
        float sinz = (float) Math.sin(rz);
        float cosz = (float) Math.cos(rz);
        Matrix4f S = new Matrix4f(new float[][]{
                {sx, 0,  0,  0},
                {0,  sy, 0,  0},
                {0,  0,  sz, 0},
                {0,  0,  0,  1}
        });

        Matrix4f Rx = new Matrix4f(new float[][]{
                {1, 0,    0,     0},
                {0, cosx, sinx,  0},
                {0, -sinx, cosx, 0},
                {0, 0,    0,     1}
        });

        Matrix4f Ry = new Matrix4f(new float[][]{
                {cosy, 0, -siny, 0},
                {0,    1, 0,     0},
                {siny, 0, cosy,  0},
                {0,    0, 0,     1}
        });

        Matrix4f Rz = new Matrix4f(new float[][]{
                {cosz,  -sinz, 0, 0},
                {sinz,  cosz,  0, 0},
                {0,     0,     1, 0},
                {0,     0,     0, 1}
        });

        Matrix4f T = new Matrix4f(new float[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        });

        return T.multiply(Rz.multiply(Ry.multiply(Rx.multiply(S))));
    }

    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f up = new Vector3f(0F, 1.0F, 0F);
        Vector3f target = camera.getTarget();
        Vector3f eye = camera.getPosition();

        Vector3f z = eye.subtract(target).normalize();
        Vector3f x = up.cross(z).normalize();
        Vector3f y = z.cross(x);

        float[][] matrix = new float[][]{
                { x.getX(), x.getY(), x.getZ(), -x.dot(eye) },
                { y.getX(), y.getY(), y.getZ(), -y.dot(eye) },
                { z.getX(), z.getY(), z.getZ(), -z.dot(eye) },
                { 0.0f,     0.0f,     0.0f,      1.0f }
        };

        return new Matrix4f(matrix);
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