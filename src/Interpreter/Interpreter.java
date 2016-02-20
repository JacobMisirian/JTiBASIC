package Interpreter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Lexer.TokenType;
import Parser.*;
import Parser.Nodes.*;

public class Interpreter {
	private AstNode ast;
	private int position;
	private java.util.Scanner scanner = new java.util.Scanner(System.in);
	
	public void Interpret(AstNode tree) throws Exception {
		ast = tree;
		for (position = 0; position < tree.children.size(); position++)
			executeStatement(tree.children.get(position));
	}
	
	private void executeStatement(AstNode node) throws Exception {
		if (node instanceof CodeBlockNode) {
			CodeBlockNode block = (CodeBlockNode)node;
			for (int i = 0; i < block.children.size(); i++)
				executeStatement(block.children.get(i));
		}
		else if (node instanceof DispNode) {
			ArgListNode args = ((DispNode)node).getArgs();
			for (int i = 0; i < args.children.size(); i++)		
				System.out.print(evaluateNode(args.children.get(i)));
			System.out.println();
		}
		else if (node instanceof InputNode) {
			ArrayList<String> variables = ((InputNode)node).getVariable();
			for (int i = 0; i < variables.size(); i++) {
				if (this.variables.containsKey(variables.get(i)))
					this.variables.remove(variables.get(i));
				this.variables.put(variables.get(i), scanner.nextLine());
			}
		}
		else if (node instanceof PromptNode) {
			ArrayList<String> variables = ((PromptNode)node).getVariables();
			for (int i = 0; i < variables.size(); i++) {
				if (this.variables.containsKey(variables.get(i)))
					this.variables.remove(variables.get(i));
				System.out.print(variables.get(i) + "? ");
				this.variables.put(variables.get(i), scanner.nextLine());
			}
		}
		else if (node instanceof ConditionalNode) {
			ConditionalNode ifNode = (ConditionalNode)node;
			boolean eval = (boolean)evaluateNode(ifNode.getPredicate());
			if (eval)
				executeStatement(ifNode.getBody());
			else if (!eval && ifNode.children.size() >= 3)
				executeStatement(ifNode.getElseBody());
		}
		else if (node instanceof WhileNode) {
			WhileNode whileNode = (WhileNode)node;
			while ((boolean)evaluateNode(whileNode.getPredicate()))
				executeStatement(whileNode.getBody());
		}
		else if (node instanceof RepeatNode) {
			RepeatNode repeatNode = (RepeatNode)node;
			do {
				executeStatement(repeatNode.getBody());
			} while (!(boolean)evaluateNode(repeatNode.getPredicate()));
		}
		else
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
		else if (node instanceof GetKeyNode) 
			return new BufferedReader(new InputStreamReader(System.in)).read();
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
