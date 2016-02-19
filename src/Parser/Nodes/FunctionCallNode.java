package Parser.Nodes;

import Parser.*;

public class FunctionCallNode extends AstNode {
	private AstNode target;
	public AstNode getTarget() {
		return target;
	}
	private AstNode arguments;
	public AstNode getArguments() {
		return arguments;
	}
	
	public FunctionCallNode(AstNode target, AstNode arguments) {
		this.target = target;
		this.arguments = arguments;
	}
}
