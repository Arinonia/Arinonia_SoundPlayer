package fr.arinonia;

import java.io.File;
import java.util.Locale.FilteringMode;
import java.util.concurrent.TimeUnit;

import fr.arinonia.customfx.alert.AlertActionListener;
import fr.arinonia.customfx.alert.CustomAlert;
import fr.arinonia.file.FileManager;
import fr.arinonia.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
/**
 * @Author Arinonia
 * @version 0.0.1
 */
public class SPanel extends Parent{

	private Rectangle topBar = new Rectangle();
	private Button close = new Button("X");
	private Button resize = new Button("᏿");
	private ImageView icon = new ImageView(Utils.loadImage("icon.png"));
	private Label label = new Label("Arinonia sound");
	private TreeItem<String> root = createNodeString("music");
	private TreeView<String> treeView = new TreeView<String>(root);
	private ProgressBar bar = new ProgressBar();
	private Label title = new Label("");

	public SPanel() {
		label.setVisible(false);
		topBar.setX(0);
		topBar.setY(0);
		topBar.setWidth(Main.getInstance().getStage().getWidth());
		topBar.setHeight(Main.getInstance().getStage().getHeight() / 30);
		topBar.setFill(Color.rgb(44, 47, 51));
		close.setLayoutX(Main.getInstance().getStage().getWidth()-40);
		close.setLayoutY(0);
		close.setPrefSize(40, Main.getInstance().getStage().getHeight() / 30);
		close.setStyle("-fx-background-color: rgb(44,47,51); -fx-text-fill: rgb(255,255,255); -fx-font-size: 12; -fx-border: 0px");
		close.setOnAction((e)->{
			new CustomAlert("Êtes vous sûr de vouloir fermer", "Si il y a un téléchargement ou une musique en cours il ou elle sera interrompu", new AlertActionListener() {
				
				@Override
				public void run() {
					Main.getInstance().getStage().close();
				}
				
				
			});
			//System.exit(0);
		});
		close.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				close.setStyle("-fx-background-color: rgb(202,28,28); -fx-text-fill: rgb(255,255,255); -fx-font-size: 12; -fx-border: 0px");
			}; 
		});
		close.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				close.setStyle("-fx-background-color: rgb(44,47,51); -fx-text-fill: rgb(255,255,255); -fx-font-size: 12; -fx-border: 0px");
			}; 
		});
		resize.setLayoutX(Main.getInstance().getStage().getWidth()-80);
		resize.setLayoutY(0);
		resize.setPrefSize(40, Main.getInstance().getStage().getHeight() / 30);
		resize.setStyle("-fx-background-color: rgb(44,47,51); -fx-text-fill: rgb(255,255,255); -fx-font-size: 12; -fx-border: 0px");
		resize.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				resize.setStyle("-fx-background-color: rgb(50,53,61); -fx-text-fill: rgb(255,255,255); -fx-font-size: 12; -fx-border: 0px");
			}; 
		});
		resize.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				resize.setStyle("-fx-background-color: rgb(44,47,51); -fx-text-fill: rgb(255,255,255); -fx-font-size: 12; -fx-border: 0px");
			}; 
		});
		resize.setOnAction((e)->{
			if(Main.getInstance().isFullScreen()) {
				Main.getInstance().setFullScreen(false);
				Rectangle2D rectangle2d = Screen.getPrimary().getVisualBounds();
		    	
		    	Main.getInstance().getStage().setWidth(rectangle2d.getWidth()/2);
		    	Main.getInstance().getStage().setHeight(rectangle2d.getHeight()/2);
		    	Main.getInstance().getStage().setX(rectangle2d.getWidth() - Main.getInstance().getStage().getWidth() *1.5 );
		    	Main.getInstance().getStage().setY(rectangle2d.getHeight()- Main.getInstance().getStage().getHeight() *1.5);
		    	close.setLayoutX(Main.getInstance().getStage().getWidth()-40);
				resize.setLayoutX(Main.getInstance().getStage().getWidth()-80);
				treeView.setPrefHeight(Main.getInstance().getStage().getHeight()-treeView.getLayoutY()-60);
				bar.setLayoutY(Main.getInstance().getStage().getHeight() - 78);
				bar.setPrefWidth(Main.getInstance().getStage().getWidth()-treeView.getPrefWidth()-20);
				title.setLayoutX(treeView.getPrefWidth() + bar.getPrefWidth()/8);
				title.setLayoutY(Main.getInstance().getStage().getHeight() -128 - title.getHeight()/2);
				title.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

			}else {
				Main.getInstance().setFullScreen(true);
		    	Rectangle2D rectangle2d = Screen.getPrimary().getVisualBounds();
		    	Main.getInstance().getStage().setX(rectangle2d.getMinX());
		    	Main.getInstance().getStage().setY(rectangle2d.getMinY());
		    	Main.getInstance().getStage().setWidth(rectangle2d.getWidth());
		    	Main.getInstance().getStage().setHeight(rectangle2d.getHeight());
		    	close.setLayoutX(Main.getInstance().getStage().getWidth()-40);
				resize.setLayoutX(Main.getInstance().getStage().getWidth()-80);
				treeView.setPrefHeight(Main.getInstance().getStage().getHeight()-treeView.getLayoutY()-60);
				bar.setLayoutX(treeView.getPrefWidth()+10);
				bar.setLayoutY(Main.getInstance().getStage().getHeight() - 78);
				bar.setPrefWidth(Main.getInstance().getStage().getWidth()-treeView.getPrefWidth()-20);
				title.setLayoutX(bar.getLayoutX()+bar.getPrefWidth()/2 - 370);
				title.setLayoutY(Main.getInstance().getStage().getHeight() -128 - title.getHeight()/2);
				title.setStyle("-fx-text-fill: white; -fx-font-size: 22;");

				
			}
		});
		icon.setFitHeight(Main.getInstance().getStage().getHeight() / 30);
		icon.setFitWidth(Main.getInstance().getStage().getHeight() / 30);
		icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				label.setLayoutX(event.getX()+12);
				label.setLayoutY(event.getY());
				label.setTextFill(Color.WHITE);
				label.setVisible(true);
				
			}
		});
		icon.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				label.setVisible(false);
			}
		});
		icon.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				label.setLayoutX(event.getX()+12);
				label.setLayoutY(event.getY());				
			}
		});
		
		treeView.setOnMouseClicked((e)->{
			MultipleSelectionModel msm = treeView.getSelectionModel();
			TreeItem<String> item = (TreeItem<String>) msm.getSelectedItem();
			if (item != null) {
				if(item.getValue() != "music") {
					if(FileManager.isPlaying()) {  FileManager.stop(); }
					if(!FileManager.isPlaying())
					//if (item != null) FileManager.playMusic(FileManager.MUSIC_DIR.getAbsolutePath() + File.separator + item.getValue());
					if (item != null)FileManager.test(bar, title,treeView.getSelectionModel().getSelectedIndex()-1, treeView);
					bar.setVisible(true);
					
					

				}

			}
			
		});
		
		treeView.setLayoutY(100);
		treeView.setPrefHeight(Main.getInstance().getStage().getHeight()-treeView.getLayoutY()-60);
		treeView.setStyle("-fx-background-color: rgb(44,47,51); -fx-control-inner-background: rgb(44,47,51);");
		treeView.setPrefWidth(300);
		bar.setLayoutX(treeView.getPrefWidth()+10);
		System.out.println(treeView.getPrefWidth());
		bar.setLayoutY(Main.getInstance().getStage().getHeight() - 78);
		bar.setPrefWidth(Main.getInstance().getStage().getWidth()-treeView.getPrefWidth()-20);
		bar.setVisible(false);
		bar.setPrefHeight(10);
		bar.setStyle("-fx-text-box-border: rgba(31,33,37, 1); -fx-control-inner-background: rgba(31,33,37, 1); -fx-accent: rgb(32,234,53); -fx-background-color: rgba(31,33,37,1); -f-box-border: rgba(255,255,255,0.0);");
		title.setLayoutX(bar.getLayoutX()+bar.getPrefWidth()/2 - 370);
		title.setLayoutY(Main.getInstance().getStage().getHeight() -128 - title.getHeight()/2);
		title.setStyle("-fx-text-fill: white; -fx-font-size: 22;");
		getChildren().add(topBar);
		getChildren().add(close);
		getChildren().add(resize);
		getChildren().add(icon);
		getChildren().add(label);
		getChildren().add(treeView);
		getChildren().add(bar);
		getChildren().add(title);

	}
	
	

	
	
	public static TreeItem<String> createNodeString(final String f) {
		return new TreeItem<String>(f) {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override
			public ObservableList<TreeItem<String>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			@Override
			public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					File ff = new File(FileManager.MUSIC_DIR.getAbsolutePath());
					isLeaf = ff.isFile();
				}
				return isLeaf;
			}

			private ObservableList<TreeItem<String>> buildChildren(TreeItem<String> TreeItem) {
				File ff = new File(FileManager.DIR.getAbsolutePath() + File.separator + TreeItem.getValue());
				//System.out.println(FileManager.MUSIC_DIR.getAbsolutePath() + File.separator + TreeItem.getValue());
				if (ff.getAbsolutePath().endsWith(".mp3")) { return FXCollections.emptyObservableList(); }
				if (ff.isFile()) { return FXCollections.emptyObservableList(); }
				//if(ff.isDirectory())System.out.println("ptdr");
				File[] files = ff.listFiles();
				if (files != null) {
					ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
					for (File childFile : files) {
						children.add(createNodeString(childFile.getAbsolutePath().replace(FileManager.MUSIC_DIR.getAbsolutePath() + File.separator,"")));
					}
					return children;
				}
				return FXCollections.emptyObservableList();
			}
		};
	}
	
}
