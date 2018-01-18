import React from 'react';

class WebsocketChat extends React.Component {

	render(){
		return (
			<div>
				<h2>Websockets</h2>
				<div id="notification-bar">Signed in: <span id="usernameHeader"></span></div>
				<div id="connectUser">
			 		<input type="text" id="username" placeholder="Username"/>
			 		<button type="button" onClick={connect}>Connect</button>
			 		<button onClick={close}>Close</button>
				</div>
				<div id="messageContainer">
				 	<textarea rows="4" cols="30" maxLength="140" id="msg" placeholder="Message"/>
				</div>
				<button onClick={send}>Send Message</button>
				<ul className='DataItemList-list' id="log"></ul>
			</div>
		)
	}
}

export default WebsocketChat;
