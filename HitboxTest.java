import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HitboxTest {

    @Test
    void intersects() {
        Hitbox h = new Hitbox(100, 100, 100, 100);
        boolean test = h.intersects(150, 150);
        assertTrue(test);
    }
}