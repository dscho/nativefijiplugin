#required cmake 2.8.6 for Generate_export_header function
cmake_minimum_required ( VERSION 2.8.6 )

project(NativeFijiPlugin)

#TODO check if win32 or x64 and set the output directory accordingly
#(LibraryLoader.java expects a win32 and x64 version of the library)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY_DEBUG 	./Debug/win64)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY_RELEASE 	./Release/win64)

#required to link examples to shared libraries on Windows
#see http://www.cmake.org/Wiki/BuildingWinDLL
include (GenerateExportHeader)

# ---------- Java and JNI -------------#
FIND_PACKAGE(Java)
include(UseJava)
find_package(JNI)
if (JNI_FOUND)
    message (STATUS "JNI_INCLUDE_DIRS=${JNI_INCLUDE_DIRS}")
    message (STATUS "JNI_LIBRARIES=${JNI_LIBRARIES}")
endif()
#--------------------------------------#

if(WIN32)
#TODO Ask Engin about the Runtime information flag (do we need it to be GR-?
    SET(CMAKE_CXX_FLAGS "/DWIN32 /D_WINDOWS /W4 /Zi /EHsc /GR /MP /openmp /bigobj")
    add_definitions(-D_OPENMP -DLIBFFTWF33_EXPORTS) # Visual Studio 2005 and up supports OpenMP
endif(WIN32)

if(UNIX)
    SET(CMAKE_CXX_FLAGS "-Wall -O3 -fopenmp")
endif(UNIX)

#-----------ITK Libraries--------------#
find_package(ITK REQUIRED)
if(ITK_FOUND)
    include(${ITK_USE_FILE})
    message(STATUS "itk found with ITK_DIR = " ${ITK_DIR})
endif(ITK_FOUND)
#--------------------------------------#


#-- FooAlg wrapper --#
# After having created the JNI headers with javah, the wrapper can be implemented and compiled in c++
# use javah_interface.cmd to create the headers
#--------------------#

#Core cvlab code
include_directories( C:/Program\ Files\ (x86)/Java/jdk1.7.0_17/include/
                     C:/Program\ Files\ (x86)/Java/jdk1.7.0_17/include/win32/
                     ${JNI_INCLUDE_DIRS}
                     include/
                     include/jniheaders/)

file(GLOB jniheaders include/jniheaders/*.h) 

#adding the headers is required if we want to list headers on Visual Studio solution
#referral question 1167154 on stackoverflow.com
#library name has to be the same as the class name that uses the native calls if we use the
#libraryLoader provided by johannes
add_library(NativeWrapper SHARED ${jniheaders} src/NativeInterface.cpp src/Canny.cpp)
target_link_libraries(NativeWrapper ${JNI_LIBRARIES} ${ITK_LIBRARIES})

#required by visual studio, as symbols are not exported by default
#see http://www.cmake.org/Wiki/BuildingWinDLL
GENERATE_EXPORT_HEADER( NativeWrapper 
        BASE_NAME NativeWrapper
        EXPORT_MACRO_NAME NativePlugin_EXPORT
        EXPORT_FILE_NAME NativePlugin_Export.h
        STATIC_DEFINE NativePlugin_BUILT_AS_STATIC
)
#required if we don't want to put auto-generated HelloWorld_Export.h into and includes
#TODO maybe copy it to include is advisable
include_directories(build)

#TODO solve LNK2005 already defined error Hello.dll already contains canny.h include
#add_executable(cannyMe src/examples/cannyMe.cpp)
#target_link_libraries(cannyMe Hello)

#test code of a simple hello world native call
#add_executable(nativeCallTest src/examples/sayHello.cpp)
#target_link_libraries(nativeCallTest NativeWrapper)

