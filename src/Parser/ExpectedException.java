package Parser;

import Lexer.TokenType;

public class ExpectedException extends Exception{
	public ExpectedException() { super(); }
	public ExpectedException(TokenType expectedClazz, TokenType gotClazz) {
		super("Expected " + expectedClazz + ", Got " + gotClazz);
	}
	public ExpectedException(TokenType expectedClazz, TokenType gotClazz, String expectedValue, String gotValue) {
		super("Expected " + expectedClazz + " " + gotClazz + ", Got " + gotClazz + " " + gotValue);
	}
}