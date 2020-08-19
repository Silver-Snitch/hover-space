package com.myorg;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

public class HoverSpaceStack extends Stack {
    public HoverSpaceStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public HoverSpaceStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        // iam for dynamo access
        // Role role = Role.Builder.create(this, "Endpoint").build();
        // List<String> actions = new ArrayList<>();
        // actions.add("AmazonDynamoDBFullAccess");
        // actions.add("AWSLambdaBasicExecutionRole");
        // PolicyStatement policyStatement =
        // PolicyStatement.Builder.create().actions(actions).build();
        // role.addToPolicy(policyStatement);

        Map<String, String> environment = new HashMap<String,String>();
        environment.put("TABLE_NAME", "hoverSpaceData");
        // Stack will have 2 lamdas to read and write to dynamo respectively
        Function readFunction = Function.Builder.create(this, "readLambda")
        .runtime(Runtime.NODEJS_10_X)
        .code(Code.fromAsset("lambda"))
        .handler("read.handler")
        .memorySize(1024)
        .timeout(Duration.minutes(1))
        .environment(environment)
        .build();

        Function writeFunction = Function.Builder.create(this, "writeLambda")
        .runtime(Runtime.NODEJS_10_X)
        .code(Code.fromAsset("lambda"))
        .handler("write.handler")
        .memorySize(1024)
        .timeout(Duration.minutes(1))
        .build();

        //Lambdas will be accessed by api gateway (with auth)
        //Downstream includes just dyanamo for now

        

    }
}
