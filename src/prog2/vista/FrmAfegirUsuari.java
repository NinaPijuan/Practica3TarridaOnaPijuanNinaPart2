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
    private JLabel etFormat;
    private Adaptador adaptador;

    public FrmAfegirUsuari(JDialog parent, Adaptador adaptador) {
        super(parent);
        this.adaptador = adaptador;
        setContentPane(contentPane);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Afegir usuari");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(btnAcceptar);

        // BOTÓ ACCEPTAR:
        // de primeres està desactivat
        btnAcceptar.setEnabled(false);
        etFormat.setVisible(false);

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
            public void insertUpdate(DocumentEvent e) { validarEmail();}

            @Override
            public void removeUpdate(DocumentEvent e) { validarEmail();}

            @Override
            public void changedUpdate(DocumentEvent e) { validarEmail();}
        });

        btnAcceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    // Afegim usuari amb el mètode d'adaptador kue ho fa
                    adaptador.afegirUsuari(txtEmail.getText(), txtNom.getText(), txtAdreca.getText(), chkEstudiant.isSelected());
                    JOptionPane.showMessageDialog(parent, "Usuari afegit correctament", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                        !txtAdreca.getText().trim().isEmpty() &&
                        txtEmail.getText().contains("@") &&
                        txtEmail.getText().contains(".");
        btnAcceptar.setEnabled(plens);
    }

    // Mètode per validar email
    private void validarEmail() {
        String email = txtEmail.getText().trim();
        boolean valid = email.contains("@") && email.contains(".");
        etFormat.setVisible(!email.isEmpty() && !valid);
        actualitzarActivacioBtnAcceptar();
    }
}
