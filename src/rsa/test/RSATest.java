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
	private BigInteger encMessage;

	@Before
	public void before() {
		serv = new RSAService();
		pair = serv.genKeyPair();
	}

	@Test
	public void testRSA() {
		//verschlüsseln
		encMessage = serv.encrypt(pair.getSeckey(), testMessage);
		//entschlüsseln
		int deMessage = serv.decrypt(pair.getOpenkey(), encMessage);
		assertEquals(testMessage, deMessage);
	}

}
