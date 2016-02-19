package Parser;

import java.util.ArrayList;
import Lexer.Token;
import Lexer.TokenType;
import Parser.Nodes.*;

public class Parser {
	private ArrayList<Token> tokens;
	private int position = 0;
	
	public Parser(ArrayList<Token> tokens) {
		this.tokens = tokens;
	}
	
	public AstNode parse() throws Exception {
		CodeBlockNode tree = new CodeBlockNode();
		while (!endOfStream())
			tree.children.add(StatementNode.parse(this));
		
		return tree;
	}
	
	public boolean endOfStream() {
		return tokens.size() <= position;
	}
	
	public boolean matchToken(TokenType clazz) {
		return position < tokens.size() && tokens.get(position).getTokenType() == clazz;
	}
	public boolean matchToken(TokenType clazz, String value) {
		return position < tokens.size() && tokens.get(position).getTokenType() == clazz && tokens.get(position).getValue().equals(value);
	}
	
    public boolean acceptToken(TokenType clazz)
    {
        if (matchToken(clazz)) {
            position++;
            return true;
        }

        return false;
    }
	public boolean acceptToken(TokenType clazz, String value) {
        if (matchToken(clazz, value)) {
            position++;
            return true;
        }

        return false;
	}
	
	public Token expectToken(TokenType clazz) throws ExpectedException {
        if (!matchToken(clazz))
            throw new ExpectedException(clazz, currentToken().getTokenType());

        return tokens.get(position++);
	}
	public Token expectToken(TokenType clazz, String value) throws ExpectedException {
        if (!matchToken(clazz, value))
            throw new ExpectedException(clazz, currentToken().getTokenType(), value, currentToken().getValue());

        return tokens.get(position++);
	}
	
	public Token currentToken() {
		return tokens.get(position);
	}
}
