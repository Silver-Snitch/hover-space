//needs refactor and error handling


var AWS = require('aws-sdk');
var ddb = new AWS.DynamoDB({apiVersion: '2012-08-10'});

exports.handler = async function(event, context, callback) {
    const tableName = process.env.TABLE_NAME;
    var params = {
    TableName: tableName,
    Key: {
        user: { S: event.user },
        url: { S: event.url }
      },
    };
  	await ddb.getItem(params,function(err,data) {
  		if(err) {
  		    // console.log("error");
  		    var response = {
                statusCode: 200,
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify("Bad request body"),
                isBase64Encoded: false
            };
            callback(null, response);
  		}
  		else {
  		    if(data.Item) {
  		        var response = {
                    statusCode: 200,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(data.Item.note.S),
                    isBase64Encoded: false
                };
                callback(null, response);
  		    }
  		    else {
  	            var response = {
                    statusCode: 200,
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify("No data present"),
                    isBase64Encoded: false
                };
                callback(null, response);
  		
  		    }
  		}
  	}).promise();
  };