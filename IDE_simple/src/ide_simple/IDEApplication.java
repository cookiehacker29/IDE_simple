/*
 * Copyright (C) 2018 erwan
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package ide_simple;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author erwan
 */
public class IDEApplication extends Application{
    
    private String nomFichier;
    private TextArea code;
    private TextArea console;
    private BorderPane bp;
    
    public static void main(String[] args){
        launch(args);
    }
    
    public Scene s(){
        
        bp = new BorderPane();
        
        Scene windows = new Scene(bp, 800,600);
        
        HBox menu = new HBox();
        
        Button save = new Button("Sauvegarder");
        Button debugage = new Button("Debuger");
        Button debugage_run = new Button("Debuger + lancer");
        
        save.setMaxWidth(Double.MAX_VALUE);
        debugage.setMaxWidth(Double.MAX_VALUE);
        debugage_run.setMaxWidth(Double.MAX_VALUE);
        
        save.setUserData("save");
        debugage.setUserData("debug");
        debugage_run.setUserData("debug_run");
        
        save.setOnAction(new ActionBoutonIDE(this));
        debugage.setOnAction(new ActionBoutonIDE(this));
        debugage_run.setOnAction(new ActionBoutonIDE(this));
        
        menu.getChildren().addAll(save, debugage, debugage_run);
                
        TitledPane saisie = new TitledPane("Zone de codage", code);
        saisie.setCollapsible(false);
        
        console = new TextArea();
        console.setEditable(false);
        TitledPane debug = new TitledPane("Console", console);
        debug.setCollapsible(false);
        
        bp.setTop(menu);
        bp.setCenter(saisie);
        bp.setBottom(debug);
        
        return windows;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("IDE SIMPLE");
        stage.setScene(s());
        stage.setResizable(false);
        System.out.println(this.nomFichier);
        stage.show();
    }
    
    public void setNomFichier(String val){
        this.nomFichier = val;
    }
    
    public String getNomFichier(){
        return this.nomFichier;
    }
    
    public void setCode(String val){
        code = new TextArea();
        code.setText(val);
    }
    
    public TextArea getCode(){
        return this.code;
    }
    
    public void setConsole(String val){
        console = new TextArea();
        console.setEditable(false);
        this.console.setText(val);
        TitledPane debug = new TitledPane("Console", console);
        debug.setCollapsible(false);
        bp.setBottom(debug);
    }
}
