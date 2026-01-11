package com.cgvsu;

import com.cgvsu.math.Matrix4f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.GraphicConveyor;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cgvsu.render_engine.RenderEngine.renderAxes;

public class GuiController {
    private static final Path HISTORY_FILE = Paths.get("resources/history.txt");
    private double dragStartX, dragStartY;
    private boolean isDragging = false;
    private boolean isRightDragging = false;
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 1000);

    private Model mesh = null;
    private Timeline timeline;

    @FXML
    private void initialize() {

        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            RenderEngine.renderAxes(canvas.getGraphicsContext2D(), camera, (int) width, (int) height);
            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }

        });

        attachToScene(canvas);

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public void attachToScene(Canvas canvas) {

        canvas.setOnScroll(event -> {
            float zoomDelta = (float) event.getDeltaY() * 0.3f;
            float mouseX = (float) event.getSceneX();
            float mouseY = (float) event.getSceneY();
            float width = (float) canvas.getWidth();
            float height = (float) canvas.getHeight();

            camera.zoomToCursor(zoomDelta);
        });

        canvas.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.MIDDLE) {
                dragStartX = e.getSceneX();
                dragStartY = e.getSceneY();
                isDragging = true;
                e.consume();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                dragStartX = e.getSceneX();
                dragStartY = e.getSceneY();
                isRightDragging = true;
                e.consume();
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (isDragging) {
                double deltaX = e.getSceneX() - dragStartX;
                double deltaY = e.getSceneY() - dragStartY;
                dragStartX = e.getSceneX();
                dragStartY = e.getSceneY();
                onWheelDragged(deltaX, deltaY, e);
            } else if (isRightDragging) {
                double deltaX = e.getSceneX() - dragStartX;
                double deltaY = e.getSceneY() - dragStartY;
                dragStartX = e.getSceneX();
                dragStartY = e.getSceneY();
                camera.rotateAroundTarget((float)deltaX, (float)deltaY);
            }
        });

        canvas.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.MIDDLE && isDragging) {
                isDragging = false;
                onWheelDragEnded(e);
                e.consume();
            } else if (e.getButton() == MouseButton.SECONDARY && isRightDragging) {
                isRightDragging = false;
                e.consume();
            }
        });
    }

    protected void onWheelDragged(double deltaX, double deltaY, MouseEvent event) {
        Matrix4f mat = GraphicConveyor.getModelMatrix(1,1,1, (float) deltaX, 50, 0, 0, 0, 0);
    }

    protected void onWheelDragEnded(MouseEvent event) {
        System.out.println("Drag ended");
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());
        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {

    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {

    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {

    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {

    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {

    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {

    }


}
