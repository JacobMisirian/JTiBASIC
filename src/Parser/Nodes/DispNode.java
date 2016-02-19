package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class DispNode extends AstNode {
	private ArgListNode args;
	public ArgListNode getArgs() {
		return args;
	}
	
	public DispNode(ArgListNode args) {
		this.args = args;
	}
	
	public static DispNode parse(Parser parser) throws Exception {
		parser.expectToken(TokenType.Identifier, "Disp");
		ArgListNode args = ArgListNode.parse(parser);
		return new DispNode(args);
	}
}
