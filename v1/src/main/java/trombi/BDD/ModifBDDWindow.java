package trombi.BDD;

import io.qt.core.QList;
import io.qt.multimedia.*;
import io.qt.multimedia.widgets.QVideoWidget;
import io.qt.widgets.*;
import trombi.BDD.listsType.colonnes;

public class ModifBDDWindow {
    QComboBox listCol;
    QLineEdit champsModif;

    public ModifBDDWindow(QWidget widgetParent) {
        QLabel titre = new QLabel(widgetParent);
        titre.move(5, 5);
        titre.setText("Quel colonne voulez vous modifier : ");

        listCol = new QComboBox(widgetParent);
        listCol.addItem("   ");
        for (colonnes iterable_element : colonnes.values()) {
            listCol.addItem(iterable_element.toString());
        }
        listCol.move(5, 40);
        listCol.activated.connect(this, "print_content()");

        QLabel valeur = new QLabel(widgetParent);
        titre.move(130, 45);
        titre.setText("Quel valeurs y mettre : ");

        champsModif = new QLineEdit(widgetParent);
        champsModif.move(300,40);
    }

    public void print_content() {
        System.out.println(listCol.getCurrentText());
    }

}