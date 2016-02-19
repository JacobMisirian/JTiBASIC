package Parser.Nodes;

import Parser.AstNode;
import Parser.Parser;

public class CodeBlockNode extends AstNode {
	public static AstNode parse(Parser parser) throws Exception {
		CodeBlockNode block = new CodeBlockNode();
		
		while (!parser.endOfStream())
			block.children.add(StatementNode.parse(parser));
		return block;
	}
}
