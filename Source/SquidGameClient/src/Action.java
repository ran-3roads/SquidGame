import java.io.Serializable;


public class Action implements Serializable {
	//100: user login101:user init 400:userXY 445:murder 999:GameOver 
	//300:user move 600:user die 700: user goal in 800:game over show me rank 444:AkillB

	private static final long serialVersionUID = 1L;
	public String code;
	public String UserName;
	public String data;
	
	public int rank;
	public int player;
	public int xPos,yPos;
	public boolean life;
	public boolean punch;

	

	public Action(String UserName, String code, String msg) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
	}

}
