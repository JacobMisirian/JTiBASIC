package Parser.Nodes;

import Parser.*;

public class StringNode extends AstNode {
	private String string;
	public String getString() {
		return string;
	}
	
	public StringNode(String string) {
		this.string = string;
	}
}
