package csharp_text_highlighter.Controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.IntFunction;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lexical_parser.ILexer;
import lexical_parser.Lexer;
import lexical_parser.Enums.TokenKind;
import lexical_parser.Helpers.LexerHelper;
import lexical_parser.Models.Location;
import lexical_parser.Models.Token;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleClassedTextArea;

public class MainController implements Initializable
{
	private ILexer lexer;
	
	private Map<TokenKind, String> styleClassNameMap;
	
	@FXML
	private StyleClassedTextArea textArea;
	
	public MainController()
	{
		this.lexer = new Lexer();
		this.lexer.setKeywords(LexerHelper.Keywords);
		
		this.styleClassNameMap = new HashMap<TokenKind, String>() {
			{
				this.put(TokenKind.CharacterLiteral, "token-characterLiteral");
				this.put(TokenKind.Comment, "token-comment");
				this.put(TokenKind.Identifier, "token-identifier");
				this.put(TokenKind.IntegerLiteral, "token-integerLiteral");
				this.put(TokenKind.Keyword, "token-keyword");
				this.put(TokenKind.Operator, "token-operator");
				this.put(TokenKind.PreprocessingDirective,
					"token-preprocessingDirective");
				this.put(TokenKind.Punctuator, "token-punctuator");
				this.put(TokenKind.RealLiteral, "token-realLiteral");
				this.put(TokenKind.StringLiteral, "token-stringLiteral");
				this.put(TokenKind.Unknown, "token-unknown");
			}
		};
	}
	
	public String getTokenStyleClassName(TokenKind tokenKind)
	{
		String className = "token-default";
		
		if (this.styleClassNameMap.containsKey(tokenKind))
		{
			className = this.styleClassNameMap.get(tokenKind);
		}

		return className;
	}

	private void highlightTokensInTextArea(String text)
	{
		this.textArea.clearStyle(0, text.length());

		this.lexer.setSource(text);
		this.lexer.parse();
		
		for (Token token : this.lexer.getTokens())
		{
			Location location = token.getLocation();
			
			this.textArea.setStyleClass(location.getOffset(),
				location.getOffset() + location.getLength(),
				this.getTokenStyleClassName(token.getKind()));
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		this.textArea.textProperty().addListener((obs, oldText, newText) -> {
			this.highlightTokensInTextArea(newText);
		});

		IntFunction<String> format = (digits -> " %" + digits + "d ");

		this.textArea.setParagraphGraphicFactory(LineNumberFactory.get(
			this.textArea, format));
	}
}
