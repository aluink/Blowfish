package net.aluink.rsa;

import java.math.BigInteger;

public class Key {
	BigInteger n;
	BigInteger e;
	public BigInteger getN() {
		return n;
	}
	public void setN(BigInteger n) {
		this.n = n;
	}
	public BigInteger getE() {
		return e;
	}
	public void setE(BigInteger e) {
		this.e = e;
	}
	public Key(BigInteger n, BigInteger e) {
		super();
		this.n = n;
		this.e = e;
	}
	
	
}
