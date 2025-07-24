package app.vista;

import app.controlador.Usuario;
import app.modelo.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class Login extends JFrame {
    private JPanel panelLogin;
    private JTextField txtNombre;
    private JButton btnAcceso;
    private JButton btnLimpiar;
    private JPasswordField txtPassword;

    public Login() {
        setTitle("LOGIN");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLogin);
        setLocationRelativeTo(null);

        btnAcceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = txtNombre.getText();
                String password = new String(txtPassword.getPassword());

                if (user.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
                    return;
                }

                Conexion conexion = new Conexion();

                if (conexion.validarUsuario(user, password)) {
                    JOptionPane.showMessageDialog(Login.this, "¡Login exitoso!");
                    Usuario.setUsuario(user);

                    Productos productos = new Productos();
                    productos.setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                txtNombre.setText("");
                txtPassword.setText("");
                txtNombre.requestFocus();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNombre.setText("");
                txtPassword.setText("");
            }
        });
    }

    private boolean verificarCredenciales(String usuario, String contrasena, String perfil) {
        try {
            BufferedReader br = new BufferedReader(new FileReader
                    ("C:\\Users\\POO\\Documentos\\users.txt"));
            String linea;
            //JOptionPane.showMessageDialog(null,"Exito al leer el archivo");
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String user = partes[0];
                    String pass = partes[1];
                    String per = partes [2];
                    if (usuario.equals(user) && contrasena.equals(pass) && perfil.equals(per)) {
                        br.close();
                        return true;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error ");
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
