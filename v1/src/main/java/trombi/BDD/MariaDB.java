package trombi.BDD;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MariaDB {

    /**
     * Conversion de XLSX en base de données
     * @param xlsx le nom du fichier à convertir (ex: "ESIR.xlsx")
     * @param connection la connexion à la base de données
     */
    public static void transformXLSXToBDD(Connection connection, String xlsx) {
        try (FileInputStream fileInputStream = new FileInputStream(xlsx)){
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Assuming the first row contains column names
            Row headerRow = sheet.getRow(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                int lastCellNum = row.getLastCellNum();
                try(PreparedStatement statement = connection.prepareStatement("""
        INSERT INTO Eleves(prenom, nom, email, specialite, option, td, tp, tdMut, tpMut, ang, innov, mana, expr, annee)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
      """)) {
                    for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
                        Cell cell = row.getCell(columnIndex);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING -> statement.setString(columnIndex, cell.getStringCellValue());
                                case NUMERIC -> {
                                    if (DateUtil.isCellDateFormatted(cell)) {
                                        statement.setDate(columnIndex, (Date) cell.getDateCellValue());
                                    } else {
                                        statement.setInt(columnIndex, (int) cell.getNumericCellValue());
                                    }
                                }
                                case BOOLEAN -> statement.setBoolean(columnIndex, cell.getBooleanCellValue());
                                default ->
                                    // Handle other cell types as needed
                                        statement.setString(columnIndex, "");
                            }
                        } else {
                            // Cellule nulle, ajouter une chaîne vide
                            statement.setString(columnIndex, "");
                        }
                        int rowsInserted = statement.executeUpdate();
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
