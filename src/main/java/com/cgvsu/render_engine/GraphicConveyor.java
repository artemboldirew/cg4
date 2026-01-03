package com.cgvsu.render_engine;

import com.cgvsu.math.Matrix3f;
import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Point2f;
import com.cgvsu.math.Quaternionf;
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

        Quaternionf q = Quaternionf.fromEuler(ry, rx, rz);
        Matrix4f rotateMatrix = q.toRotationMatrix();

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

        return T.mul(Rz.mul(Ry.mul(Rx.mul(S))));
        //return T.mul(rotateMatrix.mul(S));
    }

//    public static Matrix4f getViewMatrix(Camera camera) {
//        Vector3f eye = camera.getPosition();
//        Vector3f target = camera.getTarget();
//
//        // Направление взгляда (от камеры к цели)
//        Vector3f z = target.sub(eye).normalize();
//
//        // Используем вектор "право" для стабилизации, а не глобальный up
//        Vector3f worldUp = new Vector3f(0.0F, 1.0F, 0.0F);
//
//        // Вычисляем локальный вектор "право" (правая рука камеры)
//        Vector3f x = worldUp.cross(z).normalize();
//
//        // Если камера смотрит прямо вверх или вниз
//        if (x.getLength() < 1e-6f) {
//            // Используем альтернативный вектор для вычисления правого направления
//            Vector3f alternativeUp = new Vector3f(0.0F, 0.0F, 1.0F);
//            x = alternativeUp.cross(z).normalize();
//        }
//
//        // Локальный "верх" вычисляем из направления взгляда и правого вектора
//        Vector3f y = z.cross(x).normalize();
//
//        // Теперь x - право, y - верх, z - направление (от камеры к цели)
//        // Для view matrix нам нужно обратное направление (от мира к камере)
//
//        float[][] matrix = new float[][]{
//                { x.getX(), x.getY(), x.getZ(), -x.dot(eye) },
//                { y.getX(), y.getY(), y.getZ(), -y.dot(eye) },
//                { z.getX(), z.getY(), z.getZ(), -z.dot(eye) },
//                { 0.0f,     0.0f,     0.0f,      1.0f }
//        };
//
//        return new Matrix4f(matrix);
//    }

//    public static Matrix4f getViewMatrix(Camera camera) {
//        Vector3f eye = camera.getPosition();
//        Vector3f target = camera.getTarget();
//
//        // Получаем сохраненный up вектор камеры
//        Vector3f up = camera.getUp();
//
//        // Направление взгляда (от камеры к цели)
//        Vector3f forward = target.sub(eye).normalize();
//
//        // Проверяем, не параллельны ли forward и up
//        if (Math.abs(forward.dot(up)) > 0.9999f) {
//            // Используем альтернативную ось
//            Vector3f alternativeUp = new Vector3f(0.0F, 0.0F, 1.0F);
//            if (Math.abs(forward.dot(alternativeUp)) > 0.9999f) {
//                alternativeUp = new Vector3f(1.0F, 0.0F, 0.0F);
//            }
//
//            Vector3f right = forward.cross(alternativeUp).normalize();
//            up = right.cross(forward).normalize();
//
//            // Сохраняем исправленный up вектор
//            camera.setUp(up);
//        }
//
//        // Вычисляем локальные оси камеры
//        Vector3f right = up.cross(forward).normalize();
//
//        // Еще раз нормализуем up для точности
//        up = forward.cross(right).normalize();
//
//        // Для view matrix нужна обратная трансформация
//        float[][] matrix = new float[][]{
//                { right.getX(), right.getY(), right.getZ(), -right.dot(eye) },
//                { up.getX(), up.getY(), up.getZ(), -up.dot(eye) },
//                { forward.getX(), forward.getY(), forward.getZ(), -forward.dot(eye) },
//                { 0.0f, 0.0f, 0.0f, 1.0f }
//        };
//
//        return new Matrix4f(matrix);
//    }

    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f eye = camera.getPosition();
        Vector3f target = camera.getTarget();

        // Направление взгляда: от камеры к цели (z-ось камеры)
        Vector3f forward = eye.sub(target).normalize(); // Обратите внимание: eye - target

        // Сохраняем up вектор камеры
        Vector3f up = camera.getUp();

        // Вычисляем локальную правую ось (x)
        Vector3f right = up.cross(forward).normalize();

        // Если forward почти параллелен up, используем альтернативу
        if (right.getLength() < 1e-6f) {
            Vector3f alternativeUp = Math.abs(forward.getY()) < 0.99f
                    ? new Vector3f(0, 1, 0)
                    : new Vector3f(0, 0, 1);
            right = alternativeUp.cross(forward).normalize();
        }

        // Пересчитываем точный up вектор (y)
        up = forward.cross(right).normalize();

        // Строим view матрицу
        float[][] m = new float[4][4];

        m[0][0] = right.getX();   m[0][1] = right.getY();   m[0][2] = right.getZ();   m[0][3] = -right.dot(eye);
        m[1][0] = up.getX();      m[1][1] = up.getY();      m[1][2] = up.getZ();      m[1][3] = -up.dot(eye);
        m[2][0] = forward.getX(); m[2][1] = forward.getY(); m[2][2] = forward.getZ(); m[2][3] = -forward.dot(eye);
        m[3][0] = 0f;             m[3][1] = 0f;             m[3][2] = 0f;             m[3][3] = 1f;

        // Обновляем up в Camera на случай дальнейших вращений
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