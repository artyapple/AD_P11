
public class PrimeCheck {
	
	public static boolean isPrime(int n) {

		boolean flag = true;

		for (int j = 2; j < n; j++) {
			if ((n % j == 0) && (j != n)) {
				flag = false;
			}
		}

		return flag;
	}

}
