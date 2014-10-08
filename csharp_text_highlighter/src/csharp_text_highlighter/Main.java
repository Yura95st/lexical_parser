package csharp_text_highlighter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(Main.class.getResource("Views/MainView.fxml"));
		
		Scene scene = new Scene(root, 600, 400);

		primaryStage.setTitle("C# text highlighter.");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
