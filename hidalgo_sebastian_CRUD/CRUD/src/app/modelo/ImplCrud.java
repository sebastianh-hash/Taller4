package app.modelo;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImplCrud implements Crud{

    private final String SELECT="SELECT * from producto";
    private final String SELECT_BY_ID="SELECT* from producto where id=?";
    private final String INSERT= "INSERT INTO producto(codigo, nombre, precio) VALUES(?,?,?)";
    private final String UPDATE="UPDATE producto set codigo=?, nombre=?, precio=? WHERE id=?";
    private final String DELETE="DELETE FROM producto WHERE id=?";


    private Connection conn=null;

    private  Connection conectar(){
        Conexion conexion=new Conexion();
        conn= conexion.conectar();
        return conn;
    }

    @Override
    public Map<Integer, Producto> seleccionarTodo() {
        Map<Integer,Producto> map = new LinkedHashMap<Integer,Producto>();
        try { Connection conn = this.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SELECT);

            while (rs.next()){
                Producto producto = new Producto(rs.getInt("id"),
                        rs.getString("codigo"),rs.getString("nombre"),
                        rs.getDouble("precio"));
                        System.out.println(producto);
                        map.put(rs.getInt("id"),producto);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return map;
    }

    // Para seleccionar uno
    @Override
    public Producto buscar(int id) {
        Connection conn=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        Producto producto= null;
        try {
            conn = this.conectar();
            stmt=conn.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1,id);;
            rs=stmt.executeQuery();rs.next();
            producto=new Producto(rs.getInt("id"), rs.getString("codigo"),
                    rs.getString("nombre"),rs.getDouble("precio"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return producto;
    }

    @Override
    public void insertar(Producto producto) {
        try {
            Connection conn = this.conectar();
            PreparedStatement pstmt=conn.prepareStatement(INSERT);
            pstmt.setString(1,producto.getCodigo());
            pstmt.setString(2,producto.getNombre());
            pstmt.setDouble(3,producto.getPrecio());
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void actualizar(Producto producto) {
        try {
            conn=this.conectar();
            PreparedStatement pstmt=conn.prepareStatement(UPDATE);
            pstmt.setString(1,producto.getCodigo());
            pstmt.setString(2,producto.getNombre());
            pstmt.setDouble(3,producto.getPrecio());
            pstmt.setInt(4,producto.getId());
            pstmt.executeUpdate();
            System.out.println("Producto actualizado");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void eliminar(int id) {
        try {
            conn=this.conectar();
            PreparedStatement pstmt=conn.prepareStatement(DELETE);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }


    public Producto seleccionarProdNOSEGURO(int id,String codigo) {
        Producto producto=null;
        try {Connection conn = this.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery ("SELECT * FROM producto WHERE id = "+id+" AND codigo='"+codigo+"'") ;
            while (rs.next()) {
                producto = new Producto(rs.getString("codigo"),rs.getString("nombre"),
                        rs. getDouble("precio"));
                System.out.println(producto);
            }
        } catch (SQLException e) {System.out.println(e.getMessage());}
        return producto;
    }

    public static void main(String[] args) {
        ImplCrud c = new ImplCrud();
//       String codigo="x 'OR  'a'='a";
        int id=Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese id: "));
        String codigo=JOptionPane.showInputDialog(null,"Ingrese codigo del producto: ");
        c.seleccionarProdNOSEGURO(id,codigo);
    }
}