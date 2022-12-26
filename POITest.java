//import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

//POITest
class POITest {

	// getID() test
	@Test
	void getIDTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");

		int expectedValue = 1;
		assertEquals(expectedValue, poi.getID());
	}

	// getX() test
	@Test
	void getXTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");

		int expectedValue = 2;

		assertEquals(expectedValue, poi.getX());
	}

	// getY() test
	@Test
	void getYTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");

		int expectedValue = 3;

		assertEquals(expectedValue, poi.getY());
	}

	// getMetadata() test
	@Test
	void getMetadataTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");

		String expectedValue = "metadata";

		assertEquals(expectedValue, poi.getMetadata());
	}

	// isSelected() test
	@Test
	void isSelecedTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");

		boolean expectedValue = true;

		assertEquals(expectedValue, poi.isSelected(5, 7));
	}

	// equalTest
	@Test
	void equalsTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");
		POI newPOI = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata");
		boolean expectedValue = true;

		assertEquals(expectedValue, poi.equals(newPOI));
	}

	// getBuildingTest
	@Test
	void getBuildingTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata", "UCC", 7);
		String expectedValue = "UCC";

		assertEquals(expectedValue, poi.getBuilding());
	}

	// getFloorTest()
	@Test
	void getFloorTest() {
		BufferedImage testImage = new BufferedImage(10, 10, 1);
		POI poi = new POI(1, 2, 3, 4, 5, testImage, 6, "metadata", "UCC", 7);
		int expectedValue = 7;

		assertEquals(expectedValue, poi.getFloor());
	}
}
