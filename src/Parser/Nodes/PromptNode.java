package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class PromptNode extends AstNode {
	private String variable;
	public String getVariable() {
		return variable;
	}
	
	public PromptNode(String variable) {
		this.variable = variable;
	}
	
	public static PromptNode parse(Parser parser) throws ExpectedException {
		parser.expectToken(TokenType.Identifier, "Prompt");
		String variable = parser.expectToken(TokenType.Identifier).getValue();
		
		return new PromptNode(variable);
	}
}
