package com.aws.lambda;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

@SpringBootApplication
public class InvokeLambdaFromJavaClientApp {

	public static void main(String[] args) {
		SpringApplication.run(InvokeLambdaFromJavaClientApp.class, args);

		Regions region = Regions.AP_SOUTH_1;
		BasicAWSCredentials credentials = new BasicAWSCredentials("<<your access key>>",
				"<<your secretKey>>");
		AWSLambdaClientBuilder builder = AWSLambdaClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(region);

		AWSLambda lambdaClient = builder.build();
		InvokeRequest req = new InvokeRequest().withFunctionName("stringfun")
				.withPayload("{\n" + "  \"name\" : \"Raghuram\"\n" + "}");
		InvokeResult requestResult = lambdaClient.invoke(req);
		ByteBuffer byteBuf = requestResult.getPayload();
		if (byteBuf != null) {
			String result = StandardCharsets.UTF_8.decode(byteBuf).toString();
			System.out.println("testLambdaFunction::Lambda result: " + result);
		} else {
			System.out.println("testLambdaFunction: result payload is null");
		}

	}

}
