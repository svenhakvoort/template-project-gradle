//package example.marvin;
//
//import javax.swing.*;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import java.awt.*;
//import java.util.Vector;
//
//public class DetectMotionRegions extends JFrame implements Runnable {
//
//    private JPanel panelSlider;
//    private JSlider sliderSensibility;
//    private JLabel labelSlider;
//
//    private MarvinVideoInterface videoInterface;
//    private MarvinImagePanel videoPanel;
//
//    private MarvinImage imageIn, imageOut, imageLastFrame;
//
//    private int imageWidth, imageHeight;
//
//    private MarvinAttributes attributesOut;
//
//    private Thread thread;
//
//    private MarvinImagePlugin pluginMotionRegions;
//
//    private int sensibility = 30;
//
//    public DetectMotionRegions() {
//        try {
//            videoPanel = new MarvinImagePanel();
//            videoInterface = new MarvinJavaCVAdapter();
//            videoInterface.connect(0);
//
//            imageWidth = videoInterface.getImageWidth();
//            imageHeight = videoInterface.getImageHeight();
//
//            imageOut = new MarvinImage(imageWidth, imageHeight);
//            imageLastFrame = new MarvinImage(imageWidth, imageHeight);
//
//            attributesOut = new MarvinAttributes(null);
//
//            pluginMotionRegions = new DifferentRegions();
//            pluginMotionRegions.load();
//            pluginMotionRegions.setAttribute("comparisonImage", imageLastFrame);
//
//            loadGUI();
//
//            thread = new Thread(this);
//            thread.start();
//        } catch (MarvinVideoInterfaceException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        DetectMotionRegions dmr = new DetectMotionRegions();
//        dmr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//
//    private void loadGUI() {
//        setTitle("Video Sample - Detect Motion Regions");
//
//        sliderSensibility = new JSlider(JSlider.HORIZONTAL, 0, 60, 30);
//        sliderSensibility.setMinorTickSpacing(2);
//        sliderSensibility.setPaintTicks(true);
//        sliderSensibility.addChangeListener(new SliderHandler());
//
//        labelSlider = new JLabel("Sensibility");
//
//        panelSlider = new JPanel();
//        panelSlider.add(labelSlider);
//        panelSlider.add(sliderSensibility);
//
//        Container container = getContentPane();
//        container.setLayout(new BorderLayout());
//        container.add(videoPanel, BorderLayout.NORTH);
//        container.add(panelSlider, BorderLayout.SOUTH);
//
//        setSize(imageWidth + 10, imageHeight + 100);
//        setVisible(true);
//    }
//
//    public void run() {
//        boolean first = true;
//
//        int[] tempRect;
//
//        try {
//            while (true) {
//                imageIn = videoInterface.getFrame();
//                MarvinImage.copyColorArray(imageIn, imageOut);
//
//                if (!first) {
//                    pluginMotionRegions.setAttribute("comparisonImage", imageLastFrame);
//                    pluginMotionRegions.setAttribute("colorRange", sensibility);
//
//                    MarvinImage.copyColorArray(imageIn, imageOut);
//                    pluginMotionRegions.process(imageIn, imageOut, attributesOut, MarvinImageMask.NULL_MASK, false);
//
//                    Vector<int[]> regions = (Vector<int[]>) attributesOut.get("regions");
//
//                    for (int i = 0; i < regions.size(); i++) {
//                        tempRect = regions.get(i);
//                        imageOut.drawRect(tempRect[0], tempRect[1], tempRect[2] - tempRect[0], tempRect[3] - tempRect[1], Color.red);
//                        imageOut.drawRect(tempRect[0] + 1, tempRect[1] + 1, (tempRect[2] - tempRect[0]) - 2, (tempRect[3] - tempRect[1]) - 2, Color.red);
//                    }
//
//                    videoPanel.setImage(imageOut);
//                    MarvinImage.copyColorArray(imageIn, imageLastFrame);
//                }
//
//                first = false;
//            }
//        } catch (MarvinVideoInterfaceException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private class SliderHandler implements ChangeListener {
//        public void stateChanged(ChangeEvent a_event) {
//            sensibility = (60 - sliderSensibility.getValue());
//        }
//    }
//}