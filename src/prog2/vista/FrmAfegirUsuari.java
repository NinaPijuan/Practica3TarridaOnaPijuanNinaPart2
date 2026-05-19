package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmAfegirUsuari extends JDialog {
    private JPanel contentPane;
    private JButton btnAcceptar;
    private JButton btnCancelar;
    private JTextField txtNom;
    private JTextField txtEmail;
    private JTextField txtAdreca;
    private JCheckBox chkEstudiant;
    private JLabel etNom;
    private JLabel etEmail;
    private JLabel etAdreca;
    private Adaptador adaptador;

    public FrmAfegirUsuari(Adaptador adaptador) {
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(500, 400);
        setTitle("Afegir usuari");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // BOTÓ ACCEPTAR:
        // de primeres està desactivat
        btnAcceptar.setEnabled(false);

        // els escoltadors dels tres textfield mirem si cal activa-lo (criden un mètode implementat més avall)
        // CHAT DIU KUE AKUEST ÉS EL MILLOR PER DETECTAR KUAN ENTREN COSES EN UN JTEXTFIELD
        txtNom.getDocument().addDocumentListener(new DocumentListener() {
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

        txtEmail.getDocument().addDocumentListener(new DocumentListener() {
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

        txtAdreca.getDocument().addDocumentListener(new DocumentListener() {
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

        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    boolean estudiant = false;
                    // Per crear un usuari cal saber si és estudiant o professor
                    if(chkEstudiant.isSelected()){ estudiant = true; }
                    // Afegim usuari amb el mètode d'adaptador kue ho fa
                    adaptador.afegirUsuari(txtEmail.getText(), txtNom.getText(), txtAdreca.getText(), estudiant);
                    JOptionPane.showMessageDialog(null, "Usuari afegit correctament", "", JOptionPane.INFORMATION_MESSAGE);
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

        boolean plens = !txtNom.getText().trim().isEmpty() &&
                !txtEmail.getText().trim().isEmpty() &&
                !txtAdreca.getText().trim().isEmpty();

        btnAcceptar.setEnabled(plens);
    }
}
