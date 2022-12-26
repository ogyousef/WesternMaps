import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

    class FloorTest {
        private ArrayList<BufferedImage> testLayers = new ArrayList<>();

        /**
         * Constructor to set up the layer list to be used in testing
         */
        private FloorTest(){
            BufferedImage layer1 = loadImage("res/Layer Images/AH/Floor 1 - Transparent/AH-BF-1-NONE.png");
            testLayers.add(layer1);
            testLayers.add(null);
            testLayers.add(null);
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
            } catch (IOException e) {
            }
            return image;
        }
        /**
         * Test method for getFloor() -- creates a floor object with floor of number 10,
         *
         * Expected value: 10
         */

        @Test
        void getFloorNumber (){
            Floor f = new Floor(10,400,400,500,500,testLayers);
            assertEquals(10,f.getFloorNumber());

        }
        /**
         * Test method for addPOI() -- creates a floor object then adds a POI to it then
         * gets the size of the array and compare it 1 which is only one added
         *
         * Expected value: 1
         */
        @Test
        void addPOItest (){
            Floor f = new Floor(10,400,400,500,500,testLayers);
            POI testPointOfInterestA = new POI(100, 50, 50, 50, 50, null, 1, "A test point of interest.");
            f.addPOI(testPointOfInterestA);
            assertEquals(1,f.getPOIs().size());

        }
        /**
         * Test method for getPOIs() -- creates a floor object then adds a POI to it then
         * gets the size of the array and compare it to 2 because only two were added
         *
         * Expected value: 2
         */
        @Test
        void getPOIsTest (){
            Floor f = new Floor(10,400,400,500,500,testLayers);
            POI testPointOfInterestA = new POI(100, 50, 50, 50, 50, null, 1, "A test point of interest.");
            POI testPointOfInterestB = new POI(101, 51, 51, 51, 51, null, 1, "A test point of interest.");
            f.addPOI(testPointOfInterestA);
            f.addPOI(testPointOfInterestB);
            assertEquals(2,f.getPOIs().size());

        }
        /**
         * Test method for addDefaultPoi() -- creates a floor object then adds 3 POI to it then
         * gets the size of the array and compare it to 4 which is the number of default POIs added
         *
         * Expected value: 3
         */
        @Test
        void addDefaultPoiTest (){
            Floor f = new Floor(10,400,400,500,500,testLayers);
            POI testPointOfInterestA = new POI(100, 50, 50, 50, 50, null, 1, "A test point of interest.");
            POI testPointOfInterestB = new POI(101, 51, 51, 51, 51, null, 1, "A test point of interest.");
            POI testPointOfInterestC = new POI(102, 51, 51, 51, 51, null, 1, "A test point of interest.");
            POI testPointOfInterestD = new POI(103, 51, 51, 51, 51, null, 1, "A test point of interest.");

            f.addDefaultPoi(testPointOfInterestA);
            f.addDefaultPoi(testPointOfInterestB);
            f.addDefaultPoi(testPointOfInterestC);
            f.addDefaultPoi(testPointOfInterestD);

            assertEquals(4,f.getDefaultPOI().size());

        }
        /**
         * Test method for getDefaultPOITest() -- creates a floor object then adds 3 POI to it then
         * gets the size of the array and compare it to 3 which is the number of POIs added
         *
         * Expected value: 3
         */
        @Test
        void getDefaultPOITest (){
            Floor f = new Floor(10,400,400,500,500,testLayers);
            POI testPointOfInterestA = new POI(100, 50, 50, 50, 50, null, 1, "A test point of interest.");
            POI testPointOfInterestB = new POI(101, 51, 51, 51, 51, null, 1, "A test point of interest.");
            POI testPointOfInterestC = new POI(102, 51, 51, 51, 51, null, 1, "A test point of interest.");

            f.addDefaultPoi(testPointOfInterestA);
            f.addDefaultPoi(testPointOfInterestB);
            f.addDefaultPoi(testPointOfInterestC);

            assertEquals(3,f.getDefaultPOI().size());

        }

    }