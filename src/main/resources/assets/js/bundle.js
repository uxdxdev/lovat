var loadEventsFromServer = function(eventsUrl) {
	axios.get(eventsUrl).then(function(res) {
		var data = res.data.list;		
		renderClientEvents(data);
	});
}

loadEventsFromServer('https://dropwizardheroku-webgateway.herokuapp.com/events');