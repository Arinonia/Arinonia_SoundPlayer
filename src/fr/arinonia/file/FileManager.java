package fr.arinonia.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

/**
 * @Author Arinonia
 * @version 0.0.1
 */

public class FileManager {

	public static final File DIR = new File(System.getProperty("user.home") + File.separator + ".Arinonia_musique");
	public static final File MUSIC_DIR = new File(DIR, "music");
	private static boolean isPlaying = false;
	private static ChangeListener<Duration> progressChangeListener;
	static Label currentlyPlaying = new Label();
	static ProgressBar progress = new ProgressBar();
	private static double currentTime;
	private static double totalTime;
	private static String title;
	private static MediaPlayer mediaView;
	public static void init() {
		if (!DIR.exists()) {
			DIR.mkdir();
		}
		if (DIR.exists()) {
			if (!MUSIC_DIR.exists()) {
				MUSIC_DIR.mkdir();
			}
		}
		checkFile();

	}

	public static void checkFile() {

		File[] file = new File(MUSIC_DIR.getAbsolutePath()).listFiles();
		int number = 0;
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				number++;
				System.out.println("fichiers: " + number + "\nIl se nomme: "
						+ file[number - 1].getAbsolutePath().replace(MUSIC_DIR.getAbsolutePath() + File.separator, ""));
			}
		}
	}

	public static void test(ProgressBar bar, Label label, int index, TreeView<String> treeView) {
		isPlaying = true;
		progress = bar;
		currentlyPlaying = label;
		final File dir = FileManager.MUSIC_DIR;
		final List<MediaPlayer> players = new ArrayList<MediaPlayer>();
		for (String file : dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".mp3");

			}
		}))

			players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
		if (players.isEmpty()) {
			System.out.println("No audio found in " + dir);
			return;
		}
		final MediaView mediaView = new MediaView(players.get(index));
		for (int i = index; i < players.size(); i++) {
			final MediaPlayer player = players.get(i);
			final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
			player.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					player.currentTimeProperty().removeListener(progressChangeListener);
					mediaView.setMediaPlayer(nextPlayer);
					nextPlayer.play();
					treeView.getSelectionModel().select(treeView.getSelectionModel().getSelectedIndex()+1);
				}
			});
		}
		mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
			@Override
			public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer,
					MediaPlayer newPlayer) {
				setCurrentlyPlaying(newPlayer);
			}
		});

		mediaView.setMediaPlayer(players.get(index));
		mediaView.getMediaPlayer().play();
		setCurrentlyPlaying(mediaView.getMediaPlayer());
	}

	private static void setCurrentlyPlaying(final MediaPlayer newPlayer) {
		progress.setProgress(0);
		progressChangeListener = new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue,
					Duration newValue) {
				progress.setProgress(
						1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
				currentTime = newPlayer.getCurrentTime().toMillis();
				totalTime = newPlayer.getTotalDuration().toMillis();
				currentlyPlaying
						.setText(title 
								 + " "+
								 String.format("%d min, %d sec",
												TimeUnit.MILLISECONDS.toMinutes((long) FileManager.getCurrentTime()),
												TimeUnit.MILLISECONDS.toSeconds((long) FileManager.getCurrentTime())
														- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
																.toMinutes((long) FileManager.getCurrentTime()))) + " sur "+
																String.format("%d min, %d sec",
																		TimeUnit.MILLISECONDS.toMinutes((long) FileManager.getTotalTime()),
																		TimeUnit.MILLISECONDS.toSeconds((long) FileManager.getTotalTime())
																				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
																						.toMinutes((long) FileManager.getTotalTime())))
										
								);
			}
		};
		newPlayer.currentTimeProperty().addListener(progressChangeListener);

		String source = newPlayer.getMedia().getSource();
		source = source.substring(0, source.length() - ".mp3".length());
		source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
		currentlyPlaying.setText("Now Playing: " + source);
		title = "Now Playing: " + source;
	}

	private static MediaPlayer createPlayer(String aMediaSrc) {
		final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
		player.setOnError(new Runnable() {
			@Override
			public void run() {
				System.out.println("Media error occurred: " + player.getError());
			}
		});
		return player;
	}

	public static boolean isPlaying() {
		return isPlaying;
	}

	public static Label getCurrentlyPlaying() {
		return currentlyPlaying;
	}

	public static double getCurrentTime() {
		return currentTime;
	}

	public static double getTotalTime() {
		return totalTime;
	}
	
	public static void stop() {
		
	}

}
