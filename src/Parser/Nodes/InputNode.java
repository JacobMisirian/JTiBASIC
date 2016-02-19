package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class InputNode extends AstNode {
	private String variable;
	public String getVariable() {
		return variable;
	}
	
	public InputNode(String variable) {
		this.variable = variable;
	}
	
	public static InputNode parse(Parser parser) throws ExpectedException {
		parser.expectToken(TokenType.Identifier, "Input");
		String variable = parser.expectToken(TokenType.Identifier).getValue();
		
		return new InputNode(variable);
	}
}
