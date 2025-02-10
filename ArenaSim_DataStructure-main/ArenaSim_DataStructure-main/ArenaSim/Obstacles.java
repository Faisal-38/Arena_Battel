package ArenaSim_DataStructure.ArenaSim;



public class Obstacles extends Map implements Updatable{
    private int numberOfRock;
    private int numberOfHoles;
    private int numberOfUndergroundShaft;
    private String obstacleName;
    private javafx.scene.shape.Circle shape;



    public Obstacles() {}

    public Obstacles(float damage, String obstacleName , javafx.scene.shape.Circle shape){
        super();
        this.shape = shape;
        this.setDamage(damage);
        this.obstacleName = obstacleName;


        if ("UnderGroundShaft".equalsIgnoreCase(obstacleName)) {
            numberOfUndergroundShaft++;
        } else if ("Rock".equalsIgnoreCase(obstacleName)) {
            numberOfRock++;
        } else if ("Hole".equalsIgnoreCase(obstacleName)) {
            numberOfHoles++;
        }
    }

    public  int getNumberOfUndergroundShaft() {
        return numberOfUndergroundShaft;
    }

    public int getNumberOfRock() {
        return numberOfRock;
    }

    public int getNumberOfHoles() {
        return numberOfHoles;
    }

    public javafx.scene.shape.Circle getShape(){
        return shape;
    }

    public String getObstacleName(){
        return obstacleName;
    }


}