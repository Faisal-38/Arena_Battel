package ArenaSim_DataStructure.ArenaSim;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthBar {
    // Offset constants for positioning the health bar above the player
    private static final int OFFSET_X = 0; // Horizontal offset
    private static final int OFFSET_Y = -20; // Vertical offset (above the player)

    // Method to update the position of the health bar
    public void updatePosition(double playerX, double playerY) {
        // Set the new position of the health bar with the offset
        this.bar.setLayoutX(playerX + OFFSET_X);
        this.bar.setLayoutY(playerY + OFFSET_Y);
    }

    // Rest of the class code below

    private static final Color HEALTH_COLOR = Color.GREEN;
    float x;
    float y;
    
    private final Rectangle bar;
    private final double maxWidth;
    private final double height;


    public HealthBar(double initialHealth, double maxWidth, double height, float x,float y) {
        this.maxWidth = maxWidth;
        this.height = height;
        this.x=x;
        this.y=y;


        // Create a green bar initially
        this.bar = new Rectangle(initialHealth, height, HEALTH_COLOR);
    }

    public void updateHealth(double currentHealth, double maxHealth) {
        // Calculate the ratio of current health to maximum health
        double healthRatio = currentHealth / maxHealth;

        // Update the width of the bar based on the health ratio
        double newWidth = this.maxWidth * healthRatio;
        bar.setWidth(newWidth);

        // Update color if needed
        // e.g., if health is below a certain threshold, change color to yellow or red
    }
    
   
    public void updatePosition(float x, float y) {
        // Update the position of the bar
        bar.setX( x- maxWidth / 2);
        bar.setY( y- height / 2);
    }

    public Rectangle getBar() {
        return bar;
    }
}
