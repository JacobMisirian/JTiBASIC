package Parser.Nodes;

import java.util.ArrayList;

import Lexer.TokenType;
import Parser.*;

public class InputNode extends AstNode {
	private ArrayList<String> variables;
	public ArrayList<String> getVariable() {
		return variables;
	}
	
	public InputNode(ArrayList<String> variables) {
		this.variables = variables;
	}
	
	public static InputNode parse(Parser parser) throws ExpectedException, UnexpectedException {
		parser.expectToken(TokenType.Identifier, "Input");
		ArgListNode args = ArgListNode.parse(parser);
		ArrayList<String> variables = new ArrayList<String>();
		for (int i = 0; i < args.children.size(); i++)
			variables.add(((IdentifierNode)args.children.get(i)).getIdentifier());
		return new InputNode(variables);
	}
}
