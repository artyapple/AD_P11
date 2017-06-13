package rsa.keys;

public class RSAKey {
	
	private final int exp;
	private final int mod;
	
	public RSAKey(int exp, int mod){
		this.exp = exp;
		this.mod = mod;
	}

	public int getExp(){
		return exp;
	}
	
	public int getMod(){
		return mod;
	}
}
