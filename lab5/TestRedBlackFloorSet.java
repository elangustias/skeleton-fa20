import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        AListFloorSet aList = new AListFloorSet();
        RedBlackFloorSet rbList = new RedBlackFloorSet();
        for (int i = 0; i < 1000000; i++) {
            Double curr = StdRandom.uniform(-5000.00, 5000.00);
            aList.add(curr);
            rbList.add(curr);
        }
        for (int i = 0; i < 100000; i++) {
            Double floor = StdRandom.uniform(-5000.00, 5000.00);
            Double expected = aList.floor(floor);
            Double actual = rbList.floor(floor);
            assertEquals(expected, actual, 0.1);
        }
    }
}
