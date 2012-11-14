package net.aluink.huffman;

public class EncodedStream {
	Code [] codes;
	byte [] encodedStream;
	public Code[] getCodes() {
		return codes;
	}
	public void setCodes(Code[] codes) {
		this.codes = codes;
	}
	public byte[] getEncodedStream() {
		return encodedStream;
	}
	public void setEncodedStream(byte[] encodedStream) {
		this.encodedStream = encodedStream;
	}
	
	public int size(){
		int sum = encodedStream.length;
		for(int i = 0;i < codes.length;i++){
			sum += codes[i].code.length + 2;
		}
		return sum;
	}
	
	public EncodedStream(Code[] codes, byte[] encodedStream) {
		super();
		this.codes = codes;
		this.encodedStream = encodedStream;
	}
	
}
