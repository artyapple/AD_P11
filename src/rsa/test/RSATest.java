package rsa.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import rsa.keys.RSAKeyPair;
import rsa.services.RSAService;

public class RSATest {

	private final int testMessage = 217;
	private RSAService serv;
	private RSAKeyPair pair;
	private int encMessage;

	@Before
	public void before() {
		serv = new RSAService();
		pair = serv.genKeyPair();
	}

	@Test
	public void testRSA() {
		//verschlüsseln
		encMessage = serv.encrypt(pair.getPublicKey(), testMessage);
		//entschlüsseln
		int deMessage = serv.decrypt(pair.getPrivateKey(), encMessage);
		assertEquals(testMessage, deMessage);
	}

}
