package var.web.ws.poll;

import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/election", encoders = DataEncoder.class)
public class ElectionService {
	
	private Session session;
	
	public void notify(BallotBox zwischenstand) throws IOException, EncodeException {
		if (session.isOpen())
			session.getBasicRemote().sendObject(zwischenstand);
	}
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		BallotBox ballotBox = BallotBox.getInstance();
		ballotBox.addObserver(this);
		
		try {
			notify(ballotBox);
		} catch (EncodeException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		BallotBox.getInstance().removeObserver(this);
	}
}
