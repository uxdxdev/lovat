import React from 'react';

class WebsocketChat extends React.Component {

	render(){
		return (
			<div>
				<h5 className="text-center text-muted">Websockets</h5>
                <div id="notification-bar">Signed in: <span id="usernameHeader"></span></div>
                <form class="form-inline">

                    <div id="messageContainer">
                        <textarea className="form-control mb-2" maxLength="140" id="msg" placeholder="Message"/>
                    </div>
                    <button type="button" className="btn btn-primary mb-2 btn-sm" onClick={send}>Send Message</button>
                </form>
				<ul className='DataItemList-list' id="log"></ul>
			</div>
		)
	}
}

export default WebsocketChat;
