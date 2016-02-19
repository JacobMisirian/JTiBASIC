package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class WhileNode extends AstNode {
	private AstNode predicate;
	public AstNode getPredicate() {
		return predicate;
	}
	private AstNode body;
	public AstNode getBody() {
		return body;
	}
	
	public WhileNode(AstNode predicate, AstNode body) {
		this.predicate = predicate;
		this.body = body;
	}
	
	public static WhileNode parse(Parser parser) throws ExpectedException, UnexpectedException {
		parser.expectToken(TokenType.Identifier, "While");
		AstNode predicate = ExpressionNode.parse(parser);
		AstNode body = StatementNode.parse(parser);
		
		return new WhileNode(predicate, body);
	}
}
