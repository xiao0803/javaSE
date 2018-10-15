package io;

//: io/OSExecuteDemo.java
// Demonstrates standard I/O redirection.
import net.mindview.util.*;

public class OSExecuteDemo {
  public static void main(String[] args) {
	  //OSExecute.command("javap OSExecuteDemo");
	  //打印当前系统所有进程的信息
	  OSExecute.command("tasklist");
  }
} /* Output:
Compiled from "OSExecuteDemo.java"
public class OSExecuteDemo extends java.lang.Object{
    public OSExecuteDemo();
    public static void main(java.lang.String[]);
}
*///:~
