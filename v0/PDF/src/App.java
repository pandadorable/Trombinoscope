package PDF.src;

import java.io.File;

public class App {
    public static final String DEST = "v0/PDF/src/trombi.pdf"; //PDF final (destination)
    public static final String IMG = "v0/PDF/src/zeldalogo.png";

    public static void main(String[] args) throws Exception {
        
        //pour le test
        String[] nomE = new String[20];
        for (int i = 0; i < 20; i++) {
            int j = i+1;
            nomE[i] = "Elève "+ j;
        }
        
        //pour le test
        String[] photoE = new String[20];
        for (int i = 0; i < 20; i++) {
            photoE[i] = IMG;
        }

        GenererPdf pdf = new GenererPdf(DEST);
        pdf.manipulatePdf(nomE, photoE);
    }

    
}
