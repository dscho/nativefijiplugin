

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

    public GUI(ImagePlus image){
        dialog = new GenericDialog("Train/Run");
        
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
