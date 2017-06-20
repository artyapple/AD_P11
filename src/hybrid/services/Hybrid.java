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
		//System.out.println("RSASessionKey0: "+rsaSessionKey0);
		int rsaSessionKey1=(cryptedIntArray[4]*(int) Math.pow(BASIS, 3))
				+(cryptedIntArray[5]*(int) Math.pow(BASIS, 2))
				+(cryptedIntArray[6]*(int) Math.pow(BASIS, 1))
				+(cryptedIntArray[7]*(int) Math.pow(BASIS, 0));
		//Dechiffrieren Sie den Session-Key mit dem privaten Schlüssel des RSA-Verfahrens
		//System.out.println("RSASessionKey1: "+rsaSessionKey1);
		int sessionKey0 = new RSAService().decrypt(privateKey, rsaSessionKey0);
		//System.out.println("SessionKey0: "+sessionKey0);
		
		int sessionKey1 = new RSAService().decrypt(privateKey, rsaSessionKey1);
		//System.out.println("SessionKey1: "+sessionKey1);
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
		//RSAKeyPair keys = new RSAService().genKeyPair();
		//System.out.println("PrivateKey: "+keys.getPrivateKey());
		//System.out.println("PublicKey: "+keys.getPublicKey()+"\nPublic exp: "+keys.getPublicKey().getExp()
		//		+"\nPublic N: "+keys.getPublicKey().getMod());
		//System.out.println("PrivateKey: "+keys.getPrivateKey());
		//System.out.println("PrivateKey: "+keys.getPrivateKey()+"\nPrivate exp: "+keys.getPrivateKey().getExp()
		//		+"\nPrivate N: "+keys.getPrivateKey().getMod());
		
		Hybrid hybrid = new Hybrid();
		//Wenn jemand mir eine Nachricht schicken will muss dieser Seinen Text mit dem PublicKey verschlüsseln
		//und anschließend die Nachricht versenden
		
		
		
		RSAKey keyPublicLuis = new RSAKey(3189, 46117);
		RSAKey keyPublicRobert = new RSAKey(37, 38021);
		RSAKey keyPrivateLuis = new RSAKey(12249, 46117);
		
		RSAKey keyPublicHorny = new RSAKey(46823, 54889);
		
		RSAKey keyPrivateOWNER_OLD = new RSAKey(8395, 28103);
		
		RSAKey keyPublicOWNER = new RSAKey(53, 39917);
		RSAKey keyPrivateOWNER = new RSAKey(30569, 39917);
		
		String cryptedMessage = hybrid.encryption("consetetur sadipscing elitr",keyPublicRobert);
		System.out.println("Kryptierte Nachricht: "+cryptedMessage);
		//Wenn ich die Nachicht erhalten habe, muss ich die Nachricht über den PrivateKey verschlüsseln
		
		String decryptedMessage = hybrid.decryption(" #^i \",Lj!1v,1(\"2',1#!+!112z31 ~$&J1\"!-%$&$&4$>% u(\"2t( &1$}(&1=>%$u>u(r,1-!-',+>v($,!#13v,\".$>z-((u4 314&>} s.$$1$&>u.}.$$1,r&  1 }(#4+ ~>v1r3=>%$u>u(r,15!+'/&4rL1_&>($$.1$!21$&>r\"t4% ~>v31)'2&.1#'.1#!+!1v21$&>v 11v!',?>d3v31\"}(& 1*r2u>x4s$$&$$ J1-!>%$r>& |(~ & 12r-t3'21$%31j!1v,1(\"2',1#!+!112z31 ~$&L1j!1v,1(\"2',1#!+!112z31 ~$&J1\"!-%$&$&4$>% u(\"2t( &1$}(&1=>%$u>u(r,1-!-',+>v($,!#13v,\".$>z-((u4 314&>} s.$$1$&>u.}.$$1,r&  1 }(#4+ ~>v1r3=>%$u>u(r,15!+'/&4rL1_&>($$.1$!21$&>r\"t4% ~>v31)'2&.1#'.1#!+!1v21$&>v 11v!',?>d3v31\"}(& 1*r2u>x4s$$&$$ J1-!>%$r>& |(~ & 12r-t3'21$%31j!1v,1(\"2',1#!+!112z31 ~$&L",keyPrivateOWNER);
		System.out.println("Decodierte Nachricht: "+decryptedMessage);

	}
}
