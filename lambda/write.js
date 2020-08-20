var AWS = require('aws-sdk');
var ddb = new AWS.DynamoDB({apiVersion: '2012-08-10'});

exports.handler = async function(event) {
    
  const tableName = process.env.TABLE_NAME;
  var params = {
    TableName: tableName,
    Item: {
        user: { S: event.user },
        url: { S: event.url },
        note: { S: event.note }
      }
    };
  await ddb.putItem(params).promise();
  return {
    statusCode: 204,
    headers: {},
    body: JSON.stringify({})
  };
    
}