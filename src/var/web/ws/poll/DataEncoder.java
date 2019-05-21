package var.web.ws.poll;


import javax.json.Json;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class DataEncoder implements Encoder.Text<BallotBox> {
	
	@Override
	public void init(EndpointConfig config) {
	
	}
	
	@Override
	public void destroy() {
	
	}
	
	@Override
	public String encode(BallotBox object) {
		return Json.createObjectBuilder()
				.add("votes", object.countVotes())
				.build()
				.toString();
	}
}
