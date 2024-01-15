package PDF.src;

import java.io.File;
import java.net.MalformedURLException;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class GenererPdf {
    private String dest;
   

    public GenererPdf(String dest) {
        this.dest = dest;
    }

    protected void manipulatePdf(String[] nomEleve, String[] photoEleve) throws Exception {
        File file = new File(this.dest); 
        file.getParentFile().mkdirs();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(this.dest));
        Document doc = new Document(pdfDoc);
        Paragraph titre = new Paragraph("TROMBINOSCOPE ESIR2 SI");
        titre.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
        titre.setBold();
        doc.add(titre);

        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        table.setMargin(15);
        for (int i = 0; i < nomEleve.length; i++) {
            Cell cell = new Cell(); //Creation de la cellule
            Paragraph pNom = new Paragraph(nomEleve[i]); //Creation d'un "paragraphe" pour le nom de l'élève
            cell.add(createImageCell(photoEleve[i]));
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
