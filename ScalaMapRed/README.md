## 环境搭建

* scala-ide(eclipse) 2.10.4

## 正式环境运行

本地打包：

```
mvn package
```

上传执行和依赖包：

```
[hadoop@umcc97-44 scala]$  ls -1
scala-library-2.10.4.jar
scalamapred-1.0.jar
```

linux运行：

```
hadoop fs -mkdir -p /scala/in
hadoop fs -put /home/hadoop/hadoop-2.2.0/README.txt /scala/in/
export HADOOP_CLASSPATH=$(pwd)/*
hadoop com.github.winse.hadoop.HelloScalaMapRed -libjars scala-library-2.10.4.jar 
```

查看运行结果：

```
[hadoop@umcc97-44 scala]$ hadoop fs -ls /scala/out
Found 2 items
-rw-r--r--   3 hadoop supergroup          0 2014-10-14 12:06 /scala/out/_SUCCESS
-rw-r--r--   3 hadoop supergroup       1309 2014-10-14 12:06 /scala/out/part-r-00000
[hadoop@umcc97-44 scala]$ hadoop fs -cat /scala/out/part-r-00000 | tail
uses    1
using   2
visit   1
website 1
which   2
wiki,   1
with    1
written 1
you     1
your    1
```

