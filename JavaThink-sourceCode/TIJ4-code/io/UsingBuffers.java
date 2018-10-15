package io;

//: io/UsingBuffers.java
import java.nio.*;
import static net.mindview.util.Print.*;

/*public class UsingBuffers {
  private static void symmetricScramble(CharBuffer buffer){
    while(buffer.hasRemaining()) {
      buffer.mark();
      char c1 = buffer.get();
      char c2 = buffer.get();
      buffer.reset();
      buffer.put(c2).put(c1);
    }
  }
  public static void main(String[] args) {
    char[] data = "UsingBuffers".toCharArray();
    ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
    CharBuffer cb = bb.asCharBuffer();
    cb.put(data);
    print(cb.rewind());
    symmetricScramble(cb);
    print(cb.rewind());
    symmetricScramble(cb);
    print(cb.rewind());
  }
}*/
/* Output:
UsingBuffers
sUniBgfuefsr
UsingBuffers
*///:~


/*
 * csdn博客上面的例子
 * 
 * 
 */
public class UsingBuffers {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);// 准备10个大小的缓冲区
        System.out.println("写入数据之前：" + getInfo(intBuffer));

        int[] temp = { 5, 7, 9 };
        intBuffer.put(3);// 向缓冲区中压入一个整型值
        intBuffer.put(temp);// 向缓冲区中压入一个整型数组，此时缓冲区中已经有了7个数据

        System.out.println("put()写入数据之后：" + getInfo(intBuffer));

        intBuffer.flip();// 重设缓冲区-limit=position，position=0
        System.out.println("flip()准备输出数据时：" + getInfo(intBuffer));
        System.out.println("缓冲区中的内容：");
        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + "\t");
        }
        System.out.println("\n数据输出之后：" + getInfo(intBuffer));
        
        intBuffer.clear();  
        System.out.println("调用clear()之后：" + getInfo(intBuffer));
    }

    private static String getInfo(IntBuffer intBuffer) {
        return "position = " + intBuffer.position() + ",limit = "
                + intBuffer.limit() + ",capacity = " + intBuffer.capacity();
    }

}


