package blockChieffrierung.keys;

public class ChieffrierKey {
	private int sessionKey;
	public ChieffrierKey(){
		this.sessionKey=(int) (Math.random() * 93) + 1;
	}

public int getSessionKey(){
	return this.sessionKey;
}
	
}
