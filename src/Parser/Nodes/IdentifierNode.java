package Parser.Nodes;

import Parser.*;

public class IdentifierNode extends AstNode {
	private String identifier;
	public String getIdentifier() {
		return identifier;
	}
	
	public IdentifierNode(String identifier) {
		this.identifier = identifier;
	}
}
