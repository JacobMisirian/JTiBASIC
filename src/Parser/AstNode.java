package Parser;

import java.util.ArrayList;

public abstract class AstNode {
	public ArrayList<AstNode> children;
	
	public AstNode() {
		children = new ArrayList<AstNode>();
	}
}
