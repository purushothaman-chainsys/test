package com.ascap.apm.core.utils;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;

public class PropertyStoreClient {

	public static void main(String[] args) {
		String parameterKey = "/APM/LOOKUPTABLESXMLFILE";
		AWSCredentialsProvider credentials = DefaultAWSCredentialsProviderChain.getInstance();
		AWSSimpleSystemsManagement simpleSystemsManagementClient = AWSSimpleSystemsManagementClientBuilder.standard()
				.withCredentials(credentials).withRegion("us-east-1").build();

		long st = 0, et = 0;
		st = System.currentTimeMillis();
		/*GetParameterRequest parameterRequest = new GetParameterRequest();
		parameterRequest.withName(parameterKey).setWithDecryption(Boolean.valueOf(true));
		GetParameterResult parameterResult = simpleSystemsManagementClient.getParameter(parameterRequest);
		System.out.println(parameterResult.getParameter().getValue());
		et = System.currentTimeMillis();*/
		//System.out.println(et - st);

		st = System.currentTimeMillis();
		ParameterStorePropertySource source = new ParameterStorePropertySource("APM", simpleSystemsManagementClient);
		System.out.println(source.getProperty(parameterKey));
		et = System.currentTimeMillis();
		System.out.println(et - st);
	}

}
