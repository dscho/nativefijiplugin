#include <cstdlib>
#include "NativeInterface.h"

int main(int , char **){

    NativeInterface *helloobj = new NativeInterface();
    helloobj->sayHello();
    return EXIT_SUCCESS;

}
