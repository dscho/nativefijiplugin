
package ch.epfl.cvlab.nativePlugin;

import ij.IJ;
import ij.ImagePlus;
import ch.epfl.cvlab.libraryLoader.LibraryLoader;

public class NativeWrapper extends LibraryLoader {

   private native void sayHello();
   private native int trainNative(byte [] imageIn,
           int width, int height, int numSlices, boolean isColorImage,
           double widthpix, double heightpix, double depthpix);
   
   private native int runNative(byte [] imageIn, byte [] imageOut,
                  int width, int height, double widthpix, double heightpix);

   public void callSayHello(){

       sayHello();

   }
 
   public static void main(String[] args) {
      new NativeWrapper().sayHello();  
   }
   
   public void train(ImagePlus inputImage) {
       
       //byte [] pixelData = null;
       //ImageHandler.imageToByte(inputImage, pixelData);
     
       //IJ.showMessage("native train called");
       //this.trainNative(pixelData);
                
    }
    
   public ImagePlus run(ImagePlus inputImage) throws Exception {
       
    byte [] pixelDataOut = new byte[inputImage.getWidth() * inputImage.getHeight()];
    byte[] pixelData;

        pixelData = ImageHandler.imageToByte(inputImage);
       
       //IJ.showMessage("native run called");
       int result = this.runNative(pixelData, pixelDataOut, inputImage.getWidth(), inputImage.getHeight(),
                       inputImage.getCalibration().pixelWidth, inputImage.getCalibration().pixelHeight);
       
       if(result < 0){
           IJ.error("native run returned error: " + result);
           //TODO throw exception
           return null;
       }
       
       return ImageHandler.byteToImage(pixelDataOut, inputImage.getWidth(), inputImage.getHeight(), inputImage.getStackSize());
              
    }
    
}
