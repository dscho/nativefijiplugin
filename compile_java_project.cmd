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

set java_src_root=ch/epfl/cvlab/helloPlugin

set fijijars=C:\Users\monso\code\fiji64\Fiji.app\jars

%java_compiler% -cp %fijijars%\*;.;lib -d build %java_src_root%/HelloWorld_Plugin.java %java_src_root%/Hello.java

