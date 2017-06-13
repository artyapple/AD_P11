package hybrid.services;


import blockChieffrierung.BlockChiffrierVerfahren;
import rsa.keys.RSAKey;
import rsa.keys.RSAKeyPair;
import rsa.services.RSAService;

public class Hybrid {
	private final int BASIS = 95;
	private BlockChiffrierVerfahren chiff;
	
	/**
	 * Diese Methode kryptiert eine Nachricht
	 * @param message als String
	 * @return krypitierte Message als String
	 */
	public String encryption(String message, RSAKey publicKey){
		
		//erstellen eines intKryptArray mit S0 und S1 mit verschlüsseltem Text
		chiff = new BlockChiffrierVerfahren();
		chiff.encryption(message);
		//Verschlüsseln der Session Keys S0 und S1 mit RSA-Verfahren
		int rsaSessionKey0 = new RSAService().encrypt(publicKey, chiff.getKryptArray()[0]);
		int rsaSessionKey1 = new RSAService().encrypt(publicKey, chiff.getKryptArray()[1]);
		//umwandeln in eine vierstellige Zahl zur Basis 95
		toBase95(rsaSessionKey0, rsaSessionKey1);
		/**
		 * Wandeln Sie das Integer-Array intKryptArray um in ein char-Array
		 * oder String, indem Sie zu jeder Stelle 32 addieren und auf (char) casten.
		 */
		char[] charCryptedArray = new char[chiff.getKryptArray().length];
		for(int i=0;i<chiff.getKryptArray().length;i++){
		charCryptedArray[i] = (char) (chiff.getKryptArray()[i]+32);
		}
		return String.valueOf(charCryptedArray);
	}
	/**
	 * Speichert die rsaSessionKeys über vier indizes mit der Basis 95 in dem kryptArray ab
	 * 
	 * @param rsaSessionKey0
	 * @param rsaSessionKey1
	 */
	public void toBase95(int rsaSessionKey0, int rsaSessionKey1){
	
			chiff.getKryptArray()[0]=rsaSessionKey0/(int)(Math.pow(BASIS, 3));
			chiff.getKryptArray()[1]=(rsaSessionKey0%(int)(Math.pow(BASIS, 3)))/(int)(Math.pow(BASIS, 2));
			chiff.getKryptArray()[2]=(rsaSessionKey0%(int)(Math.pow(BASIS, 2)))/BASIS;
			chiff.getKryptArray()[3]=rsaSessionKey0%BASIS;
			
			chiff.getKryptArray()[4]=rsaSessionKey1/(int)(Math.pow(BASIS, 3));
			chiff.getKryptArray()[5]=(rsaSessionKey1%(int)(Math.pow(BASIS, 3)))/(int)(Math.pow(BASIS, 2));
			chiff.getKryptArray()[6]=(rsaSessionKey1%(int)(Math.pow(BASIS, 2)))/BASIS;
			chiff.getKryptArray()[7]=rsaSessionKey1%BASIS;
		
	}
	/**
	 * Decodiert eine Message
	 * 
	 * @param message als String
	 * @return decodierte Message als String
	 */
	public String decryption(String message, RSAKey privateKey ){
		char[] cryptedCharArray = new char[message.length()];
		cryptedCharArray = message.toCharArray();
		int[] cryptedIntArray = new int[cryptedCharArray.length];
		for(int i=0;i<cryptedCharArray.length;i++){
			cryptedIntArray[i]= cryptedCharArray[i]-32;
		}
		int rsaSessionKey0=(cryptedIntArray[0]*(int) Math.pow(BASIS, 3))
				+(cryptedIntArray[1]*(int) Math.pow(BASIS, 2))
				+(cryptedIntArray[2]*(int) Math.pow(BASIS, 1))
				+(cryptedIntArray[3]*(int) Math.pow(BASIS, 0));
		
		int rsaSessionKey1=(cryptedIntArray[4]*(int) Math.pow(BASIS, 3))
				+(cryptedIntArray[5]*(int) Math.pow(BASIS, 2))
				+(cryptedIntArray[6]*(int) Math.pow(BASIS, 1))
				+(cryptedIntArray[7]*(int) Math.pow(BASIS, 0));
		//Dechiffrieren Sie den Session-Key mit dem privaten Schlüssel des RSA-Verfahrens
		
		int sessionKey0 = new RSAService().decrypt(privateKey, rsaSessionKey0);
		int sessionKey1 = new RSAService().decrypt(privateKey, rsaSessionKey1);
		//Dechiffrieren Sie den Text mit dem Session-Key ??? Es sind doch zwei Session Keys???
		cryptedIntArray[0]=sessionKey0;
		cryptedIntArray[1]=sessionKey1;
		for(int i=2;i<=7;i++){
			cryptedIntArray[i]=0;
		}
		
		char[] cryptedCharArrayNew = new char[cryptedIntArray.length];
		for(int i=0;i<cryptedIntArray.length;i++){
			cryptedCharArrayNew[i]=(char) cryptedIntArray[i];
		}
		return chiff.decryption(String.valueOf(cryptedCharArrayNew));
		

		

	
		
	}
	
	
	
	public static void main(String[] args) {
		RSAKeyPair keys = new RSAService().genKeyPair();
		System.out.println("PrivateKey: "+keys.getPrivateKey());
		System.out.println("PublicKey: "+keys.getPublicKey());
		Hybrid hybrid = new Hybrid();
		//Wenn jemand mir eine Nachricht schicken will muss dieser Seinen Text mit dem PublicKey verschlüsseln
		//und anschließend die Nachricht versenden
		String cryptedMessage = hybrid.encryption("Hallo Phil wie geht es dir?",keys.getPublicKey());
		System.out.println("Kryptierte Nachricht: "+cryptedMessage);
		//Wenn ich die Nachicht erhalten habe, muss ich die Nachricht über den PrivateKey verschlüsseln
		String decryptedMessage = hybrid.decryption(cryptedMessage,keys.getPrivateKey());
		System.out.println("Decodierte Nachricht: "+decryptedMessage);

	}
}
