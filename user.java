import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * User object for each account used in the application
 * <br><br>
 * Users require:
 * <t>A username</t>
 * <t>A password</t>
 * <t>An array of favourite POIs</t>
 * <t>An arraylist of created POIs</t>
 * <t>A boolean for if they are an admin</t>
 * <b>Example use:</b>
 * <code>user newUser = new user("guest", "abcdefg", [10000, 1045], [POI1, POI2], false);</code>
 * @version 1.0
 * @author Jaden Keeble
 */

public class user {
    /** String of user's username */
    private final String userName;
    /** String of the user's password */
    private final String password;
    /** Integer array of POI id's the user favourites */
    private int[] favouriteList;
    /** Arraylist of POI's the user has created */
    private final ArrayList<POI> pointsOfInterest;
    /** Boolean for if the user is an administrator */
    private final boolean admin;

    /**
     * The constructor for the user class.
     *
     * @param username         The new user's username.
     * @param password         The new user's password. This will be hashed before it is stored within the .json files.
     * @param admin            Whether the user is an admin or not. This will default to no for new users.
     * @param favouriteList    The user's list of favourites.
     * @param pointsOfInterest The user's list of point of interests.
     */
    public user(String username, String password, boolean admin, int[] favouriteList, ArrayList<POI> pointsOfInterest) {
        this.userName = username;
        this.password = password;
        this.admin = admin;
        this.favouriteList = favouriteList;
        this.pointsOfInterest = pointsOfInterest;
    }

    /**
     * Return the username of the active user.
     *
     * @return The active user's username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieve the password of the active user
     * @return The active user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the favourite list of the active user.
     *
     * @return The active user's favourite list.
     */
    public int[] getFavouriteList() {
        return favouriteList;
    }

    /**
     * Return the point of interest list of the active user.
     *
     * @return The active user's point of interest list.
     */
    public ArrayList<POI> getPointsOfInterest() {
        return pointsOfInterest;
    }

    /**
     * Adds a favourite to the user's list of favourites.
     *
     * @param id The id of the point of interest the user wants to favourite.
     */
    public void addFavourites(int id) {
        int[] favouriteCopy = new int[favouriteList.length + 1];
        for (int i = 0; i < favouriteCopy.length; i++) {
            if (i < favouriteList.length && favouriteList[i] == id) return; //cannot add same favourite twice
            if (i < favouriteList.length) favouriteCopy[i] = favouriteList[i];
            else favouriteCopy[i] = id;
        }
        favouriteList = favouriteCopy;
    }

    /**
     * Add a point of interest to the user's list of point of interests.
     *
     * @param x      The x map coordinate of the point of interest.
     * @param y      The y map coordinate of the point of interest.
     * @param width  The width of the point of interest.
     * @param height The height of the point of interest.
     * @param image  The image to use to represent the point of interest.
     */
    public void addPointOfInterest(int id, int x, int y, int width, int height, BufferedImage image, int screen, String metadata, String buildingName, int floor) {
        POI newPoi = new POI(id, x, y, width, height, image, screen, metadata, buildingName, floor);
        pointsOfInterest.add(newPoi);
    }

    /**
     * Remove a favourite from the user's list of favourites.
     *
     * @param id The id of the point of interest to remove.
     */
    public void removeFavourite(int id) {
        int[] favouriteCopy = new int[favouriteList.length - 1]; //minus one spot for the removed entry
        int j = 0; //increment for copy
        for (int i = 0; i < favouriteList.length - 1; i++) {
            if (favouriteList[i] != id) {
                favouriteCopy[j] = favouriteList[i];
                j++;
            } //only increment j for valid entries
        }
        //by time loop ends, favouriteCopy has all entries except one to remove
        favouriteList = favouriteCopy;
    }

    /**
     * Checks whether the current user is an administrator for the application.
     *
     * @return True if the user is an administrator, False otherwise.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Method to remove a POI from this user instance's POI list
     * @param id Integer of the POI id to remove
     */
    public void removePoi(int id) {
        for (int i = 0; i < pointsOfInterest.size(); i++) {
            if (pointsOfInterest.get(i).getID() == id) {
                pointsOfInterest.remove(i);
            }
        }
    }
}