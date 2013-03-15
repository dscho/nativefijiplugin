[windows]
== compilation, building and packaging information ==

= quick peek =

> create_plugin_win.cmd

> javah -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.nativePlugin.NativeWrapper

> cd build

> cmake -G "Visual Studio 10 Win64" ..

> MSBuild.exe NativeFijiPlugin.sln

> create_plugin_win.cmd

== verbose ==

I actually used create_plugin_win.cmd for compiling and packaging. The headers were obtained with javah as stated below

command would be (replace the fiji_path accordingly):
C:\Users\monso\workspace\HelloPlugin\src>"C:\Program Files\Java\jdk1.6.0_24\bin\javac.exe" -cp C:\Users\monso\code\fiji64\Fiji.app\jars\*;.;lib -d build ch/epfl/cvlab/nativePlugin/Native_Plugin.java ch/epfl/cvlab/nativePlugin/NativeWrapper.java

javah -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.nativePlugin.NativeWrapper

"C:\Program Files\Java\jdk1.6.0_24\bin\javah.exe" -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.helloPlugin.Hello

javah -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.helloPlugin.Hello

"C:\Program Files\Java\jdk1.6.0_24\bin\jar.exe" -cvf build/HelloWorld_Plugin.jar build/plugins.config -C build ch -C build/Debug/ win64/Hello.dll

cmake -G "Visual Studio 10 Win64" .. -> builds 64 
cmake -G "Visual Studio 10" .. -> builds 32 (default)

[unix]
== compilation, building and packaging information ==

= quick peek =

$ javac -cp ~/code/Fiji.app/jars/*:.:lib -d build ch/epfl/cvlab/nativePlugin/Native_Plugin.java ch/epfl/cvlab/nativePlugin/NativeWrapper.java


$ javah -jni -d include/jniheaders -classpath ~/code/Fiji.app/jars/*:build/ ch.epfl.cvlab.nativePlugin.NativeWrapper

$ cd build

$ cmake ..

$ make

$ jar -cvf build/finalPlugin/Native_Plugin.jar build/plugins.config -C build ch -C build/ linux64/libNativeWrapper.so

== verbose ==

I actually used create_plugin_win.cmd for compiling and packaging. The headers were obtained with javah as stated below

command would be (replace the fiji_path accordingly):
C:\Users\monso\workspace\HelloPlugin\src>"C:\Program Files\Java\jdk1.6.0_24\bin\javac.exe" -cp C:\Users\monso\code\fiji64\Fiji.app\jars\*;.;lib -d build ch/epfl/cvlab/nativePlugin/Native_Plugin.java ch/epfl/cvlab/nativePlugin/NativeWrapper.java

javah -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.nativePlugin.NativeWrapper

"C:\Program Files\Java\jdk1.6.0_24\bin\javah.exe" -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.helloPlugin.Hello

javah -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.helloPlugin.Hello

"C:\Program Files\Java\jdk1.6.0_24\bin\jar.exe" -cvf build/HelloWorld_Plugin.jar build/plugins.config -C build ch -C build/Debug/ win64/Hello.dll

cmake -G "Visual Studio 10 Win64" .. -> builds 64 
cmake -G "Visual Studio 10" .. -> builds 32 (default)
