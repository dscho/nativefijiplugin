#include "Canny.h"

void Canny::filterImage(const itk::Image< unsigned char, 2 > * imagein, itk::Image< unsigned char, 2 >::Pointer & imageout){

  typedef unsigned char PixelType;
  typedef itk::Image< PixelType, 2 > ImageType;

  typedef itk::Image< double, 2 > RealImageType;
  typedef itk::ImageFileWriter< ImageType > WriterType;

  //canny requires cast to double pixel type
  typedef itk::CastImageFilter< ImageType,RealImageType> CastToRealFilterType;
  typedef itk::RescaleIntensityImageFilter< RealImageType, ImageType > RescaleFilterType;
  typedef itk::MeanImageFilter< ImageType, ImageType > DenoiseFilterType;

  typedef itk::CannyEdgeDetectionImageFilter< RealImageType,RealImageType > CannyFilterType;

  CastToRealFilterType::Pointer castToRealFilter = CastToRealFilterType::New();
  RescaleFilterType::Pointer rescaleFilter = RescaleFilterType::New();
  CannyFilterType::Pointer cannyFilter = CannyFilterType::New();
  
  DenoiseFilterType::Pointer denoiseFilter = DenoiseFilterType::New();

  //noise reduction
  ImageType::SizeType indexRadius;
  indexRadius[0] = 5; // radius along x
  indexRadius[1] = 5; // radius along y
  denoiseFilter->SetRadius( indexRadius );

  denoiseFilter->SetInput( imagein );

  castToRealFilter->SetInput( denoiseFilter->GetOutput() );

  rescaleFilter->SetOutputMinimum(   0 );
  rescaleFilter->SetOutputMaximum( 255 );

  cannyFilter->SetInput( castToRealFilter->GetOutput() );
  cannyFilter->SetVariance( 20.0 );
  cannyFilter->SetUpperThreshold( 0.0 );
  cannyFilter->SetLowerThreshold( 200.0 );

  rescaleFilter->SetInput( cannyFilter->GetOutput() );

  rescaleFilter->Update();

  imageout = rescaleFilter->GetOutput();
  //just to be safe no further changes will occur
  imageout->DisconnectPipeline();
 
}

