package com.github.winse;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tachyon.TachyonURI;
import tachyon.client.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TachyonIOTest {

    @Test
    public void testLocalWrite() {
        // 由于本地是windows系统，而tachyon使用了Linux的/bin/chmod来修改权限。对源码进行一些屏蔽修改。

        String host = "Lenovo-PC";
        try {
            writeFile(host, WriteType.MUST_CACHE);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testHDFSWrite() {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        String host = "umcc97-44";

        // hdfs
//		hadoop fs -mkdir /underfs/tmp/tachyon/data
        try {
            writeFile(host, WriteType.THROUGH); // 没有Worker不能用Cache
        } catch (Exception e) {
            fail(e);
        }
    }

    private void fail(Exception e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
    }

    private void writeFile(String host, WriteType type) throws IOException {
        TachyonFS fs = TachyonFS.get(host, 19998, false);
        TachyonURI tURI = new TachyonURI("/NOTICE2");

        fs.delete(tURI, true);
        fs.createFile(tURI);
        TachyonFile file = fs.getFile(tURI);

        OutputStream out = file.getOutStream(type);

        FileInputStream in = new FileInputStream("E:/winsegit/helloJ/HelloTachyon/pom.xml");
        try {
            IOUtils.copy(in, out);
            out.flush();
        } finally {
            out.close();
            in.close();
            fs.close();
        }
    }

    @Test
    public void testHDFSRead() throws IOException {
        String host = "umcc97-44";
        TachyonFS fs = TachyonFS.get(host, 19998, false);
        TachyonURI tURI = new TachyonURI("/NOTICE2");

        TachyonFile file = fs.getFile(tURI);

        // ReadType为CACHE，会尝试本地缓冲这个数据。如果本地没有Worker会打印错误堆栈。不影响程序运行。
        try (InStream in = file.getInStream(ReadType.CACHE_PROMOTE)) {
            IOUtils.copy(in, System.out);
        }
    }


}
