package com.github.winse.hadoop

import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.Reducer
import org.apache.hadoop.io.Text
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import org.apache.hadoop.fs.Path
import scala.Array.canBuildFrom
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.util.Tool
import org.apache.hadoop.util.ToolRunner

class ScalaMapper extends Mapper[LongWritable, Text, Text, IntWritable] {

  val one = new IntWritable(1);

  override def map(key: LongWritable, value: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context) {
    value.toString().split("\\s+").map(word => context.write(new Text(word), one))
  }

}

class ScalaReducer extends Reducer[Text, IntWritable, Text, IntWritable] {

  override def reduce(key: Text, values: java.lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context) {
    var sum: Int = 0

    val itr = values.iterator()
    while (itr.hasNext()) {
      sum += itr.next().get()
    }
    context.write(key, new IntWritable(sum))
  }

}

object HelloScalaMapRed extends Configured with Tool {

  override def run(args: Array[String]): Int = {

    val job = Job.getInstance(getConf(), "WordCount Scala.")
    job.setJarByClass(getClass())

    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])

    job.setMapperClass(classOf[ScalaMapper])
    job.setCombinerClass(classOf[ScalaReducer])
    job.setReducerClass(classOf[ScalaReducer])

    FileInputFormat.addInputPath(job, new Path("/scala/in/"));
    FileOutputFormat.setOutputPath(job, new Path("/scala/out/"));

    job.waitForCompletion(true) match {
      case true => 0
      case false => 1
    }

  }

  def main(args: Array[String]) {
    val res: Int = ToolRunner.run(new Configuration(), this, args)
    System.exit(res);
  }

}