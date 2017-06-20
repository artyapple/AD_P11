package blockChieffrierung;

public class BlockChiffrierVerfahren {
	private final int BASIS = 95;
	private char[] charClearArray;
	private int[] intClearArray;
	private int[] intKryptArray;
	int firstKey;
	int secondKey;


//	public static void main(String[] args) {
//		BlockChiffrierVerfahren chiff = new BlockChiffrierVerfahren();
//		String encryptedMessage = chiff.encryption("Hallo, wie gehts dir?");
//		System.out.println(encryptedMessage);
//		String decryptedMessage = chiff.decryption(encryptedMessage);
//		System.out.println(decryptedMessage);
//		System.out.println("ENDE");
//	}

	public int[] getKryptArray() {
		return this.intKryptArray;
	}

	private void charToIntArray() {
		for (int i = 0; i < charClearArray.length; i++) {
			intClearArray[i] = charClearArray[i] - 32;
		}
	}

	private int createSessionKey() {
		int sessionKey = (int) (Math.random() * 93) + 1;
		return sessionKey;
	}
/**
 * 
 * Kryptierung einer Message durch ein symmetrisches Verfahren
 * 
 * @param message
 * @return kryptierte Message
 */
	public String encryption(String message) {
		charClearArray = message.toCharArray();
		intClearArray = new int[charClearArray.length];
		firstKey = createSessionKey();
		secondKey = createSessionKey();
		charToIntArray();
		intKryptArray = new int[intClearArray.length + 8];
		intKryptArray[0] = firstKey;
		intKryptArray[1] = secondKey;
		if (intClearArray[0] != 0) {
			for (int i = 0; i < intClearArray.length; i+=2) {
				intKryptArray[i + 8] = (intClearArray[i] + firstKey) % BASIS;
				if ((i + 1) < intClearArray.length) {
					intKryptArray[i + 1 + 8] = (intClearArray[i + 1] + secondKey) % BASIS;
				}
			}
		}
		return String.valueOf(intKryptArray);
	}

	/**
	 * 
	 * Dekryptierung
	 * 
	 * @param cryptedMessage
	 * @return
	 */
	public String decryption(String cryptedMessage) {
		char[] cryptedCharArray = new char[cryptedMessage.length()];
		cryptedCharArray = cryptedMessage.toCharArray();

		int[] cryptedIntArray = new int[cryptedCharArray.length];
		for (int i = 0; i < cryptedCharArray.length; i++) {
			cryptedIntArray[i] = cryptedCharArray[i];
		}
		int s0 = cryptedIntArray[0];
		int s1 = cryptedIntArray[1];
		char[] charClearArrayNew = new char[cryptedIntArray.length - 8];
		int[] intClearArray = new int[cryptedIntArray.length - 8];

		for (int i = 0; i < intClearArray.length; i+=2) {
			
			if (s0 > cryptedIntArray[i +8]) {
				intClearArray[i] = cryptedIntArray[i + 8] + BASIS - s0;
			} else {
				intClearArray[i] = cryptedIntArray[i + 8] - s0;
			}

			if ((i + 1) < intClearArray.length) {
				if (s1 > cryptedIntArray[i + 1 + 8]) {
					intClearArray[i + 1] = cryptedIntArray[i + 1 + 8] + BASIS - s1;
				} else {
					intClearArray[i + 1] = cryptedIntArray[i + 1 + 8] - s1;
				}

			}
		}
		for (int i = 0; i < intClearArray.length; i++) {
			charClearArrayNew[i] = (char) (intClearArray[i] + 32);
		}
		return String.valueOf(charClearArrayNew);
	}

	
}
