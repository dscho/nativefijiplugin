#include <iostream>
#include "Canny.h"


int main(int argc, char* argv[])
{

	typedef unsigned char PixelType;
	typedef itk::Image< PixelType, 2 > ImageType;
	typedef itk::Image< double, 2 > RealImageType;
	typedef itk::ImageFileWriter< ImageType > WriterType;

  //canny requires cast to double pixel type
  typedef itk::CastImageFilter< ImageType,RealImageType> CastToRealFilterType;
  typedef itk::RescaleIntensityImageFilter< RealImageType, ImageType > RescaleFilterType;
  typedef itk::MeanImageFilter< ImageType, ImageType > DenoiseFilterType;

  typedef itk::CannyEdgeDetectionImageFilter< RealImageType,RealImageType > CannyFilterType;


  WriterType::Pointer writer = WriterType::New();

  writer->SetFileName( "output.png" );

    std::cout << "fill image" << std::endl;
  Canny cannyFilter;

  ImageType::Pointer imageIn = ImageType::New();

    ImageType::SizeType size;
  	ImageType::RegionType region;
  	size[0] = 128;
  	size[1] = 128;
  	region.SetSize( size );
  	imageIn->SetRegions(region);
  	imageIn->Allocate();

  std::cout << "fill image" << std::endl;

  typedef itk::ImageRegionIterator<ImageType> Iterator;
  Iterator it(imageIn,region);
  PixelType pixelValue = 0;
  for(it.GoToBegin(); !it.IsAtEnd(); ++it, ++pixelValue)
  {
	  it.Set(pixelValue);
  }

  std::cout << "filter" << std::endl;

  ImageType::Pointer outputImage;
  cannyFilter.filterImage(imageIn, outputImage);

  writer->SetInput(outputImage);

  try
    {
    writer->Update();
    }
  catch( itk::ExceptionObject & err )
    {
    std::cout << "ExceptionObject caught !" << std::endl;
    std::cout << err << std::endl;
    return EXIT_FAILURE;
    }

  std::cout << "ITK Done !" << std::endl;

  return 0;
}




