package trombi.BDD.MODIF_BDD;
import io.qt.core.QMetaObject.Slot0;
import io.qt.widgets.QPushButton;
import io.qt.widgets.QWidget;

public class ModifBDD_plus_ou_moins_subwidget {

    public static int start_offset = 125;
    public static int offset_per_cond = 50;
    public static QPushButton plus;
    public static QPushButton moins;

    public ModifBDD_plus_ou_moins_subwidget(ModifBDDWindow modifBDDWindow,QWidget widgetParent) {
        plus = new QPushButton(widgetParent);
        plus.setText("+");
        plus.resize(45, 25);
        plus.clicked.connect(modifBDDWindow,"plus()");

        moins = new QPushButton(widgetParent);
        moins.setText("-");
        moins.resize(45, 25);
        moins.clicked.connect(modifBDDWindow, "moins()");

        ModifBDDWindow.refreshPosition();
    }

    
}
