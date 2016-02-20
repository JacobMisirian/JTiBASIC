package Parser.Nodes;

import java.util.ArrayList;

import Lexer.TokenType;
import Parser.*;

public class PromptNode extends AstNode {
	private ArrayList<String> variables;
	public ArrayList<String> getVariables() {
		return variables;
	}
	
	public PromptNode(ArrayList<String> variables) {
		this.variables = variables;
	}
	
	public static PromptNode parse(Parser parser) throws ExpectedException, UnexpectedException {
		parser.expectToken(TokenType.Identifier, "Prompt");
		ArgListNode args = ArgListNode.parse(parser);
		ArrayList<String> variables = new ArrayList<String>();
		for (int i = 0; i < args.children.size(); i++)
			variables.add(((IdentifierNode)args.children.get(i)).getIdentifier());
		
		return new PromptNode(variables);
	}
}
