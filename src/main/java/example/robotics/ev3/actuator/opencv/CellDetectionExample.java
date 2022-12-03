//package example.robotics.ev3.actuator.opencv;
//
//import nu.pattern.OpenCV;
//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.List;
//
//import static example.robotics.ev3.actuator.opencv.ImageUtils.matToBufferedImage;
//
//public class CellDetectionExample {
//
//    public static Logger LOGGER = LoggerFactory.getLogger(CellDetectionExample.class);
//
//    private BufferedImage image;
//    private Mat imageMat;
//    private ImageViewer viewer;
//
//    public CellDetectionExample() {
//        imageMat = Imgcodecs.imread("D:\\Users\\Sven\\Documents\\prgrm\\template-project-gradle\\src\\main\\resources\\cells.png");
//
//        Mat grayScaled = new Mat();
//        Imgproc.cvtColor(imageMat, grayScaled, Imgproc.COLOR_BGR2GRAY);
//
//        Mat floodfilled = Mat.zeros(imageMat.rows() + 2, imageMat.cols() + 2, CvType.CV_8UC1);
//        Point seed = new Point(imageMat.height()/2, imageMat.width()/2);
//        Imgproc.floodFill(grayScaled, floodfilled, seed, new Scalar(0, 0, 0), new Rect(), new Scalar(1), new Scalar(1));
//
//        Rect roi = new Rect(1, 1, grayScaled.cols() - 2, grayScaled.rows() - 2);
//        grayScaled = grayScaled.submat(roi);
//
////        Mat blur = new Mat();
////        Imgproc.GaussianBlur(grayScaled, blur, new Size(5,5), 0);
////
////        Mat canny = new Mat();
////        Imgproc.Canny(blur, canny, 30, 150, 3);
//
////        Mat dilated = new Mat();
////        Mat kernel = Mat.ones(new Size(1, 1), CvType.CV_8U);
////        Imgproc.dilate(canny, dilated, kernel, new Point(0,0), 0);
//
//        List<MatOfPoint> contours = new ArrayList<>();
//        Imgproc.findContours(grayScaled, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
//        System.out.println(contours.size());
//
//        Imgproc.drawContours(imageMat, contours, -1, new Scalar(0, 255, 0), 2);
//        image = matToBufferedImage(imageMat);
//
//        if (viewer == null) {
//            viewer = new ImageViewer(image);
//        } else {
//            viewer.repaint(image);
//        }
//    }
//
//    public static void main(final String[] args) throws Exception {
//        OpenCV.loadShared();
//
//        CellDetectionExample example = new CellDetectionExample();
//
//        while (true) {
//        }
//    }
//
//}
