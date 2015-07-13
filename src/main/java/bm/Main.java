package bm;

import bm.ioc.BombermanFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static javafx.scene.input.KeyCombination.NO_MATCH;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("bm/meta-info/spring/bm-spring-context.xml");
    BombermanFXMLLoader loader = ctx.getBean(BombermanFXMLLoader.class);

    Parent root = loader.load();
    primaryStage.setTitle("Hello World");
    Scene scene = new Scene(root, 800, 600);

    scene.getStylesheets().add("bm/ui/skins/defaultBM.css");
    primaryStage.setFullScreenExitKeyCombination(NO_MATCH);
    primaryStage.setScene(scene);
    primaryStage.setFullScreen(false);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
