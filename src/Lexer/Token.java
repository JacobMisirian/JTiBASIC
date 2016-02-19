package Lexer;

public class Token {
	private TokenType tokenType;
	public TokenType getTokenType() {
		return tokenType;
	}
	
	private String value;
	public String getValue() {
		return value;
	}
	
	public Token(TokenType tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}
	
	public String toString() {
		return tokenType + "\t" + value;
	}
}