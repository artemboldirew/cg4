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
            final int height)
    {
        Matrix4f modelMatrix = GraphicConveyor.getModelMatrix(1,1,1, 0, 0, 0, 0, 0, 0);
        Matrix4f viewMatrix = GraphicConveyor.getViewMatrix(camera);
        Matrix4f projectionMatrix = GraphicConveyor.getProjectionMatrix(camera);


        Matrix4f mvp = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector4f vec4 = new Vector3f(vertex.getVector()).to4f();
                Vector4f afterAll = mvp.multiply(vec4);

                Point2f resultPoint = vertexToPoint(afterAll.ndc(), width, height);
                resultPoints.add(resultPoint);
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
}