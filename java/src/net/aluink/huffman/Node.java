package net.aluink.huffman;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
	enum Type {
		LEAF,INTERNAL;
	};
	
	Type type;
	Node left;
	Node right;
	byte value;
	long weight;
	
	public Node(Type t, Node left, Node right, Byte value, long w){
		this.type = t;
		this.left = left;
		this.right = right;
		if(value != null)this.value = value;
		this.weight = w;
	}
	
	public Node(Type t) {
		this.type = t;
		left = null;
		right = null;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public byte getValue() {
		return value;
	}
	public void setValue(byte value) {
		this.value = value;
	}
	public long getWeight() {
		return weight;
	}
	public void setWeight(long weight) {
		this.weight = weight;
	}
	
	public String toString(){
		return "(" + value + "," + weight + ")";
	}
	
	public void printTree(){
		Queue<Node> q = new LinkedList<Node>();
		int c,i;
		i = 1;
		c = 2;
		q.add(this);
		while(!q.isEmpty()){
			if(i++ == c){
				System.out.println();
				c *= 2;
			}
			Node n = q.remove();
			System.out.print(n + "  ");
			if(n.left != null)
				q.add(n.left);
			if(n.right != null)
				q.add(n.right);			
		}
	}
	
	
	
}
