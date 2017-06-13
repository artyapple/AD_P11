package rsa.keys;

public class RSAKeyPair {
	
	private final RSAKey openkey;
	private final RSAKey seckey;
	
	public RSAKeyPair(RSAKey openkey, RSAKey seckey) {
		this.openkey = openkey;
		this.seckey = seckey;
	}
	public RSAKey getOpenkey() {
		return openkey;
	}
	public RSAKey getSeckey() {
		return seckey;
	}
}
