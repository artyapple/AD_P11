package rsa.keys;

public class RSAKeyPair {
	
	private final RSAKey publicKey;
	private final RSAKey privateKey;
	
	public RSAKeyPair(RSAKey openkey, RSAKey seckey) {
		this.publicKey = openkey;
		this.privateKey = seckey;
	}
	public RSAKey getPublicKey() {
		return publicKey;
	}
	public RSAKey getPrivateKey() {
		return privateKey;
	}
}
