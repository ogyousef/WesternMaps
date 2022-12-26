import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {


    private ArrayList<BufferedImage> testLayers = new ArrayList<>();

    /**
     * Constructor to set up the layer list to be used in testing
     */
    private BuildingTest(){
        BufferedImage layer1 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-NONE.png");
        BufferedImage layer2 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-ACCESS.png");
        BufferedImage layer3 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-WASHROOM.png");
        testLayers.add(layer1);
        testLayers.add(layer2);
        testLayers.add(layer3);
        testLayers.add(null);
        testLayers.add(null);
        testLayers.add(null);
    }

    /**
     * Method to load images to put in the layerlists to create building objects
     * @param fn
     * @return BufferedImage
     */
    private BufferedImage loadImage(String fn) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(fn));
            //System.out.println("Image found");
        } catch (IOException e) {
            //System.out.printf("\nImage not found at location \"%s\"", fn);
        }
        return image;
    }

    /**
     * Test method for setFloor() -- creates a building and sets floorNum to 5
     * setFloor(5)
     * Expected value: 5
     */
    @Test
    void setFloor() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        b.setFloor(5);

        assertEquals(5, b.getFloor());
    }

    /**
     * Test method for getFloor() -- creates a building and sets floorNum to 5 then does getFloor()
     *
     * Expected value: 5
     */
    @Test
    void getFloor() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        b.setFloor(5);
        assertEquals(5, b.getFloor());
    }

    /**
     * Test method for getNumFloor() -- creates a building object with three floors, calls getNumFloor()
     *
     * Expected value: 3
     */
    @Test
    void getNumFloor() {
        Floor Floor1 = new Floor(1,1,1,1,1, testLayers);
        Floor Floor2 = new Floor(2,1,1,1,1, testLayers);
        Floor Floor3 = new Floor(3,1,1,1,1, testLayers);
        ArrayList<Floor> floorList = new ArrayList<>();
        floorList.add(Floor1);
        floorList.add(Floor2);
        floorList.add(Floor3);
        Building b = new Building(floorList, "Building", null, 1,1,1,1,1 );
        assertEquals(3, b.getNumFloor());
    }

    /**
     * Test method for isSelected() -- calls isSelected on building with parameters mx = 1 and my = 1
     * Test should fail as mx < x and my < y
     * Expected value: false
     */
    @Test
    void isSelectedFalse() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        assertEquals(false, b.isSelected(1, 1));
    }

    /**
     * Test method for isSelected() -- calls isSelected on building with parameters mx = 2 and my = 2
     * Test should fail as mx > x and mx < x+ width, and my > y and my < y + height
     * Expected value: true
     */
    @Test
    void isSelectedTrue() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,3,3,1 );
        assertEquals(true, b.isSelected(2, 2));
    }

    /**
     * Test method for getIsSelected() -- calls isSelected with true parameters, then calls getIsSelected
     *
     * Expected value: true
     */
    @Test
    void getIsSelectedTrue() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,3,3,1 );
        b.isSelected(2, 2);

        assertEquals(true, b.getIsSelected());
    }
    /**
     * Test method for getIsSelected() -- calls isSelected with false parameters, then calls getIsSelected
     *
     * Expected value: false
     */
    @Test
    void getIsSelectedFalse() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,3,3,1 );
        b.isSelected(1, 1);

        assertEquals(false, b.getIsSelected());
    }

    /**
     * Test method for resetselection() -- creates a new building, then calls resetSelection(), then tests with getIsSelected()
     * resetSelection should make the isSelected field false
     * Expected value: false
     */
    @Test
    void resetSelection() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,3,3,1 );
        b.resetSelection();
        assertEquals(false, b.getIsSelected());
    }

    /**
     * Test method for getX() -- creates a new building with x = 10
     *
     * Expected value: 10
     */
    @Test
    void getX() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 10,1,3,3,1 );

        assertEquals(10, b.getX());
    }

    /**
     * Test method for getY() -- creates a new building with y = 55
     *
     * Expected value: 55
     */
    @Test
    void getY() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,55,3,3,1 );

        assertEquals(55, b.getY());
    }

    /**
     * Test method for getHeight() -- creates a new building with height = 3
     *
     * Expected value: 10
     */
    @Test
    void getHeight() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,3,3,1 );

        assertEquals(3, b.getHeight());
    }

    /**
     * Test method for getWidth() -- creates a new building with width = 21
     *
     * Expected value: 21
     */
    @Test
    void getWidth() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,21,3,1 );

        assertEquals(21, b.getWidth());
    }

    /**
     * Test method for getFloorList() -- creates a three floor objects and adds them to an arraylist, then adds floorlist to building
     * Expected function: getFloorList returns the created floorList
     * Expected value: floorList
     */
    @Test
    void getFloorList() {
        Floor Floor1 = new Floor(1,1,1,1,1, testLayers);
        Floor Floor2 = new Floor(2,1,1,1,1, testLayers);
        Floor Floor3 = new Floor(3,1,1,1,1, testLayers);
        ArrayList<Floor> floorList = new ArrayList<>();
        floorList.add(Floor1);
        floorList.add(Floor2);
        floorList.add(Floor3);
        Building b = new Building(floorList, "Building", null, 1,1,1,1,1 );

        assertEquals(floorList, b.getFloorList());
    }

    /**
     * Test method for getBuildingName() -- creates a building with name "Building" and tests getBuildingName()
     *
     * Expected value: "Building"
     */
    @Test
    void getBuildingName() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,21,3,1 );

        assertEquals("Building", b.getBuildingName());
    }

    /**
     * Test method for recalculateX() -- Create a new building with x value of 1, then recalculate x
     * This method calculates a new X position based on the number of buildings existing
     * Expected value: actual_x
     */
    @Test
    void recalculateX() {
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,21,3,1 );

        b.recalculateX(1);
        int actual_x = (1200 / (Main.getBuildings().size() + 1))*(2) - 200;
        assertEquals(actual_x, b.getX());
    }
}