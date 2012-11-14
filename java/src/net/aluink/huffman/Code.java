package net.aluink.huffman;

public class Code {
	byte value;
	byte [] code;
	byte size;
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}
	public byte[] getCode() {
		return code;
	}
	public void setCode(byte[] code) {
		this.code = code;
	}
	public byte getSize() {
		return size;
	}
	public void setSize(byte size) {
		this.size = size;
	}
	public Code(byte value, byte[] code, byte size) {
		super();
		this.value = value;
		this.code = code;
		this.size = size;
	}
	
}
