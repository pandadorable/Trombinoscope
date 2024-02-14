package trombi.BDD;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.layout.element.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MariaDB {

    /**
     * Conversion de XLSX en base de données.
     *
     * @param xlsx       le nom du fichier à convertir (ex: "ESIR.xlsx")
     * @param connection la connexion à la base de données
     */
    public static void transformXLSXToBDD(Connection connection, String xlsx) {
        try (FileInputStream fileInputStream = new FileInputStream(xlsx)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Assuming the first row contains column names
            Row headerRow = sheet.getRow(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                int lastCellNum = row.getLastCellNum();
                try (PreparedStatement statement = connection.prepareStatement(
                        """
                                  REPLACE ELEVE(prenom, nom, email, specialite, option, td, tp, tdMut, tpMut, ang, innov, mana, expr, annee)
                                  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                                """)) {
                    for (int columnIndex = 1; columnIndex <= lastCellNum; columnIndex++) {
                        Cell cell = row.getCell(columnIndex - 1);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING -> statement.setString(columnIndex,
                                        cell.getStringCellValue());
                                case NUMERIC -> {
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        statement.setDate(columnIndex,
                                                (Date) cell.getDateCellValue());
                                    } else {
                                        statement.setInt(columnIndex, (int) cell.getNumericCellValue());
                                    }
                                }
                                case BOOLEAN -> statement.setBoolean(columnIndex,
                                        cell.getBooleanCellValue());
                                default ->
                                    // Handle other cell types as needed
                                    statement.setString(columnIndex, "");
                            }
                        } else {
                            // Cellule nulle, ajouter une chaîne vide
                            statement.setString(columnIndex, "");
                        }
                    }
                    // date non présente
                    statement.setString(14, "2XXX");
                    int rowsInserted = statement.executeUpdate();
                    System.out.println("Insertion terminée");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertImage(Connection connection, String email, String pathImage) throws IOException {
        File image = new File(pathImage);
        FileInputStream inputStream = null;
        try {
            // create an input stream pointing to the file
            inputStream = new FileInputStream(image);
            try (PreparedStatement statement = connection.prepareStatement("""
                      UPDATE ELEVE SET image = ? WHERE email = ?
                    """)) {
                statement.setString(2, email);
                statement.setBlob(1, inputStream);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new IOException("Unable to convert file to byte array. " +
                    e.getMessage());
        } finally {
            // close input stream
            if (inputStream != null) {
                inputStream.close();
            }
        }
        System.out.println("Insertion image pour : " + email);
    }

    public static BufferedImage getImage(Connection connection, String email) throws IOException {
        try (PreparedStatement statement = connection.prepareStatement("""
                  SELECT image FROM ELEVE WHERE email = ?
                """)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            InputStream is = resultSet.getBlob(1).getBinaryStream();
            return ImageIO.read(is);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet autoRequest(Connection connection, String[] nomCollumnWanted, String[] nomCollumnCondition, String[] condition) {
        assert(nomCollumnCondition.length == condition.length);
        boolean conflictWantedCondition = false;
        for(String e1 : nomCollumnCondition) 
        {
            for(String e2 : nomCollumnWanted)
            {
                conflictWantedCondition = conflictWantedCondition || e1.equals(e2);
            }
        }
        assert(!conflictWantedCondition);

        String listCollumn = "";
        for(int i = 0 ; i < nomCollumnWanted.length ; i++)
        {
            listCollumn += " " + nomCollumnWanted[i];
            if(i < nomCollumnWanted.length-1) listCollumn +=",";
        }
        String listCondition = "";
        for(int i = 0 ; i < nomCollumnCondition.length ; i++)
        {
            listCondition += " WHERE "+ nomCollumnCondition[i] + " = ?";
        }

        try (PreparedStatement statement = connection.prepareStatement(
            "SELECT "+listCollumn+" FROM ELEVE"+listCondition)) {

                for(int i = 0 ; i < condition.length ; i++) statement.setString(i,condition[i]);
                return statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
