package net.aluink.huffman;

import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import net.aluink.huffman.Node.Type;

public class Huffman {
	byte [] input;
	
	public Huffman(byte [] input){
		this.input = input;
	}
	
	Comparator<Node> comp = new Comparator<Node>(){
		@Override
		public int compare(Node n1, Node n2) {
			return Long.valueOf(n1.getWeight()).compareTo(Long.valueOf(n2.getWeight()));
		}
	};
	
	public EncodedStream encode(){
		Queue<Node> nodes = new PriorityQueue<Node>(1, comp); 
		long [] vals = new long[256];
		
		for(int i = 0;i < input.length;i++){
			vals[input[i]]++;
		}
		
		for(int i = 0; i < 256;i++){
			if(vals[i] > 0){
				nodes.add(new Node(Type.LEAF, null, null, (byte)i, vals[i]));
			}
		}
		
		Code [] codes = new Code[nodes.size()];
		while(nodes.size() > 1){
			Node n1 = nodes.remove();
			Node n2 = nodes.remove();
			nodes.add(new Node(Type.INTERNAL, n1,n2,null, n1.weight+n2.weight));
		}
		
		Node root = nodes.remove();
		Map<Byte,String> map = new HashMap<Byte,String>();
		getCodes(root, "", map);
		int j = 0;
		
		for(byte b : map.keySet()){
			Object [] v = makeByteCode(map.get(b));
			byte [] code = (byte[])v[0];
			Integer size = (Integer)v[1];
			codes[j++] = new Code(b,code,(byte)size.intValue());
		}
		StringBuilder sb = new StringBuilder("");
		for(int i = 0;i < input.length;i++){
			sb.append(map.get(input[i]));
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(sb.length()/8 + 1);
		byte b = 0x0;
		int i = 0;
		for(;i < sb.length();i++){
			b <<= 1;
			if(sb.charAt(i) == '1')
				b |= 0x1;
			if((i +1) % 8 == 0){
				out.write(b);
				b = 0x0;
			}
		}
		int m = 8 - (i%8); 
		b <<= m;
		out.write(b);
		System.out.println(input.length + " " + out.size());
		return new EncodedStream(codes, out.toByteArray());
	}
	
	
	
	private Object[] makeByteCode(String string) {
		int size = string.length()/8;
		if(string.length() % 8 != 0)
			size++;
		ByteOutputStream out = new ByteOutputStream(size);
		int i;
		byte b = 0x0;
		for(i = 0;i < string.length();i++){
			if(string.charAt(i) == '1')
				b |= 0x1 << (7-(i%8));
			if((i +1) % 8 == 0){
				out.write(b);
				b = 0x0;
			}
		}
		out.write(b);
		Object [] v = new Object[2];
		v[0] = out.toByteArray();
		v[1] = Integer.valueOf(string.length());
		return v;
	}



	private void getCodes(Node node, String prefix, Map<Byte, String> map) {
		if(node.type == Type.LEAF){
			map.put(node.value, prefix);
		} else {
			if(node.left != null)
				getCodes(node.left, prefix + "0", map);
			if(node.right != null)
				getCodes(node.right, prefix + "1", map);
		}
	}

	public byte[] decode(EncodedStream enc) {
		Node root;
//		if(enc.codes.length > 1)
			root = new Node(Type.INTERNAL);
		
		Node tmp;
		for(int i = 0;i < enc.codes.length;i++){
			tmp = root;
			Code c = enc.codes[i];
			for(int j = 0;j < c.size;j++){
				byte b = (byte) (c.code[j/8] >> (7-(j%8)) & 0x1);
				if(b == 0){
					if(tmp.left == null){
						tmp.left = new Node(Type.INTERNAL, null, null, (byte)0, 0L);
					}
					tmp = tmp.left;
				} else {
					if(tmp.right == null){
						tmp.right = new Node(Type.INTERNAL, null, null, (byte)0, 0L);
					}
					tmp = tmp.right;
				}
			}
			tmp.setType(Type.LEAF);
			tmp.setValue(c.value);
		}
		
		ByteOutputStream out = new ByteOutputStream();
		tmp = root;
		for(int i = 0;i < enc.encodedStream.length;i++){
			byte b = enc.encodedStream[i];
			for(int j = 0;j < 8;j++){
				byte bit = (byte) ((b >> (7-j)) & 0x1);
				if(bit == 1){
					tmp = tmp.right;
				} else {
					tmp = tmp.left;
				}
				if(tmp.type == Type.LEAF){
					out.write(tmp.value);
					tmp = root;
				}
			}
		}
		return out.toByteArray();
	}
	
	public static void main(String[] args) {
		Huffman h = new Huffman("In computer science and information theory, Huffman coding is an entropy encoding algorithm used for lossless data compression. The term refers to the use of a variable-length code table for encoding a source symbol (such as a character in a file) where the variable-length code table has been derived in a particular way based on the estimated probability of occurrence for each possible value of the source symbol. It was developed by David A. Huffman while he was a Ph.D. student at MIT, and published in the 1952 paper A Method for the Construction of Minimum-Redundancy Codes Huffman coding uses a specific method for choosing the representation for each symbol, resulting in a prefix code (sometimes called prefix-free codes, that is, the bit string representing some particular symbol is never a prefix of the bit string representing any other symbol) that expresses the most common source symbols using shorter strings of bits than are used for less common source symbols. Huffman was able to design the most efficient compression method of this type: no other mapping of individual source symbols to unique strings of bits will produce a smaller average output size when the actual symbol frequencies agree with those used to create the code. A method was later found to design a Huffman code in linear time if input probabilities (also known as weights) are sorted.[citation needed] For a set of symbols with a uniform probability distribution and a number of members which is a power of two, Huffman coding is equivalent to simple binary block encoding, e.g., ASCII coding. Huffman coding is such a widespread method for creating prefix codes that the term Huffman code is widely used as a synonym for prefix code even when such a code is not produced by Huffman's algorithm. Although Huffman's original algorithm is optimal for a symbol-by-symbol coding (i.e. a stream of unrelated symbols) with a known input probability distribution, it is not optimal when the symbol-by-symbol restriction is dropped, or when the probability mass functions are unknown, not identically distributed, or not independent (e.g., cat is more common than cta). Other methods such as arithmetic coding and LZW coding often have better compression capability: both of these methods can combine an arbitrary number of symbols for more efficient coding, and generally adapt to the actual input statistics, the latter of which is useful when input probabilities are not precisely known or vary significantly within the stream. However, the limitations of Huffman coding should not be overstated; it can be used adaptively, accommodating unknown, changing, or context-dependent probabilities. In the case of known independent and identically-distributed random variables, combining symbols together reduces inefficiency in a way that approaches optimality as the number of symbols combined increases.".getBytes());
		EncodedStream enc = h.encode();
		System.out.println((enc.codes.length*3) + " " + (enc.encodedStream.length));
		byte [] dec = h.decode(enc);
		System.out.println(h.input.length + " " + enc.size());
	}



	
	
}
