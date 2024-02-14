
package trombi.PDF;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import trombi.BDD.MariaDB;

public class GenererPdf {
    private final String dest;
   

    public GenererPdf(String dest) {
        this.dest = dest;
    }

    protected void manipulatePdf(ArrayList<String> nomEleve, ArrayList<String> mailEleve, String nomPdf) throws Exception {
        // File file = new File(this.dest); 
        // file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(this.dest));
        Document doc = new Document(pdfDoc);


        Paragraph titre = new Paragraph(nomPdf);
        titre.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
        titre.setBold();
        doc.add(titre);

        //TODO : Nombre de cellules par lignes automatique
        //Permet d'avoir toutes les photos sur une seule page
        //(int) (double) (nomEleve.size() / 4)+1)
        int nbCellules = 4;
        Table table = new Table(UnitValue.createPercentArray(nbCellules)).useAllAvailableWidth();
        table.setMargin(15);
        for (int i = 0; i < nomEleve.size(); i++) {
            Cell cell = new Cell(); //Creation de la cellule
            Paragraph pNom = new Paragraph(nomEleve.get(i)); //Creation d'un "paragraphe" pour le nom de l'élève
            cell.add(createImageCell(mailEleve.get(i)));
            cell.add(pNom);
            table.addCell(cell);
        }

        doc.add(table);
        doc.close();
    }

    private static Cell createImageCell(String mail) throws MalformedURLException {
        Image img;
        try {
            byte[] imgData = MariaDB.getImage(mail);
            if(imgData == null)
            {
                img = new Image(ImageDataFactory.create("zeldalogo.png"));
            }
            else
            {
                img = new Image(ImageDataFactory.create(imgData));
            }
            return new Cell().add(img.setAutoScale(true).setWidth(UnitValue.createPercentValue(100)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
