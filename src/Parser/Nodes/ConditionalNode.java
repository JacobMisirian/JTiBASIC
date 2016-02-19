package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class ConditionalNode extends AstNode {
	public AstNode getPredicate() {
		return children.get(0);
	}
	public AstNode getBody() {
		return children.get(1);
	}
	public AstNode getElseBody() {
		return children.get(2);
	}
	
	public ConditionalNode(AstNode predicate, AstNode body) {
		children.add(predicate);
		children.add(body);
	}
	public ConditionalNode(AstNode predicate, AstNode body, AstNode elseBody) {
		children.add(predicate);
		children.add(body);
		children.add(elseBody);
	}
	
	public static ConditionalNode parse(Parser parser) throws Exception {
		parser.expectToken(TokenType.Identifier, "If");
		AstNode predicate = ExpressionNode.parse(parser);
		AstNode body = ExpressionNode.parse(parser);
		
		if (parser.matchToken(TokenType.Identifier, "Else"))
			return new ConditionalNode(predicate, body, StatementNode.parse(parser));
		return new ConditionalNode(predicate, body);
	}
}
