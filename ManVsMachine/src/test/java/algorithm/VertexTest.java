package algorithm;

import mvsm.algorithm.Vertex;
import java.util.HashSet;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VertexTest {

    private Vertex vertex;

    public VertexTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.vertex = new Vertex(2, 2);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void scaleOffsetTest() {
        Vertex v = this.vertex.scaleOffset(40, 0, 0);
        Vertex v2 = this.vertex.scaleOffset(40, 1, 0);
        Vertex v3 = this.vertex.scaleOffset(40, 0, 1);
        Vertex v4 = this.vertex.scaleOffset(40, -1, 1);
        if (v.getRow() == 40 && v.getColumn() == 40) {
            return;
        }
        if (v2.getRow() == 80 && v2.getColumn() == 40) {
            return;
        }
        if (v3.getRow() == 40 && v3.getColumn() == 80) {
            return;
        }
        if (v4.getRow() == 0 && v4.getColumn() == 80) {
            return;
        }
        fail("scaleOffset returns an vertex with wrong coordinates");
    }

    @Test
    public void hashCollisionTest() {
        HashSet<Vertex> set = new HashSet<>();
        Random rnd = new Random(17);
        for (int i = 0; i < 1000; i++) {
            set.add(new Vertex(rnd.nextInt(30), rnd.nextInt(40)));
        }
        if (set.size() == 673) {
            return;
        }
        fail("HashCollision detected. set size should be: 672" + " But is:" + set.size());
    }

    @Test
    public void equalsTest() {
        String t = "Test";
        Vertex t2 = new Vertex(23, 24);
        Vertex t3 = new Vertex(1, 2);
        Vertex t4 = new Vertex(-2, -2);
        Vertex t5 = new Vertex(4, 4);
        Vertex t6 = new Vertex(2, 2);
        if (!this.vertex.equals(t) && !this.vertex.equals(t2) && !this.vertex.equals(t3) && !this.vertex.equals(t4) && !this.vertex.equals(t5) && this.vertex.equals(t6) && this.vertex.equals(this.vertex) && !this.vertex.equals(null)) {
            return;
        }
        fail("Equals doesn't work how it is supposed to work");
    }
}
