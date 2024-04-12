package trombi.BDD.MODIF_BDD;

import io.qt.widgets.QComboBox;
import io.qt.widgets.QLabel;
import io.qt.widgets.QLineEdit;
import io.qt.widgets.QWidget;
import trombi.BDD.listsType.colonnes;

public class ModifBDD_subwidget {
    QLabel titre;
    QComboBox listCol;
    QLabel valeur;
    QLineEdit champsModif;

    public ModifBDD_subwidget(QWidget widgetParent,String titreString) {
        titre = new QLabel(widgetParent);
        titre.setText(titreString);

        listCol = new QComboBox(widgetParent);
        listCol.addItem("");
        for (colonnes iterable_element : colonnes.values()) {
            listCol.addItem(iterable_element.toString());
        }

        valeur = new QLabel(widgetParent);
        valeur.setText("Quelles valeurs y mettre : ");

        champsModif = new QLineEdit(widgetParent);
        move(0,0);
    }


    public void move(int x, int y) {
        titre.move(5+x, 5+y);
        listCol.move(5+x, 40+y);
        valeur.move(105+x, 45+y);
        champsModif.move(275+x,40+y);
    }

    public void hide() {
        titre.hide();
        listCol.hide();
        valeur.hide();
        champsModif.hide();
    }

    public void show() {
        titre.show();
        listCol.show();
        valeur.show();
        champsModif.show();
    }

    public String getColonne() {
        return listCol.getCurrentText();
    }

    public String getValeur() {
        return champsModif.getText();
    }
}
