var socket;
var username;

function createNewSocketConnection() {
	// close open connection
	if (socket) {
		closeSocketConnection();
	}

    socket = new WebSocket('wss://lovat.herokuapp.com/chat');
    //socket = new WebSocket('ws://localhost:8080/chat');
    socket.onopen = function(event) {
    }

    socket.onerror = function(event) {
        log('', 'Error: ' + JSON.stringify(event));
    }

    socket.onmessage = function(event) {
        var message = JSON.parse(event.data);
        log(message.from, message.content);
    }

    socket.onclose = function(event) {
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
	if (socket && message) {
		var json = JSON.stringify({
			from : "",
			to : "",
			content : message
		});
		socket.send(json);
		message = "";
	}
}

function log(from, text) {
	var li = document.createElement('li');
    li.className = "row col";

	var usernameDiv = document.createElement('div');
	var highlight = "text-primary";

	// give the admin a special username color
	if(from === "admin"){
        highlight = "text-success";
    }

    usernameDiv.className = highlight;
    usernameDiv.innerHTML = from;

    var messageDiv = document.createElement('div');
    messageDiv.innerHTML =  ": " + text;

	li.appendChild(usernameDiv);
	li.appendChild(messageDiv);

	var list = document.getElementById("log");
	if (list !== null) {
		list.prepend(li);
	}
}

window.addEventListener("beforeunload", function() {
	if(socket) {
        socket.close();
    }
});
