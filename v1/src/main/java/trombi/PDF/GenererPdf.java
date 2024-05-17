
package trombi.PDF;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.checkerframework.checker.units.qual.m;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import trombi.BDD.MariaDB;

public class GenererPdf {

    private final String dest;//path de destination pour le fichier créé
    private static int nbCellules = 5; //nombre d'élève par lignes
    private static float imageWidth = (PageSize.DEFAULT.getWidth()-90)/nbCellules;

    public GenererPdf(String dest) {
        this.dest = dest;
    }

    /**
     * Crée le PDF de trombinoscope (création, remplissage et arrangement des cellules)
     *
     * @param nomEleve la liste des élèves qu'on veut mettre dans le trombinoscope
     * @param nomPdf le nom du document
     * @throws Exception
     */
    protected void manipulatePdf(ArrayList<String> nomEleve, ArrayList<String> mailEleve, String nomPdf) throws Exception {

        //Création du document
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(this.dest));
        Document doc = new Document(pdfDoc);
        

        //Création d'un paragraphe (champ de texte) avec le nom du document 
        Paragraph titre = new Paragraph(nomPdf);
        titre.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
        titre.setBold();
        doc.add(titre);

        //Création de la table
        Table table = new Table(nbCellules);
        table.setExtendBottomRow(false);
        //table.setAutoLayout();

        //Ajout des élèves dans la table (nom + photo)
        for (int i = 0; i < nomEleve.size(); i++) {
            Cell cell = new Cell(); //Création de la cellule
            Paragraph pNom = new Paragraph(nomEleve.get(i)); //Création d'un "paragraphe" pour le nom de l'élève
            cell.add(createImageCell(mailEleve.get(i)));
            cell.add(pNom);
            table.addCell(cell);
        }

        doc.add(table);
        
        if(needMail){
            doc.add(new AreaBreak(com.itextpdf.layout.properties.AreaBreakType.NEXT_PAGE));
            String mailList = "Liste des mails des élèves : \n\n";
            for (String mail : mailEleve) {
                mailList += mail + "\n";
            }
            doc.add(new Paragraph(mailList));
        }
        
        doc.close();
    }
    /**
     * 
     * @param mail le mail de l'étudiant dont on souhaite récupérer la photo
     * @return une cellule contenant l'image correspondant à l'élève dont c'est le mail
     * @throws MalformedURLException
     */
    private static Image createImageCell(String mail) throws MalformedURLException {
        Image img;
        try {
            byte[] imgData = MariaDB.getImage(mail);
            if(imgData == null)
            {
                img = new Image(ImageDataFactory.create("nobody.png"));
                img.setOpacity(0.3f);
            }
            else
            {
                img = new Image(ImageDataFactory.create(imgData));
            }
            img.setWidth(imageWidth);
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
