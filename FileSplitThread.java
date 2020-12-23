package Test2;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * 文件切割的线程类
 */
public class FileSplitThread implements Runnable {

    //要读取的文件
    private File file;

    //分割后每个大小
    private int size;

    //文件指针
    private int skip;

    //计数器(用于合并文件)
    private int i;

    //空参构造
    public FileSplitThread() {
    }

    //目标文件为参数
    public FileSplitThread(File file, int size) {
        this.file = file;
        this.size = size;
    }


    //线程的任务
    @Override
    public void run() {

        try {
            //raf用于读取文件,它只有读取权限
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            //raf2用于把数据写出到文件中
            RandomAccessFile raf2;
            //字节数组用于存放数据
            byte[] content = new byte[size];
            //同步代码块保证线程同步，其中同步监视器为this对象
            synchronized (this) {
                //移动指针
                raf.seek(skip);
                //读取
                raf.read(content);
                System.out.println(Thread.currentThread().getName() + " 中skip= " + skip + "   " + new String(content));
                //指针位置=原本的指针位置+每个文件的大小
                skip = skip + size;
                //存放在D://temp目录下,文件名为x.txt
                raf2 = new RandomAccessFile(new File("D://temp//" + (i++) + ".txt"), "rw");
                //写出数据
                raf2.write(content);
            }
            //关闭资源
            raf.close();
            raf2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
