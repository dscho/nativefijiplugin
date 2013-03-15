#ifndef HELLOWORLD_H
#define HELLOWORLD_H
#include <iostream>

//jni interface auto-generated header
#include "ch_epfl_cvlab_nativePlugin_NativeWrapper.h"

#include "itkImageFileWriter.h"
#include "itkMinimumMaximumImageCalculator.h"
#include "itkShiftScaleImageFilter.h"
#include "itkNumericTraits.h"
#include "itkExpImageFilter.h"
#include "itkRGBPixel.h"

#include "itkImageDuplicator.h"

#include "NativePlugin_Export.h"

#include "Canny.h"

class NativePlugin_EXPORT NativeInterface {

  public:

    void sayHello();

    // Algorithm code is here!
    template<class TInputPixel, unsigned int VDimension>
    typename itk::Image<TInputPixel,VDimension>::Pointer
    Execute(typename itk::Image<TInputPixel,VDimension>::Pointer inputImage);

    template<class TInputPixel, unsigned int VDimension>
    typename itk::Image<TInputPixel,VDimension>::Pointer
    Copy(typename itk::Image<TInputPixel,VDimension>::Pointer inputImage);


};

#endif
