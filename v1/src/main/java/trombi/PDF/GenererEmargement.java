package trombi.PDF;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class GenererEmargement {
        private final String dest;


        public GenererEmargement(String dest) {
            this.dest = dest;
        }

        protected void manipulatePdf(ArrayList<String> nomEleve, String nomPdf) throws Exception {
            File file = new File(this.dest);
            file.getParentFile().mkdirs();

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(this.dest));
            Document doc = new Document(pdfDoc);


            Paragraph titre = new Paragraph(nomPdf);
            titre.setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER);
            titre.setBold();
            doc.add(titre);

            int nbCellules = 6;

            Table table = new Table(UnitValue.createPercentArray(nbCellules+1)).useAllAvailableWidth();
            table.setMargin(15);

            table.addCell(new Cell().add(new Paragraph("Nom")));
            for (int i = 0; i < nbCellules; i++) {
                Cell cell = new Cell();
                Paragraph pNom = new Paragraph("Séance " + (i+1));
                cell.add(pNom);
                table.addCell(cell);
            }

            for (String eleve : nomEleve) {
                Cell cell = new Cell(); //Creation de la cellule
                Paragraph pNom = new Paragraph(eleve); //Creation d'un "paragraphe" pour le nom de l'élève
                cell.add(pNom);
                table.addCell(cell);
                for (int i = 0; i < nbCellules; i++) {
                    Cell emptyCell = new Cell();
                    table.addCell(emptyCell);
                }
            }

            doc.add(table);
            doc.close();
        }

    }

