package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class StatementNode extends AstNode {
	public static AstNode parse(Parser parser) throws ExpectedException, UnexpectedException {
		if (parser.matchToken(TokenType.Identifier, "If"))
			return ConditionalNode.parse(parser);
		else if (parser.matchToken(TokenType.Identifier, "While"))
			return WhileNode.parse(parser);
		else if (parser.matchToken(TokenType.Identifier, "Repeat"))
			return RepeatNode.parse(parser);
		else if (parser.matchToken(TokenType.Identifier, "Disp"))
			return DispNode.parse(parser);
		else if (parser.matchToken(TokenType.Identifier, "Input"))
			return InputNode.parse(parser);
		else if (parser.matchToken(TokenType.Identifier, "Prompt"))
			return PromptNode.parse(parser);
		else
			return ExpressionNode.parse(parser);
	}
}
