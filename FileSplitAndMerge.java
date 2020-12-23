package Test2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class FileSplitAndMerge {
    public static void main(String[] args) throws Exception {
        //----------------------------------------------FileSplit部分---------------------------------------------------------
        //读入要切割的文件，只有输入的文件路径存在,才会跳出循环
        File file = inputPath("请输入你想切割(复制)的文件的路径:");

        //切割后的每份的文件的大小为size
        int size = inputSize();


        //目标文件的大小
        long length = file.length();
        //要开启的线程数目
        long num = (length / size) + 1;

        //文件分割的线程类
        FileSplitThread fileSplitThread = new FileSplitThread(file, size);

        //创建一个具有固定线程数8个的线程池
        ExecutorService pool = Executors.newFixedThreadPool(8);

        //提交任务
        for (int i = 0; i < num; i++) {
            pool.submit(fileSplitThread);
        }

        //关闭线程池
        pool.shutdown();


        Thread.sleep(500);
        //----------------------------------------------FileMerge部分---------------------------------------------------------

        //获取切割后的文件列表
        File[] files = new File("D://temp").listFiles();
        FileInputStream inputStream = null;
        File fileMerge = inputPath("请输入合并后的文件的保存路径:");

        //文件输出流.将数据写入到合并文件中
        FileOutputStream outputStream = new FileOutputStream(fileMerge, true);
        //字节数组,存储数据
        byte content[] = new byte[1024];
        int i, len;

        //读数据写数据
        for (i = 0; i < files.length; i++) {
            File file1 = new File("D://temp//" + i + ".txt");
            inputStream = new FileInputStream(file1);

            while ((len = inputStream.read(content)) != -1) {
                //写入
                outputStream.write(content, 0, len);
            }
        }

        System.out.println("文件合并成功！ 一共读了:" + i + " 个文件");
        //关闭资源
        outputStream.close();
        if (inputStream != null) {
            inputStream.close();
        }
    }

    //输入文件的存放的位置
    public static File inputPath(String message) {
        Scanner scanner = new Scanner(System.in);
        File file = null;
        boolean flag = true;
        while (flag) {
            System.out.println(message);
            String pathname = scanner.nextLine();
            file = new File(pathname);
            if (file.exists()) {
                flag = false;
            }
        }
        return file;
    }


    //输入切割后的每份的文件的大小
    public static int inputSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入切割后的每份文件的大小(字节):");
        int size = 0;
        try {
            size = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("您的输入非法!!!");
            inputSize();
        }

        if (size <= 0) {
            size = 128;
        }
        return size;
    }
}
