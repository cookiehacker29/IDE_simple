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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author erwan
 */
public class ActionBoutonHome implements EventHandler<ActionEvent> {

    private HomeApplication app;

    ActionBoutonHome(HomeApplication app) {
        this.app = app;
    }

    @Override
    public void handle(ActionEvent t) {
        Button b = (Button) t.getSource();

        if (b.getUserData().equals("creer")) {
            TextInputDialog dialog = new TextInputDialog("SansNom");
            dialog.setTitle("IDE SIMPLE");
            dialog.setHeaderText("Creation d'un nouveau projet");
            dialog.setContentText("Veuillez entrez le nom du projet : ");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    File file = new File("./" + result.get() + ".simple");
                    if (file.createNewFile()) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("IDE SIMPLE");
                        alert.setHeaderText(null);
                        alert.setContentText("Votre projet a été creer !");
                        alert.showAndWait();
                        
                        app.getStage().close();
                        
                        IDEApplication ide = new IDEApplication();
                        ide.setNomFichier(result.get());
                        ide.setCode("/*\nLangage simple\n*/\n");
                        ide.start(new Stage());
                        
                    } else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("IDE SIMPLE");
                        alert.setHeaderText(null);
                        alert.setContentText("Le fichier existe déjà !");

                        alert.showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                    Logger.getLogger(ActionBoutonHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (b.getUserData().equals("ouvrir")) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Charger un projet");
            fc.setInitialDirectory(new File("./"));
            FileChooser.ExtensionFilter et = new FileChooser.ExtensionFilter("Script", "*.simple");
            fc.getExtensionFilters().add(et);
            File f = fc.showOpenDialog(null);
            if(f != null){
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));
                    String res = "";
                    String line = "";
                    
                    while((line = br.readLine())!=null){
                        res+=line+"\n";
                    }
                    
                    app.getStage().close();
                    
                    IDEApplication ide = new IDEApplication();
                    ide.setNomFichier(f.getName().replace(".simple", ""));
                    ide.setCode(res);
                    ide.start(new Stage());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ActionBoutonHome.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ActionBoutonHome.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ActionBoutonHome.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }

}
