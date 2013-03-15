#ifndef CANNY_H
#define CANNY_H

#include <iostream>

#include "itkImage.h"
//image reading
#include "itkImageFileReader.h"
#include "itkImageFileWriter.h"

#include "itkRecursiveGaussianImageFilter.h"
#include "itkMeanImageFilter.h"

#include "itkCastImageFilter.h"

#include "itkCannyEdgeDetectionImageFilter.h"
#include "itkRescaleIntensityImageFilter.h"

class Canny{

    public:
		/**
		 * Filters the input image
		 * Canny filter with fixed parameters for illustrating purposes
		 * TODO template this
		 * TODO typedef this
		 */
    	void filterImage(const itk::Image< unsigned char, 2 > * imagein, itk::Image< unsigned char, 2 >::Pointer & imageout);
};
#endif
