import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


class userTest {

    private user createTestUser() {
        int[] testFavouriteList = {1, 2, 3, 4, 5};
        BufferedImage testImage = new BufferedImage(10, 10, 1);
        POI testPointOfInterestA = new POI(100, 50, 50, 50, 50, testImage, 1, "A test point of interest.");
        POI testPointOfInterestB = new POI(101, 51, 51, 51, 51, testImage, 1, "A test point of interest.");
        ArrayList<POI> testPOIList = new ArrayList<>();
        testPOIList.add(testPointOfInterestA);
        testPOIList.add(testPointOfInterestB);

        return new user("test_username", "test_password", true, testFavouriteList, testPOIList);
    }


    @Test
    void getUserName() {
        user testUser = createTestUser();

        String expResult = "test_username";
        assertEquals(expResult, testUser.getUserName());
    }

    @Test
    void getPassword() {
        user testUser = createTestUser();

        String expResult = "test_password";
        assertEquals(expResult, testUser.getPassword());
    }

    @Test
    void getFavouriteList() {
        user testUser = createTestUser();

        int[] expResult = {1, 2, 3, 4, 5};
        assertArrayEquals(expResult, testUser.getFavouriteList());
    }

    @Test
    void getPointsOfInterest() {
        user testUser = createTestUser();

        BufferedImage testImage = new BufferedImage(10, 10, 1);
        POI PointOfInterestA = new POI(100, 50, 50, 50, 50, testImage, 1, "A test point of interest.");
        POI PointOfInterestB = new POI(101, 51, 51, 51, 51, testImage, 1, "A test point of interest.");
        ArrayList<POI> toCompare = new ArrayList<>();
        toCompare.add(PointOfInterestA);
        toCompare.add(PointOfInterestB);

        boolean expResult = toCompare.get(0).equals(testUser.getPointsOfInterest().get(0)) && toCompare.get(1).equals(testUser.getPointsOfInterest().get(1));

        assertTrue(expResult);
    }

    @Test
    void addFavourites() {
        user testUser = createTestUser();
        testUser.addFavourites(6);

        int[] expResult = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expResult, testUser.getFavouriteList());
    }

    @Test
    void addPointOfInterest() {
        user testUser = createTestUser();
        BufferedImage testImage = new BufferedImage(10, 10, 1);
        testUser.addPointOfInterest(102, 52, 52, 52, 52, testImage, 1, "A test point of interest.", "Rubber Duck Factory", 1);

        POI PointOfInterestA = new POI(100, 50, 50, 50, 50, testImage, 1, "A test point of interest.");
        POI PointOfInterestB = new POI(101, 51, 51, 51, 51, testImage, 1, "A test point of interest.");
        POI PointOfInterestC = new POI(102, 52, 52, 52, 52, testImage, 1, "A test point of interest.", "Rubber Duck Factory", 1);
        ArrayList<POI> toCompare = new ArrayList<>();
        toCompare.add(PointOfInterestA);
        toCompare.add(PointOfInterestB);
        toCompare.add(PointOfInterestC);

        boolean expResult = toCompare.get(0).equals(testUser.getPointsOfInterest().get(0)) && toCompare.get(1).equals(testUser.getPointsOfInterest().get(1)) && toCompare.get(2).equals(testUser.getPointsOfInterest().get(2));

        assertTrue(expResult);
    }

    @Test
    void removeFavourite() {
        user testUser = createTestUser();
        testUser.removeFavourite(5);

        int[] expResult = {1, 2, 3, 4};
        assertArrayEquals(expResult, testUser.getFavouriteList());
    }

    @Test
    void isAdmin() {
        user testUser = createTestUser();

        assertTrue(testUser.isAdmin());
    }

    @Test
    void removePoi() {
        user testUser = createTestUser();
        testUser.removePoi(101);

        BufferedImage testImage = new BufferedImage(10, 10, 1);
        POI PointOfInterestA = new POI(100, 50, 50, 50, 50, testImage, 1, "A test point of interest.");
        ArrayList<POI> toCompare = new ArrayList<>();
        toCompare.add(PointOfInterestA);

        boolean expResult = toCompare.get(0).equals(testUser.getPointsOfInterest().get(0));

        assertTrue(expResult);
    }
}