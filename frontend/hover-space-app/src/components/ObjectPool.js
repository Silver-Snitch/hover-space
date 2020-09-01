import { CognitoUserPool } from 'amazon-cognito-identity-js';
import appConfig from '../../src/config/app-config.json';
const poolData = {
	UserPoolId: appConfig.userPool,
	ClientId: appConfig.clientId
}
export default new CognitoUserPool(poolData);
  