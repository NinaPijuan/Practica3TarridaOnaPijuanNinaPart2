package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;

public class FrmGestioExemplars extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private Adaptador adaptador;

    public FrmGestioExemplars(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

    }


}
