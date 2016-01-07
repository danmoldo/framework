package today.learnjava.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Development on 5/9/2015.
 */
@Component
public class AmazonS3Service {

    private static String bucketName     = "xxx-bucketname-xxx";

    public static final int MAX_WIDTH = 300;
    public static final Long FILE_TYPE_PROFILE_PIC = 1l;
    public static final Long FILE_TYPE_TOUR_PIC = 2l;

    public static Boolean storeFile (File file) {
        AmazonS3 s3client = new AmazonS3Client(new BasicAWSCredentials("xxx-AWS-key-xxx", "xxx-AWS-value-xxx"));
        try {
            System.out.println("Uploading a new object to S3 from a file\n");

            //Resize the photo so that it won't consume a lot of space on the cloud
            BufferedImage bufferedImage = ImageIO.read(file);
            BufferedImage resized = AmazonS3Service.getScaledInstance(bufferedImage, MAX_WIDTH, (MAX_WIDTH * bufferedImage.getHeight()) / bufferedImage.getWidth(), RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
            ImageIO.write(resized, "png", file);

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName, file.getName(), file);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(putObjectRequest);
            file.delete();
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return false;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }

    private static BufferedImage getScaledInstance(BufferedImage img,
                                                   int targetWidth,
                                                   int targetHeight,
                                                   Object hint,
                                                   boolean higherQuality) {

        if (img.getWidth() <= MAX_WIDTH) {
            return img;
        }

        int type = (img.getTransparency() == Transparency.OPAQUE) ?
                BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage)img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }

}
