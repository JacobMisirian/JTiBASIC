package Parser.Nodes;

import Parser.*;

public class BinaryOpNode extends AstNode {
	private BinaryOperation binaryOperation;
	public BinaryOperation getBinaryOperation() {
		return binaryOperation;
	}
	private AstNode left;
	public AstNode getLeft() {
		return left;
	}
	private AstNode right;
	public AstNode getRight() {
		return right;
	}
	
	public BinaryOpNode(BinaryOperation binaryOperation, AstNode left, AstNode right) {
		this.binaryOperation = binaryOperation;
		this.left = left;
		this.right = right;
	}
}
