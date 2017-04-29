class Events extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      events: props.events
    };
  }
  
  loadEventsFromServer(component, eventsUrl) {
    console.log("Events.loadEventsFromServer()", eventsUrl)
    axios.get(eventsUrl)
      .then(res => {
        const events = res.data.list;
        component.setState({ events });
    });
   }

  componentDidMount() {    
  	this.eventsUrl = 'http://localhost:8080/v1/api/events';
    this.loadEventsFromServer(this, this.eventsUrl);
    setInterval(this.loadEventsFromServer.bind(null, this, this.eventsUrl), this.props.pollInterval);
  }

  render() {
    return (
      <div>
        <h1>{`Events`}</h1>
        <ul>
          {this.state.events.map((event, index) =>
            <li key={index}>{event.name}</li>
          )}
        </ul>
      </div>
    );
  }
}

var renderEventsComponentServer = function (events) {
    var data = Java.from(events);
    return React.renderToString(<Events events={events} pollInterval={5000} />);
};