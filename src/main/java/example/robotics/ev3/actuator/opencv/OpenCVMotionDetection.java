//package example.robotics.ev3.actuator.opencv;
//
//import nu.pattern.OpenCV;
//import org.opencv.core.*;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import static example.robotics.ev3.actuator.opencv.ImageUtils.matToBufferedImage;
//
//public class OpenCVMotionDetection {
//
//    public static Logger LOGGER = LoggerFactory.getLogger(OpenCVMotionDetection.class);
//
//    private BufferedImage image;
//    private Mat imageMat;
//    private Mat diffFrame = null;
//    private Mat tempFrame = null;
//    private boolean firstIteration = true;
//
//    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
//    private VideoCapture camera;
//    private ImageViewer viewer;
//
//    public OpenCVMotionDetection() {
//        camera = new VideoCapture(0);
//        imageMat = new Mat();
//
//        Runnable frameGrabber = () -> {
//            try {
//                if (camera.read(imageMat)) {
//                    Mat inputClone = imageMat.clone();
//                    Mat outerBox = new Mat(inputClone.size(), CvType.CV_8UC1);
//                    Imgproc.cvtColor(inputClone, outerBox, Imgproc.COLOR_BGR2GRAY);
//                    Imgproc.GaussianBlur(outerBox, outerBox, new Size(3, 3), 0);
//
//                    if (firstIteration) {
//                        tempFrame = new Mat(outerBox.size(), CvType.CV_8UC1);
//                        diffFrame = outerBox.clone();
//                        firstIteration = false;
//                    } else {
//                        Core.absdiff(outerBox, tempFrame, diffFrame);
//
//                        Imgproc.threshold(diffFrame, diffFrame, 20, 255, Imgproc.THRESH_BINARY);
//
//                        Mat kernel2 = Mat.ones(new Size(imageMat.width()/100, imageMat.height()/100), CvType.CV_8U);
//                        Imgproc.dilate(diffFrame, diffFrame, kernel2, new Point(0,0), 1);
//
//                        List<Rect> contours = detectContours(diffFrame);
////                        Imgproc.drawContours(imageMat, contours, -1, new Scalar(0, 255, 0), 2, Imgproc.LINE_AA);
//                        for (Rect contour : contours) {
//                            Imgproc.rectangle(imageMat, contour.br(), contour.tl(),
//                                    new Scalar(0, 255, 0), 1);
//                        }
//                    }
//
//                    image = matToBufferedImage(imageMat);
//
//                    if (viewer == null) {
//                        viewer = new ImageViewer(image);
//                    } else {
//                        viewer.repaint(image);
//                    }
//                    tempFrame = outerBox.clone();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//
//        this.executorService.scheduleAtFixedRate(frameGrabber, 0, 1000/30, TimeUnit.MILLISECONDS);
//    }
//
//    public static List<Rect> detectContours(Mat inputFrame) {
//        Mat hierarchy = new Mat();
//        Mat inputFrameClone = inputFrame.clone();
//        List<MatOfPoint> contours = new ArrayList<>();
//        Imgproc.findContours(inputFrameClone, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        double maxArea = 500;
//        List<Rect> rectList = new ArrayList<>();
//
//        for (Mat contour : contours) {
//            double contourarea = Imgproc.contourArea(contour);
//            if (contourarea > maxArea) {
//                Rect r = Imgproc.boundingRect(contour);
//                rectList.add(r);
//            }
//        }
//
//        hierarchy.release();
//
//        return rectList;
//    }
//
//    public static void main(final String[] args) throws Exception {
//        OpenCV.loadShared();
//
//        OpenCVMotionDetection example = new OpenCVMotionDetection();
//
//        while (true) {
//        }
//    }
//
//
//}
