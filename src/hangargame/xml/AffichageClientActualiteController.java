/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangargame.xml;

import com.jfoenix.controls.JFXListView;
import hangargame.HangarGame;
import hangargame.entites.Actualite;
import hangargame.services.CrudActualite;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author yesmine
 */
public class AffichageClientActualiteController implements Initializable {

    @FXML
    private JFXListView<Label> listactualite;
    List<String> liste = new ArrayList<>();
    List<String> listNoma;
    List<String> listdesa;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        CrudActualite crud = new CrudActualite();
        List<Actualite> list = crud.afficherActualite();
listactualite.getItems().clear();
        for (int i = 0; i < list.size(); i++) {

            Label lbl = new Label("Titre :"+list.get(i).getTitre() + "\n" 
                    + "Description :"+list.get(i).getText());
             lbl.setPrefSize(500, 200);
    liste.add(list.get(i).getId() + "");
           
     
       
        try {
                 InputStream inputStream= new FileInputStream(list.get(i).getImage());
                 ImageView imageView = new ImageView(new Image(inputStream));
                 imageView.setFitWidth(150);
                 imageView.setFitHeight(100);
                  listactualite.getItems().add(lbl);

          lbl.setGraphic(imageView);
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(AffichageClientActualiteController.class.getName()).log(Level.SEVERE, null, ex);
             }

    }    
    
}

    @FXML
    private void deplacerAccueil(ActionEvent event) throws IOException {
        
        HangarGame hang =new HangarGame();
        hang.depalcerlAccueil();
    }
}