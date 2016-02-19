package Interpreter;

import java.util.HashMap;
import java.util.Map;

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
			String identifier = ((IdentifierNode)node).getIdentifier();
			if (!variables.containsKey(identifier))
				throw new Exception("Unknown identifier " + identifier);
			return variables.get(identifier);
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
			case Assignment:
				String variable = ((IdentifierNode)binNode.getRight()).getIdentifier();
				Object value = evaluateNode(binNode.getLeft());
				if (variables.containsKey(variable))
					variables.remove(variable);
				variables.put(variable, value);
				return value;
			case Equal:
				return evaluateNode(binNode.getLeft()).hashCode() == evaluateNode(binNode.getRight()).hashCode();
			case NotEqual:
				return evaluateNode(binNode.getLeft()).hashCode() != evaluateNode(binNode.getRight()).hashCode();
			case GreaterThan:
				return getLeft(binNode) > getRight(binNode);
			case LesserThan:
				return getLeft(binNode) < getRight(binNode);
			case GreaterThanOrEqual:
				return getLeft(binNode) >= getRight(binNode);
			case LesserThanOrEqual:
				return getLeft(binNode) <= getRight(binNode);
            case And:
                return (boolean)evaluateNode(binNode.getLeft()) && (boolean)evaluateNode(binNode.getRight());
            case Or:
                return (boolean)evaluateNode(binNode.getLeft()) || (boolean)evaluateNode(binNode.getRight());
			default:
				throw new Exception("Unhandled Binary Operation " + binNode.getBinaryOperation());
		}
	}
	
	private double getLeft(BinaryOpNode binNode) throws NumberFormatException, Exception {
		return Double.valueOf(evaluateNode(binNode.getLeft()).toString());
	}
	
	private double getRight(BinaryOpNode binNode) throws NumberFormatException, Exception {
		return Double.valueOf(evaluateNode(binNode.getRight()).toString());
	}
	
	private Map<String, Object> variables = new HashMap<String, Object>();
}
