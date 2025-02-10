package ArenaSim_DataStructure.ArenaSim;


import java.util.LinkedList;
import java.util.Queue;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SpecialPlayer extends Player {
    private boolean isRunning;
    private long runEndTime;
    private long cooldownEndTime;
    private static final long RUN_DURATION = 1000; // 10 seconds
    private static final long COOLDOWN_DURATION = 15000; // 15 seconds
    Player player = new Player();
    private Queue<Integer> queue; // goal done
    private boolean oneManStanding; // flag for checking if the special player is alive while teammates are all dead
    
    

    public SpecialPlayer(){}

    public SpecialPlayer(float health, float atk_speed, float damage, int movementSpeed, String name, Circle shape) {
        super(health, atk_speed, damage, movementSpeed, name, shape);
        super.updateHealthBar();
        this.isRunning = false;
        this.runEndTime = 0;
        this.cooldownEndTime = 0;
        this.queue = new LinkedList<>(); // Initialize the queue here
        queue.add(1); // attack
        queue.add(2); // run away when health below 25%
        queue.add(3); // summone
        
    }

    public void printQueue(Queue<Integer> priorities){
        for(Integer priority: priorities){
            System.out.println(priority);
        }
    }
   
    // method that updates the priority of the special player
    @Override
    public void updatePriority(Player enemy){
        Integer temp;
        if(this.getHealth() < this.getMaxHealth() * 0.20 && this.getAttackingMe() != null && !getIsOneManStanding() ){
           
            for(int i = 1; i < queue.size(); i++){
                if(queue.peek() != 2 && queue.peek() !=null){
                    temp = queue.remove();
                    queue.add(temp);
                }
            }
        }
        // summone logic
        else if(this.getHealth() < this.getMaxHealth() * 0.70 && this.getAttackingMe() != null && !getIsOneManStanding()){

            for(int i = 1; i < queue.size(); i++){
                if(queue.peek() != 3 && queue.peek() !=null){
                    temp = queue.remove();
                    queue.add(temp);
                }
            }
        }
        else{
            // attack logic (default priority)
            for(int i = 1; i < queue.size(); i++){
                if(queue.peek() != 1 && queue.peek() !=null){
                    temp = queue.remove();
                    queue.add(temp);
                }
            }
          
        }    
        
    }
    
    // when running, move to opposite direction of the attacker
    public void moveTowardsOppositeDirection(double targetX, double targetY){
        Point2D currentLocation = this.getReadLocation();
        double currentX = currentLocation.getX();
        double currentY = currentLocation.getY();
    
        double dx = targetX - currentX;
        double dy = targetY - currentY;
        double length = Math.sqrt(dx * dx + dy * dy);
    
        // Prevent division by zero if target and current positions are the same
        if (length == 0) {
            return; // Player has reached the target, no movement needed
        }
    
        double normX = dx / length;
        double normY = dy / length;

        
        // Move in the opposite direction
         double moveX = -normX * this.getMovmentSpeed(); 
         double moveY = -normY * this.getMovmentSpeed();
         this.setReadLocation(new Point2D(currentX + moveX, currentY + moveY));
         
         Platform.runLater(() -> {
            getShape().setCenterX(currentX + moveX);
            getShape().setCenterY(currentY + moveY);
        });
    }

    // this method is the logic of calling a teammate to help the special player
    public void summonePlayer(Player player){
        if(!(player instanceof SpecialPlayer)){
        if(player.getName().contains("Enemy")){
            player.getShape().setFill(Color.MAROON);
        }
        if(player.getName().contains("Player")){
            player.getShape().setFill(Color.NAVY);
        }
        player.setSommoneMe(this);
        player.setIsSummoneMe(true);
        }
    }

   
    // update running status of the special player, this method needs a refrenece of the player with the highest health
    public void updateRunningStatus(Player player) {
        long currentTime = System.currentTimeMillis();

        if (!queue.isEmpty()) {
            Integer currentPriority = queue.peek();

            if (currentPriority == 2 && currentTime > cooldownEndTime && getAttackingMe() != null) {
                
                    startRunning();
                    this.setAtackwho(null);                
                
            } else if (currentTime > runEndTime && currentPriority == 1) {
                stopRunning();
            }
            // we want to implement the summoning algorithim
            else if (currentPriority == 3){
                summonePlayer(player);
                
            }
        } else {
            System.out.println("Priority queue is empty. Cannot update running status.");
        }
    }
    
    
    private void startRunning() {
        if (System.currentTimeMillis() > cooldownEndTime) { // Only start running if the cooldown has expired
            isRunning = true;
            runEndTime = System.currentTimeMillis() + RUN_DURATION;
            cooldownEndTime = runEndTime + 2000; // Cooldown period of 3 seconds after running ends
        } else {
            System.out.println("Cannot run: still in cooldown period.");
        }
    }
    
    
    private void stopRunning() {
        // Set isRunning to false and start the cooldown
        isRunning = false;
    }

    // Override getDamage if damage changes when running
    @Override
    public float getDamage() {
        if (isRunning) {
            return super.getDamage() * 3; 
        }
        return super.getDamage();
    }

    public void update() {
        updateRunningStatus(player);
    }

    public boolean getIsRunning(){
        return isRunning;
    }

    public boolean getIsOneManStanding(){
        return this.oneManStanding;
    }

    public void setRunEndTime(long runEndTime) {
        this.runEndTime = runEndTime;
    }

    public long getCooldownEndTime() {
        return cooldownEndTime;
    }

    public long getRunEndTime() {
        return runEndTime;
    }

    public void setCooldownEndTime(long cooldownEndTime) {
        this.cooldownEndTime = cooldownEndTime;
    }

    public void setIsOneManStanding(boolean oneManStanding){
        this.oneManStanding = oneManStanding;
    }

    public void setIsRunning(boolean condition){
        this.isRunning = condition;
    }
   
    
}