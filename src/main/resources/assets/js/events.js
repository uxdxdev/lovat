var Events = React.createClass({
	getInitialState: function () {
		return {events: this.props.events};
	},

  loadEventsFromServer: function(component, eventsUrl) {
	console.log("Events.loadEventsFromServer()", eventsUrl)
    axios.get(eventsUrl).then(function (res) {
      var eventList = res.data.list;
      component.setState({ events: eventList });
    });
  },

  componentDidMount: function() {
    this.eventsUrl = 'http://localhost:8080/v1/api/events';
    this.loadEventsFromServer(this, this.eventsUrl);
    setInterval(this.loadEventsFromServer.bind(null, this, this.eventsUrl), this.props.pollInterval);
  },

  render: function() {
	  return React.createElement(
		        "div",
		        null,
		        React.createElement(
		          "h1",
		          null,
		          "Events"
		        ),
		        React.createElement(
		          "ul",
		          null,
		          this.state.events.map(function (event, index) {
		            return React.createElement(
		              "li",
		              { key: index },
		              event.name
		            );
		          })
		        )
		      );
  }
});

var renderEventsComponentServer = function (eventList) {
  var data = Java.from(eventList);  
  return React.renderToString(
	        React.createElement(Events, {events: data, pollInterval: 5000})
	    );
};