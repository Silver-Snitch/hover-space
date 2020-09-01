import React, { useState } from 'react';
import UserPool from './ObjectPool.js';
import {CognitoUserAttribute} from 'amazon-cognito-identity-js';
export default () => {

  const [email, setEmail]=useState('');
  const [password, setPassword]=useState('');
  const onSubmit = event => {
    event.preventDefault();
    //The code below is messy, ideally we shouldn't import cognito specific components here
    var attributeList=[];
    var dataEmail = {
      Name: 'email',
      Value: email
    };
    var attributeEmail = new CognitoUserAttribute(dataEmail);
    attributeList.push(attributeEmail);
    UserPool.signUp(email,password,attributeList,null,(err,data) => {
      if(err) console.log(err);
      else console.log(data);
    });
  };

  return (
    <div>
      <form onSubmit={onSubmit}>
        <div>Not validating the password for now, cognito will throw an error back to frontend if password criteria not met </div>
        <input
          value={email}
          onChange={event => setEmail(event.target.value)}
        />
        <input
          value={password}
          onChange={event => setPassword(event.target.value)}
        />
        <button type='submit'>Signup</button>
      </form>
    </div>
  );
}