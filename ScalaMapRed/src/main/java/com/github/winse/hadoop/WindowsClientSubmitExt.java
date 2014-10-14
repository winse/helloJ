package com.github.winse.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.util.ConfigUtil;

public class WindowsClientSubmitExt {

	static {
		// genPackage();

		System.setProperty("HADOOP_USER_NAME", "hadoop");
//		System.setProperty("hadoop.home.dir", 
//				"E:/local/libs/big/hadoop-2.2.0");

		// 先加载mapreduce的配置
		ConfigUtil.loadResources();
		Configuration.addDefaultResource("analyser-test-site.xml");
	}

//	private static void genPackage() {
//		File file = new File("windows-debug-package.xml"); // 文件名任意，只要能找到就可以
//		Project project = new Project();
//
//		DefaultLogger consoleLogger = new DefaultLogger();
//		consoleLogger.setErrorPrintStream(System.err);
//		consoleLogger.setOutputPrintStream(System.out);
//		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
//		project.addBuildListener(consoleLogger);
//
//		project.fireBuildStarted();
//		project.init();
//		ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
//		projectHelper.parse(project, file);
//		project.executeTarget(project.getDefaultTarget());
//		project.fireBuildFinished(null);
//		project.log("生成任务jar成功！");
//	}

}
