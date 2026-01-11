package com.cgvsu.render_engine;

import java.util.ArrayList;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Point2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.math.Vector4f;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;
import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height, Placement placement)
    {

        Matrix4f modelMatrix = GraphicConveyor.getModelMatrix(placement);
        Matrix4f viewMatrix = GraphicConveyor.getViewMatrix(camera);
        Matrix4f projectionMatrix = GraphicConveyor.getProjectionMatrix(camera);


        Matrix4f mvp = projectionMatrix.mul(viewMatrix).mul(modelMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector4f vec4 = new Vector3f(vertex.getVector()).to4f();
                Vector3f afterAll = mvp.mul(vec4).ndc();
                if (afterAll.isBetweenNAndF()) {
                    Point2f resultPoint = vertexToPoint(afterAll, width, height);
                    resultPoints.add(resultPoint);
                }
            }
            drawPolygon(graphicsContext, resultPoints);

        }
    }

    public static void drawPolygon(GraphicsContext graphicsContext, ArrayList<Point2f> resultPoints) {
        int nVerticesInPolygon = resultPoints.size();
        for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
            graphicsContext.strokeLine(
                    resultPoints.get(vertexInPolygonInd - 1).x,
                    resultPoints.get(vertexInPolygonInd - 1).y,
                    resultPoints.get(vertexInPolygonInd).x,
                    resultPoints.get(vertexInPolygonInd).y);
        }

        if (nVerticesInPolygon > 0)
            graphicsContext.strokeLine(
                    resultPoints.get(nVerticesInPolygon - 1).x,
                    resultPoints.get(nVerticesInPolygon - 1).y,
                    resultPoints.get(0).x,
                    resultPoints.get(0).y);
    }

    public static void renderAxes(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final int width,
            final int height) {

        Matrix4f viewMatrix = GraphicConveyor.getViewMatrix(camera);
        Matrix4f projectionMatrix = GraphicConveyor.getProjectionMatrix(camera);
        Matrix4f mvp = projectionMatrix.mul(viewMatrix);

        float axisLength = 20.0f;

        // Точки для осей
        Vector3f[] axisPoints = {
                new Vector3f(0, 0, 0),        // начало
                new Vector3f(axisLength, 0, 0),  // X
                new Vector3f(0, axisLength, 0),  // Y
                new Vector3f(0, 0, axisLength)   // Z
        };

        // Преобразуем все точки в экранные координаты
        Point2f[] screenPoints = new Point2f[4];
        for (int i = 0; i < 4; i++) {
            screenPoints[i] = worldToScreen(axisPoints[i], mvp, width, height);
        }

        // Ось X - красная
        graphicsContext.setStroke(javafx.scene.paint.Color.RED);
        graphicsContext.strokeLine(screenPoints[0].x, screenPoints[0].y,
                screenPoints[1].x, screenPoints[1].y);
        graphicsContext.fillText("X", screenPoints[1].x + 5, screenPoints[1].y + 5);

        // Ось Y - зеленая
        graphicsContext.setStroke(javafx.scene.paint.Color.GREEN);
        graphicsContext.strokeLine(screenPoints[0].x, screenPoints[0].y,
                screenPoints[2].x, screenPoints[2].y);
        graphicsContext.fillText("Y", screenPoints[2].x + 5, screenPoints[2].y + 5);

        // Ось Z - синяя
        graphicsContext.setStroke(javafx.scene.paint.Color.BLUE);
        graphicsContext.strokeLine(screenPoints[0].x, screenPoints[0].y,
                screenPoints[3].x, screenPoints[3].y);
        graphicsContext.fillText("Z", screenPoints[3].x + 5, screenPoints[3].y + 5);

        // Возвращаем цвет по умолчанию
        graphicsContext.setStroke(javafx.scene.paint.Color.BLACK);
    }

    private static Point2f worldToScreen(Vector3f worldPoint, Matrix4f mvp, int width, int height) {
        // Преобразуем точку с w=1
        Vector4f vec4 = new Vector4f(worldPoint.getX(), worldPoint.getY(), worldPoint.getZ(), 1.0f);
        Vector4f transformed = mvp.mul(vec4);

        // Делим на w для получения NDC
        float w = transformed.getW();
        if (Math.abs(w) < 0.0001f) {
            w = 0.0001f; // Избегаем деления на 0
        }

        float x = transformed.getX() / w;
        float y = transformed.getY() / w;
        float z = transformed.getZ() / w;

        // Преобразуем NDC в экранные координаты
        float screenX = x * width + width / 2.0f;
        float screenY = -y * height + height / 2.0f;

        return new Point2f(screenX, screenY);
    }



}