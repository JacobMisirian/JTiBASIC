package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class RepeatNode extends AstNode {
	private AstNode predicate;
	public AstNode getPredicate() {
		return predicate;
	}
	private AstNode body;
	public AstNode getBody() {
		return body;
	}
	
	public RepeatNode(AstNode predicate, AstNode body) {
		this.predicate = predicate;
		this.body = body;
	}
	
	public static RepeatNode parse(Parser parser) throws ExpectedException, UnexpectedException {
		parser.expectToken(TokenType.Identifier, "Repeat");
		AstNode predicate = ExpressionNode.parse(parser);
		AstNode body = ExpressionNode.parse(parser);
		
		return new RepeatNode(predicate, body);
	}
}
