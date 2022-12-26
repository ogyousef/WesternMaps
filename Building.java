import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Building object for each building in the map system
 *
 * Buildings contain:
 * A floor list which contains all the floor objects within the building
 * A building name
 * Total number of floors
 * A building image, with x, y, width, and height
 * A hitbox object for the main screen interactions
 *
 * @version 1.0
 * @author Mustafa Atoof
 */
public class Building {

    /** List of floor objects representing floors in the building */
    private final ArrayList<Floor> floorList;
    /** Number of floors in the building */
    private int numFloors;
    /** The current floor being viewed */
    private int currFloor;
    /** String containing name of building*/
    private final String buildingName;
    /** Boolean to determine building selections */
    private boolean isSelected;
    /** x-coordinate on screen for building image */
    private int x;
    /** y-coordinate on screen for building image */
    private final int y;
    /** width of building image */
    private final int width;
    /** height of building image */
    private final int height;
    /** Image object containing image of building */
    private final BufferedImage buildingImg;
    /** Screen for hitbox */
    private int screen;
    /** Hitbox object for building's hitbox selection in main menu*/
    private Hitbox buildingHit;

    /**
     * Building constructor. Creates a new building.
     *
     * @param floorList the list of floors in this building
     * @param buildingName the name of the building
     * @param buildingImg image object of building
     * @param x the x coordinate of the building image
     * @param y the y coordinate of the building image
     * @param width the width of the building image
     * @param height the height of the building image
     * @param screen screen for creating the hitbox
     */
    public Building(ArrayList<Floor> floorList, String buildingName, BufferedImage buildingImg, int x, int y, int width, int height, int screen){
        this.floorList = floorList;
        this.buildingName = buildingName;
        this.numFloors = floorList.size();

        //initializing image and hitbox vars
        this.buildingImg = buildingImg;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.screen = screen;
        //building hitbox overlaps the image
        buildingHit = new Hitbox(x, y, width, height);
    }

    /**
     * Set the current floor for viewing
     * @param newFloor Integer number of floor currently viewing
     */
    public void setFloor(int newFloor){
        currFloor = newFloor;
    }

    /**
     * Returns the current floor
     * @return int currFloor
     */
    public int getFloor(){
        return currFloor;
    }

    /**
     * Retrieve the number of floors on this building
     * @return Integer of how many floors there are
     */
    public int getNumFloor() { return numFloors; }
    /**
     * Returns true if building is selected, false if not
     * @param mx mouse click x coordinate
     * @param my mouse click y coordinate
     * @return boolean isSelected
     */
    public boolean isSelected(int mx, int my){
        if (buildingHit.intersects(mx, my)){
            isSelected = true;
            return true;
        }
        return false;
    }

    /**
     * Tell if this building has been selected
     * @return true if selected, else false
     */
    public boolean getIsSelected() { return isSelected; }

    /**
     * Unselect this building so it is no longer active
     */
    public void resetSelection(){
        isSelected = false;
    }

    /**
     * Returns x-coordinate of building image
     * @return int x
     */
    public int getX() {
        return x;
    }

    /**
     * Returns y-coordinate of building image
     * @return int y
     */
    public int getY(){
        return y;
    }

    /**
     * Returns height of building image
     * @return int height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns width of building image
     * @return int width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the list of all floors associated with this building
     * @return arraylist of all floors
     */
    public ArrayList<Floor> getFloorList() {
        return floorList;
    }

    /**
     * Returns the building name
     * @return buildingName String
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Paint method for the building, sends in the building information, so that it can be drawn to the screen
     * @param g Graphics
     */
    public void paint(Graphics g){
        g.drawImage(buildingImg, x, y, width, height, Main.getGraphics());
    }
      /**
     * This method recalculates the x position of the buildings so that the buildings are always centered on the screen, also updates the hitbox
     * @param index int index of where the building is in the buildings arraylist in main
     */
    public void recalculateX(int index) {
    	this.x = (1200 / (Main.getBuildings().size() + 1))*(index+1) - 200;
    	buildingHit = new Hitbox(x,y,width,height);
    }

}
