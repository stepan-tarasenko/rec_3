import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestMain {

    private TextPredictor textPredictor;

    @Before
    public void init() {
        textPredictor = new TextPredictor();
    }

    @Test
    public void example() {
        Assert.assertEquals(1,1);
    }

    @Test
    public void testUp() {
        double res = textPredictor.updateUp(
                1, 0, 0,
                new double[][][] {{{0, 1, 2, 3}, {0, 1, 2, 3}},{{0, 1, 2, 3},{0, 1, 2, 3}}},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[] {0, 1, 2},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[][]{{0, 1}, {0, 1}, {0, 1}}
        );
        Assert.assertEquals(5, res, 0.1);
    }

    @Test
    public void testDown() {
        double res = textPredictor.updateDown(
                0, 0, 0,
                new double[][][] {{{0, 1, 2, 3}, {0, 1, 2, 3}},{{0, 1, 2, 3},{0, 1, 2, 3}}},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[] {0, 1, 2},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[][]{{0, 1}, {0, 1}, {0, 1}}
        );
        Assert.assertEquals(5, res, 0.1);
    }

    @Test
    public void testLeft() {
        double res = textPredictor.updateLeft(
                0, 1, 0,
                new double[][][] {{{0, 1, 2, 3}, {0, 1, 2, 3}},{{0, 1, 2, 3},{0, 1, 2, 3}}},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[] {0, 1, 2},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[][]{{0, 1}, {0, 1}, {0, 1}}
        );
        Assert.assertEquals(1, res, 0.1);
    }

    @Test
    public void testRight() {
        double res = textPredictor.updateRight(
                1, 0, 0,
                new double[][][] {{{0, 1, 2, 3}, {0, 1, 2, 3}},{{0, 1, 2, 3},{0, 1, 2, 3}}},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[] {0, 1, 2},
                new double[][][] {{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}},{{0, 1 ,2 ,3}, {0, 1 ,2 ,3}}},
                new double[][]{{0, 1}, {0, 1}, {0, 1}}
        );
        Assert.assertEquals(1, res, 0.1);
    }
}
