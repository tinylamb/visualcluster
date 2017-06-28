package mnist;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by samo on 2017/6/28.
 *
 * @author samo
 * @date 2017/06/28
 *
 * refer https://stackoverflow.com/questions/17279049/reading-a-idx-file-type-in-java
 */
public class IdxReader {
    public static void main(String[] args) throws Exception{
        FileInputStream inImage = null;
        FileInputStream inLabel = null;

        final String basePath = "/Users/samo/Documents/githubRepo/"
            + "T-SNE-Java/tsne-demos/src/main/resources/datasets/";
        String inImagePath = basePath + "t10k-images-idx3-ubyte";
        String inLabelPath = basePath + "t10k-labels-idx1-ubyte";

        int[] hashMap = new int[10];

        try {
            inImage = new FileInputStream(inImagePath);
            inLabel = new FileInputStream(inLabelPath);
            int magicNumberImages = (inImage.read() << 24) |
                (inImage.read() << 16) |
                (inImage.read() << 8) |
                (inImage.read());
            int numberOfImages = (inImage.read() << 24) |
                (inImage.read() << 16) |
                (inImage.read() << 8) |
                (inImage.read());
            int numberOfRows  = (inImage.read() << 24) |
                (inImage.read() << 16) |
                (inImage.read() << 8) |
                (inImage.read());
            int numberOfColumns = (inImage.read() << 24) |
                (inImage.read() << 16) |
                (inImage.read() << 8) |
                (inImage.read());
            //System.out.println("magicNumberImages : " + magicNumberImages);
            //System.out.println("numberOfImages : " + numberOfImages);
            //System.out.println("numberOfRows : " + numberOfRows);
            //System.out.println("numberOfColumns : " + numberOfColumns);

            int magicNumberLabels = (inLabel.read() << 24) |
                (inLabel.read() << 16) |
                (inLabel.read() << 8) |
                (inLabel.read());
            int numberOfLabels = (inLabel.read() << 24) |
                (inLabel.read() << 16) |
                (inLabel.read() << 8) |
                (inLabel.read());

            BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
            int numberOfPixels = numberOfRows * numberOfColumns;
            int[] imgPixels = new int[numberOfPixels];

            for(int i = 0; i < numberOfImages; i++) {
                if (i == 1) {
                    break;
                }

                if(i % 100 == 0) {
                    System.out.println("Number of images extracted: " + i);
                }

                for(int p = 0; p < numberOfPixels; p++) {
                    int b = inImage.read();
                    //System.out.print(b + " ");
                    int gray = 255 - b;
                    imgPixels[p] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                    //imgPixels[p] = gray * 0x00010101;
                }

                image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);

                int label = inLabel.read();

                hashMap[label]++;
                File outputfile = new File(basePath + label + "_0" + hashMap[label] + ".png");

                ImageIO.write(image, "png", outputfile);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inImage != null) {
                try {
                    inImage.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (inLabel != null) {
                try {
                    inLabel.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
