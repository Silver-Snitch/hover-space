exports.handler = async function(event) {
    const tableName = process.env.TABLE_NAME;
    console.log("request:", JSON.stringify(event, undefined, 2));
    return {
      statusCode: 200,
      headers: { "Content-Type": "text/plain" },
      body: `Hello, Read function invoked on table `+tableName+JSON.stringify(event)
    };
  };