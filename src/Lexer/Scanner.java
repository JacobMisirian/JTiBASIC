package Lexer;

import java.util.ArrayList;
import java.io.Console;
import java.lang.Character;

public class Scanner {
	private String code;
	private int position = 0;
	private ArrayList<Token> result = new ArrayList<Token>();
	
	public Scanner(String source) {
		code = source;
	}
	
	public ArrayList<Token> scan() {
		whiteSpace();
		while (peekChar() != -1) {
			if (Character.isLetterOrDigit(peekChar()))
				result.add(scanData());
			else {
				switch (peekChar()) {
					case '\"':
						result.add(scanString());
						break;
					case ',':
						result.add(new Token(TokenType.Comma, Character.valueOf((char) readChar()).toString()));
						break;
					case '<':
						position++;
						if (peekChar() == '=') {
							result.add(new Token(TokenType.Comparison, "<="));
							position++;
						} else
							result.add(new Token(TokenType.Comparison, "<"));
						break;
					case '>':
						position++;
						if (peekChar() == '=') {
							result.add(new Token(TokenType.Comparison, ">="));
							position++;
						} else
							result.add(new Token(TokenType.Comparison, ">"));
						break;
					case '=':
						result.add(new Token(TokenType.Comparison, Character.valueOf((char) readChar()).toString()));
						break;
					case '+':
					case '*':
					case '/':
						result.add(new Token(TokenType.Operation, Character.valueOf((char) readChar()).toString()));
						break;
					case '(':
					case ')':
						result.add(new Token(TokenType.Parentheses, Character.valueOf((char) readChar()).toString()));
						break;
					case '-':
						position++;
						if (peekChar() == '>') {
							result.add(new Token(TokenType.Assignment, "->"));
							position++;
						}
						result.add(new Token(TokenType.Operation, "-"));
						break;
				}
			}
			whiteSpace();
		}
		return result;
	}
	
	private Token scanData() {
		String res = "";
		while (peekChar() != -1 && Character.isLetterOrDigit(peekChar()))
			res += Character.valueOf((char) readChar()).toString();
		try { 
			return new Token(TokenType.Number, Integer.valueOf(res).toString());
		} catch (Exception ex) {
			return new Token(TokenType.Identifier, res);
		}
	}
	
	private Token scanString() {
		String res = "";
		position++;
		while (peekChar() != -1 && peekChar() != '\"')
			res += Character.valueOf((char) readChar()).toString();
		position++;
		
		return new Token(TokenType.String, res);
	}
	
	private void whiteSpace() {
		while (Character.isWhitespace(peekChar()))
			position++;
	}
	
	private int peekChar() {
		if (code.length() > position)
			return code.charAt(position);
		return -1;
	}
	private int peekChar(int n) {
		if (code.length() > position + n)
			return code.charAt(position + n);
		return -1;
	}
	
	private int readChar() {
		if (code.length() > position)
			return code.charAt(position++);
		return -1;
	}
}
