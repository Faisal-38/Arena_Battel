package ArenaSim_DataStructure.ArenaSim;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main_menu {
    private Stage primaryStage;
    

    public Main_menu(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createMainMenuContent() {
        Pane root = new Pane();
        root.setPrefSize(1280, 720);
        Image bgim = new Image(getClass().getResource("MainMenu2.png").toExternalForm(), root.getPrefWidth(), root.getPrefHeight(), false, true);

        VBox box = new VBox(20,
                new menuitem("START", () -> showCharacterSelection() ),
                new menuitem("EXIT", () -> Platform.exit())
        );
        box.setTranslateX(50);
        box.setTranslateY(250);
        root.getChildren().addAll(new ImageView(bgim), box);
        return new Scene(root);
    }

    private void showCharacterSelection() {
        CharacterSelection characterSelection = new CharacterSelection(primaryStage, createMainMenuContent());
        Scene characterSelectionScene = characterSelection.createCharacterSelectionScene(primaryStage);
        primaryStage.setScene(characterSelectionScene);
    }

    private static class menuitem extends StackPane {
        menuitem(String name, Runnable action) {
            LinearGradient gradient = new LinearGradient(0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                    new Stop(0.1, Color.web("white", 0.5)), new Stop(1, Color.web("black", 0.5)));

            Rectangle bg = new Rectangle(250, 30, gradient);

            Text text = new Text(name);
            text.fillProperty().bind(Bindings.when(hoverProperty())
                    .then(Color.WHITE).otherwise(Color.DARKRED));

            setOnMouseClicked(e -> action.run());
            getChildren().addAll(bg, text);
        }
    }
  
}