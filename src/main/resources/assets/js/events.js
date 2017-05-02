class Events extends React.Component {
	constructor(props){
		super(props);
		this.state = {
				events: props.events
		}
	}

  loadEventsFromServer (component, eventsUrl) {	  
	  axios.get(eventsUrl).then(function (res) {
		  var eventList = res.data.list;		  
		  component.setState({ events: eventList });
	  });
  }

  componentDidMount () {
    this.eventsUrl = 'http://localhost:8080/events';
    this.loadEventsFromServer(this, this.eventsUrl);
    setInterval(this.loadEventsFromServer.bind(null, this, this.eventsUrl), this.props.pollInterval);
  }

  render () {
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
}

var renderClientEvents = function (eventList) {
    var data = eventList || [];
    ReactDOM.render(
        React.createElement(Events, {events: data, pollInterval: 5000}),
        document.getElementById("events")
    );
};

var renderServerEvents = function (eventList) {
  var data = Java.from(eventList);  
  return ReactDOMServer.renderToString(
	        React.createElement(Events, {events: data, pollInterval: 5000})
	    );
};