package Tools;

public class Node {
	
	private int indexPlayer;
	private Type type;
	
	/**
	 * @param indexPlayer
	 * @param type
	 */
	public Node(int indexPlayer, Type type) {
		this.indexPlayer = indexPlayer;
		this.type = type;
	}
	
	/**
	 * @return the indexPlayer
	 */
	public int getIndexPlayer() {
		return indexPlayer;
	}
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	public boolean equals(Node n) {
		return (this.indexPlayer == n.getIndexPlayer() && this.type.equals(n.getType()));
	}
	
	public String toString() {
		String res = " Joueur : " + indexPlayer + " Type : " + type.toString();
		return res;
	}

}

