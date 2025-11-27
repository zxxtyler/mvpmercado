package angeloni;

import angeloni.view.MenuInicialView;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuInicialView();
            }
        });
    }
}