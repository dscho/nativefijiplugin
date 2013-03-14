
== compilation, building and packaging information ==

I actually used create_plugin_win.cmd for compiling and packaging. The headers were obtained with javah as stated below

command would be (replace the fiji_path accordingly):
"C:\Program Files\Java\jdk1.6.0_24\bin\javac.exe" -cp C:\Users\monso\code\fiji64\Fiji.app\jars\*;. -d build ch/epfl/cvlab/helloPlugin/HelloWorld_Plugin.java ch/epfl/cvlab/helloPlugin/Hello.java

"C:\Program Files\Java\jdk1.6.0_24\bin\javah.exe" -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.helloPlugin.Hello


javah -jni -d include/jniheaders -classpath C:\Users\monso\code\fiji64\Fiji.app\jars\*;build/ ch.epfl.cvlab.helloPlugin.Hello

To run the example using the JNI interface (no fiji):
java -Djava.library.path=build\Debug -cp build\ ch.epfl.cvlab.examples.SayHelloExample

[todo: packaging the example on a jar:
jar -cvfe build/HelloWorldExample.jar -C build ch -C build fiji -C build/Debug/ win64/Hello.dll
]

"C:\Program Files\Java\jdk1.6.0_24\bin\jar.exe" -cvf build/HelloWorld_Plugin.jar build/plugins.config -C build ch -C build/Debug/ win64/Hello.dll


Test if x64 or 32 with
dumpbin /headers *.dll

cmake -G "Visual Studio 10 Win64" .. -> builds 64 
cmake -G "Visual Studio 10" .. -> builds 32 (default)

== lessons I learned with this example ==

not using the LibraryLoader written by Johannes Schindelin and instead a sole System.loadlibrary("Hello") is way too system dependent and risky, his library loader makes it more portable but keep in mind:

that the library assumes the .dll will bi under win64/ or win32/ directory, this could be changed in the java class

that in order to use .dll packed in a jar they have to be unpacked to somewhere. The libraryLoader creates a temp directory. That's the first place you should look if you get an UnsatisfiedLinkError exeption, since it's the weak point of JNI, it won't tell you until you run into it (same thing aplies to functions not present in the dll, java only noteices it on runtime as it is now.)

The library loaded (win 32/64 or mac or unix) is the one matchin the architecture told by Fiji.

the symbols (to see if the functions are there on the dll) can be read with:

dumpbin /symbols Hello.dll

(VCVARS32 has to be ran before dumpbin to get everything on the PATH)

and architecture of a dll can be learned with

dumpbin /headers Hello.dll
