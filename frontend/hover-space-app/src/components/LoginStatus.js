import React, { useState, useContext, useEffect } from 'react';
import {AccountContext} from './Account.js';

export default () => {
	const [status, setStatus] = useState(false);
	const {getSession, logout} = useContext(AccountContext);
	useEffect(()=>{
		getSession()
		.then(session => {
			console.log('Session:', session);
			setStatus(true);
		})
		.catch(err=>{console.log('Some err', err)})
	}, []);

	return (
		<div>
			{status ? (
				<div>
					You are logged in.
					<button onClick={logout}> logout </button>
				</div>
			):(
				'Login'
			)};
		</div>
	);
};