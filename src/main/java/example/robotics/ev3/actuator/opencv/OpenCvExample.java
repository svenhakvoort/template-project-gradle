package example.robotics.ev3.actuator.opencv;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static example.robotics.ev3.actuator.opencv.ImageUtils.matToBufferedImage;

public class OpenCvExample {

    public static Logger LOGGER = LoggerFactory.getLogger(OpenCvExample.class);

    private BufferedImage image;
    private Mat imageMat;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private VideoCapture camera;
    private ImageViewer viewer;

    private CascadeClassifier faceCascade;
    private CascadeClassifier smileCascade;
    private int absoluteFaceSize;

    public OpenCvExample() {
        camera = new VideoCapture(0);
        imageMat = new Mat();
        this.faceCascade = new CascadeClassifier();
        this.smileCascade = new CascadeClassifier();
        String fileName = "haarcascade_frontalface_default.xml";
        String fileName2 = "haarcascade_smile.xml";
        this.faceCascade.load("D:\\Users\\Sven\\Documents\\prgrm\\template-project-gradle\\src\\main\\resources\\haarcascades\\"+fileName);
        this.smileCascade.load("D:\\Users\\Sven\\Documents\\prgrm\\template-project-gradle\\src\\main\\resources\\haarcascades\\"+fileName2);
        this.absoluteFaceSize = 0;

        Runnable frameGrabber = () -> {
            try {
                if (camera.read(imageMat)) {
                    detectAndDisplay(imageMat);
                    image = matToBufferedImage(imageMat);

                    if (viewer == null) {
                        viewer = new ImageViewer(image);
                    } else {
                        viewer.repaint(image);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        this.executorService.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
    }

    public static void main(final String[] args) throws Exception {
        OpenCV.loadShared();

        OpenCvExample example = new OpenCvExample();

        while (true){}
    }

    private void detectAndDisplay(Mat frame)
    {
        try {
            MatOfRect faces = new MatOfRect();
            Mat grayFrame = new Mat();

            // convert the frame in gray scale
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
            // equalize the frame histogram to improve the result
            Imgproc.equalizeHist(grayFrame, grayFrame);

            // compute minimum face size (20% of the frame height, in our case)
            if (this.absoluteFaceSize == 0) {
                int height = grayFrame.rows();
                if (Math.round(height * 0.2f) > 0) {
                    this.absoluteFaceSize = Math.round(height * 0.2f);
                }
            }

            // detect faces
            this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, Objdetect.CASCADE_SCALE_IMAGE,
                    new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

            // each rectangle in faces is a face: draw them!
            Rect[] facesArray = faces.toArray();
            for (int i = 0; i < facesArray.length; i++) {
                Mat faceFrame = grayFrame.submat(facesArray[i]);
                MatOfRect smiles = new MatOfRect();

                this.smileCascade.detectMultiScale(faceFrame, smiles, 1.3, 5);
                Rect[] smilesArray = smiles.toArray();
                boolean isSmiling = false;

                for (int x = 0; x < smilesArray.length; x++) {
                    isSmiling = true;
                    break;
                }

                Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
                String text = isSmiling ? "SMILING " : "";
                text += "HUMAN DETECTED";

                Imgproc.putText(frame, text, facesArray[i].tl(), Imgproc.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 0, 255, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
