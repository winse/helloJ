## �����

* scala-ide(eclipse) 2.10.4

## ��ʽ��������

���ش����

```
mvn package
```

�ϴ�ִ�к���������

```
[hadoop@umcc97-44 scala]$  ls -1
scala-library-2.10.4.jar
scalamapred-1.0.jar
```

linux���У�

```
hadoop fs -mkdir -p /scala/in
hadoop fs -put /home/hadoop/hadoop-2.2.0/README.txt /scala/in/
export HADOOP_CLASSPATH=$(pwd)/*
hadoop com.github.winse.hadoop.HelloScalaMapRed -libjars scala-library-2.10.4.jar 
```

