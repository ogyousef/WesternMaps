import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * Point of interest for each building
 * Point of interest contains:
 * x, y, width, height, image, isSelected
 *
 * @version 1.0
 * @author SoYoon Kim
 */
public class POI {
	/**Unique 4-digit identifier applied to each POI*/
	private final int id;
	/**X coordinate of the poi on the screen*/
	private final int x;
	/**Y coordinate of the poi on the screen*/
	private final int y;
	/**Width to draw the poi image on screen*/
	private final int width;
	/**Height to draw the poi image on screen*/
	private final int height;
	/**Image object associated with this poi*/
	private BufferedImage image;
	/**Screen number the poi appears on*/
	private final int screen;
	/**Description of the poi*/
	private final String metadata;
	/**Hitbox assigned to this poi*/
	private final Hitbox poiHitbox;
	/**Building name this poi appears in*/
	private String building;
	/**Floor number this poi appears on*/
	private int floor;

	/**
	 * Constructor for POI with parameters x,y, width, height, image
	 * @param id unique integer for each poi
	 * @param x coordinate of x position
	 * @param y coordinate of y position
	 * @param width poi image width
	 * @param height poi image height
	 * @param image BufferedImage with poi pin image
	 * @param screen Integer for screen this poi appears on
	 * @param metadata String description of this poi
	 */
	public POI(int id, int x, int y, int width, int height, BufferedImage image, int screen, String metadata) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.screen = screen;
		this.metadata = metadata;
		poiHitbox = new Hitbox(x, y, width, height);
	}

	/**
	 * Constructor for POI with parameters x,y, width, height, image
	 * @param id unique integer for each poi
	 * @param x coordinate of x position
	 * @param y coordinate of y position
	 * @param width poi image width
	 * @param height poi image height
	 * @param image BufferedImage with poi pin image
	 * @param screen Integer for screen this poi appears on
	 * @param metadata String description of this poi
	 * @param building String of building name this poi appears in
	 * @param floor Integer of floor number this poi appears on
	 */
	public POI(int id, int x, int y, int width, int height, BufferedImage image, int screen, String metadata, String building, int floor) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		this.screen = screen;
		this.metadata = metadata;
		this.building= building;
		this.floor = floor;
		poiHitbox = new Hitbox(x, y, width, height);
	}

	/**
	 * Retrieve this poi's id
	 * @return unique 4-digit identifier for this id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Retrieve x coordinate of the poi on screen
	 * @return Image x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retrieve y coordinate of the poi on screen
	 * @return Image y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Retrieve description of the poi
	 * @return String metadata of this poi
	 */
	public String getMetadata() {
		return metadata;
	}

	/**
	 * Detects if a POI was clicked
	 * @param mx Mouse click x position
	 * @param my Mouse click y position
	 * @return True if the click intersects, else return false
	 */
	public boolean isSelected(int mx, int my) {
		return poiHitbox.intersects(mx, my);
	}

	/**
	 * Method to check if two POIs are the same.
	 * @param p other POI to check against this instance.
	 * @return true if the two POIs have the same id, else false.
	 */
	public boolean equals(POI p) {
		return (p.getID() == id && p.getX() == x && p.getY() == y);
	}

	/**
	 * Draw the image on screen using the coordinates and size
	 * @param g Graphics object to draw with
	 */
	public void paintImage(Graphics g) {
		g.drawImage(image, x, y, width, height, Main.getGraphics());
	}

	/**
	 * Retrieve the building name this poi appears in
	 * @return String building name
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * Retrieve the floor number this poi appears on
	 * @return Integer floor number
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * Method to change the active POI image to the highlighted version when it is selected
	 */
	public void highlighted(){
		image = Main.poiImageHighlighted;
	}

	/**
	 * Method to change the previously active POI image back to the normal version when it is unselected
	 */
	public void unhighlighted(){
		image = Main.poiImage;
	}
}