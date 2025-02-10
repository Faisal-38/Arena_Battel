package ArenaSim_DataStructure.ArenaSim;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.util.List;

public class Leaderboard {

    private Stage primaryStage;
    private Scene mainMenuScene;

    public Leaderboard(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
    }


    public Scene createLeaderboardScene(List<Player> players, List<Player> enemies) {
        BorderPane root = new BorderPane();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
    
        // Leaderboard title and content
        Text title = new Text("Game Over - Leaderboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        layout.getChildren().add(title);
        displayTeamInfo(layout, "Blue Team:", players);
        displayTeamInfo(layout, "Red Team:", enemies);
    
        // Wrapping the VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane); // Set the ScrollPane in the center of the BorderPane
    
        // Back to Main Menu Button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> {
            Sample sample = new Sample();
            sample.resetGameConditions(); // Reset game conditions before switching scenes
            primaryStage.setScene(mainMenuScene); // Assumes mainMenuScene is already initialized
            System.out.println("Back to Main Menu button clicked");
        });

        backButton.setOnAction(e -> {
            primaryStage.setScene(mainMenuScene);
            System.out.println("Scene set to main menu. Current scene: " + primaryStage.getScene());
            e.consume();
        });

        
    
        // HBox for button alignment
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER); // Center align the button
        buttonContainer.getChildren().add(backButton);
        buttonContainer.setPadding(new Insets(10)); // Padding for the button
        root.setBottom(buttonContainer); // Place the HBox at the bottom of the BorderPane
    
        return new Scene(root, 600, 600); // Adjusted scene size
    }
        


    private void displayTeamInfo(VBox layout, String teamTitle, List<Player> team) {
        layout.getChildren().add(new Text(teamTitle));
        for (Player member : team) {
            // Check if the player is dead
            String healthStatus = member.die(member.getHealth()) ? "dead" : String.valueOf(member.getHealth());
    
            String status = member.getName() + "   current health: " + healthStatus +
                            ", damage: " + member.getDamage() + 
                            ", attack speed: " + member.getAtk_speed() + 
                            ", movement speed: " + member.getMovmentSpeed();
            layout.getChildren().add(new Text(status));
        }
    }
}
