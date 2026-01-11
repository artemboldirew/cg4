package com.cgvsu.render_engine;


import com.cgvsu.math.MathUtil;
import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Quaternion;
import com.cgvsu.math.Quaternionf;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;

public class Camera {
    private Vector3f up;

    public Camera(
            final Vector3f position,
            final Vector3f target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
        this.up = new Vector3f(0.0F, 1.0F, 0.0F);
    }

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target.add(target);
    }

    Matrix4f getViewMatrix() {
        return GraphicConveyor.getViewMatrix(this);
    }

    Matrix4f getProjectionMatrix() {
        return GraphicConveyor.getProjectionMatrix(this);
    }

    private Vector3f position;
    private Vector3f target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

    public float getFov() {
        return fov;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public float getNearPlane() {
        return nearPlane;
    }

    public float getFarPlane() {
        return farPlane;
    }

    public void addZPosition(float delta) {
        position.addInPlace(new Vector3f(0, 0, delta));
    }

    public void addXPosition(float delta) {
        position.addInPlace(new Vector3f(delta, 0, 0));
    }

    public void addYPosition(float delta) {
        position.addInPlace(new Vector3f(0, delta, 0));
    }

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up.normalize();
    }

    public void zoomToCursor(float zoomDelta) {
        // Получаем текущее направление взгляда камеры
        Vector3f forward = target.sub(position).normalize();
        float currentDistance = target.sub(position).getLength();
        float minDistance = 1.0f; // Минимальное расстояние до цели

        // Проверяем, не подходим ли слишком близко
        if (currentDistance - zoomDelta < minDistance) {
            zoomDelta = currentDistance - minDistance;
            if (zoomDelta < 0.001f) return; // Слишком маленькое изменение - игнорируем
        }

        // Двигаем камеру прямо по направлению взгляда
        position = position.add(forward.mul(zoomDelta));

        // Обновляем расстояние до цели (хотя оно должно оставаться прежним)
        // На всякий случай корректируем целевую точку, сохраняя направление
//        float distanceAfterZoom = target.sub(position).getLength();
//        if (Math.abs(distanceAfterZoom - currentDistance) > 0.001f) {
//            target = position.add(forward.mul(currentDistance));
//        }
    }





    public void rotateAroundTarget(float deltaX, float deltaY) {
        Vector3f offset = position.sub(target);
        float distance = offset.getLength();

        if (distance < 0.0001f) return;

        Vector3f forward = target.sub(position).normalize();
        Vector3f currentRight = forward.cross(up).normalize();

        if (currentRight.getLength() < 0.01f) {
            Vector3f worldUp = new Vector3f(0, 1, 0);
            currentRight = forward.cross(worldUp).normalize();

            if (currentRight.getLength() < 0.01f) {
                Vector3f alternativeUp = new Vector3f(0, 0, 1);
                currentRight = forward.cross(alternativeUp).normalize();
            }

            up = currentRight.cross(forward).normalize();
        }

        Quaternion rotation = Quaternion.identity();
        float sensitivity = 0.005f;

        if (Math.abs(deltaX) > 0.0001f) {
            Quaternion yawRot = Quaternion.fromAxisAngle(up, -deltaX * sensitivity);
            rotation = yawRot.multiply(rotation);
        }

        if (Math.abs(deltaY) > 0.0001f) {
            Quaternion pitchRot = Quaternion.fromAxisAngle(currentRight, -deltaY * sensitivity);
            rotation = pitchRot.multiply(rotation);
        }

        Vector3f rotatedOffset = rotation.rotateVector(offset);
        rotatedOffset.normalize().mulInPlace(distance);

        position = target.add(rotatedOffset);

        forward = target.sub(position).normalize();
        currentRight = forward.cross(up).normalize();

        if (currentRight.getLength() < 0.01f) {
            Vector3f worldUp = new Vector3f(0, 1, 0);
            currentRight = forward.cross(worldUp).normalize();
        }

        Vector3f newUp = currentRight.cross(forward).normalize();
        up = newUp;
    }




}