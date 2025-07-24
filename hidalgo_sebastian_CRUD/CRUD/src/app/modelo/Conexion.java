package app.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class Conexion {
    public Connection conectar(){
        String url="jdbc:sqlite:"+new File("C:/Users/POO/Downloads/prod.db").getAbsolutePath();
        Connection conn=null;
        try {
            conn= DriverManager.getConnection(url);
            System.out.println("Holaa");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public boolean validarUsuario(String user, String pass) {
        String ruta = "C:/Users/POO/IdeaProjects/CRUD/src/app/modelo/users.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    String contrasena = partes[1].trim();
                    if (user.equals(nombre) && pass.equals(contrasena)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
        return false;
    }
    }
/*
    public static void main(String[] args) {
     Conexion c= new Conexion();
     c.conectar();
    }

 */

