package com.cgvsu.render_engine;


import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Quaternion;
import com.cgvsu.math.Vector3f;

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
        // Нормализованные координаты курсора от -1 до 1
        float normalizedX = (2.0f * mouseX) / screenWidth - 1.0f;
        float normalizedY = 1.0f - (2.0f * mouseY) / screenHeight; // Инвертируем Y

        // Направление взгляда камеры
        Vector3f forward = target.sub(position).normalize();
        Vector3f right = forward.cross(up).normalize(); // Используем up камеры, а не мировой
        Vector3f cameraUp = right.cross(forward).normalize();

        // Точка, на которую смотрит курсор в плоскости фокуса
        float focusDistance = target.sub(position).getLength();
        Vector3f cursorWorldPoint = target
                .add(right.mul(normalizedX * focusDistance)) // Горизонтальное смещение
                .add(cameraUp.mul(normalizedY * focusDistance)); // Вертикальное смещение

        // Направление от камеры к точке под курсором
        Vector3f toCursor = cursorWorldPoint.sub(position).normalize();

        float currentDistance = target.sub(position).getLength();
        float minDistance = 1.0f;

        if (currentDistance - zoomDelta < minDistance) {
            zoomDelta = currentDistance - minDistance;
            if (zoomDelta < 0.001f) return;
        }

        // Двигаем камеру к точке под курсором
        position = position.add(toCursor.mul(zoomDelta));

        // Target также двигаем к той же точке под курсором
        // чтобы сохранить фокус на том же объекте
        target = target.add(toCursor.mul(zoomDelta));
    }



    public void rotateAroundTarget(float deltaX, float deltaY) {
        Vector3f pivot = new Vector3f(0, 0, 0);

        // 1. Вычисляем текущие экранные координаты pivot (origin) в системе камеры
        Vector3f toPivot = pivot.sub(position).normalize();

        // Разлагаем toPivot на компоненты в системе камеры
        Vector3f forward = target.sub(position).normalize();
        Vector3f right = forward.cross(up).normalize();
        Vector3f cameraUp = right.cross(forward).normalize();

        // Проекции toPivot на оси камеры
        float pivotRight = toPivot.dot(right);
        float pivotUp = toPivot.dot(cameraUp);
        float pivotForward = toPivot.dot(forward);

        // 2. Применяем вращение камеры вокруг своей позиции
        float sensitivity = 0.005f;
        Quaternion rotation = Quaternion.identity();

        if (Math.abs(deltaX) > 0.0001f) {
            rotation = Quaternion.fromAxisAngle(up, -deltaX * sensitivity).multiply(rotation);
        }
        if (Math.abs(deltaY) > 0.0001f) {
            rotation = Quaternion.fromAxisAngle(right, -deltaY * sensitivity).multiply(rotation);
        }

        // Поворачиваем оси камеры
        Vector3f newForward = rotation.rotateVector(forward).normalize();
        Vector3f newUp = rotation.rotateVector(up).normalize();
        Vector3f newRight = newForward.cross(newUp).normalize();

        // 3. Пересчитываем up, чтобы система была ортогональной
        newUp = newRight.cross(newForward).normalize();

        // 4. Вычисляем новую позицию камеры
        Vector3f desiredToPivotDir =
                newRight.mul(pivotRight)
                        .add(newUp.mul(pivotUp))
                        .add(newForward.mul(pivotForward))
                        .normalize();

        // Расстояние до origin (0,0,0)
        float distanceToOrigin = position.getLength(); // position - (0,0,0) = position

        // Новая позиция камеры: из origin отступаем на расстояние в обратном направлении
        position = desiredToPivotDir.mul(-distanceToOrigin); // pivot.sub(dir * distance) = -dir * distance

        // Обновляем target: смотрим в том же направлении
        target = position.add(newForward.mul(distanceToOrigin));

        // Обновляем up
        up = newUp;
    }





}