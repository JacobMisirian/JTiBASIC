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
		Scanner scanner = new Scanner("Disp \"Hello, World!\"");
		ArrayList<Token> tokens = scanner.scan();
		Interpreter interpreter = new Interpreter();
		for (int i = 0; i < tokens.size(); i++)
			System.out.println(tokens.get(i).toString());
		
		interpreter.Interpret(new Parser(tokens).parse());
	}
}
