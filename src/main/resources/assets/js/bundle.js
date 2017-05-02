function loadEventsFromServer(eventsUrl) {
	axios.get(eventsUrl).then(function(res) {
		var data = res.data.list;		
		renderClientEvents(data);
	});
}

loadEventsFromServer('http://localhost:8080/events');