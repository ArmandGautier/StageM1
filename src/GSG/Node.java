package GSG;

public class Node {
	
	private int indexPlayer;
	private String type;
	/**
	 * @param indexPlayer
	 * @param type
	 */
	public Node(int indexPlayer, String type) {
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
	public String getType() {
		return type;
	}

}
