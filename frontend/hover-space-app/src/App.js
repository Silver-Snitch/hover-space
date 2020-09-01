import React from 'react';
import Signup from './components/Signup.js';
import Login from './components/Login.js';
import LoginStatus from './components/LoginStatus.js';
import {Account} from './components/Account.js';

export default () => {
  return (
    <Account>
    	<LoginStatus />
    	<Signup />
      	<Login />
    </Account>
  );
}