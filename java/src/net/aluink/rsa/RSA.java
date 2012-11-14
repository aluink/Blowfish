package net.aluink.rsa;

import java.math.BigInteger;

public class RSA {
	Key pu = null;
	Key pr = null;
	
	static final BigInteger TWO = new BigInteger("2");
	
	public RSA(Key pu, Key pr){
		this.pu = pu;
		this.pr = pr;
	}
	
	public RSA(BigInteger p, BigInteger q) throws Exception {
		BigInteger n = p.multiply(q);
		BigInteger t = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger e = t.subtract(BigInteger.ONE);
		for(;e.compareTo(BigInteger.ZERO) == 1 && e.gcd(t) == BigInteger.ONE;e = e.subtract(BigInteger.ONE));
		
		if(e.compareTo(BigInteger.ONE) == 0){
			throw new Exception("Can't find e");
		}
		
		BigInteger d = e.modInverse(t);
		pu = new Key(n,e);
		pr = new Key(n,d);
	}
	
	public byte [] encrypt(byte [] bytes){
		return exponentiate(new BigInteger(bytes), pu.e, pu.n).toByteArray();		
	}
	
	public byte [] decrypt(byte [] bytes){
		return exponentiate(new BigInteger(bytes), pr.e, pr.n).toByteArray();
	}
	
	private static BigInteger exponentiate(BigInteger m, BigInteger e, BigInteger n) {
		if(e.compareTo(BigInteger.ONE) == 0){
			return m.mod(n);
		}
		else if(e.mod(TWO).compareTo(BigInteger.ONE) == 0){ //ODD
			BigInteger tmp = exponentiate(m, e.subtract(BigInteger.ONE).divide(TWO), n);
			return tmp.multiply(tmp).multiply(m).mod(n);
		} else { //EVEN
			BigInteger tmp = exponentiate(m, e.divide(TWO), n);
			return tmp.multiply(tmp).mod(n);
		}
	}

	public static void main(String[] args) throws Exception {
		
		String m = "ABCDEFGH";
		BigInteger e = new BigInteger("17");
		BigInteger n = new BigInteger("3233");
		Key pr = new Key(n, new BigInteger("2753"));
		Key pu = new Key(n, e);
		RSA rsa = new RSA(new BigInteger("997427"), new BigInteger("997573"));
		byte [] c = rsa.encrypt(m.getBytes());
		System.out.println(m);
		System.out.println(new String(rsa.decrypt(c)));
	}
}