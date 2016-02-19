package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class ArgListNode extends AstNode {
	public static ArgListNode parse(Parser parser) throws ExpectedException, UnexpectedException {
		ArgListNode ret = new ArgListNode();
		ret.children.add(ExpressionNode.parse(parser));
		while (parser.acceptToken(TokenType.Comma))
			ret.children.add(ExpressionNode.parse(parser));
		return ret;
	}
}
