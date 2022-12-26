/**
 * Hitbox object used for outlining all object on screen for interaction
 * <br><br>
 * <b>Example:</b>
 * <pre>
 *     <code>Hitbox h = new Hitbox(100, 200, 10, 40);</code>
 * </pre>
 * <b>Output:</b> A new hitbox at point (100,200) with dimensions 10x40.
 * @version 1.0
 * @author Jacob Smith
 */
public class Hitbox {
    /**The x position of the hitbox on the screen*/
    private final int x;
    /**The y position of the hitbox on the screen.*/
    private final int y;
    /**The width of the hitbox on the screen.*/
    private final int width;
    /**The height of the hitbox on the screen.*/
    private final int height;

    /**
     * Constructor for Hitbox. Creates a new hitbox.
     *
     * @param x the hitbox's x position
     * @param y the hitbox's y position
     * @param width the hitbox's width
     * @param height the hitbox's height
     */
    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Detects if a mouse click was contained within the hitbox.
     *
     * @param mx the mouse click's x coordinate
     * @param my the mouse click's y coordinate
     * @return true if the click intersects, otherwise false
     */
    public boolean intersects(int mx, int my) {
        if (mx > x && mx < x+width)
            return my > y && my < y + height;
        return false;
    }
}