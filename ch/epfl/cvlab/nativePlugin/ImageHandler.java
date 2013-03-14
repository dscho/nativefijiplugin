
package ch.epfl.cvlab.nativePlugin;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;


public class ImageHandler {
    
    private static final int MAX_PIXEL_VALUE = 255;
    private ImagePlus sampleImage;
    
    protected ImagePlus getSampleImage(){
        
        return sampleImage;
    }
    
    protected static void invertImage(ImagePlus imageIn){
        int depth = imageIn.getImageStackSize();
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        if((imageIn.getType() == ImagePlus.GRAY8) || (imageIn.getType() == ImagePlus.COLOR_256))
        {
            byte [] slicePixelData = new byte[width*height];
            for(int i=0; i < depth; i++)
            {
                ByteProcessor bpStack = (ByteProcessor)imageIn.getImageStack().getProcessor(i+1);
                slicePixelData = (byte [])bpStack.getPixels();
                for(int j=0; j < width*height; j++)
                {
                    slicePixelData[j] = (byte) (MAX_PIXEL_VALUE - slicePixelData[j]);
                }
            }
        }
        else // imageType == ImagePlus.COLOR_RGB
        {

            
            int [] slicePixelData;
            for(int i=0; i < depth; i++){
                ByteProcessor byteProcessorStack = (ByteProcessor) imageIn.getImageStack().getProcessor(i+1);
                slicePixelData = (int []) byteProcessorStack.getPixels();
                for(int j=0; j < height*width;j++){
                    
                    // ImageJ RGB pixel type is an integer container.
                    int value = slicePixelData[j];
                    int red = (value >> 16) & 0xff;
                    int green = (value >> 8) & 0xff;
                    int blue = value & 0xff;
                    
                    red = MAX_PIXEL_VALUE - red;
                    green = MAX_PIXEL_VALUE - green;
                    blue = MAX_PIXEL_VALUE - blue;
                    
                    value = red << 16 + green << 8 + blue;
                    
                    slicePixelData[j] = (byte)value;
                }
                imageIn.getImageStack().getProcessor(i+1).setPixels(slicePixelData);
                
            }
        }
    
    }
    
    //TODO template this if I want to continue using it
    protected static void invertImageFloat(ImagePlus imageIn){
        int depth = imageIn.getImageStackSize();
        int width = imageIn.getWidth();
        int height = imageIn.getHeight();
        if((imageIn.getType() == ImagePlus.GRAY8) || (imageIn.getType() == ImagePlus.COLOR_256))
        {
            byte [] slicePixelData = new byte[width*height];
            for(int i=0; i < depth; i++)
            {
                FloatProcessor bpStack = (FloatProcessor)imageIn.getImageStack().getProcessor(i+1);
                slicePixelData = (byte [])bpStack.getPixels();
                for(int j=0; j < width*height; j++)
                {
                    slicePixelData[j] = (byte) (MAX_PIXEL_VALUE - slicePixelData[j]);
                }
            }
        }
        else // imageType == ImagePlus.COLOR_RGB
        {
            //TODO fix this if we're going to use it
            IJ.error("Color image invert not supported");

        }
    
    }
    
    protected static byte [] imageToByte(ImagePlus image) throws Exception{
    
        int depth = image.getImageStack().getSize();
        int width = image.getWidth(); 
        int height = image.getHeight(); 
        
        byte [] pixelData;
        
        System.out.println("image dim: " + width + " " + height + " " + depth);
        
        // Possible types are : GRAY8 COLOR_256 and COLOR_RGB.
        int imageType = image.getType();
        if( ! ((imageType == ImagePlus.GRAY8) || 
                     (imageType == ImagePlus.COLOR_256) || 
                     (imageType == ImagePlus.COLOR_RGB)) )
        {
            IJ.error("Not an 8-bit gray-scale or color image.");
            //TODO custom exception
            throw new Exception("Not an 8-bit gray-scale or color image.");
        }
        
        if( ((long)width)*((long)height)*((long)depth) > ((long)Integer.MAX_VALUE) )
        {
            IJ.error("Input image is too large.");
            throw new Exception("Input image is too large.");
        }
        
        if((imageType == ImagePlus.GRAY8) || (imageType == ImagePlus.COLOR_256))
        {
            byte [] SlicePixelData = new byte[width*height];
            pixelData = new byte[width*height*depth];
            for(int i=0; i < depth; i++)
            {
                ByteProcessor bpStack = (ByteProcessor) image.getImageStack().getProcessor(i+1);
                SlicePixelData = (byte [])bpStack.getPixels();
                for(int j=0; j < width*height; j++)
                {
                    pixelData[width*height*i + j] = SlicePixelData[j];
                }
            }
        }
        else // imageType == ImagePlus.COLOR_RGB
        {
            if( ((long)width)*((long)height)*((long)depth*((long)3)) > ((long)Integer.MAX_VALUE) )
            {
                IJ.error("Input image is too large.");
                throw new Exception("Input image is too large.");
            }
            
            int [] SlicePixelData = new int[width*height];
            pixelData = new byte[width*height*depth*3]; // rgb slice
            for(int i=0; i < depth; i++)
            {
                ColorProcessor bpStack = (ColorProcessor) image.getImageStack().getProcessor(i+1);
                SlicePixelData = (int [])bpStack.getPixels();
                for(int j=0; j < width*height; j++)
                {
                    int ind = 3*(width*height*i + j);
                    
                    // ImageJ RGB pixel type is an integer container.
                    int value = SlicePixelData[j];
                    int red = (value >> 16) & 0xff;
                    int green = (value >> 8) & 0xff;
                    int blue = value & 0xff;
                    
                    pixelData[ind] = (byte)red;
                    pixelData[ind+1] = (byte)green;
                    pixelData[ind+2] = (byte)blue;
                }
            }
        }
        
        System.out.println("pixeldata[100] =" + pixelData[100]);
        return pixelData;
    
    }
    
    public static ImagePlus byteToImage(byte[] pixelData, int width, int height, int depth) {
        
        ImageStack newstack = new ImageStack(width, height);
        
        for(int i=0;i<depth;i++)
        {
            
            byte[] pix = new byte[width*height]; // get your bytes somehow
            for(int j=0;j<width*height;j++)
            {
                pix[j] = pixelData[width*height*i + j];
            }
            
            ByteProcessor proc = new ByteProcessor(width, height, pix, null);
            newstack.addSlice("", proc);
        }   
        
        ImagePlus outputImage = new ImagePlus("output image", newstack);
                
        System.out.println("created outputImage");
        
        return outputImage;
        
    }
    
    public static ImagePlus floatToImage(float[] pixelData, int width, int height, int depth) {
        
        ImageStack newstack = new ImageStack(width, height);
        
        for(int i=0;i<depth;i++)
        {
            
            float[] pix = new float[width*height]; // get your bytes somehow
            for(int j=0;j<width*height;j++)
            {
                pix[j] = pixelData[width*height*i + j];
            }
            
            FloatProcessor proc = new FloatProcessor(width, height, pix, null);
            newstack.addSlice("", proc);
        }   
        
        ImagePlus outputImage = new ImagePlus("output image", newstack);
                
        System.out.println("created outputImage");
        
        return outputImage;
        
    }
    
    protected void getImages() throws ImageNotFoundException, ImageFormatException {
        final ImagePlus image = WindowManager.getCurrentImage();
        sampleImage = WindowManager.getCurrentImage();
        
        if( image == null )
        {
            throw new ImageNotFoundException("No image is open");
        }
        
        ImageStack stack = image.getImageStack();
        
        // Possible types are : GRAY8 COLOR_256 and COLOR_RGB.
        int imageType = image.getType();
        if( ! ((imageType == ImagePlus.GRAY8) || 
               (imageType == ImagePlus.COLOR_256) || 
               (imageType == ImagePlus.COLOR_RGB)) )
        {
            throw new ImageFormatException("Image format incorrect: Not an 8-bit gray-scale or color image.");
        }
        
        int depth = stack.getSize(); 
        int width = image.getWidth(); 
        int height = image.getHeight(); 
        
        if( ((long)width)*((long)height)*((long)depth) > ((long)Integer.MAX_VALUE) )
        {
            throw new ImageFormatException("Input image is too large.");
        }
                    
    }

    

}
