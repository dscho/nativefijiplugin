::Script to build the Fiji plugin from a dll file
::Please read http://fiji.sc/wiki/index.php/Developing_Fiji#Adding_plugins

set buildMode=Debug

:: Change to the directory this batch script resides.
cd "%~dp0"

::set java_home="C:\Program Files\Java\jdk1.7.0_15\bin"
set java_home="C:\Program Files\Java\jdk1.6.0_24\bin"

::you can also use the jdk 1.7, you'll have to use -source 1.6 -target 1.6 flags
::set java_compiler="C:\Program Files\Java\jdk1.7.0_15\bin\javac.exe"

::or use the same version that ImageJ uses
set java_compiler="C:\Program Files\Java\jdk1.6.0_24\bin\javac.exe"

::or use directly the bundled javac from Fiji
::set java_compiler="C:\Users\monso\code\fiji64\Fiji.app\ImageJ-win64.exe" --javac

set java_src_root=ch/epfl/cvlab/nativePlugin

set fijijars=C:\Users\monso\code\fiji64\Fiji.app\jars

%java_compiler% -cp %fijijars%\*;.;lib -d build %java_src_root%/Native_Plugin.java %java_src_root%/NativeWrapper.java

%java_home%\javah.exe -jni -d include/jniheaders -classpath %fijijars%\*;build/ ch.epfl.cvlab.nativePlugin.NativeWrapper

::for some reason, fiji expects the plugins config to be named plugins.config otherwise is not recognized by fiji
copy pluginMenuName\NativePlugin.config build\plugins.config

mkdir build\finalPlugin

%java_home%\jar.exe -cvf build/finalPlugin/Native_Plugin.jar build/plugins.config -C build ch -C build/%buildMode%/ win64/NativeWrapper.dll
