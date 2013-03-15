
package ch.epfl.cvlab.nativePlugin;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.*;

public class Native_Plugin implements PlugIn {

    private NativeWrapper hello;
    private GUI gui;
    private ImageHandler imgHandler;

    public Native_Plugin() {
        hello = new NativeWrapper();
        imgHandler = new ImageHandler();
    }

    public void run(String ignored) {
        
        try {
            imgHandler.getImages();
            
            gui = new GUI(imgHandler.getSampleImage());
            
            gui.show();
            
            ImagePlus imageOut = null;
            
            //Example loading an image
            ImagePlus image = imgHandler.getSampleImage();
            
            hello.train(image);
            
            imageOut = hello.run(image);
            
            gui.showImage(imageOut);
            
            if( gui.wasCanceled() ) return;
            
        } catch(ImageNotFoundException exp){
            IJ.error(exp.getMessage() + ": " + exp.toString());
            return;
        } catch(ImageFormatException exp){
            IJ.error(exp.getMessage());
            return;
        } catch (Exception exp) {
            exp.printStackTrace();
            IJ.error(exp.getMessage());
            return;
        }        
    }
    
}

