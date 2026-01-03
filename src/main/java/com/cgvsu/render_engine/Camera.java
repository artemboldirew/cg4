package com.cgvsu.render_engine;


import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Quaternionf;
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

    public void zoomToCursor(float zoomDelta, float mouseX, float mouseY,
                             float screenWidth, float screenHeight) {
        // Создаем вектор из центра экрана к курсору
        float centerX = screenWidth / 2.0f;
        float centerY = screenHeight / 2.0f;

        // Относительное смещение курсора от центра (в долях от половины экрана)
        float offsetX = (mouseX - centerX) / centerX;
        float offsetY = (centerY - mouseY) / centerY; // Y инвертируем

        // Направление взгляда камеры
        Vector3f forward = target.sub(position).normalize();
        Vector3f right = forward.cross(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = right.cross(forward).normalize();

        // Коэффициент влияния смещения курсора (можно регулировать)
        float sensitivity = 0.5f;

        // Вычисляем смещение для зума
        Vector3f offsetVector = right.mul(offsetX * sensitivity)
                .add(up.mul(offsetY * sensitivity));

        // Луч для зума = направление взгляда + смещение от курсора
        Vector3f zoomRay = forward.add(offsetVector).normalize();
        float currentDistance = target.sub(position).getLength();
        float minDistance = 1.0f; // Никогда не ближе этого

        if (currentDistance - zoomDelta < minDistance) {
            zoomDelta = currentDistance - minDistance;
            if (zoomDelta < 0.001f) return;
        }

        // Двигаем камеру по этому лучу
        position = position.add(zoomRay.mul(zoomDelta));

        // Target двигаем так, чтобы сохранить расстояние и направление
        float distance = target.sub(position).getLength();
        target = position.add(forward.mul(distance));
    }





    public void rotateAroundTarget(float deltaX, float deltaY) {
        target = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f offset = new Vector3f(position).sub(target);
        float distance = offset.getLength();
        if (distance < 0.0001f) return;

        Vector3f forward = new Vector3f(offset).normalize();
        Vector3f right = forward.cross(up).normalize();

        if (right.getLength() < 0.01f) {
            Vector3f alternativeUp;
            if (Math.abs(forward.getY()) < 0.99f) {
                alternativeUp = new Vector3f(0, 1, 0);
            } else {
                alternativeUp = new Vector3f(0, 0, 1);
            }
            right = forward.cross(alternativeUp).normalize();
        }

        Vector3f localUp = right.cross(forward).normalize();

        float sensitivity = 0.005f;
        if (Math.abs(deltaX) > 0.0001f) {
            offset = rotateVectorAroundAxis(offset, localUp, -deltaX * sensitivity);
        }

        if (Math.abs(deltaY) > 0.0001f) {
            offset = rotateVectorAroundAxis(offset, right, -deltaY * sensitivity);

            forward = new Vector3f(offset).normalize();
            right = forward.cross(up).normalize();

            if (right.getLength() < 0.01f) {
                Vector3f alternativeUp;
                if (Math.abs(forward.getY()) < 0.99f) {
                    alternativeUp = new Vector3f(0, 1, 0);
                } else {
                    alternativeUp = new Vector3f(0, 0, 1);
                }
                right = forward.cross(alternativeUp).normalize();
            }

            localUp = right.cross(forward).normalize();
        }

        offset.normalize().mulInPlace(distance);
        position.setVector(new float[]{
                target.getX() + offset.getX(),
                target.getY() + offset.getY(),
                target.getZ() + offset.getZ()
        });

        forward = new Vector3f(target).sub(position).normalize();

        Vector3f newRight = forward.cross(up).normalize();

        if (newRight.getLength() < 0.01f) {
            Vector3f worldUp = new Vector3f(0, 1, 0);
            newRight = forward.cross(worldUp).normalize();

            if (newRight.getLength() < 0.01f) {
                Vector3f alternativeUp = new Vector3f(0, 0, 1);
                newRight = forward.cross(alternativeUp).normalize();
            }
        }

        this.up = newRight.cross(forward).normalize();
    }

    private Vector3f rotateVectorAroundAxis(Vector3f v, Vector3f axis, float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        Vector3f cross = axis.cross(v);
        float dot = axis.dot(v);

        return new Vector3f(v).mulInPlace(cos)
                .addInPlace(cross.mulInPlace(sin))
                .addInPlace(new Vector3f(axis).mulInPlace(dot * (1 - cos)))
                .normalize();
    }




}