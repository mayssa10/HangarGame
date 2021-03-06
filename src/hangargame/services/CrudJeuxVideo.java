/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangargame.services;

import hangargame.connexionDB.ConnexionSingleton;
import hangargame.entites.JeuxVideo;
import hangargame.serviceinterface.IJeuxVideoCrud;
import hangargame.xml.AffichageClientJeuController;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author mishka
 */
public class CrudJeuxVideo implements IJeuxVideoCrud {

    Connection connect;
    Statement ste;
    PreparedStatement prepste;
    private ObservableList<JeuxVideo> data;
    List<JeuxVideo> list = new ArrayList<>();
      JeuxVideo jeux= new JeuxVideo(0, "", "", "", "", "",0);

    public CrudJeuxVideo() {

        connect = ConnexionSingleton.getInstance();

    }

    @Override
    public void ajouterJeuxVideo(String nom, String genre, String date_sortie, String description, String image, int id_console) {
        JeuxVideo j = new JeuxVideo(nom, genre, date_sortie, description, image, id_console);

        String req1 = "INSERT INTO `jeux_video`(`console`, `nom`, `genre`, `date_sortie`, `description`, `image`) VALUES(?,?,?,?,?,?)";
        try {
            // String req1="insert into jeux_video(nom,genre)values(?,?)";
            // InputStream inputStream= new FileInputStream(image);

            prepste = connect.prepareStatement(req1);
            prepste.setInt(1, j.getId_console());
            prepste.setString(2, j.getNom());
            prepste.setString(3, j.getGenre());

            prepste.setString(4, j.getDate_sortie());
            prepste.setString(5, j.getDescription());
            prepste.setString(6, j.getImage());
           
         
            System.out.println(prepste);
            prepste.executeUpdate();
            System.out.println("c'est fait");
            tray.notification.TrayNotification tr = new TrayNotification();
            tr.setTitle("Terminé");
            tr.setMessage("jeu a été ajouté avec succes");
            tr.setNotificationType(NotificationType.SUCCESS);
            tr.setAnimationType(AnimationType.SLIDE);
            tr.showAndDismiss(Duration.seconds(4));
        } catch (SQLException ex) {
            Logger.getLogger(CrudJeuxVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void supprimerJeuxVideo(String nom) {
        try {
            JeuxVideo je = new JeuxVideo();
            String req2 = "delete from jeux_video where nom=?";

            prepste = connect.prepareStatement(req2);
            prepste.setString(1, nom);
            prepste.execute();
            System.out.println("ciiiiiiiii ");
            tray.notification.TrayNotification tr = new TrayNotification();
            tr.setTitle("Terminé");
            tr.setMessage("jeu a été Supprimé avec succes");
            tr.setNotificationType(NotificationType.SUCCESS);
            tr.setAnimationType(AnimationType.SLIDE);
            tr.showAndDismiss(Duration.seconds(4));
        } catch (SQLException ex) {
            Logger.getLogger(JeuxVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean modifierJeuxVideo(JeuxVideo j) {
        System.out.println(j.getId());
        String req3 = "update  jeux_video set nom=?,"
               
                + "genre= ?,"
                + "date_sortie= ?,"
                + "description= ?,"
                + "image=?"
                
                +"where id=?"
               
                //+ "nom_console=?"
              
                ;
                
        try {
            prepste = connect.prepareStatement(req3);
            prepste.setString(1, j.getNom());
            prepste.setString(2, j.getGenre());
            prepste.setString(3, j.getDate_sortie());
            prepste.setString(4, j.getDescription());
           
            //System.out.println(j.getImage());
          prepste.setString(5, j.getImage());
           prepste.setInt(6, j.getId());
            //     prepste.setInt(6,j.getId());
            prepste.executeUpdate();
            System.out.println("c'est fait");
            tray.notification.TrayNotification tr = new TrayNotification();
            tr.setTitle("Terminé");
            tr.setMessage("jeu a été modifié avec succes");
            tr.setNotificationType(NotificationType.SUCCESS);
            tr.setAnimationType(AnimationType.SLIDE);
            tr.showAndDismiss(Duration.seconds(4));
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(CrudJeuxVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ObservableList<JeuxVideo> afficherJeuxVideo() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = connect.createStatement().executeQuery("select * from jeux_video");
            while (rs.next()) {
                data.add(new JeuxVideo(rs.getInt("id") ,rs.getString("nom"),rs.getString("genre"), rs.getString("date_sortie"), rs.getString("description"), rs.getString("image")));
                
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.err.println("Erreur" + ex);
        }
        
        return data;
    }

    public List<JeuxVideo> reccuperer() {

        String query = "Select * from jeux_video";

        try {
            prepste = connect.prepareStatement(query);
            ResultSet rs = prepste.executeQuery();
            while (rs.next()) {
                int idjeu = rs.getInt("id");
                String nomj = rs.getString("nom");
                String genrej = rs.getString("genre");

                 String datej = rs.getString("date_sortie");
                String desc = rs.getString("description");
                 String imagee = rs.getString("image");
                

                //Timestamp dateAnnonces = rs.getTimestamp("dataAjout");
                JeuxVideo jeu = new JeuxVideo(idjeu, nomj, genrej, datej, desc, imagee );
              //  JeuxVideo jj = new JeuxVideo(nomj, genrej, "",desc, "","", "");
                
                list.add(jeu);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CrudJeuxVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }
    
    
    
      public ObservableList<JeuxVideo>  rechercher(String n) {
        
        try {
            String req4= "SELECT * from  jeux_video where nom=?";
            connect = ConnexionSingleton.getInstance();
            data = FXCollections.observableArrayList();
            prepste = connect.prepareStatement(req4);
            prepste.setString(1,n);
          ResultSet rs= prepste.executeQuery();
            while (rs.next()) {
                  data.add(new JeuxVideo(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
                   
            }
        } catch (SQLException ex) {
            System.err.println("Erreur" + ex);
        }
return data ;
}
      
     public JeuxVideo reccupererJeu() {

        String query = "Select * from jeux_video where id=?";

        try {
           
            prepste = connect.prepareStatement(query);
            prepste.setInt(1,Integer.parseInt(AffichageClientJeuController.indexjeu));
            ResultSet rs = prepste.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomJ = rs.getString("nom");
                String genreJ = rs.getString("genre");
                
                String datej = rs.getString("date_sortie");
                String desc = rs.getString("description");

                //Timestamp dateAnnonces = rs.getTimestamp("dataAjout");
               String image = rs.getString("image");
               

                //InputStream inputStream = image.getBinaryStream();
                 jeux = new JeuxVideo(id, nomJ, genreJ, datej, desc, image);

            }
        } catch (SQLException ex) {
            Logger.getLogger(CrudJeuxVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("wwwwwwwwww" + jeux);
        return jeux;

    }

     
     

    

}
