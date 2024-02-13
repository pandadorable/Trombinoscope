package trombi.BDD;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ExcelToSQl{

    
    public static String[] transformXLSXToBDD() {
        String[] reponse = new String[2];
        reponse[0]=null;
        reponse[1]=null;
        try (FileInputStream fileInputStream = new FileInputStream("ESIR.xlsx")) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Assuming the first row contains column names
            Row headerRow = sheet.getRow(0);

            StringBuilder createTableSQL = new StringBuilder("CREATE TABLE Eleves (");

            for (Cell cell : headerRow) {
                createTableSQL.append("\"").append(cell.getStringCellValue()).append("\" TEXT, ");
            }

            // Remove the last comma and add closing parenthesis
            createTableSQL.setLength(createTableSQL.length() - 2);
            createTableSQL.append(");");

            //System.out.println(createTableSQL.toString());
            reponse[0] = createTableSQL.toString();
            // Optionally, generate INSERT statements
            StringBuilder insertStatements = new StringBuilder("INSERT INTO Eleves VALUES ");

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                insertStatements.append("(");
                int lastCellNum = row.getLastCellNum();
                //System.out.println("Nombre total de cellules dans la ligne " + rowIndex + ": " + lastCellNum);

                for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) {
                    Cell cell = row.getCell(columnIndex);
                    String cellValue="";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                cellValue = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = cell.getDateCellValue().toString();
                                } else {
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                cellValue = cell.getCellFormula();
                                break;
                            default:
                                // Handle other cell types as needed
                                cellValue = "";
                        }
                    } else {
                        // Cellule nulle, ajouter une chaîne vide
                        cellValue = "";
                    }

                    insertStatements.append("'").append(cellValue).append("', ");
                    //System.out.println("Contenu de la cellule " + rowIndex + ", " + columnIndex + ": " + cellValue);
                }

                // Remove the last comma and add closing parenthesis
                insertStatements.setLength(insertStatements.length() - 2);
                insertStatements.append("), ");
                //System.out.println("Script partiel après la ligne " + rowIndex + ": " + insertStatements.toString());
            }

            // Remove the last comma and semicolon
            insertStatements.setLength(insertStatements.length() - 2);
            insertStatements.append(";");

            //System.out.println(insertStatements.toString());
            reponse[1]=insertStatements.toString();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reponse;
    }


    public static void main(String[] args) {
        
    }
}
