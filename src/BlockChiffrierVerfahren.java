
public class BlockChiffrierVerfahren {

	private char[] charClearArray;
	private int[] intClearArray;
	private int[] intKryptArray;
	int firstKey;
	int secondKey;
	private int[] intClearArrayNew;
	private char[] charClearArrayNew;
	

	public static void main(String[] args) {
		BlockChiffrierVerfahren chiff = new BlockChiffrierVerfahren("Hallo, wie gehts dir?");
		chiff.encryption();
		chiff.decryption();
		chiff.ausgeben();
		System.out.println("ENDE");
	}

	public BlockChiffrierVerfahren(String text) {
		this.charClearArray = text.toCharArray();
		this.intClearArray = new int[charClearArray.length];
		this.firstKey = createSessionKey();
		this.secondKey = createSessionKey();
	}

	private void charToIntArray() {
		for (int i = 0; i < charClearArray.length; i++) {
			intClearArray[i] = (int) charClearArray[i] - 32;
		}
	}

	private int createSessionKey() {
		int sessionKey = (int) (Math.random() * 93) + 1;
		return sessionKey;
	}

	private void encryption() {
		charToIntArray();
		intKryptArray = new int[intClearArray.length + 8];
		intKryptArray[0] = firstKey;
		intKryptArray[1] = secondKey;
		for (int i = 0; i < intClearArray.length; i++) {
			intKryptArray[i + 8] = (intClearArray[i] + firstKey) % 95;
			if ((i + 1) < intClearArray.length) {
				intKryptArray[i + 1 + 8] = (intClearArray[i + 1] + secondKey) % 95;
			}
		}
	}

	private void decryption() {
		intClearArrayNew = new int[intClearArray.length];
		charClearArrayNew = new char[charClearArray.length];
		for (int i = 0; i < intClearArray.length; i++) {
			intClearArrayNew[i] = (intKryptArray[i + 8] + (95 - firstKey))%95;
			if ((i + 1) < intClearArray.length) {
				intClearArrayNew[i + 1] = (intKryptArray[i + 1 + 8] + (95 - secondKey))%95;
			}
		}
		for (int j = 0; j < intClearArrayNew.length; j++) {
			
			charClearArrayNew[j] = (char) (intClearArrayNew[j]+32);
		}
	}
	
	public void ausgeben(){
		System.out.println(String.valueOf(charClearArrayNew));
	
	}
}
