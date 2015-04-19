package com.github.winse.hadoop;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.MRJobConfig;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 本地Windows提交任务到集群Linux
 * 
 * 打包mapreduce的程序为jar后，直接运行main方法进行提交。
 */
public class HelloMapRed extends WindowsClientSubmitExt {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		new HelloMapRed().execute(args);
	}

	public void execute(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if (otherArgs.length != 2) {
			otherArgs = new String[] { "/tmp/bugtest", "/tmp/bugtest-out12345" };
		}
		execute(conf, otherArgs[0], otherArgs[1]);
	}

	public void execute(Configuration conf, String in, String out) throws IOException, ClassNotFoundException,
			InterruptedException {
		// del out
		FileSystem fs = FileSystem.get(conf);
		fs.delete(new Path(out), true);
		
		// job
		Job job = Job.getInstance(conf, "word count");
		// 程序打包的路径
		job.setJar(getJarPath());

//		job.setJarByClass(HelloMR.class);
		job.setMapperClass(HelloMapper.class);
		job.setCombinerClass(HelloReduce.class);
		job.setReducerClass(HelloReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		setting(job);

		FileInputFormat.addInputPath(job, new Path(in));
		FileOutputFormat.setOutputPath(job, new Path(out));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	protected void setting(Job job) {
		// job.setMaxMapAttempts(1);
		job.setNumReduceTasks(1);
		job.getConfiguration().setInt(MRJobConfig.NUM_MAPS, 1);
	};

	protected String getJarPath() {
		return "test-jar/helloMR.jar";
	}

	static class HelloMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());

			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, one);
			}

		}

	}

	static class HelloReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
		private static Logger LOG = LoggerFactory.getLogger("Hello Reduce");
		private IntWritable result = new IntWritable();

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
				InterruptedException {

			LOG.info("key ： " + key);
			
			int sum = 0;
			for (IntWritable val : values) {
				LOG.info("对象hash： " + val);
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);

		}
	}

}
