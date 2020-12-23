# Hardworking-bee-Test2
勤奋蜂第二次作业——多线程 文件的切割与合并 2019210941 信管刘志昊
控制台打印结果:
"D:\idea\IntelliJ IDEA 2019.3.3\jbr\bin\java.exe" "-javaagent:D:\idea\IntelliJ IDEA 2019.3.3\lib\idea_rt.jar=52257:D:\idea\IntelliJ IDEA 2019.3.3\bin" -Dfile.encoding=UTF-8 -classpath D:\111lzh\Study\java实验\java8\out\production\java8 Test2.FileSplitAndMerge
请输入你想切割(复制)的文件的路径:
D://abc.txt
请输入切割后的每份文件的大小(字节):
128
pool-1-thread-1 中skip= 0    /**
         * 拆分文件 
         * @param fileName 待拆分的完整文件名
         * @param byteSize 按多少字�
pool-1-thread-6 中skip= 128   ��大小拆分
         * @return 拆分后的文件名列表
         * @throws IOException
         */
     public List<St
pool-1-thread-7 中skip= 256   ring> splitBySize(String fileName, int byteSize){
            List<String> parts = new ArrayList<String>();
            File f
pool-1-thread-8 中skip= 384   ile = new File(fileName);
            int count = (int) Math.ceil(file.length() / (double) byteSize);
            int countLen
pool-1-thread-2 中skip= 512    = (count + "").length();
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5,
                    10, 1, Tim
pool-1-thread-4 中skip= 640   eUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(count * 2));

        for (int i = 0; i < count; i++) {

pool-1-thread-5 中skip= 768               String partFileName = file.getParent() + File.separator +  file.getName() + "."
                    + FileUtils.lef
pool-1-thread-3 中skip= 896   tPad((i + 1) + "", countLen, '0') + ".part";
            long startPos =   (long)i * (long)byteSize;
            threadPool.ex
pool-1-thread-5 中skip= 1024   ecute(new SplitRunnable(byteSize, startPos,partFileName, file));
            parts.add(partFileName);
        }
        threa
pool-1-thread-4 中skip= 1152   dPool.shutdown();
    //        while (!threadPool.isTerminated()){
    //            try {
    //                Thread.slee
pool-1-thread-2 中skip= 1280   p(1000);
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
pool-1-thread-8 中skip= 1408   
    //        }

        return parts;
    }


    private class SplitRunnable implements Runnable {
        int byteSiz
pool-1-thread-7 中skip= 1536   e;
        String partFileName;
        File originFile;
        long startPos;

        public SplitRunnable(int byteSize,
pool-1-thread-6 中skip= 1664    long startPos, String partFileName,
                             File originFile) {
            this.startPos = startPos;
  
pool-1-thread-1 中skip= 1792             this.byteSize = byteSize;
            this.partFileName = partFileName;
            this.originFile = originFile;
 
pool-1-thread-7 中skip= 1920          }

        public void run() {
            RandomAccessFile rFile;
            OutputStream os;
            try {
 
pool-1-thread-8 中skip= 2048                  rFile = new RandomAccessFile(originFile, "r");
                byte[] b = new byte[byteSize];
                S
pool-1-thread-2 中skip= 2176   ystem.out.println(startPos);
                rFile.seek(startPos);// 移动指针到每“段”开头
                int s =
pool-1-thread-4 中skip= 2304    rFile.read(b);
                os = new FileOutputStream(partFileName);
                os.write(b, 0, s);
                o
pool-1-thread-5 中skip= 2432   s.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
          
pool-1-thread-3 中skip= 2560     }
        }
    }
                                                                                                         
请输入合并后的文件的保存路径:
D://order//order.txt
文件合并成功！ 一共读了:21 个文件
