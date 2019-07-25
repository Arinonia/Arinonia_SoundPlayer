package fr.arinonia.customfx.alert;

import fr.arinonia.utils.Utils;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * @Author Arinonia
 * @version 0.0.1
 */

public class CustomAlert{
	
	/**
	 * @param text: first text
	 * @param erreur: twice text
	 * @param event: action if user click on "OK"
	 */
	public CustomAlert(String text, String erreur, AlertActionListener event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Vérification");
		alert.setHeaderText(text);
		alert.setContentText(erreur);
		ButtonType yesButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, cancelButton);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(Utils.loadImage("icon.png"));
		alert.showAndWait().ifPresent(rs -> {
			if (rs == yesButton) {
				event.run();
			}
		});
	}
	
}