import React from 'react';

class WebsocketChat extends React.Component {

	render(){
		return (
			<div>
				<h2 className="text-center">Websockets</h2>
                <div id="notification-bar">Signed in: <span id="usernameHeader"></span></div>
                <form class="form-inline">
                    <label className="sr-only" for="username">Username</label>
                    <input type="text" className="form-control mb-2" id="username" placeholder="Username"/>

                    <button type="button" className="btn btn-primary mb-2 mr-2" onClick={connect}>Connect</button>
                    <button type="button" className="btn btn-primary mb-2" onClick={close}>Close</button>

                    <div id="messageContainer">
                        <textarea className="form-control mb-2" maxLength="140" id="msg" placeholder="Message"/>
                    </div>
                    <button type="button" className="btn btn-primary mb-2" onClick={send}>Send Message</button>
                </form>
				<ul className='DataItemList-list' id="log"></ul>
			</div>
		)
	}
}

export default WebsocketChat;
