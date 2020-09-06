package com.myorg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.apigateway.MethodOptions;
import software.amazon.awscdk.services.apigateway.MethodResponse;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;

public class HoverSpaceStack extends Stack {
    public HoverSpaceStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HoverSpaceStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        String databaseArn="arn:aws:dynamodb:ap-south-1:584832312317:table/hoverSpaceData"; //Arn of hoverSpaceData table, this needs to be changed if we change the db

        // iam for dynamo access
        PolicyStatement initialPolicyWrite = new PolicyStatement();
        initialPolicyWrite.addActions("dynamodb:UpdateItem","dynamodb:PutItem","dynamodb:DeleteItem");
        initialPolicyWrite.addResources(databaseArn);   
        PolicyStatement initialPolicyRead = new PolicyStatement();
        initialPolicyRead.addActions("dynamodb:GetItem","dynamodb:BatchGetItem");
        initialPolicyRead.addResources(databaseArn);
        
        Map<String, String> environment = new HashMap<String,String>();
        environment.put("TABLE_NAME", "hoverSpaceData");
        
        // Stack will have 2 lamdas to read and write to dynamo respectively
        Function readFunction = Function.Builder.create(this, "readLambda")
        .runtime(Runtime.NODEJS_10_X)
        .code(Code.fromAsset("lambda"))
        .handler("read.handler")
        .memorySize(1024)
        .timeout(Duration.minutes(1))
        .initialPolicy(Arrays.asList(initialPolicyRead))
        .environment(environment)
        .build();

        Function writeFunction = Function.Builder.create(this, "writeLambda")
        .runtime(Runtime.NODEJS_10_X)
        .code(Code.fromAsset("lambda"))
        .handler("write.handler")
        .memorySize(1024)
        .timeout(Duration.minutes(1))
        .initialPolicy(Arrays.asList(initialPolicyWrite))
        .environment(environment)
        .build();


        Function authFunction = Function.Builder.create(this, "authLambda")
        .runtime(Runtime.NODEJS_10_X)
        .code(Code.fromAsset("lambda"))
        .handler("auth.handler")
        .memorySize(1024)
        .timeout(Duration.minutes(1))
        .build();

        
        //can the following code be refactored? Yes. Will I refactor it? Maybe.
        //Endpoint won't work right now because the returned response is not compatible with apig (for now)

        final List<MethodResponse> methodResponses = new ArrayList<>();
        methodResponses.add(MethodResponse.builder()
        .statusCode("200")
        .build());
        
        final RestApi apiWrite = RestApi.Builder
        .create(this, "writeEndpoint")
        .build();

        apiWrite.getRoot()
        .addResource("data")
        .addMethod("POST", LambdaIntegration.Builder
                        .create(writeFunction)
                        .build(),
                        MethodOptions.builder()
                        .methodResponses(methodResponses)
                        .build());
        
        final RestApi apiRead = RestApi.Builder
        .create(this, "readEndpoint")
        .build();

        apiRead.getRoot()
        .addResource("data")
        .addMethod("POST", LambdaIntegration.Builder
                        .create(readFunction)
                        .build(),
                        MethodOptions.builder()
                        .methodResponses(methodResponses)
                        .build());
        
    }
}
