package Parser.Nodes;

import Parser.*;

public class NumberNode extends AstNode {
	private double number;
	public double getNumber() {
		return number;
	}
	
	public NumberNode(double number) {
		this.number = number;
	}
}
