import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    private ArrayList<BufferedImage> testLayers = new ArrayList<>();
    private ArrayList<Floor> floorList = new ArrayList<>();
    private ArrayList<String> layerImgPaths = new ArrayList<>();
    private BufferedImage poiImage;

    public MainTest(){
        BufferedImage layer1 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-NONE.png");
        BufferedImage layer2 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-ACCESS.png");
        BufferedImage layer3 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-WASHROOM.png");
        BufferedImage poiImage = loadImage("res/poiImage.png");
        testLayers.add(layer1);
        testLayers.add(layer2);
        testLayers.add(layer3);
        testLayers.add(null);
        testLayers.add(null);
        testLayers.add(null);

        layerImgPaths.add("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-NONE.png");
        layerImgPaths.add("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-ACCESS.png");
        layerImgPaths.add("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-WASHROOM.png");
        layerImgPaths.add("");
        layerImgPaths.add("");
        layerImgPaths.add("");


        Floor Floor1 = new Floor(1,1,1,1,1, testLayers);
        Floor Floor2 = new Floor(2,1,1,1,1, testLayers);
        Floor Floor3 = new Floor(3,1,1,1,1, testLayers);

        Floor1.setImgLayerPaths(layerImgPaths);
        Floor2.setImgLayerPaths(layerImgPaths);
        Floor3.setImgLayerPaths(layerImgPaths);

        floorList.add(Floor1);
        floorList.add(Floor2);
        floorList.add(Floor3);
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

    @Test
    void main() {
    }

    @Test
    void getGraphics() {
        Main test = new Main();
        JFrame graphicsPanel = new JFrame();

        //assertEquals();
    }

    @Test
    void getScreen() {
        Main test = new Main();
        test.setScreen(2);

        assertEquals(2, test.getScreen());
    }

    @Test
    void getBuildings() {
        Main test = new Main();
        Building b1 = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        Building b2 = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        Building b3 = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b1);
        buildings.add(b2);
        buildings.add(b3);

        test.buildings = buildings;

        assertEquals(buildings, test.getBuildings());
    }

    @Test
    void encryptPassword() {
        Main test = new Main();
        String password = "password";
        password = test.encryptPassword(password, false);
        assertEquals("xqk{\u0087gzt", password);
    }

    @Test
    void searchForMap() {
    }

    @Test
    void setScreen() {
        Main test = new Main();
        test.setScreen(2);

        assertEquals(2, test.getScreen());
    }


    @Test
    void getActiveBuilding() {
        Main test = new Main();
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b);
        test.buildings = buildings;
        test.setActiveBuilding("Building");

        assertEquals("Building", test.getActiveBuilding());
    }

    @Test
    void setActiveBuilding() {
        Main test = new Main();
        Building b = new Building(new ArrayList<Floor>(), "Building", null, 1,1,1,1,1 );
        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b);
        test.buildings = buildings;
        test.setActiveBuilding("Building");

        assertEquals("Building", test.getActiveBuilding());
    }

    @Test
    void getActiveFloor() {
        Main test = new Main();

        Floor Floor1 = new Floor(1,1,1,1,1, testLayers);
        Floor Floor2 = new Floor(2,1,1,1,1, testLayers);
        Floor Floor3 = new Floor(3,1,1,1,1, testLayers);
        ArrayList<Floor> floorList = new ArrayList<>();
        floorList.add(Floor1);
        floorList.add(Floor2);
        floorList.add(Floor3);
        Building b = new Building(floorList, "Building", null, 1,1,1,1,1 );

        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b);


        test.buildings = buildings;
        test.setActiveBuilding(b.getBuildingName());

        test.changeFloor(1);
        assertEquals(2, test.getActiveFloor());
    }

    @Test
    void resetActiveFloor() {
        Main test = new Main();

        Building b = new Building(floorList, "Building", null, 1,1,1,1,1 );

        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b);


        test.buildings = buildings;

        test.changeFloor(1);
        test.resetActiveFloor();
        assertEquals(1, test.getActiveFloor());
    }

    @Test
    void changeFloor() {
        Main test = new Main();

        Building b = new Building(floorList, "Building", null, 1,1,1,1,1 );

        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b);


        test.buildings = buildings;
        test.setActiveBuilding(b.getBuildingName());

        test.changeFloor(1);
        assertEquals(2, test.getActiveFloor());
    }

    @Test
    void getUsers() {
    }

    @Test
    void getCurrentUser() {
    }

    @Test
    void setCurrentUserObj() {
    }

    @Test
    void getCurrentUserObj() {
    }

    @Test
    void setCurrentUser() {
    }

    @Test
    void toggleLayer() {
    }

    @Test
    void getNextID() {
    }

    @Test
    void addFavourite() {
    }

    @Test
    void createPOI() {
    }

    @Test
    void saveBuilding() throws IOException, ParseException {
        boolean found = false;
        Main test = new Main();
        String filepath = "res/MainBuildingImage/TDB.png";

        Building b = new Building(floorList, "TestBuilding", loadImage(filepath), 1,1,1,1,1 );

        ArrayList<Building> buildings = new ArrayList<>();
        buildings.add(b);

        test.buildings = buildings;

        test.saveBuilding(filepath);

        test = new Main(); // to recreate the buildings from JSON

        for (Building c : test.getBuildings()){
            if (c.getBuildingName().equals(b.getBuildingName())){
                found = true;
            }
        }

        assertEquals(true, found);
    }


    @Test
    void deleteBuilding() throws IOException, ParseException {
        Building b = null;
        boolean found = false;
        Main test = new Main();
        for (Building c : test.getBuildings()){
            if (c.getBuildingName().equals("TestBuilding")) test.deleteBuilding(c);;
        }

        test = new Main();
        b = null;
        for (Building c : test.getBuildings()){
            if (c.getBuildingName().equals("TestBuilding")) b = c;
        }

        assertEquals(true, b == null);
    }

    @Test
    void recalculateBuildingPositions() {
    }

    @Test
    void userHasPoi() {
        Main test = new Main();
        int[] fav = {1, 2};
        ArrayList<POI> created = new ArrayList<>();
        POI p = new POI(1000, 10, 10, 10, 10, poiImage, 5, "A", "Middlesex", 2);
        created.add(p);
        user u = new user("guest", "pass", false, fav, created);
        test.users.add(u);
        test.setCurrentUser(u.getUserName());
        boolean check = test.userHasPoi(p);
        assertTrue(check);
    }

    @Test
    void addPOItoUser() {
    }

    @Test
    void removePOI() {
    }

    @Test
    void deleteFavourites() {
    }

    @Test
    void saveJSON() {
    }
}