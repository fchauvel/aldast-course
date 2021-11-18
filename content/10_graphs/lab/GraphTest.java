import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class GraphTest {


    @Test
    public void testConnect() {
        var graph = new Graph();
        assertTrue(graph.connect());
    }

}
