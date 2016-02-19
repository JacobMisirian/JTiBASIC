package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class StatementNode extends AstNode {
	public static AstNode parse(Parser parser) throws Exception {
		if (parser.matchToken(TokenType.Identifier, "If"))
			return ConditionalNode.parse(parser);
		else if (parser.matchToken(TokenType.Identifier, "Disp"))
			return DispNode.parse(parser);
		else
			return ExpressionNode.parse(parser);
	}
}
