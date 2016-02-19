package Parser.Nodes;

import Lexer.TokenType;
import Parser.*;

public class ExpressionNode extends AstNode {
	public  static AstNode parse(Parser parser) throws Exception {
		return parseAssignment(parser);
	}
	
	private static AstNode parseAssignment(Parser parser) throws Exception {
        AstNode left = parseAdditive(parser);

        if (parser.acceptToken(TokenType.Assignment))
            return new BinaryOpNode(BinaryOperation.Assignment, left, parseAssignment(parser));
        else
            return left;
	}
	
    private static AstNode parseAdditive(Parser parser) throws Exception {
        AstNode left = parseMultiplicitive(parser);
        while (parser.matchToken(TokenType.Operation) || parser.matchToken(TokenType.Identifier, "and") || parser.matchToken(TokenType.Identifier, "or")) {
            switch (parser.currentToken().getValue()) {
                case "+":
                    parser.acceptToken(TokenType.Operation);
                    left = new BinaryOpNode(BinaryOperation.Addition, left, parseMultiplicitive(parser));
                    continue;
                case "-":
                    parser.acceptToken(TokenType.Operation);
                    left = new BinaryOpNode(BinaryOperation.Subtraction, left, parseMultiplicitive(parser));
                    continue;
                case "and":
                    parser.acceptToken(TokenType.Identifier);
                    left = new BinaryOpNode(BinaryOperation.And, left, parseMultiplicitive(parser));
                    continue;
                case "or":
                    parser.acceptToken(TokenType.Identifier);
                    left = new BinaryOpNode(BinaryOperation.Or, left, parseMultiplicitive(parser));
                    continue;
                default:
                    break;
            }
            break;
        }
        return left;
    }
	
	private static AstNode parseMultiplicitive(Parser parser) throws Exception {
		AstNode left = parseComparison(parser);
		while (parser.matchToken(TokenType.Operation)) {
			switch (parser.currentToken().getValue()) {
				case "*":
					parser.acceptToken(TokenType.Operation);
					left = new BinaryOpNode(BinaryOperation.Multiplication, left, parseComparison(parser));
					break;
                case "/":
                    parser.acceptToken(TokenType.Operation);
                    left = new BinaryOpNode(BinaryOperation.Division, left, parseComparison(parser));
                    continue;
                case "%":
                    parser.acceptToken(TokenType.Operation);
                    left = new BinaryOpNode(BinaryOperation.Modulus, left, parseComparison(parser));
                    continue;
                default:
                    break;
			}
			break;
		}
		return left;
	}
	
    private static AstNode parseComparison(Parser parser) throws Exception {
        AstNode left = parseFunctionCall(parser);
        while (parser.matchToken(TokenType.Comparison)) {
            switch ((parser.currentToken().getValue())) {
                case "=":
                    parser.acceptToken(TokenType.Comparison);
                    left = new BinaryOpNode(BinaryOperation.Equal, left, parseFunctionCall(parser));
                    continue;
                case "!=":
                    parser.acceptToken(TokenType.Comparison);
                    left = new BinaryOpNode(BinaryOperation.NotEqual, left, parseFunctionCall(parser));
                    continue;
                case ">":
                    parser.acceptToken(TokenType.Comparison);
                    left = new BinaryOpNode(BinaryOperation.GreaterThan, left, parseFunctionCall(parser));
                    continue;
                case "<":
                    parser.acceptToken(TokenType.Comparison);
                    left = new BinaryOpNode(BinaryOperation.LesserThan, left, parseFunctionCall(parser));
                    continue;
                case ">=":
                    parser.acceptToken(TokenType.Comparison);
                    left = new BinaryOpNode(BinaryOperation.GreaterThanOrEqual, left, parseFunctionCall(parser));
                    continue;
                case "<=":
                    parser.acceptToken(TokenType.Comparison);
                    left = new BinaryOpNode(BinaryOperation.LesserThanOrEqual, left, parseFunctionCall(parser));
                    continue;
                default:
                    break;
            }
            break;
        }
        return left;
    }
	
    private static AstNode parseFunctionCall(Parser parser) throws Exception {
        return parseFunctionCall(parser, parseTerm(parser));
    }
    private static AstNode parseFunctionCall(Parser parser, AstNode left) throws Exception {
        if (parser.matchToken(TokenType.Parentheses, "(")) {
            AstNode functionCall = parseFunctionCall(parser, new FunctionCallNode(left, ArgListNode.parse(parser)));
            //parser.ExpectToken(TokenType.Parentheses, ")");
            return functionCall;
        }
        else
            return left;
    }
	
	private static AstNode parseTerm(Parser parser) throws Exception {
        if (parser.matchToken(TokenType.Number))
            return new NumberNode(Double.valueOf(parser.expectToken(TokenType.Number).getValue()));
        else if (parser.acceptToken(TokenType.Parentheses, "(")) {
            AstNode statement = ExpressionNode.parse(parser);
            parser.expectToken(TokenType.Parentheses, ")");
            return statement;
        }
        else if (parser.matchToken(TokenType.Identifier, "Then")) {
            CodeBlockNode block = new CodeBlockNode();
            parser.expectToken(TokenType.Identifier, "Then");

            while (!parser.endOfStream() && !parser.matchToken(TokenType.Identifier, "EndIf") && !parser.matchToken(TokenType.Identifier, "Else"))
                block.children.add(StatementNode.parse(parser));

            if (parser.matchToken(TokenType.Identifier, "Else"))
                return block;

            parser.expectToken(TokenType.Identifier, "EndIf");

            return block;
        }
        else if (parser.matchToken(TokenType.Identifier, "Else")) {
            parser.expectToken(TokenType.Identifier, "Else");
            return StatementNode.parse(parser);
        }
        else if (parser.matchToken(TokenType.Identifier, "Do")) {
            CodeBlockNode block = new CodeBlockNode();
            parser.expectToken(TokenType.Identifier, "Do");

            while (!parser.endOfStream() && !parser.matchToken(TokenType.Identifier, "End"))
                block.children.add(StatementNode.parse(parser));

            parser.expectToken(TokenType.Identifier, "End");

            return block;
        }
        else if (parser.matchToken(TokenType.String))
            return new StringNode(parser.expectToken(TokenType.String).getValue());
        else if (parser.matchToken(TokenType.Identifier))
            return new IdentifierNode(parser.expectToken(TokenType.Identifier).getValue());
        else
            throw new Exception("Unexpected " + parser.currentToken().getTokenType() + " in Parser: " + parser.currentToken().getValue() + ".");
	}
}
