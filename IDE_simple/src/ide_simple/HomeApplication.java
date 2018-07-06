
package ide_simple;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author erwan
 */
public class HomeApplication extends Application{
    
    public Stage stage;
    
    public static void main(String[] args){
        launch(args);
    }
    
    public Scene s(){
        BorderPane bp = new BorderPane();
        
        Scene windows = new Scene(bp, 300, 300);
        
        VBox vb = new VBox();
        
        Button creer = new Button("Creer un nouveau projet");
        creer.setUserData("creer");
        creer.setOnAction(new ActionBoutonHome(this));
        
        Button ouvrir = new Button("Ouvrir un projet existant");
        ouvrir.setUserData("ouvrir");
        ouvrir.setOnAction(new ActionBoutonHome(this));
        
        ouvrir.setMaxWidth(Double.MAX_VALUE);
        creer.setMaxWidth(Double.MAX_VALUE);
        
        vb.getChildren().addAll(creer, ouvrir);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);
        
        bp.setCenter(vb);
        
        return windows;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("IDE SIMPLE");
        stage.setScene(s());
        stage.setResizable(false);
        stage.show();
        this.stage = stage;
    }
    
    public Stage getStage(){
        return this.stage;
    }
    
}
