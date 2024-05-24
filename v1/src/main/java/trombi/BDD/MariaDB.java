package trombi.BDD;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import trombi.BDD.listType.colonnes;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class MariaDB {

    // Connection à la BDD
    private static Connection CONNECTION = null;

    /**
     * @return Instance de la connection à la BDD
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static Connection getConnection() {
        if (CONNECTION == null) {
            // Scanner du fichier config
            List<String> lc = new ArrayList<>();
            String file = "config";
            Scanner scanner;
            try {
                scanner = new Scanner(new File(file));
                scanner.useDelimiter("\n");
                while (scanner.hasNext()) {
                    String tmp = scanner.next();
                    if (tmp.charAt(0) != '#')
                        lc.add(tmp.replace("\r", ""));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // JScH data
            String ssh_username = lc.get(0);
            String ssh_password = lc.get(1);
            String ssh_host = lc.get(2);
            int ssh_port = Integer.valueOf(lc.get(3));

            String ssh_RemoteHost = lc.get(4);
            int remotePort = Integer.valueOf(lc.get(5));
            int localPort = Integer.valueOf(lc.get(6));

            try {
                localPort = sshTunnel(ssh_username, ssh_password, ssh_host, ssh_port, ssh_RemoteHost, remotePort);
            } catch (JSchException e) {
                e.printStackTrace();
            } finally {
            }

            String BDD_username = lc.get(7);
            String BDD_password = lc.get(8);
            String jdbcUrl = "jdbc:mariadb://localhost:" + localPort + "/" + lc.get(9);
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                CONNECTION = DriverManager.getConnection(jdbcUrl, BDD_username, BDD_password);
            } catch (SQLException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return CONNECTION;
    }

    public static void kill() throws SQLException {

        if (CONNECTION != null) {
            System.out.println("kill CONNECTION");
            CONNECTION.close();
        }
    }

    /**
     * 
     * @param ssh_username
     * @param ssh_password
     * @param ssh_host
     * @param ssh_port
     * @param ssh_RemoteHost
     * @param remotePort
     * @throws JSchException
     */
    public static int sshTunnel(String ssh_username, String ssh_password, String ssh_host, int ssh_port,
            String ssh_RemoteHost, int remotePort) throws JSchException {
        Session session = null;
        session = new JSch().getSession(ssh_username, ssh_host, ssh_port);
        session.setPassword(ssh_password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session.setPortForwardingL(0, ssh_RemoteHost, remotePort);
    }

    /**
     * Conversion de XLSX en base de données.
     *
     * @param xlsx le nom du fichier à convertir (ex: "ESIR.xlsx")
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static void transformXLSXToBDD(String xlsx, Label confirm_label) throws SQLException, FileNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(xlsx)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            //define all column
            String[] header = new String[listType.colonnes.values().length-1];
            int headerIndex = 0;
            for (colonnes c : listType.colonnes.values()) { //exclude image from the list
                if(!c.equals(listType.colonnes.image))
                {
                    header[headerIndex] = c.toString();
                    headerIndex++; 
                }
            }

            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Assuming the first row contains column names
            //Row headerRow = sheet.getRow(0);
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                int lastCellNum = row.getLastCellNum();
                String[] dataValues = new String[header.length];

                for (int columnIndex = 0; columnIndex < lastCellNum; columnIndex++) 
                {
                    Cell cell = row.getCell(columnIndex);
                        if (cell != null) {
                            dataValues[columnIndex] = cell.getStringCellValue();
                        } else {
                            // Cellule nulle, ajouter une chaîne vide
                            dataValues[columnIndex] = "";
                        }
                }
                String[] colCondion = {"email"};
                String[] condition = {row.getCell(2).getStringCellValue()};
                modifRequest(header, dataValues, colCondion, condition, typeCondition.AND);
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        confirm_label.setText("Base de donnée mise à jour");
    }

    public static void transformBDDtoXLS(String filename, Label confirm_label) {
        try {
            //Connection connection = getConnection();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("ESIR");
            // 1st line with all column
            XSSFRow rowhead = sheet.createRow((short) 0);
            String[] header = new String[listType.colonnes.values().length-1];
            int headerIndex = 0;
            for (colonnes c : listType.colonnes.values()) { //exclude image from the list
                if(!c.equals(listType.colonnes.image))
                {
                    header[headerIndex] = c.toString();
                    headerIndex++; 
                }
            }
            for (int i = 0; i < header.length; i++) {
                rowhead.createCell(i).setCellValue(header[i].toString());
            }
            // all other lines
            LinkedList<XSSFRow> rowList = new LinkedList<XSSFRow>();
            ResultSet listEleve = autoRequest(header, new String[0], new String[0], typeCondition.AND);
            int nbLine = 0;
            while(listEleve.next())
            {
                nbLine++;
                rowList.add(sheet.createRow((short)nbLine));
                int columnIndex = 0;
                for (String head : header) {
                    rowList.getLast().createCell(columnIndex).setCellValue(listEleve.getString(head));
                    columnIndex++;
                }
            }
            // export
            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            confirm_label.setText("Base de donnée importée\n-> SAUVEGARDE.xlsx");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    /**
     * Insertion d'une image dans la base de données associée à un élève
     *
     * @param email de l'élève à qui associer l'image
     * @param image ImageView à insérer dans la BDD
     * @throws IOException
     * @throws SQLException
     */
    public static void insertImage(String email, ImageView image) {
        Connection connection = getConnection();
        ByteArrayInputStream inputStream = null;
        if (isMailExist(email)) {
            try {
                // create an input stream pointing to the file
                BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
                ByteArrayOutputStream s = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", s);
                byte[] res = s.toByteArray();
                inputStream = new ByteArrayInputStream(res);
                s.close();

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
                try {
                    throw new IOException("Unable to convert file to byte array. " +
                            e.getMessage());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } finally {
                // close input stream
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Insertion image pour : " + email);
        } else
            System.out.println("Email introuvable :/");
    }

    /**
     * Renvoie l'image associée à un élève
     *
     * @param email de l'élève dont on veut l'image
     * @return l'image associée à l'adresse email
     * @throws IOException
     * @throws SQLException
     */
    public static byte[] getImage(String email) throws IOException, SQLException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement("""
                  SELECT image FROM ELEVE WHERE email = ?
                """)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getBytes(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Liste des types de conditions pour autoRequest
     */
    public static enum typeCondition {
        OR {
            @Override
            public String toString() {
                return " OR ";
            }
        },
        AND {
            @Override
            public String toString() {
                return " AND ";
            }
        };

        public abstract String toString();
    };

    /**
     *
     * @param nomColumnWanted    : La liste des colonnes voulant être récupéré
     *                           dans la base de données
     * @param nomColumnCondition : La liste des colonnes sur lesquelles on pose
     *                           une condition
     * @param condition          : La liste des conditions /!\ doit faire la même
     *                           taille que nomColumnCondition, la condition à
     *                           l'indice 0 vaut pour la column à l'indice 0
     *
     * @param typeCondition      le type de requete souhaité (condition1 AND
     *                           condition2 AND ....) ou (condition1 OR condition2
     *                           OR ....)
     * @return Le ResultSet de la requete
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static ResultSet autoRequest(String[] nomColumnWanted, String[] nomColumnCondition,
            String[] condition, typeCondition typeCondition) throws SQLException, FileNotFoundException {
        assert (nomColumnCondition.length == condition.length);

        Connection connection = getConnection();
        String listColumn = "";
        for (int i = 0; i < nomColumnWanted.length; i++) {
            listColumn += " " + nomColumnWanted[i];
            if (i < nomColumnWanted.length - 1)
                listColumn += ",";
        }
        String listCondition = "";
        if (nomColumnCondition.length > 0) {
            listCondition += " WHERE ";
            for (int i = 0; i < nomColumnCondition.length; i++) {
                listCondition += nomColumnCondition[i] + " = ?";
                if (i < nomColumnCondition.length - 1)
                    listCondition += typeCondition.toString();
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT " + listColumn + " FROM ELEVE" + listCondition)) {

            for (int i = 0; i < condition.length; i++)
                statement.setString(i + 1, condition[i]);
            return statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param columnToModif      : La colonnes à modifié
     *
     * @param dataToAdd          : La valeurs a rentrer dans la BDD
     *
     * @param nomColumnCondition : La liste des colonnes sur lesquelles on pose
     *                           une condition
     *
     * @param condition          : La liste des conditions /!\ doit faire la même
     *                           taille que nomColumnCondition, la condition à
     *                           l'indice 0 vaut pour la column à l'indice 0
     *
     * @param typeCondition      le type de requete souhaité (condition1 AND
     *                           condition2 AND ....) ou (condition1 OR condition2
     *                           OR ....)
     *
     * @return Le ResultSet de la requete
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static ResultSet modifRequest(String columnToModif, String dataToAdd, String[] nomColumnCondition,
            String[] condition, typeCondition typeCondition) throws FileNotFoundException, SQLException
            {
                String[] colModif = {columnToModif};
                String[] datModif = {dataToAdd};
                return modifRequest(colModif, datModif, nomColumnCondition, condition, typeCondition);
            }

    /**
     *
     * @param columnToModif      : La liste des colonnes à modifié
     *
     * @param dataToAdd          : La liste des valeurs a rentrer dans la BDD
     *
     * @param nomColumnCondition : La liste des colonnes sur lesquelles on pose
     *                           une condition
     *
     * @param condition          : La liste des conditions /!\ doit faire la même
     *                           taille que nomColumnCondition, la condition à
     *                           l'indice 0 vaut pour la column à l'indice 0
     *
     * @param typeCondition      le type de requete souhaité (condition1 AND
     *                           condition2 AND ....) ou (condition1 OR condition2
     *                           OR ....)
     *
     * @return Le ResultSet de la requete
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public static ResultSet modifRequest(String[] columnToModif, String[] dataToAdd, String[] nomColumnCondition,
            String[] condition, typeCondition typeCondition) throws SQLException, FileNotFoundException {
        assert (nomColumnCondition.length == condition.length);
        assert (columnToModif.length == dataToAdd.length);

        String listCol = "";
        for(int i = 0 ; i < columnToModif.length ; i++)
        {
            listCol += columnToModif[i] + " = '"+dataToAdd[i]+"'";
            if(i < columnToModif.length-1) listCol+=",";
        }

        String listCondition = "";
        listCondition += " WHERE ";
        for (int i = 0; i < nomColumnCondition.length; i++) {
            listCondition += nomColumnCondition[i] + " = ?";
            if (i < nomColumnCondition.length - 1)
                listCondition += typeCondition.toString();
        }

        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE ELEVE SET " + listCol + listCondition)) {

            for (int i = 0; i < condition.length; i++)
                statement.setString(i + 1, condition[i]);
            //System.out.println(statement);
            return statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int howMuchDataExist(String[] columnToCheck, String[] dataToFind, typeCondition typeCondition)
            throws SQLException, FileNotFoundException {
        Connection connection = getConnection();
        String statementString = "SELECT * FROM ELEVE";
        if (columnToCheck.length > 0)
            statementString += " WHERE ";
        for (int i = 0; i < columnToCheck.length; i++) {
            statementString += columnToCheck[i] + " = ?";
            if (i != columnToCheck.length - 1)
                statementString += typeCondition.toString();
        }
        statementString += ";";
        try (PreparedStatement statement = connection.prepareStatement(
                statementString)) {
            for (int i = 0; i < columnToCheck.length; i++) {
                statement.setString(i + 1, dataToFind[i]);
            }
            ResultSet rs = statement.executeQuery();
            int sum = 0;
            while (rs.next()) {
                sum++;
            }
            return sum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static boolean isDataExist(String columnToCheck, String dataToFind)
            throws SQLException, FileNotFoundException {
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM ELEVE WHERE " + columnToCheck + " = ?")) {
            statement.setString(1, dataToFind);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isMailExist(String email) {
        try {
            return isDataExist("email", email);
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
