package trombi.BDD.MODIF_BDD;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import io.qt.widgets.*;
import trombi.BDD.MariaDB;

public class ModifBDDWindow {

    public static ModifBDD_subwidget tSubwidget;
    public static ArrayList<ModifBDD_subwidget> listCond;
    public static int nb_cond_bdd;

    private static QWidget widgetParent;

    public ModifBDDWindow(QWidget widgetParent) {
        ModifBDDWindow.widgetParent = widgetParent;

        tSubwidget = new ModifBDD_subwidget(widgetParent, "Quelle colonne voulez vous modifier : ");
        QLabel condiLabel = new QLabel(widgetParent);
        condiLabel.move(5, 95);
        condiLabel.setText("Pour quelle(s) condition(s) : ");

        listCond = new ArrayList<>();
        init_list_cond();
        ModifBDD_plus_ou_moins_subwidget line = new ModifBDD_plus_ou_moins_subwidget(this, widgetParent);

        QPushButton verifButton = new QPushButton(widgetParent);
        verifButton.setText("VERIFICATION");
        verifButton.move(450, 10);
        verifButton.clicked.connect(this, "verifQuantity()");
    }

    public static void init_list_cond() {
        nb_cond_bdd = 0;
        for (int i = 0; i < 5; i++) {
            ModifBDD_subwidget tmp = new ModifBDD_subwidget(ModifBDDWindow.widgetParent, "");
            tmp.move(0, 95 + i * 50);
            tmp.hide();
            ModifBDDWindow.listCond.add(tmp);
        }

    }

    public static void verifQuantity() {
        if (checkAllBoxusable()) {
            String[] colC = new String[nb_cond_bdd];
            String[] cond = new String[nb_cond_bdd];
            for (int i = 0; i < nb_cond_bdd; i++) {
                colC[i] = listCond.get(i).getColonne();
                cond[i] = listCond.get(i).getValeur();
            }
            try {
                System.out.println(MariaDB.howMuchDataExist(colC, cond, MariaDB.typeCondition.AND));
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Des cases ne sont pas remplies");
        }
    }

    public static boolean checkAllBoxusable() {
        boolean titre = !tSubwidget.getColonne().equals("") && !tSubwidget.getValeur().equals("");
        boolean cond = true;
        for (int i = 0; i < nb_cond_bdd; i++) {
            cond = cond && !listCond.get(i).getColonne().equals("") && !listCond.get(i).getValeur().equals("");
        }
        return titre && cond;
    }

    public void plus() {
        System.out.println("plus");
        if (ModifBDDWindow.nb_cond_bdd < ModifBDDWindow.listCond.size()) {
            ModifBDDWindow.listCond.get(ModifBDDWindow.nb_cond_bdd).show();
            ModifBDDWindow.nb_cond_bdd++;
            refreshPosition();
        }
    }

    public void moins() {
        System.err.println("moins");
        if (ModifBDDWindow.nb_cond_bdd > 0) {
            ModifBDDWindow.nb_cond_bdd--;
            ModifBDDWindow.listCond.get(ModifBDDWindow.nb_cond_bdd).hide();
            refreshPosition();
        }
    }

    public static void refreshPosition() {
        ModifBDD_plus_ou_moins_subwidget.plus.move(25, ModifBDD_plus_ou_moins_subwidget.start_offset
                + nb_cond_bdd * ModifBDD_plus_ou_moins_subwidget.offset_per_cond);
        ModifBDD_plus_ou_moins_subwidget.moins.move(75, ModifBDD_plus_ou_moins_subwidget.start_offset
                + nb_cond_bdd * ModifBDD_plus_ou_moins_subwidget.offset_per_cond);
    }

}