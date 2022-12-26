import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Floor object for each floor of a building
 * <br><br>
 * Floors require:
 *<t>Their location on screen.</t>
 * <t>The building they're associated with.</t>
 * <t>An arraylist containing all the image variations for accessibility layers.</t>
 * <b>Example use:</b>
 * <code>Floor firstFloor = new Floor(1, 100, 200, 500, 500, accessibilityImageList);</code>
 *
 * @version 1.0
 * @author Jacob Smith
 */
public class Floor {
    /**The list of images for each accessibility layer of the map.*/
    private final ArrayList<BufferedImage> layerList;
    /**The floor number of the building this floor is in.*/
    private final int floorNumber;
    /**The list of Points of Interest on this floor.*/
    private final ArrayList<POI> poiList;
    /**The x coordinate of the floor image.*/
    private final int x;
    /**The y coordinate of the floor image.*/
    private final int y;
    /**The width of the floor image.*/
    private final int width;
    /**The height of the floor image.*/
    private final int height;
    /**The current layer shown when this floor is selected.*/
    private final int[] currentLayers;
    /**The arraylist of strings that contain file paths to the layer images**/
    private ArrayList<String> layerImgPaths;
    /**The arraylist of POI's that are default POI's (not user-created)*/
    private final ArrayList<POI> defaultPOI;

    /**
     * Floor constructor. Creates a new floor.
     *
     * @param floorNumber the floor number this floor appears on in respect to a building.
     * @param x the x coordinate of the floor image.
     * @param y the y coordinate of the floor image.
     * @param width the width of the floor image.
     * @param height the height of the floor image.
     * @param layerList the list of accessibility images for this floor.
     */
    public Floor(int floorNumber, int x, int y, int width, int height, ArrayList<BufferedImage> layerList) {
        this.floorNumber = floorNumber;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.layerList = layerList;
        currentLayers = new int[]{0, 0, 0, 0, 0, 0}; //create currentLayers array with max six possible layers
        fillLayers();
        poiList = new ArrayList<>();
        defaultPOI = new ArrayList<>();
    }

    /**
     * Private method to set all layers but base layer to zero
     * This array is used is paint() for determining which layers are active
     */
    private void fillLayers() {
        currentLayers[0] = 1; //base layer is always active
        for (int i = 1; i < currentLayers.length; i++) {
            currentLayers[i] = 0; //inactive layer
            if (layerList.get(i) == null) currentLayers[i] = -1; //non-existent layer
        }
    }

    /**
     * Change which layer we are looking at for this floor.
     * @param layer integer representing which layer is visible
     */
    public void activateLayer(int layer) {
        if (currentLayers[layer] != -1) currentLayers[layer] = 1;
    }

    /**
     * Deactivate or ensure a layer is deactivated still
     * @param layer integer at position to deactivate
     */
    public void deactivateLayer(int layer) {
        if (currentLayers[layer] != -1) currentLayers[layer] = 0;
    }

    /**
     * Retrieve the floor number this floor object is associated with.
     * @return floorNumber integer
     */
    public int getFloorNumber() {
        return floorNumber;
    }

    /**
     * Retrieve all points of interest on this floor.
     * @return arraylist of all POI objects on this floor
     */
    public ArrayList<POI> getPOIs() {
        return poiList;
    }

    /**
     * Retrieve only the default POIs on this floor
     * @return Arraylist of POI's on this floor with 0 < id < 2000
     */
    public ArrayList<POI> getDefaultPOI() {
        return defaultPOI;
    }

    /**
     * Method to add a new POI to this floor
     * @param p POI object to be added
     */
    public void addPOI(POI p) {
        poiList.add(p);
    }

    /**
     * Method to add a new POI to this floor with default specification
     * @param p new default POI to be added
     */
    public void addDefaultPoi(POI p) {
        defaultPOI.add(p);
    }


    /**
     * Displays all images of this floor
     * Requires an array of images because more than one layer can be activated at once.
     * @param g Graphics object which can draw the floor images
     */
    public void paint(Graphics g) {
        //set image to corresponding image of layerList
        for (int i = 0; i < currentLayers.length; i++){
            if (currentLayers[i] == 1) {
                g.drawImage(layerList.get(i), x+25, y, width, height, Main.getGraphics());
            }
        }
    }

    /**
     * Sets this instance of image layer file paths
     * @param imgLayerPaths arraylist of strings for each layer image file path
     */
    public void setImgLayerPaths(ArrayList<String> imgLayerPaths){
        this.layerImgPaths = imgLayerPaths;
    }

    /**
     * Retrieve an arraylist Strings for each layer image file path
     * @return String arraylist of layer image file paths
     */
    public ArrayList<String> getLayerImgPaths(){
        return layerImgPaths;
    }
}


