var socket;
var username;

function createNewSocketConnection() {
	// close open connection
	if (socket) {
		closeSocketConnection();
	}

	// get new username
	var usernameInput = document.getElementById("username");
	username = usernameInput.value;
	usernameInput.value = "";

	// if not empty create the connection
	if (username) {

		// set username header
		var usernameHeader = document.getElementById("usernameHeader");
		usernameHeader.innerHTML = username;

		socket = new WebSocket('ws://lovat.herokuapp.com/chat' + username);
		socket.onopen = function(event) {
			console.log(username + ' connected 🎉');
		}

		socket.onerror = function(event) {
			log('Error: ' + JSON.stringify(event));
		}

		socket.onmessage = function(event) {
			var message = JSON.parse(event.data);
			log(message.from + ": " + message.content);
		}

		socket.onclose = function(event) {
			log(username + ' closed connection 😱');
		}
	}
}

function closeSocketConnection() {
	if (socket) {
		var usernameHeader = document.getElementById("usernameHeader");
		usernameHeader.innerHTML = "";
		socket.close();
		socket = null;
	}
}

function sendMessageOverSocket() {
	var content = document.getElementById("msg");
	var message = content.value;
	content.value = "";
	// only send if username and message are not empty
	if (socket && username && message) {
		var json = JSON.stringify({
			from : username,
			to : "",
			content : message
		});
		socket.send(json);
		message = "";
	}
}

function log(text) {
	var li = document.createElement('li');
	li.className = "List-item";
	li.innerHTML = text;
	var list = document.getElementById("log");
	if (list !== null) {
		list.prepend(li);
	}
}

window.addEventListener("beforeunload", function() {
	socket.close();
});
