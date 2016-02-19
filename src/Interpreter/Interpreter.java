package Interpreter;

import Lexer.TokenType;
import Parser.*;
import Parser.Nodes.*;

public class Interpreter {
	private AstNode ast;
	private int position;
	
	public void Interpret(AstNode tree) throws Exception {
		ast = tree;
		for (position = 0; position < tree.children.size(); position++)
			executeStatement(tree.children.get(position));
	}
	
	private void executeStatement(AstNode node) throws Exception {
		if (node instanceof DispNode) {
			ArgListNode args = ((DispNode)node).getArgs();
			for (int i = 0; i < args.children.size(); i++)
				System.out.print(evaluateNode(args.children.get(i)));
			System.out.println();
		} else
			evaluateNode(node);
	}
	
	private Object evaluateNode(AstNode node) throws Exception {
		if (node instanceof NumberNode)
			return ((NumberNode)node).getNumber();
		else if (node instanceof BinaryOpNode)
			return interpretBinaryOp((BinaryOpNode)node);
		else if (node instanceof StringNode)
			return ((StringNode)node).getString();
		else if (node instanceof IdentifierNode) {
			System.out.println(((IdentifierNode)node).getIdentifier());
			return null;
		}
		else
			throw new Exception("Unhandled node " + node);
	}
	
	private Object interpretBinaryOp(BinaryOpNode binNode) throws Exception {
		switch (binNode.getBinaryOperation()) {
			case Addition:
				return getLeft(binNode) + getRight(binNode);
			case Subtraction:
				return getLeft(binNode) - getRight(binNode);
			case Multiplication:
				return getLeft(binNode) * getRight(binNode);
			case Division:
				return getLeft(binNode) / getRight(binNode);
			case Modulus:
				return getLeft(binNode) % getRight(binNode);
			default:
				throw new Exception("Unhandled Binary Operation " + binNode.getBinaryOperation());
		}
	}
	
	private double getLeft(BinaryOpNode binNode) {
		return ((NumberNode)binNode.getLeft()).getNumber();
	}
	
	private double getRight(BinaryOpNode binNode) {
		return ((NumberNode)binNode.getRight()).getNumber();
	}
}
