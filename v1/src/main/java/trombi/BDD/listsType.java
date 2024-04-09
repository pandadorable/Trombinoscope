package trombi.BDD;

public class listsType {
    //Enum contenant les différents intitulés de colonnes du .xlsx original, et par extension les noms de celles de la BDD
    public static enum colonnes {
        prenom {
            @Override
            public String toString() {
                return "prenom";
            }
        },
        nom {
            @Override
            public String toString() {
                return "nom";
            }
        },
        email{
            @Override
            public String toString() {
                return "email";
            }
        },
        specialite{
            @Override
            public String toString() {
                return "specialite";
            }
        },
        option{
            @Override
            public String toString() {
                return "option";
            }
        },
        td{
            @Override
            public String toString() {
                return "td";
            }
        },
        tp{
            @Override
            public String toString() {
                return "tp";
            }
        },
        tdMut{
            @Override
            public String toString() {
                return "tdMut";
            }
        },
        tpMut{
            @Override
            public String toString() {
                return "tpMut";
            }
        },
        ang{
            @Override
            public String toString() {
                return "ang";
            }
        },
        innov{
            @Override
            public String toString() {
                return "innov";
            }
        },
        mana{
            @Override
            public String toString() {
                return "mana";
            }
        },
        expr{
            @Override
            public String toString() {
                return "expr";
            }
        },
        annee{
            @Override
            public String toString() {
                return "annee";
            }
        },
        image{
            @Override
            public String toString() {
                return "image";
            }
        },
    }
}
