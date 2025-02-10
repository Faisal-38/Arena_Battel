package ArenaSim_DataStructure.ArenaSim;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class Arena {
    private Stage primaryStage;
    private LocalTime startTime;
    private ImageView arenaView;
    private Scene mainMenuScene;
    private Button pauseButton; // Member variable for the pause button
    public static TextArea eventLog; // Make it a public static variable

    public Arena() {
        Image bgImage = new Image(getClass().getResource("map1.png").toExternalForm(), 1280, 720, false, true);
        arenaView = new ImageView(bgImage);
    }

    public Arena(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
        Image bgImage = new Image(getClass().getResource("map1.png").toExternalForm(), 1280, 720, false, true);
        arenaView = new ImageView(bgImage);
    }

    public Arena(Scene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
    }

    public ImageView getArenaView() {
        return arenaView;
    }

    public Pane setupArena(List<Player> players, List<Player> enemies) {
        BorderPane root = new BorderPane();
        root.getChildren().add(getArenaView()); // Add the background image

        // Pause button setup
        pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> showPauseMenu());
        root.setTop(pauseButton);

        // Timer and event log container
        VBox rightContainer = new VBox(0); // Spacing between elements
        rightContainer.setPadding(new Insets(0));

        // Timer Label
        Label timeLabel = new Label("Time: 00:00");
        timeLabel.setStyle("-fx-text-fill: red;"); // Set text color
        rightContainer.getChildren().add(timeLabel);

        // Event Log TextArea
        eventLog = new TextArea();
        eventLog.setEditable(false);
        eventLog.setPrefSize(150, 200); // Halfway into the screen, adjust as needed
        eventLog.setStyle("-fx-background-color: white; -fx-opacity: 1; -fx-border-color: black; -fx-border-width: 2;");
        rightContainer.getChildren().add(eventLog);

        // Add VBox to the right region of the BorderPane
        root.setRight(rightContainer);

        // Start the timer
        startTime = LocalTime.now();
        updateTimer(timeLabel);

        return root;
    }

    private void updateTimer(Label timeLabel) {
        Thread timerThread = new Thread(() -> {
            NumberFormat formatter = new DecimalFormat("00");

            while (true) {
                Duration duration = Duration.between(startTime, LocalTime.now());
                long minutes = duration.toMinutes();
                long seconds = duration.minusMinutes(minutes).getSeconds();
                String timeString = "Time: " + formatter.format(minutes) + ":" + formatter.format(seconds);

                Platform.runLater(() -> timeLabel.setText(timeString));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    private void showPauseMenu() {

        if (primaryStage == null) {
            System.err.println("Primary stage is not set in Arena class.");
            return; // or handle appropriately
        }

        ContextMenu popupMenu = new ContextMenu();
        MenuItem resumeItem = new MenuItem("Resume");
        MenuItem exitToMainMenu = new MenuItem("Exit to Main Menu");
        MenuItem exitProgramItem = new MenuItem("Exit Program");

        resumeItem.setOnAction(event -> popupMenu.hide());
        exitToMainMenu.setOnAction(event -> primaryStage.setScene(mainMenuScene));
        exitProgramItem.setOnAction(event -> Platform.exit());

        popupMenu.getItems().addAll(resumeItem, exitToMainMenu, exitProgramItem);

        double x = primaryStage.getX() + pauseButton.getLayoutX();
        double y = primaryStage.getY() + pauseButton.getLayoutY() + pauseButton.getHeight();

        popupMenu.show(primaryStage, x, y);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static class PopupMenu extends ContextMenu {
        @Override
        public void hide() {
            super.hide();
        }
    }
}
