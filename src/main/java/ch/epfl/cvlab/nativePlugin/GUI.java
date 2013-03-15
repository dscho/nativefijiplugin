

package ch.epfl.cvlab.nativePlugin;

import java.awt.event.*;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.gui.ImageCanvas;
import ij.measure.Calibration;

public class GUI implements MouseListener, MouseMotionListener{

    private GenericDialog dialog;
    Calibration calib;
    ImageCanvas imagecanvas;
    
    static String title = "blobs";
    static int param1 = 4;
    static double param2 = 10.3;

    public GUI(ImagePlus image){
        //TODO write a proper GUI, with parameters that are actually passed through the filters
        dialog = new GenericDialog("Train/Run temporary dialog");
        dialog.addStringField("Example File name (inactive): ", title);
        dialog.addNumericField("ExampleParameter1 (inactive): ", param1, 0);
        dialog.addNumericField("ExampleParameter2 (inactive): ", param2, 0);
        dialog.addMessage("Hit Ok to run the Filter");
        
        ImageCanvas imagecanvas = image.getCanvas();
        imagecanvas.addMouseListener(this);
        imagecanvas.addMouseMotionListener(this);
    }

    public void configGUI(ImagePlus image){

        imagecanvas = image.getCanvas();

    }

    public void show(){
        dialog.showDialog();    
    }

    public boolean wasCanceled(){
        return dialog.wasCanceled();
    }


    public void showImage(ImagePlus imageOut) {
        
        //TODO check info and calibration
        imageOut.show();
        
    }

    
    @Override
    public void mouseDragged(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

}
