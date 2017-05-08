var Events = React.createClass({
	getInitialState: function () {
		return {events: this.props.events};
	},

  loadEventsFromServer: function(component, eventsUrl) {	  
	  axios.get(eventsUrl).then(function (res) {
		  var eventList = res.data.list;		  
		  component.setState({ events: eventList });
	  });
  },

  componentDidMount: function() {
    this.eventsUrl = 'https://dropwizardheroku-webapigateway.com/events';
    this.loadEventsFromServer(this, this.eventsUrl);
    setInterval(this.loadEventsFromServer.bind(null, this, this.eventsUrl), this.props.pollInterval);
  },

  render: function() {
	  return React.createElement(
		          "ul",
		          null,
		          this.state.events.map(function (event, index) {
		            return React.createElement(
		              "li",
		              { key: index },
		              event.name
		            );
		          })
		        );
	  }
});

global.renderServerEvents = function (eventList) {
  var data = Java.from(eventList);  
  return ReactDOMServer.renderToString(
	        React.createElement(Events, {events: data, pollInterval: 5000})
	    );
};

global.renderClientEvents = function (eventList) {
    var data = eventList || [];
    ReactDOM.render(
        React.createElement(Events, {events: data, pollInterval: 5000}),
        document.getElementById("events")
    );
};

global.loadEvents = function (eventsUrl) {
	axios.get(eventsUrl).then(function(res) {
		var data = res.data.list;		
		renderClientEvents(data);
	});
};	