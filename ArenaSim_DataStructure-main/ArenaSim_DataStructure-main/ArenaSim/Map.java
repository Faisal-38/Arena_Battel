package ArenaSim_DataStructure.ArenaSim;

import javafx.geometry.Point2D;

public class Map {
   private float damage;
   private int totalCharacters;
   private Point2D readLocation = new Point2D(0, 0);
   

   public Map() {
   }

   public float takeDamage(float health, float damage) {
      health -= damage;
      return health;
   }

   public boolean die(float health) {
      if (health <= 0) {
         return true;
      } else {
         return false;
      }
   }

   public float getDamage() {
      return damage;
   }

  

   public int getTotalCharacters() {
      return totalCharacters;
   }

   public Point2D getReadLocation(){
      return readLocation;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public void setTotalCharacters(int totalCharacters) {
      this.totalCharacters = totalCharacters;
   }

   public void setReadLocation(Point2D readLocation){
      this.readLocation = readLocation;
   }
}
