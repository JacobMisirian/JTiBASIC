package Parser;

import Lexer.TokenType;

public class UnexpectedException extends Exception {
	public UnexpectedException() { super(); }
	public UnexpectedException(TokenType clazz) {
		super("Unexpected " + clazz);
	}
	public UnexpectedException(TokenType clazz, String value) {
		super("Unexpected " + clazz + ", " + value);
	}
}
