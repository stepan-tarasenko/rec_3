import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class TextPredictor {
    private int[][] inputPicture;
    private double[] C = new double[] {0, 64, 128, 192, 255};
    private double[][][] L;
    private double[][][] D;
    private double[][][] R;
    private double[][][] U;
    private double[][][] phi;
    private double[][] g;
    private double[][][] q;



    public void main() throws IOException, URISyntaxException {
        inputPicture = Utils.readPictureAsArray("lisa.png");
        initArrays();
        run();
    }

    private void run() {
        for (int i = inputPicture.length - 2; i >= 0; i--) {
            for (int j = inputPicture[0].length - 2; j >= 0; j--) {
                for (int c = 0; c < C.length; c++) {
                    R[i][j][c] = updateRight(i, j, c, R, phi, C);
                    D[i][j][c] = updateDown(i, j, c, D, phi, C);
                }
            }
        }
        for (int iter = 0; iter < 10; iter++) {
            for (int i = 1; i < inputPicture.length; i++) {
                for (int j = 1; j < inputPicture[0].length; j++) {
                    for (int k = 0; k < C.length; k++) {
                        L[i][j][k] = updateLeft(i, j, k, L, phi, C);
                        U[i][j][k] = updateUp(i, j, k, U, phi, C);
                        phi[i][j][k] = (L[i][j][k] + U[i][j][k] - R[i][j][k] - D[i][j][k])/2;
                    }
                }
            }
            for (int i = inputPicture.length - 2; i >= 0; i--) {
                for (int j = inputPicture[0].length - 2; j >= 0; j--) {
                    for (int c = 0; c < C.length; c++) {
                        R[i][j][c] = updateRight(i, j, c, R, phi, C);
                        D[i][j][c] = updateDown(i, j, c, D, phi, C);
                        phi[i][j][c] = (L[i][j][c] + U[i][j][c] - R[i][j][c] - D[i][j][c])/2;
                    }
                }
            }
        }
        double[][] newImage = new double[inputPicture.length][inputPicture[0].length];
        List<Double> newIm = new ArrayList<>();
        for (int i = 0; i < newImage.length; i++) {
            for (int j = 0; j < newImage[0].length; j++) {
                newImage[i][j] = restoreK(i, j, C);
                newIm.add(newImage[i][j]);
            }
        }

        double[] writableIm = new double[inputPicture.length * inputPicture[0].length];
        for (int i = 0; i < newIm.size(); i++) {
            writableIm[i] = newIm.get(i);
        }
        BufferedImage outputImage = new BufferedImage(inputPicture[0].length, inputPicture.length, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = outputImage.getRaster();
        raster.setSamples(0, 0, inputPicture[0].length, inputPicture.length, 0, writableIm);
        try {
            ImageIO.write(outputImage, "png", new File("rec_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double updateLeft(int i, int j, int k, double[][][] dir, double[][][] phi, double[] C) {
        double[] val = new double[C.length];
        for (int l = 0; l < C.length; l++) {
            val[l] = dir[i][j-1][l] + 0.5*q[i][j-1][l] - phi[i][j-1][l] + g[l][k];
        }
        double max = val[0];
        for (int l = 0; l < val.length; l++) {
            if (val[l] > max) {
                max = val[l];
            }
        }
        return max;
    }

    private double updateUp(int i, int j, int k, double[][][] dir, double[][][] phi, double[] C) {
        double[] val = new double[C.length];
        for (int l = 0; l < C.length; l++) {
            val[l] = dir[i-1][j][l] + 0.5*q[i-1][j][l] - phi[i-1][j][l] + g[l][k];
        }
        double max = val[0];
        for (int l = 0; l < val.length; l++) {
            if (val[l] > max) {
                max = val[l];
            }
        }
        return max;
    }

    private double updateRight(int i, int j, int k, double[][][] dir, double[][][] phi, double[] C) {
        double[] val = new double[C.length];
        for (int l = 0; l < C.length; l++) {
            val[l] = dir[i][j+1][l] + 0.5*q[i][j+1][l] + phi[i][j+1][l] + g[l][k];
        }
        double max = val[0];
        for (int l = 0; l < val.length; l++) {
            if (val[l] > max) {
                max = val[l];
            }
        }
        return max;
    }

    private double restoreK(int i, int j, double[] C){
        double[] val = new double[C.length];
        for (int k = 0; k < C.length; k++) {
            val[k] = L[i][j][k] + R[i][j][k] + q[i][j][k] - phi[i][j][k];
        }
        double max = val[0];
        int maxIndex = 0;
        for (int l = 0; l < val.length; l++) {
            if (val[l] > max) {
                max = val[l];
                maxIndex = l;
            }
        }
        return C[maxIndex];
    }



    private double updateDown(int i, int j, int k, double[][][] dir, double[][][] phi, double[] C) {
        double[] val = new double[C.length];
        for (int l = 0; l < C.length; l++) {
            val[l] = dir[i+1][j][l] + 0.5*q[i+1][j][l] + phi[i+1][j][l] + g[l][k];
        }
        double max = val[0];
        for (int l = 0; l < val.length; l++) {
            if (val[l] > max) {
                max = val[l];
            }
        }
        return max;
    }

    private void initArrays() {
        L = new double[inputPicture.length][inputPicture[0].length][C.length];
        D = new double[inputPicture.length][inputPicture[0].length][C.length];
        R = new double[inputPicture.length][inputPicture[0].length][C.length];
        U = new double[inputPicture.length][inputPicture[0].length][C.length];
        phi = new double[inputPicture.length][inputPicture[0].length][C.length];
        g = new double[C.length][C.length];
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C.length; j++) {
                g[i][j] = -abs(C[i] - C[j]);
            }
        }
        q = new double[inputPicture.length][inputPicture[0].length][C.length];
        for (int i = 0; i < inputPicture.length; i++) {
            for (int j = 0; j < inputPicture[0].length; j++) {
                for (int k = 0; k < C.length; k++) {
                    if (inputPicture[i][j] != 0) {
                        q[i][j][k] =  -abs(inputPicture[i][j] - C[k]);
                    } else {
                        q[i][j][k] = 0;
                    }
                }
            }
        }
    }
}
