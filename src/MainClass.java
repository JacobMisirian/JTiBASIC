import java.util.ArrayList;

import Lexer.Scanner;
import Lexer.Token;
import Parser.*;
import Interpreter.Interpreter;

public class MainClass {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		Interpreter interpreter = new Interpreter();
		while (true) {
			String source = scanner.nextLine();
			ArrayList<Token> tokens = new Scanner(source).scan();
	//		for (int i = 0; i < tokens.size(); i++)
		//		System.out.println(tokens.get(i).toString());
			
			interpreter.Interpret(new Parser(tokens).parse());
		}
	}
}
