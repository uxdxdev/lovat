import React from 'react';

class WebsocketChat extends React.Component {

	render(){
		return (
			<div className="chat-container col-12 p-0">
				<div className="chat-log" > 
                	<ul id="log"></ul>
				</div>
                <form className="chat-input" onSubmit={this.send}>
                    <input className="form-control mb-2" maxLength="140" id="msg" placeholder="Message"/>
                    <button type="button" id="sendMessageButton" className="btn btn-primary mb-2 btn-sm col-12" onClick={send}>Send Message</button>
                </form>
			</div>
		)
	}
}

export default WebsocketChat;
