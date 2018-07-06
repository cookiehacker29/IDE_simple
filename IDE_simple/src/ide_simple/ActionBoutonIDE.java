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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author erwan
 */
public class ActionBoutonIDE implements EventHandler<ActionEvent> {

    private final IDEApplication app;
    private Process p;

    ActionBoutonIDE(IDEApplication app) {
        this.app = app;
    }

    @Override
    public void handle(ActionEvent t) {
        Button b = (Button) t.getSource();

        if (b.getUserData().equals("save")) {
            save();
        }

        else if (b.getUserData().equals("debug")) {
            try {
                save();

                p = Runtime.getRuntime().exec("./simple " + app.getNomFichier() + ".simple");
                p.waitFor();

                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = "";
                String res = "";
                while ((line = reader.readLine()) != null) {
                    res += line + "\n";
                }
                app.setConsole(res);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ActionBoutonIDE.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if(b.getUserData().equals("debug_run")){
            save();
            
            try {
                p = Runtime.getRuntime().exec("./simple " + app.getNomFichier() + ".simple");
                p.waitFor();
                p = Runtime.getRuntime().exec("gcc -o "+app.getNomFichier()+" "+app.getNomFichier()+".c");
                p.waitFor();
                p = Runtime.getRuntime().exec("xterm -hold -e ./"+app.getNomFichier());
                p.waitFor();
                
                app.setConsole("BUILD SUCESSFUL");
                
            } catch (IOException | InterruptedException ex) {
                app.setConsole("ERROR :\n"+ex.getMessage());
            }
        }
    }

    public void save() {
        try {
            PrintWriter writer = new PrintWriter("./" + app.getNomFichier() + ".simple", "UTF-8");
            writer.println(app.getCode().getText());
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("IDE SIMPLE");
            alert.setHeaderText(null);
            alert.setContentText("Fichier sauvegarder !");
            alert.showAndWait();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(ActionBoutonIDE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
