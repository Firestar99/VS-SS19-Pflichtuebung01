package var.web.ws.poll;

import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/election", encoders = DataEncoder.class)
public class ElectionService extends Endpoint {
	
	private Session session;
	
	public void notify(BallotBox ballotBox) throws EncodeException {
		try {
			session.getBasicRemote().sendObject(ballotBox);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		BallotBox ballotBox = BallotBox.getInstance();
		ballotBox.addObserver(this);
		
		try {
			notify(ballotBox);
		} catch (EncodeException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClose(Session session, CloseReason closeReason) {
		BallotBox.getInstance().removeObserver(this);
	}
}
