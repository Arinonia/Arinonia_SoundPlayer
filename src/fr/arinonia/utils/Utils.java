package fr.arinonia.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.arinonia.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * @Author Arinonia
 * @version 0.0.1
 */

public class Utils {

	/**
	 * 
	 * @param name of your image **with extention**
	 * @return your image
	 * @throws IOException
	 */
	public static Image loadImage(String image) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(Main.class.getResource("/fr/arinonia/res/" + image));
		} catch (IOException localIOException) {}
		Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
		return fxImage;
	}

}
