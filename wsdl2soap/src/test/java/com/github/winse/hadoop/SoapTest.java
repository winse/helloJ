package com.github.winse.hadoop;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

import javax.naming.ConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlLoader;
import com.eviware.soapui.model.iface.Operation;


public class SoapTest {

	@Test
	public void test() throws Exception {
		WsdlProject wsdlProject = new WsdlProject();
		String wsdl = "http://webservice.webxml.com.cn/WebServices/StockInfoWS.asmx?wsdl";
		WsdlInterface[] wsdlInterfaces = wsdlProject.importWsdl(wsdl, true, createWsdlLoader(wsdl, null));

		for (WsdlInterface wsdlInterface : wsdlInterfaces) {
			System.out.println("==============================" + wsdlInterface.getName() + "=================================");
			Map<String, Operation> operationInsts = wsdlInterface.getOperations();
			for (Operation operationInst : operationInsts.values()) {
				String requestTemplate = operationInst.getRequestAt(0).getRequestContent();
				// System.out.println("Action: "+operationInst.getAction());
				System.out.println(operationInst.getName());
				System.out.println(requestTemplate);
				System.out.println(operationInst);
			}
		}
	}

	private static WsdlLoader createWsdlLoader(String wsdl, Properties httpClientProps) throws ConfigurationException {
		final HttpClient httpClient = new HttpClient();
		
		return new WsdlLoader(wsdl){
			public InputStream load(String url) throws Exception {
		        GetMethod httpGetMethod;

		        if(url.startsWith("file")) {
		            return new URL(url) .openStream();
		        }

		        // Authentication is not being overridden on the method.  It needs
		        // to be present on the supplied HttpClient instance!
		        httpGetMethod = new GetMethod(url);
		        httpGetMethod.setDoAuthentication(true);

		        try {
		            int result = httpClient.executeMethod(httpGetMethod);

		            if(result != HttpStatus.SC_OK) {
		                if(result < 200 || result > 299) {
		                    throw new HttpException("Received status code '" + result + "' on WSDL HTTP (GET) request: '" + url + "'.");
		                } else {
		                    System.err.println("Received status code '" + result + "' on WSDL HTTP (GET) request: '" + url + "'.");
		                }
		            }

		            return new ByteArrayInputStream(httpGetMethod.getResponseBody());
		        } finally {
		            httpGetMethod.releaseConnection();
		        }
		    }

			@Override
			public void close() {
			}
		};
	}

}
