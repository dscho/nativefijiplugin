#include "NativeInterface.h"

// Algorithm code is here!
template<class TInputPixel, unsigned int VDimension>
typename itk::Image<TInputPixel,VDimension>::Pointer
Execute(typename itk::Image<TInputPixel,VDimension>::Pointer inputImage)
{
    // Define the dimension of the images
    const unsigned int Dimension = VDimension;

    typedef itk::Image<TInputPixel, Dimension> ImageType;

    //doSomething here
    Canny cannyFilter;

    inputImage->Update();

	  ImageType::Pointer outputImage;
    cannyFilter.filterImage(inputImage, outputImage);

    return outputImage;
}

template<class TInputPixel, unsigned int VDimension>
typename itk::Image<TInputPixel,VDimension>::Pointer
Copy(typename itk::Image<TInputPixel,VDimension>::Pointer inputImage)
{

  typedef itk::Image<TInputPixel, VDimension> ImageType;
	typedef typename itk::ImageDuplicator< ImageType > DuplicatorType;
	typename DuplicatorType::Pointer duplicator = DuplicatorType::New();
	duplicator->SetInputImage(inputImage);
	duplicator->Update();
	GrayImageType::Pointer imageOut = duplicator->GetOutput();

	return imageOut;

}

JNIEXPORT void JNICALL Java_ch_epfl_cvlab_nativePlugin_NativeWrapper_sayHello
  (JNIEnv *env, jobject ignored){

    NativeInterface *helloobj = new NativeInterface();
    helloobj->sayHello();
  
}

JNIEXPORT jint JNICALL Java_ch_epfl_cvlab_nativePlugin_NativeWrapper_trainNative
  (JNIEnv *env, jobject ignored, jbyteArray jimageIn, jint width, jint height, jint numSlices, jboolean isColorImage,
		  jdouble widthpix, jdouble heightpix, jdouble depthpix){

	return 0;
}

JNIEXPORT jint JNICALL Java_ch_epfl_cvlab_nativePlugin_NativeWrapper_runNative
  (JNIEnv *env, jobject ignored, jbyteArray jimageIn, jbyteArray jimageOut,
                jint width, jint height, jdouble widthpix, jdouble heightpix){

  jboolean isCopy;
  jbyte *jimageInBytePtr    = (jbyte *) env->GetByteArrayElements(jimageIn,&isCopy);
  jbyte *jimageOutBytePtr = (jbyte *) env->GetByteArrayElements(jimageOut,&isCopy);

  if( ! jimageInBytePtr )
  {
      std::cerr << "Failed getting byte array from JNI's env" << std::endl;
      //TODO pass exceptions to JNI instead of error ints
      return -1;
  }

  //TODO check/support color image
  //allocate space for gray image
  typedef unsigned char GrayPixelType;
  typedef itk::Image<GrayPixelType, 2> GrayImageType;
  GrayImageType::Pointer imageIn = GrayImageType::New();
  GrayImageType::SizeType size;
  GrayImageType::RegionType region;
  size[0] = width;
  size[1] = height;

  region.SetSize( size );
  imageIn->SetRegions(region);
  imageIn->Allocate();

  //Copy image from JNI (byte[]) to itk::Image
  typedef itk::ImageRegionIterator<GrayImageType> Iterator;
  Iterator it(imageIn,region);
  it.GoToBegin();
  for(int i = 0; !it.IsAtEnd(); ++it, ++i)
  {
    it.Set(jimageInBytePtr[i]);
  }

  //Do something with it
  GrayImageType::Pointer imageOut = Execute<GrayPixelType, 2>(imageIn);

  //Copy result to byte []
  Iterator itOut(imageOut,region);
	itOut.GoToBegin();
	for(int i=0; !itOut.IsAtEnd(); ++i, ++itOut)
	{
		jimageOutBytePtr[i] = itOut.Value();
	}

	//Inform VM to Copy back the content and free the BytePtr
	//http://docs.oracle.com/javase/1.5.0/docs/guide/jni/spec/functions.html#wp17314
	env->ReleaseByteArrayElements(jimageIn, jimageInBytePtr,0);
	env->ReleaseByteArrayElements(jimageOut, jimageOutBytePtr,0);

	return 0;

}

void NativeInterface::sayHello(){

    std::cout << "Object: Hello World!" << std::endl;

}

