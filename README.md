Check out and build ITK
=======================

```bash
git clone git://itk.org/ITK.git
cd ITK/
mkdir build
cd build/
ccmake ..
# Hit 'c' until you are offered 'g'
make
```

Build the native plugin with Maven
==================================

```bash
cd /path/to/nativefijiplugin/
mvn -Ditk.directory=/path/to/ITK/
```

Copy artifacts to Fiji's directory
==================================

```bash
cp target/*.jar ~/Fiji.app/plugins/
mkdir -p ~/Fiji.app/lib/$PLATFORM/
cp target/nar/*/*/*/*/*.so ~/Fiji.app/lib/$PLATFORM/
cp /path/to/ITK/build/lib/libITKCommon*.so.1 ~/Fiji.app/lib/$PLATFORM/
cp /path/to/ITK/build/lib/libitkvnl*.so.1 ~/Fiji.app/lib/$PLATFORM/
```
