package trombi.BDD;

import java.sql.SQLException;

import io.qt.NonNull;
import io.qt.widgets.QFileDialog;
import io.qt.widgets.QFileDialog.Result;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QTabWidget;

public class ImportXlsx {
    private QTabWidget widgetParent;
    private String filePath;

    public ImportXlsx(QTabWidget widgetParent) {
        this.widgetParent = widgetParent;
        QPushButton btnXlsx = new QPushButton("Importer XLSX", widgetParent);
        btnXlsx.move(78, widgetParent.getY() + 2);
        // Action xlsx
        // Selectionner le fichier
        btnXlsx.clicked.connect(this, "openFile()");
    }

    void openFile() {
        @NonNull
        Result<@NonNull String> file = QFileDialog.getOpenFileName(widgetParent, "Open Xlsx");
        if (file != null) {
            // Chemin du fichier excel
            this.filePath = file.result;
            System.out.println(filePath);

            // Traitement ... A faire
            try {
                MariaDB.transformXLSXToBDD(filePath);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public String getFilePath() {
        return this.filePath;
    }
}
