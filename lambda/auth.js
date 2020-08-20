exports.handler = async function(event) {
    return {
      statusCode: 200,
      headers: { "Content-Type": "text/plain" },
      body: `This lambda will return a policy document containing permissions to execute the other 2 lambdas`
    };
  };