package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class FrmAfegirExemplar extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JTextField txtId;
    private JTextField txtTitol;
    private JTextField txtAutor;
    private JCheckBox chkAdmetLlarg;
    private JLabel etId;
    private JLabel etTitol;
    private JLabel etAutor;

    private Adaptador adaptador;


    // és raro però em penso kue el chkAdmetLlarg no necessita escoltador pk hi ha el mètode
    // chk.isSelected() kue ja funciona
    public FrmAfegirExemplar(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(500, 400);
        setTitle("Afegir exemplar");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // BOTÓ ACCEPTAR:
        // de primeres està desactivat
        btnAcceptar.setEnabled(false);

        // els escoltadors dels tres textfield mirem si cal activa-lo
        // CHAT DIU KUE AKUEST ÉS EL MILLOR PER DETECTAR KUAN ENTREN COSES EN UN JTEXTFIELD
        txtId.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
        });

        txtTitol.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
        });

        txtAutor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualitzarActivacioBtnAcceptar();
            }
        });

        // escoltador del botó un cop activat
        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    boolean admetPrestecLlarg = false;
                    if(chkAdmetLlarg.isSelected()){ admetPrestecLlarg = true; }
                    adaptador.afegirExemplar(txtId.getText(), txtTitol.getText(), txtAutor.getText(), admetPrestecLlarg);
                    JOptionPane.showMessageDialog(null, "Exemplar afegit correctament", "", JOptionPane.INFORMATION_MESSAGE
                    );
                    dispose();

                    // CAL REFRESCAR LA LLISTA??

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // BOTÓ CANCEL·LAR
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });



    }

    /*
    * Mètode kue activa el botó acceptar si els tres jtextfields estan plens
     */
    private void actualitzarActivacioBtnAcceptar() {

        boolean plens = !txtTitol.getText().trim().isEmpty() &&
                        !txtAutor.getText().trim().isEmpty() &&
                        !txtId.getText().trim().isEmpty();

        btnAcceptar.setEnabled(plens);
    }
}
