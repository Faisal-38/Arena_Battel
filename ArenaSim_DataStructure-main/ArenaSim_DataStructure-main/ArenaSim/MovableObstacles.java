package ArenaSim_DataStructure.ArenaSim;

import javafx.application.Platform;
import javafx.scene.shape.Circle;
import java.util.Random;

public class MovableObstacles extends Obstacles {
    private float speed;
    private int counter;
    private boolean isVisible;
    private Random random = new Random();

    public MovableObstacles(){}

    public MovableObstacles(float damage, String name, float speed, Circle shape) {
        super(damage, name, shape);
        this.speed = speed;
        this.counter = 0;
        this.isVisible = true; // Visible by default for linear moving obstacles
    }

    public void updatePosition() {
        // Get the current position
        double speed = 0.5; // Example slower speed

    // Update the shape's position directly inside Platform.runLater
        Platform.runLater(() -> {
            double newX = this.getShape().getCenterX() + speed;
            double newY = this.getShape().getCenterY(); // For linear horizontal movement

        // Boundary check
            if (newX > 1280) { 
                newX = 0; // Reset to start from the left again
            }

            this.getShape().setCenterX(newX);
            this.getShape().setCenterY(newY);

        });
}

    // Method to update the position of the moving underground shaft
    public void updateLinearMovement() {
        // Example linear movement
        getShape().setLayoutX(getShape().getLayoutX() + speed);
        // Add boundary checks and reverse direction logic if needed
    }

    // Method to manage the meteor's appearance and disappearance
    public void updateMeteor() {
        counter++;

        if (isVisible) {
            if (counter >= 5 * 60) { 
                // Make meteor invisible and reset counter
                getShape().setVisible(false);
                isVisible = false;
                counter = 0;
            }
        } else {
            if (counter >= 30 * 60) { // 30 seconds of being invisible
                // Make meteor visible at a random location
                getShape().setVisible(true);
                Circle meteor = getShape();
                meteor.setCenterX(random.nextDouble() * 1280); 
                meteor.setCenterY(random.nextDouble() * 720);  
                isVisible = true;
                counter = 0;
            }
        }
    }

    
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
