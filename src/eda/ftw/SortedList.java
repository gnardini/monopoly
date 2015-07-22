package eda.ftw;

import java.util.Iterator;

public class SortedList<T extends Comparable<T>> implements Iterable<T>{

	private Node first;
	private int size;
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>(){
			Node node = first;
			@Override
			public boolean hasNext() {
				return node != null;
			}
			@Override
			public T next() {
				T elem = node.elem;
				node = node.next;
				return elem;
			}
			@Override
			public void remove() {}
		};
	}

	public T get(int position) {
		if(position >= size || position<0) throw new IllegalArgumentException("Indice invalido");
		return get(first, position);
	}
	
	private T get(Node node, int position){
		if(position == 0) return node.elem;
		return get(node.next, position-1);
	}
	
	public void remove(T elem){
		first = remove(first, elem);
	}
	
	private Node remove(Node node, T elem){
		if(node == null) return null;
		if(node.elem.equals(elem)){
			size--;
			return node.next;
		}
		node.next = remove(node.next, elem);
		return node;
	}

	public int size() {
		return size;
	}

	public void add(T elem) {
		first = add(elem, first);
		size++;
	}
	
	private Node add(T elem, Node node){
		if(node == null) return new Node(elem, null);
		
		if(elem.compareTo(node.elem) < 0)
			return new Node(elem, node);
		else
			node.next = add(elem, node.next);
		
		return node;
	}
	
	
	
	private class Node {
		T elem;
		Node next;
		
		public Node(T elem, Node next){
			this.elem = elem;
			this.next = next;
		}
	}
}
