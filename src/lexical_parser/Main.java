package lexical_parser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lexical_parser.Helpers.ExceptionHelper;
import lexical_parser.Models.Lexeme;

public class Main
{
	public static void main(String[] args)
	{
		ILexer lexer = new Lexer();
		
		try
		{
			Path path = Paths.get(args[0]);

			List<String> lines = Files.readAllLines(path,
				StandardCharsets.UTF_8);
			
			StringBuilder stringBuilder = new StringBuilder();
			
			for (String line : lines)
			{
				stringBuilder.append(line);
				stringBuilder.append(System.getProperty("line.separator"));
			}
			
			String source = stringBuilder.toString().trim();
			
			lexer.setSource(source);
			
			lexer.parse();
			
			Iterable<Lexeme> lexemes = lexer.getLexemes();

			for (Lexeme lexeme : lexemes)
			{
				System.out.println(String.format("%1$s (%2$s)", lexeme.getValue(), lexeme.getKind().name()));
			}
		}
		catch (Exception exception)
		{
			System.out.println("Error occured:");
			System.out.println(ExceptionHelper
					.getFullExceptionMessage(exception));
		}
	}
}
