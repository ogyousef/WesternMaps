import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileReader;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Main class to run the application. <br>
 * <br>
 * Reads the json files to create its database
 *
 * @version 1.0
 * @author Jacob Smith
 * @author Jaden Keeble
 * @author Mustafa Atoof
 * @author So Yoon Kim
 * @author Osama Yousef
 */
public class Main {


	/**
	 * Initialize a new instance of main
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		new Main();
	}

	/** First graphics screen to appear */
	static Signin graphicsPanel;
	/** List of all buildings existing in the database */
	static ArrayList<Building> buildings = new ArrayList<>();
	/** List of all users existing in the database */
	static ArrayList<user> users = new ArrayList<>();
	// screen: 1 = log in; 2 = register account; 3 = choose building; 4 = add new map; 5 = individual map
	/** Integer keeping track of which screen is currently visible */
	private static int currentScreen = 1;
	/** Holds username who is currently logged in */
	private static String currentUser = "";
	/** Holds user object who is currently logged in */
	private static user currentUserObj;
	/** Image used for a POI */
	public static BufferedImage poiImage;
	/** POI selected Image for POI */
	public static BufferedImage poiImageHighlighted;
	/** Building currently selected. Chosen in MainMenu GUI */
	private static String activeBuilding;
	/** Floor currently selected. Chosen in BuildingMap and defaults as 1. */
	private static int activeFloor = 1;

	/**
	 * Main method to run the application Calls graphics setup and json file reading
	 */
	Main() {
		FlatLightLaf.setup();
		poiImage = loadImage("res/poiImage.png");
		poiImageHighlighted = loadImage("res/poiImageHig.png");
		try {
			createBuildings(); // must be first
			createUsers(); // contains favourite pois which require buildings first
			createDefaultPOIs(); // pois require buildings first
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
		new Signin().setVisible(true);
	}

	/**
	 * Method to read buildings.json and create all buildings, floors, and layers from it
	 * @throws IOException    if the json file cannot be found
	 * @throws ParseException if the JSON parser cannot parse the file
	 */
	private void createBuildings() throws IOException, ParseException {
		// read buildings.json
		// create building object from file
		// add to buildings arraylist
		ArrayList<Floor> buildingFloors;
		Object obj = new JSONParser().parse(new FileReader("res/buildings.json"));
		JSONArray buildingArray = (JSONArray) obj;
		for (int i = 0; i < buildingArray.size(); i++) {
			buildingFloors = new ArrayList<>(); // reset list of floors every new floor
			JSONObject newBuildingInfo = (JSONObject) buildingArray.get(i);
			String name = (String) newBuildingInfo.get("name");
			Long numFloorsLong = (Long) newBuildingInfo.get("numFloors");
			int numFloors = numFloorsLong.intValue();
			String buildingImageName = (String) newBuildingInfo.get("buildingImage");
			BufferedImage buildingImage = loadImage(buildingImageName);

			// create floors
			JSONObject floorObjects = (JSONObject) newBuildingInfo.get("floors");
			for (int j = 1; j <= numFloors; j++) {
				ArrayList<BufferedImage> layerImages = new ArrayList<>(); // create temporary arraylist of
																						// images
				String floorName = "floor" + j; // floor1, floor2, floor3, etc.
				JSONObject newFloor = (JSONObject) floorObjects.get(floorName);
				/*
				 * Following lines involve finding image name from buildings.json, then turning
				 * that into an image
				 */
				String floorImageName = (String) newFloor.get("floorImage");
				layerImages.add(loadImage(floorImageName));
				String accName = (String) newFloor.get("accessibilityLayerImage");
				if (accName != null)
					layerImages.add(loadImage(accName));
				else
					layerImages.add(null);
				String washName = (String) newFloor.get("washroomLayerImage");
				if (washName != null)
					layerImages.add(loadImage(washName));
				else
					layerImages.add(null);
				String className = (String) newFloor.get("classroomLayerImage");
				if (className != null)
					layerImages.add(loadImage(className));
				else
					layerImages.add(null);
				String labName = (String) newFloor.get("labLayerImage");
				if (labName != null)
					layerImages.add(loadImage(labName));
				else
					layerImages.add(null);
				String restName = (String) newFloor.get("restaurantLayerImage");
				if (restName != null)
					layerImages.add(loadImage(restName));
				else
					layerImages.add(null);

				// Then, this arraylist can be used to create a floor object
				Floor f = new Floor(j, 150, 50, 960, 700, layerImages);
				// Then, add this completed floor to the current building
				buildingFloors.add(f);
			}
			// Once all floors have been created, create the building
			Building b = new Building(buildingFloors, name, buildingImage, 20 + 200 * (buildings.size()), 200, 200, 200, 4);

			// Add new building to list of all buildings
			buildings.add(b);
		}
		recalculateBuildingPositions();
	}
	/**
	 * Method to read users.json and create user object from that information
	 *
	 * @throws IOException    if the file name of the JSON file cannot be found
	 * @throws ParseException if the JSON parser cannot parse the file
	 */
	private void createUsers() throws IOException, ParseException {
		// read users.json
		Object obj = new JSONParser().parse(new FileReader("res/users.json")); // start reading the file
		JSONArray userArray = (JSONArray) obj; // create an array of each user's information
		for (int i = 0; i < userArray.size(); i++) {
			JSONObject newUserInfo = (JSONObject) userArray.get(i);
			String username = (String) newUserInfo.get("username");
			String password = (String) newUserInfo.get("password");
			boolean admin = (boolean) newUserInfo.get("admin");
			password = encryptPassword(password, true);
			// create the integer array of favourite ids
			int numFavourites = ((Long) newUserInfo.get("numFavourites")).intValue();
			JSONArray favourites = (JSONArray) newUserInfo.get("favourites");
			int[] favouriteList = new int[numFavourites];
			for (int j = 0; j < numFavourites; j++) {
				int newFavourite = ((Long) favourites.get(j)).intValue();
				favouriteList[j] = newFavourite;
			}

			user newUser = new user(username, password, admin, favouriteList, new ArrayList<POI>());

			// all user created POIs
			JSONObject createdPOIs = (JSONObject) newUserInfo.get("createdPOIs");
			// must iterate through each poi created
			int numCreated = ((Long) newUserInfo.get("numCreated")).intValue();

			for (int j = 1; j <= numCreated; j++) {

				String poiName = "poi" + j; // poi0, poi1, poi2, etc.

				JSONObject created = (JSONObject) createdPOIs.get(poiName);

				int id = ((Long) created.get("id")).intValue(); // json parser detects numbers as longs by default
				if(id != -1){
					int x = ((Long) created.get("x")).intValue();
					int y = ((Long) created.get("y")).intValue();
					String buildingName = (String) created.get("building");
					int floor = ((Long) created.get("floor")).intValue();
					String metadata = (String) created.get("metadata");
					POI newPoi = new POI(id, x, y, 20, 28, poiImage, 5, metadata);
					newUser.addPointOfInterest(id, x, y, 20, 28, poiImage, 5, metadata, buildingName, floor); // add to
					// user's favourites
					// add the poi to its respective location
					for (Building b : buildings) {
						if (b.getBuildingName().equals(buildingName)) { // if correct building
							for (Floor f : b.getFloorList()) { // check each floor
								if (f.getFloorNumber() == floor) {
									f.addPOI(newPoi); // if correct floor
									break;
								}
							}
						}
					}
				}}
			users.add(newUser); // add new user to list of users.
		}
	}

	/**
	 * Method to read defaultpois.json and create all POIs that appear by default
	 * @throws IOException    if the json file cannot be found
	 * @throws ParseException if the JSON parser cannot read the file
	 */
	private void createDefaultPOIs() throws IOException, ParseException {
		// read defaultpois.json
		// create pois from file
		// add to defaultPOIs arraylist
		Object obj = new JSONParser().parse(new FileReader("res/defaultpois.json")); // start reading the file
		JSONArray poiArray = (JSONArray) obj; // create an array of each user's information
		for (int i = 0; i < poiArray.size(); i++) { // for each POI defined in the JSON file
			JSONObject newPOIInfo = (JSONObject) poiArray.get(i);
			Long id = (Long) newPOIInfo.get("id");
			int poiID = id.intValue();
			Long x = (Long) newPOIInfo.get("x");
			int poiX = x.intValue();
			Long y = (Long) newPOIInfo.get("y");
			int poiY = y.intValue();
			String buildingName = (String) newPOIInfo.get("building");
			Long floor = (Long) newPOIInfo.get("floor");
			int poiFloor = floor.intValue();
			String metadata = (String) newPOIInfo.get("metadata");
			POI newPOI = new POI(poiID, poiX+25, poiY, 20, 28, poiImage, 5, metadata);
			for (Building b : buildings) { // iterate through each building
				if (b.getBuildingName().equals(buildingName)) { // if correct building
					for (Floor f : b.getFloorList()) { // iterate through each floor
						if (f.getFloorNumber() == poiFloor){
							f.addPOI(newPOI);
							f.addDefaultPoi(newPOI);} // if right floor, add the new POI
					}
				}
			}
		}
	}

	/**
	 * Method takes in a file name, which will often be in the form of "res\file"
	 * and creates a BufferedImage from it
	 * 
	 * @param fn String name of the image file
	 * @return BufferedImage referenced by fn
	 */
	private BufferedImage loadImage(String fn) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(fn));
		} catch (IOException e) {
			System.out.printf("\nImage not found at location \"%s\"", fn);
		}
		return image;
	}

	/**
	 * Method to find and return the GUI objects for POI and Floor to draw to
	 * 
	 * @return GUI object
	 */
	public static JFrame getGraphics() {
		return graphicsPanel;
	}

	/**
	 * Method to retrieve the currently activated screen
	 * 
	 * @return integer assigned to currently active screen
	 */
	public static int getScreen() {
		return currentScreen;
	}

	/**
	 * Retrieve arraylist of all Building objects
	 * @return ArrayList of Buildings created in database
	 */
	public static ArrayList<Building> getBuildings() {
		return buildings;
	}

	/**
	 * This method takes a password and either encrypts if it is not encrypted, or
	 * decrypts it if it is.
	 * 
	 * @param password  The password to be encrypted.
	 * @param encrypted A boolean which checks whether the password is encrypted or
	 *                  not. If true, the password is instead decrypted.
	 * @return Either the encrypted or decrypted password.
	 */
	public static String encryptPassword(String password, boolean encrypted) {
		char[] encPass = new char[password.length()];
		int key = 0;

		int encFactor = 1;
		if (encrypted) {
			encFactor = -1;
		}

		for (int i = 0; i < password.length(); i++) {
			switch (i % 3) {
			case 0:
				key = password.length() * encFactor;
				break;
			case 1:
				key = password.length() * 2 * encFactor;
				break;
			case 2:
				key = password.length() * -1 * encFactor;
				break;
			}
			encPass[i] = (char) (password.charAt(i) + key);
		}
		return String.copyValueOf(encPass);
	}

	/**
	 * Searches for the text inputted in the search box among the building information.
	 * @param searchInput The keyword to be searched.
	 * @return The buildings with information that contained the keyword.
	 */
	public static ArrayList<String> searchForMap(String searchInput) {
		ArrayList<String> foundResults = new ArrayList<String>();
		int numResults = 0;

		for (Building b : buildings) {
			if (b.getBuildingName().toLowerCase().contains(searchInput.toLowerCase())) {
				if (!foundResults.contains(b.getBuildingName())) {
					numResults++;
					if (foundResults.size() < 5) {
						foundResults.add(b.getBuildingName());
					}
				}
			}
			for (Floor f : b.getFloorList()) {
				for (POI p : f.getPOIs()) {
					if (p.getMetadata().toLowerCase().contains(searchInput.toLowerCase())) {
						String buildingAndFloor = String.format("%s, Floor: %s", b.getBuildingName(), f.getFloorNumber());
						if (!foundResults.contains(b.getBuildingName()) && !foundResults.contains(buildingAndFloor)) {
							numResults++;
							if (foundResults.size() < 5) {
								foundResults.add(buildingAndFloor);
							}
						}
					}
				}
			}
		}
		if (numResults == 0) {
			foundResults.add("No results found");
		} else if (numResults > 5) {
			foundResults.add(String.format("%s Additional Results", numResults - 5));
		}
		return foundResults;
	}

	/**
	 * Method to update the current screen value every time a screen is changed
	 * 
	 * @param s screen number changed whenever screen changes
	 */
	public static void setScreen(int s) {
		currentScreen = s;
	}

	/**
	 *Method to return currently active building
	 * @return String activeBuilding
	 */
	public static String getActiveBuilding() {
		return activeBuilding;
	}

	/**
	 * Method to set a new active building
	 * @param buildingName String
	 */
	public static void setActiveBuilding(String buildingName) {
		activeBuilding = buildingName;
	}

	/**
	 * Method to get active floor
	 * @return int activeFloor
	 */
	public static int getActiveFloor() {
		return activeFloor;
	}

	/**
	 * Method to reset the active floor to 1
	 * Called when a new building is being viewed
	 */
	public static void resetActiveFloor() {
		activeFloor = 1;
	}

	/**
	 * Method to change the active floor when directed by BuildingMap.java
	 * 
	 * @param direction integer 1 = up, -1 = down
	 * @return status boolean true if successful, else false
	 */
	public static boolean changeFloor(int direction) {
		Building reference = null; // object to temporarily hold the active building
		for (Building b : buildings) {
			if (activeBuilding.equals(b.getBuildingName()))
				reference = b;
		}
		assert reference != null;
		int numFloors = reference.getFloorList().size();
		if (numFloors > activeFloor && direction == 1)
			activeFloor++; // go up one floor if not at max
		else if (activeFloor > 1 && direction == -1)
			activeFloor--; // go down one floor if not at min
		else
			return false;
		return true;
	}

	/**
	 * Method to return the arraylist of user objects
	 * @return ArrayList/<user> users
	 **/
	public static ArrayList<user> getUsers() {
		return users;
	}

	/**
	 * This method returns current logged in user
	 * @return currentUser String
	 */
	public static String getCurrentUser() {
		return currentUser;
	}

	/**
	 * This method sets the currently logged in user object
	 */
	public static void setCurrentUserObj(){
		for (user u : users){
			if (u.getUserName().equals(currentUser))
				currentUserObj = u;
		}
	}

	/**
	 * This method returns the currently logged in user object
	 * @return user currentUserObj
	 */
	public static user getCurrentUserObj() {
		return currentUserObj;
	}

	/**
	 * This method sets the current user value to the logged in user
	 * @param username String
	 */
	public static void setCurrentUser(String username) {
		currentUser = username;
	}

	/**
	 * Method to turn a layer off if it's on, and vice versa
	 * @param layer The integer position of the layer to toggle
	 * @param activate Boolean for if it should be toggled on or off
	 */
	public static void toggleLayer(int layer, boolean activate) {
		for (Building b : buildings) {
			if (b.getBuildingName().equals(activeBuilding)) { //find correct building
				for (Floor f : b.getFloorList()) {
					if (f.getFloorNumber() == activeFloor) { //find correct floor
						if (activate)
							f.activateLayer(layer);
						else
							f.deactivateLayer(layer);
					}
				}
			}
		}
	}

	/**
	 * Method to find the next id of the current user's created POIs
	 * 
	 * @return the next unused id number
	 */
	public static int getNextID() {
		for (user u : users) {
			if (u.getUserName().equals(currentUser)) {
				int numCreated = u.getPointsOfInterest().size();
				return numCreated + 2001; // all ids are 2000 + order created
			}
		}
		return 0;
	}

	/**
	 * Method to take the id of an added favourite and update users.json
	 * 
	 * @param id the id number of the new poi to add
	 * @throws IOException    if the file name of the JSON file cannot be found
	 * @throws ParseException if the JSON parser cannot parse the file
	 */
	public static void addFavourite(int id) throws IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("res/users.json")); // start reading the file
		JSONArray userArray = (JSONArray) obj; // create an array of each user's information
		for (int i = 0; i < userArray.size(); i++) {
			JSONObject userInfo = (JSONObject) userArray.get(i);
			if (userInfo.get("username").equals(currentUser)) { // only update current user
				JSONArray favourites = (JSONArray) userInfo.get("favourites");
				favourites.add(favourites.size(), id);
				int newNumFavourites = ((Long) userInfo.get("numFavourites")).intValue() + 1;
				userInfo.put("numFavourites", newNumFavourites);
				userInfo.put("favourites", favourites);

				Files.write(Paths.get("res/users.json"), userArray.toJSONString().getBytes());
			}
		}
	}

	/**
	 * Method to update users.json when a new poi is created
	 * 
	 * @param p the new POI object to be added
	 * @throws IOException    if the file name of the JSON file cannot be found
	 * @throws ParseException if the JSON parser cannot parse the file
	 */
	public static void createPOI(POI p) throws IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("res/users.json")); // start reading the file
		JSONArray userArray = (JSONArray) obj; // create an array of each user's information
		for (int i = 0; i < userArray.size(); i++) {
			JSONObject userInfo = (JSONObject) userArray.get(i);
			if (userInfo.get("username").equals(currentUser)) { // edit the logged-in user only
				// all user created POIs
				JSONObject createdPOIs = (JSONObject) userInfo.get("createdPOIs");
				// must iterate through each poi created
				int numCreated = ((Long) userInfo.get("numCreated")).intValue() + 1;
				// add new poi which will be called "poi" + numCreated
				JSONObject newPOI = new JSONObject();
				newPOI.put("id", p.getID());
				newPOI.put("x", p.getX());
				newPOI.put("y", p.getY());
				newPOI.put("building", activeBuilding);
				newPOI.put("floor", activeFloor);
				newPOI.put("metadata", p.getMetadata());

				String poiName = "poi" + numCreated;
				createdPOIs.put(poiName, newPOI);
				userInfo.put("numCreated", numCreated);

				Files.write(Paths.get("res/users.json"), userArray.toJSONString().getBytes());
			}
		}
	}

	/**
	 * This method saves the new building's information in the json file
	 * Called after a user successfully creates a new building
	 * @param buildingImgPath string that holds the file path of the building image
	 * @throws IOException If the file name of the JSON cannot be found
	 * @throws ParseException If the JSON file cannot be parsed
	 */
	public static void saveBuilding(String buildingImgPath) throws IOException, ParseException {

		  Building b = buildings.get(buildings.size() - 1); // gets the newest building
		  Object obj = new JSONParser().parse(new FileReader("res/buildings.json")); //start reading

		  JSONArray buildArray = (JSONArray) obj;
		  JSONObject newBuilding = new JSONObject();

		  //start parsing the building information into a json format
		  newBuilding.put("name", b.getBuildingName());
		  newBuilding.put("numFloors",b.getNumFloor());
		  newBuilding.put("buildingImage", buildingImgPath);
		  JSONObject eachFloorJSON = new JSONObject();
		  JSONObject floorImgJSON = new JSONObject();

		  int i = 0;
			  for (; i < b.getNumFloor(); i++) {
				  floorImgJSON.put("floorImage", b.getFloorList().get(i).getLayerImgPaths().get(0));
				  floorImgJSON.put("accessibilityLayerImage", b.getFloorList().get(i).getLayerImgPaths().get(1));
				  floorImgJSON.put("washroomLayerImage", b.getFloorList().get(i).getLayerImgPaths().get(2));
				  floorImgJSON.put("classroomLayerImage", b.getFloorList().get(i).getLayerImgPaths().get(3));
				  floorImgJSON.put("labLayerImage", b.getFloorList().get(i).getLayerImgPaths().get(4));
				  floorImgJSON.put("restaurantLayerImage", b.getFloorList().get(i).getLayerImgPaths().get(5));

				  eachFloorJSON.put("floor" + (String) ((i+1) + ""), floorImgJSON);
			  }
			  newBuilding.put("floors", eachFloorJSON);

			  buildArray.add(newBuilding);
			  Files.write(Paths.get("res/buildings.json"), buildArray.toJSONString().getBytes()); //write to buildings.json file
	}

	/**
	 * This method deletes a building entry from the json file when the user deletes a building
	 * @param toDelete Building object to delete
	 * @throws IOException If the JSON file cannot be found
	 * @throws ParseException If the JSon file cannot be parsed
	 */
	public static void deleteBuilding(Building toDelete) throws IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("res/buildings.json")); //read the buildings.json file

		JSONArray buildingArray = (JSONArray) obj;
		for (int i = 0; i < buildingArray.size();i++){ //find the correct building in the file
			JSONObject buildingInfo = (JSONObject) buildingArray.get(i);

			if (buildingInfo.get("name").equals(toDelete.getBuildingName())){
				buildingArray.remove(buildingInfo); //remove the entry
				Files.write(Paths.get("res/buildings.json"), buildingArray.toJSONString().getBytes()); //write new array to the file
				buildings.remove(toDelete); //remove it from the buildings arraylist
			}
		}
		recalculateBuildingPositions();
	}
	

	/**
	 * This method calls the recalculateX method in building for each building to get new X positions
	 */
	public static void recalculateBuildingPositions() {
		for (int i=0;i<buildings.size();i++) {
			Building b = buildings.get(i);
			b.recalculateX(i);
		}
	}

	/**
	 * Method to detect if the current poi belongs to the user who is logged in or not
	 * @param p POI that is being checked
	 * @return true if the poi belongs to the current user
	 */
	public static boolean userHasPoi(POI p) {
		for (user u : users) {
			if (currentUser.equals(u.getUserName())) {
				for (POI pois : u.getPointsOfInterest()) {
					if (p.equals(pois))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to add a new poi to the active user object
	 * @param p New POI to be added
	 */
	public static void addPOItoUser(POI p) {
		for (user u : users) {
			if (u.getUserName().equals(currentUser)) {
				u.addPointOfInterest(p.getID(), p.getX(), p.getY(), 20, 28, poiImage, 5, p.getMetadata(),
						activeBuilding, activeFloor);
			}
		}
	}

	/**
	 * Method to remove a POI from a user's created POI's list
	 * Replaces the POI id with -1 to mark it for delete, so as not to change the order
	 * @param id Integer id of the POI to find and remove
	 * @throws IOException If the JSON file cannot be found
	 * @throws ParseException If the JSON parser cannot read the file
	 */
	public static void  removePOI(int id) throws IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("res/users.json")); //start reading the file
		JSONArray userArray = (JSONArray) obj; //create an array of each user's information
		for (int i = 0; i < userArray.size(); i++) {
			JSONObject userInfo = (JSONObject) userArray.get(i);
			if (userInfo.get("username").equals(currentUser)) { //only update current user
				JSONObject poiList = (JSONObject) userInfo.get("createdPOIs");
				int numCreated = ((Long) userInfo.get("numCreated")).intValue();
				String idToString = String.valueOf(id);
				int sizeofPOI = poiList.size();
				for(int x =1; x< sizeofPOI+1;x++){
					JSONObject WANTED = (JSONObject) poiList.get("poi"+ x);
					if(WANTED.get("id").toString().equals(idToString) ){
						System.out.println(poiList);
						WANTED.put("id",-1);
						userInfo.put("numCreated", numCreated-1);
						Files.write(Paths.get("res/users.json"), userArray.toJSONString().getBytes());
					}
				}
			}
		}
	}

	/**
	 * Method to find and delete a favourite POI from users.json
	 * @param id Integer id of the id to find and delete
	 * @throws IOException If the JSON file cannot be found
	 * @throws ParseException If the JSON parser cannot read the file
	 */
	public static void deleteFavourites(int id) throws IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader("res/users.json")); //start reading the file
		JSONArray userArray = (JSONArray) obj; //create an array of each user's information
		for (int i = 0; i < userArray.size(); i++) {
			JSONObject userInfo = (JSONObject) userArray.get(i);
			if (userInfo.get("username").equals(currentUser)) { //only update current user
				JSONArray favourites = (JSONArray) userInfo.get("favourites");
				String idToString = String.valueOf(id);
				for(int x =0; x< favourites.size();x++){
					if(favourites.get(x).toString().equals(idToString)){
						userInfo.put("numFavourites",favourites.size()-1);
						favourites.remove(favourites.get(x));
						Files.write(Paths.get("res/users.json"), userArray.toJSONString().getBytes());
					}
				}
			}
		}
	}

	/**
	 * Method to update JSON file when a new user is created
	 * Rewrites the JSON file with the new user added to the JSONArray of users
	 * @throws IOException If the JSON file cannot be found
	 */
	public static void saveJSON() throws IOException {

		try {

			JSONArray jsonArray = new JSONArray();

			for (int i = 0; i < users.size(); i++) {
				// User
				user user = users.get(i);
				JSONObject userJson = new JSONObject();
				userJson.put("username", user.getUserName());
				userJson.put("password", encryptPassword(user.getPassword(), false));
				userJson.put("admin", user.isAdmin());

				int numFavourites = user.getFavouriteList() == null ? 0 : user.getFavouriteList().length;
				int favouriteList[] = user.getFavouriteList() == null ? new int[0] : user.getFavouriteList();

				userJson.put("numFavourites", numFavourites);

				JSONArray favouriteArray = new JSONArray();
				if (numFavourites > 0) {
					for (int k = 0; k < numFavourites; k++) {
						favouriteArray.add(favouriteList[k]);
					}
				}
				userJson.put("favourites", favouriteArray);

				int numCreated = user.getPointsOfInterest() == null ? 0 : user.getPointsOfInterest().size();
				ArrayList<POI> pointsOfInterest = user.getPointsOfInterest() == null ? new ArrayList<POI>()
						: user.getPointsOfInterest();

				JSONObject createdPOIs = new JSONObject();
				userJson.put("numCreated", numCreated);

				if (numCreated > 0) {
					for (int j = 0; j < pointsOfInterest.size(); j++) {
						JSONObject poiNum = new JSONObject();
						JSONObject poiJson = new JSONObject();
						poiJson.put("id", pointsOfInterest.get(j).getID());
						poiJson.put("x", pointsOfInterest.get(j).getX());
						poiJson.put("y", pointsOfInterest.get(j).getY());

						poiJson.put("building", pointsOfInterest.get(j).getBuilding());
						poiJson.put("floor", pointsOfInterest.get(j).getFloor());
						poiJson.put("metadata", pointsOfInterest.get(j).getMetadata());

						createdPOIs.put("poi" + String.valueOf(j + 1), poiJson);
					}
				}
				userJson.put("createdPOIs", createdPOIs);

				jsonArray.add(userJson);
				// Save json file to your computer

			} // end of for loop
			Files.write(Paths.get("res/users.json"), jsonArray.toJSONString().getBytes());

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}