package rsa.services;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import rsa.keys.RSAKey;
import rsa.keys.RSAKeyPair;

public class RSAService {
	
	public RSAKeyPair genKeyPair() {
		int p = getRandomPrime();
		int q = getRandomPrime();
		int n = getModulValue(p, q);
		int eul = getEulerFuncValue(p, q);
		int e = getEValue(eul);
		int d = dBrutForce(e, eul);
		RSAKey openkey = new RSAKey(e, n);
		RSAKey seckey = new RSAKey(d, n);
		return new RSAKeyPair(openkey, seckey);
	}
	
	public int encrypt(RSAKey key, int msg) {
		BigInteger bi1, bi2, bi3;
		bi1 = BigInteger.valueOf(msg);
		bi3 = BigInteger.valueOf(key.getMod());
		bi2 = bi1.pow(key.getExp());
		return bi2.mod(bi3).intValue();
	}

	public int decrypt(RSAKey key, int crmsg) {
		BigInteger bi1, bi2, bi3;
		bi1 = BigInteger.valueOf(crmsg);
		bi3 = BigInteger.valueOf(key.getMod());
		bi2 = bi1.pow(key.getExp());
		bi2 = bi2.mod(bi3);
		return bi2.intValue();
	}

	private int getRandomPrime() {
		int val = ThreadLocalRandom.current().nextInt(101, 500);
		while (!PrimeCheck.isPrime(val)) {
			val++;
		}
		return val;
	}

	private int getModulValue(int p, int q) {
		return p * q;
	}

	private int getEulerFuncValue(int p, int q) {
		return (p - 1) * (q - 1);
	}

	private int getEValue(int eulerValue) {
		int val = ThreadLocalRandom.current().nextInt(1, 100);
		while (ggt(val, eulerValue) != 1) {
			val++;
		}
		return val;
	}

	private int ggt(int m, int n) {
		while (n != 0) {
			if (m > n) {
				m = m - n;
			} else {
				n = n - m;
			}
		}
		return m;
	}

	public int dBrutForce(int e, int eulerValue) {
		int val = 1;

		while (!isMultInverse(val, e, eulerValue)) {
			val++;
		}

		return val;
	}

	private boolean isMultInverse(int d, int e, int eulVal) {
		double dd = d;
		double de = e;
		double dev = eulVal;
		return ((de * dd) % dev == 1);
	}

	public void extendedEuclid(int a, int b) {
		int x = 0, y = 1, lastx = 1, lasty = 0, temp;
		while (b != 0) {
			int q = a / b;
			int r = a % b;

			a = b;
			b = r;

			temp = x;
			x = lastx - q * x;
			lastx = temp;

			temp = y;
			y = lasty - q * y;
			lasty = temp;
		}
	}

	
}
