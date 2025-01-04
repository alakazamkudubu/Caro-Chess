package caro;

public class ArrayOfNodes {
	private HitBox[] nodes;
	
	public ArrayOfNodes (HitBox[] nodes) {
		this.nodes = nodes;
	}
	
	public ArrayOfNodes () {
		this.nodes = null;
	}
	
	public HitBox[] getNodes() {
		return nodes;
	}
	
	public int getLength() {
		return nodes.length;
	}
	
	public Integer getNodeIndex(int x, int y) {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getCenterX() == x && nodes[i].getCenterY() == y) {
				return i;
			}
		}
		return null;
	}
	
	public HitBox getNode(int index) {
		return nodes[index];
	}
	
	public void addNode(HitBox node) {
		if (nodes == null) {
			nodes = new HitBox[] {node};
		}
		HitBox[] newArray = new HitBox[nodes.length+1];
		for (int i = 0; i < nodes.length; i++) {
			newArray[i] = nodes[i];
		}
		newArray[nodes.length] = node;
		nodes = newArray;
	}
	
	public void empty() {
		nodes = null;
	}
}
