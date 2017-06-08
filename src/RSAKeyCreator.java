import java.util.concurrent.ThreadLocalRandom;

public class RSAKeyCreator {

	public static void main(String[] args) {
		RSAKeyCreator r = new RSAKeyCreator();
		r.execute();
	}

	public void execute() {
		int p = getRandomPrime();
		int q = getRandomPrime();
		System.out.println("p: "+p);
		System.out.println("q: "+q);
		int n  = getModulValue(p, q);
		System.out.println("N: "+n);
		int eul = getEulerFuncValue(p, q);
		System.out.println("eul: "+eul);
		int e = getEValue(eul);
		System.out.println("E: "+e);
		System.out.println("ggt check: "+ggt(e, eul));
		int d = dBrutForce(e, eul);
		System.out.println("d: "+d);
		//extendedEuclid(3, 9167368);
		//6111579
		
		int testMessage = 3;
		int encMsg = encrypt(e, n, testMessage);
		System.out.println("encript: "+encMsg);
		System.out.println("desc: " + decryption(d, n, encMsg));
		
		
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
		int val = ThreadLocalRandom.current().nextInt(1, eulerValue);
		while(ggt(val, eulerValue)!=1){
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
	
	
	public int dBrutForce(int e, int eulerValue){
		int val = 1;
		
		while(!isMultInverse(val, e, eulerValue)){
			val++;
		}
		
		return val;
	}
	
	private boolean isMultInverse(int d, int e, int eulVal){
		double dd = d;
		double de = e;
		double dev = eulVal;
		return ((de*dd)%dev ==1);
	}
	
	public void extendedEuclid(int a, int b)
    {
        int x = 0, y = 1, lastx = 1, lasty = 0, temp;
        while (b != 0)
        {
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
        System.out.println("Roots  x : "+ lastx*a +" y :"+ lasty*b);
    }
	
	public int encrypt(int k, int mod, int msg){
		double dk = k;
		double dmod = mod;
		double dmsg = msg;
		return (int)(Math.pow(msg, dk)%dmod);
	} 
	
	public int decryption(int k, int mod, int crmsg){
		double dk = k;
		double dmod = mod;
		double dmsg = crmsg;
		return (int)(Math.pow(crmsg, dk)%dmod);
	}
}
