package trombi.BDD;

import io.qt.widgets.QPushButton;
import io.qt.widgets.QWidget;

public class ModifBDD_subwidget {

    private String name;
    private int id;

    public ModifBDD_subwidget(QWidget widgetParent,String name,int id) {
        this.name = name;
        this.id = id;
        QPushButton photoButton = new QPushButton(name, widgetParent);
        photoButton.move(5, id*100+10);
        photoButton.resize(150, 40);
        photoButton.clicked.connect(this, "print()");
    }

    public void print() {
        System.out.println(name);
    }
}
