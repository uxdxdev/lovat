import React from 'react';

class WebsocketChat extends React.Component {

	render(){
		return (
			<div>
				<h5 className="text-center text-muted">Websockets</h5>
                <form class="form-inline" onSubmit={this.send}>
                    <input className="form-control mb-2" maxLength="140" id="msg" placeholder="Message"/>
                    <button type="button" id="sendMessageButton" className="btn btn-primary mb-2 btn-sm" onClick={send}>Send Message</button>
                </form>
				<ul id="log"></ul>
			</div>
		)
	}
}

export default WebsocketChat;
