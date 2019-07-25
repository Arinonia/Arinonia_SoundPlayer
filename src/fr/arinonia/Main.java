package fr.arinonia;

import java.net.URI;
import java.net.URISyntaxException;

import fr.arinonia.file.FileManager;
import fr.arinonia.utils.Utils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Author Arinonia
 * @version 0.0.1
 */

public class Main extends Application{
	
	private double xOffset = 0; 
	private double yOffset = 0;
	private Stage stage;
	private static Main instance;
	private boolean isFullScreen;
	
	
	public static void main(String... args) throws URISyntaxException {
		FileManager.init();
		launch(args);
	}

	
	@Override
	public void start(Stage stage) throws Exception {
        this.stage = stage;
		instance = this;
		isFullScreen = true;
		stage.setTitle("Arinonia SoundPlayer");
		stage.initStyle(StageStyle.UNDECORATED);
		stage.getIcons().add(Utils.loadImage("icon.png"));
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: rgb(35,39,42);");
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root, 1080, 720, Color.DARKGRAY);
        stage.setScene(scene);
        
    	Rectangle2D rectangle2d = Screen.getPrimary().getVisualBounds();
    	stage.setX(rectangle2d.getMinX());
        stage.setY(rectangle2d.getMinY());
        stage.setWidth(rectangle2d.getWidth());
        stage.setHeight(rectangle2d.getHeight());
        stage.show();
        SPanel test = new SPanel();
		root.getChildren().add(test);
	}
	/**
	 * 
	 * @return stage
	 */
	public Stage getStage() {
		return stage;
	}
	/**
	 * 
	 * @return instance
	 */
	public static Main getInstance() {
		return instance;
	}
	/**
	 * 
	 * @return isFullScreen
	 */
	public boolean isFullScreen() {
		return isFullScreen;
	}
	/**
	 * 
	 * @param isFullScreen
	 */
	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}
		
}