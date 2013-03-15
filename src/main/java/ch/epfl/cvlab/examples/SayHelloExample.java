package ch.epfl.cvlab.examples;

import ch.epfl.cvlab.nativePlugin.NativeWrapper;

public class SayHelloExample {

   public static void main(String[] args) {
      System.out.println("JNI Example run");
      new NativeWrapper().callSayHello();  
   }

}
