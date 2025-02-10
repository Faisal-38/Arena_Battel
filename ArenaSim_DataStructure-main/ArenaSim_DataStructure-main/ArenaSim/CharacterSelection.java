package ArenaSim_DataStructure.ArenaSim;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class CharacterSelection {
    private Stage primaryStage;
    private VBox playerTeamAttributesBox;
    private VBox enemyTeamAttributesBox;
    private Scene mainMenuScene;
    private List<CharacterAttributes> playerTeamCharacters = new ArrayList<>();
    private List<CharacterAttributes> enemyTeamCharacters = new ArrayList<>();
    private static CharacterSelection instance;

    private void startGame() {
        List<CharacterSelection.CharacterAttributes> playerCharacters = getPlayerTeamCharacters();
        List<CharacterSelection.CharacterAttributes> enemyCharacters = getEnemyTeamCharacters();

        // Create new lists for Player objects

        // Create the arena scene with the selected characters and empty player/enemy
        // lists
        Scene arenaScene = Sample.createArenaScene(primaryStage, playerCharacters, enemyCharacters);

        primaryStage.setScene(arenaScene);
    }

    public CharacterSelection() {
        if (instance == null) {
            instance = this;
        }
    }

    public CharacterSelection(Stage primaryStage, Scene mainMenuScene) {
        this.primaryStage = primaryStage;
        this.mainMenuScene = mainMenuScene;
        this.playerTeamAttributesBox = new VBox(10);
        this.enemyTeamAttributesBox = new VBox(10);
    }

    public Scene createCharacterSelectionScene(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setCenter(createCharacterSelectionPane());
        return new Scene(root, 1280, 720);
    }

    private BorderPane createCharacterSelectionPane() {
        BorderPane characterSelectionPane = new BorderPane();
        characterSelectionPane.setPadding(new Insets(20));
        VBox playerTeamBox = createTeamBox("Blue Team", playerTeamAttributesBox);
        VBox enemyTeamBox = createTeamBox("Red Team", enemyTeamAttributesBox);
        characterSelectionPane.setRight(new VBox(playerTeamBox, playerTeamAttributesBox));
        characterSelectionPane.setLeft(new VBox(enemyTeamBox, enemyTeamAttributesBox));
        Button backButton = new Button("Back");

        // Logic to main_menu
        backButton.setOnAction(event -> primaryStage.setScene(mainMenuScene));
        Button startButton = new Button("Start");

        Button defaultSetupButton = new Button("Default Setup");
        defaultSetupButton.setOnAction(event -> setupDefaultTeams());
        // Logic to start the game
        startButton.setOnAction(event -> {

            startGame();
        });
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, startButton, defaultSetupButton);
        characterSelectionPane.setBottom(buttonBox);
        return characterSelectionPane;
    }

    private void setupDefaultTeams() {
        // Temporary lists to hold characters
        List<CharacterAttributes> tempPlayerTeamCharacters = new ArrayList<>();
        List<CharacterAttributes> tempEnemyTeamCharacters = new ArrayList<>();
        int scaler = 5;
        // Setup default characters for both teams
        for (int i = 0; i < 1; i++) {
            // Normal Players
            tempPlayerTeamCharacters
            .add(new CharacterAttributes("Player " + (i + 1), 10, 100, 1, 1, 100 * i, 130, false));
   

            tempEnemyTeamCharacters
            .add(new CharacterAttributes("Enemy " + (i + 1), 20, 200, 1, 1, 100 * i, 530, false));
                   scaler --;
        }
        tempPlayerTeamCharacters
        .add(new CharacterAttributes("Player " + ( 10), 10, 100, 1, 1, 500, 700, false));
        // Special Players
        //tempPlayerTeamCharacters.add(new CharacterAttributes("Special Player", 1, 100, 1.5f, 2, 300, 100, true));
        tempEnemyTeamCharacters.add(new CharacterAttributes("Special Enemy", 1, 70, 1.5f, 4, 700, 700, true));
        // Clear current teams and UI elements
        playerTeamCharacters.clear();
        enemyTeamCharacters.clear();
        playerTeamAttributesBox.getChildren().clear();
        enemyTeamAttributesBox.getChildren().clear();

        // Add temporary list characters to actual teams and update UI
        tempPlayerTeamCharacters.forEach(character -> addCharacterToTeam("Blue Team", character));
        tempEnemyTeamCharacters.forEach(character -> addCharacterToTeam("Red Team", character));
    }

    private VBox createTeamBox(String teamName, VBox teamAttributesBox) {
        HBox teamHeaderBox = new HBox(20);
        teamHeaderBox.setAlignment(Pos.CENTER);
        Label teamLabel = new Label(teamName);
        Button addCharacterButton = new Button("Add Character");
        addCharacterButton.setOnAction(event -> showCharacterInputDialog(teamName));
        teamHeaderBox.getChildren().addAll(teamLabel, addCharacterButton);

        // Wrap the attributes box in a ScrollPane for vertical scrolling
        ScrollPane teamScrollPane = new ScrollPane(teamAttributesBox);
        teamScrollPane.setFitToWidth(true);

        VBox teamBox = new VBox(10, teamHeaderBox, teamScrollPane);
        teamBox.setPadding(new Insets(10));
        return teamBox;
    }

    private void showCharacterInputDialog(String teamName) {
        Dialog<CharacterAttributes> dialog = new Dialog<>();
        dialog.setTitle("Add New Character");
        dialog.setHeaderText("Enter Character Attributes");

        // Set the button types
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create the content of the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField damageField = new TextField();
        TextField healthField = new TextField();
        TextField attackSpeedField = new TextField();
        TextField movementSpeedField = new TextField();

        // Add the new features: x-axis, y-axis, and special player checkbox
        TextField xAxisField = new TextField();
        TextField yAxisField = new TextField();
        CheckBox specialPlayerCheckBox = new CheckBox("Special Player");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Damage:"), 0, 1);
        grid.add(damageField, 1, 1);
        grid.add(new Label("Health:"), 0, 2);
        grid.add(healthField, 1, 2);
        grid.add(new Label("Attack Speed:"), 0, 3);
        grid.add(attackSpeedField, 1, 3);
        grid.add(new Label("Movement Speed:"), 0, 4);
        grid.add(movementSpeedField, 1, 4);

        // Add the new fields to the grid
        grid.add(new Label("X-axis:"), 0, 5);
        grid.add(xAxisField, 1, 5);
        grid.add(new Label("Y-axis:"), 0, 6);
        grid.add(yAxisField, 1, 6);
        grid.add(specialPlayerCheckBox, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Convert the result to a CharacterAttributes object when the "Add" button is
        // clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                try {
                    String name = nameField.getText();
                    float damage = Float.parseFloat(damageField.getText());
                    float health = Float.parseFloat(healthField.getText());
                    float attackSpeed = Float.parseFloat(attackSpeedField.getText());
                    int movementSpeed = Integer.parseInt(movementSpeedField.getText());
                    int xAxis = Integer.parseInt(xAxisField.getText());
                    int yAxis = Integer.parseInt(yAxisField.getText());
                    boolean isSpecialPlayer = specialPlayerCheckBox.isSelected();

                    return new CharacterAttributes(name, damage, health, attackSpeed, movementSpeed, xAxis, yAxis,
                            isSpecialPlayer);
                } catch (NumberFormatException e) {
                    showAlert("Invalid input. Please enter numeric values.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(attributes -> {
            // Process the character attributes and add the character to the team
            addCharacterToTeam(teamName, attributes);
        });
    }

    private void addCharacterToTeam(String teamName, CharacterAttributes attributes) {
        if (attributes != null) {
            // Add logic to process the character attributes and add the character to the
            // team
            // Create a new VBox with labels and lines to display the character attributes
            VBox characterAttributesBox = new VBox(5);
            Line topLine = new Line(0, 0, 200, 0);
            Line bottomLine = new Line(0, 0, 200, 0);

            characterAttributesBox.getChildren().addAll(
                    topLine,
                    new Label("Name: " + attributes.name),
                    new Label("Damage: " + attributes.damage),
                    new Label("Health: " + attributes.health),
                    new Label("Attack Speed: " + attributes.attackSpeed),
                    new Label("Movement Speed: " + attributes.movementSpeed),
                    new Label("X: " + attributes.xAxis + " Y: " + attributes.yAxis),
                    new Label("special player  " + attributes.isSpecialPlayer),
                    bottomLine

            );

            // Add the VBox to the appropriate team's attributes box
            if (teamName.equals("Blue Team")) {
                playerTeamAttributesBox.getChildren().add(characterAttributesBox);
                playerTeamCharacters.add(attributes);
            } else if (teamName.equals("Red Team")) {
                enemyTeamAttributesBox.getChildren().add(characterAttributesBox);
                enemyTeamCharacters.add(attributes);
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static CharacterSelection getInstance() {
        return instance;
    }

    public List<CharacterAttributes> getPlayerTeamCharacters() {
        return playerTeamCharacters;
    }

    public List<CharacterAttributes> getEnemyTeamCharacters() {
        return enemyTeamCharacters;
    }

    public static class CharacterAttributes {
        public String name;
        public float damage;
        public float health;
        public float attackSpeed;
        public int movementSpeed;
        public int xAxis;
        public int yAxis;
        public boolean isSpecialPlayer;

        public CharacterAttributes(String name, float damage, float health, float attackSpeed, int movementSpeed,
                int xAxis, int yAxis, boolean isSpecialPlayer) {
            this.name = name;
            this.damage = damage;
            this.health = health;
            this.attackSpeed = attackSpeed;
            this.movementSpeed = movementSpeed;
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.isSpecialPlayer = isSpecialPlayer;
        }
    }

}