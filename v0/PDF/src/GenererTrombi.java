package PDF.src;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class GenererTrombi {
    private final String dest;
   

    public GenererTrombi(String dest) {
        this.dest = dest;
    }

    protected void manipulatePdf(ArrayList<String> nomEleve, ArrayList<String> photoEleve, String nomPdf) throws Exception {
        File file = new File(this.dest); 
        file.getParentFile().mkdirs();

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
            cell.add(createImageCell(photoEleve.get(i)));
            cell.add(pNom);
            table.addCell(cell);
        }

        doc.add(table);
        doc.close();
    }

    private static Cell createImageCell(String path) throws MalformedURLException {
        Image img = new Image(ImageDataFactory.create(path));
        return new Cell().add(img.setAutoScale(true).setWidth(UnitValue.createPercentValue(100)));
    }
}
