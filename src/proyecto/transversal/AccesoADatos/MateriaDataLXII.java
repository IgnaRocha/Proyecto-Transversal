/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.transversal.AccesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import proyecto.transversal.Entidades.MateriaLXII;

/**
 *
 * @author Agente Sosa
 */
public class MateriaDataLXII {

    private Connection con = null;

    public MateriaDataLXII() {

        con = ConexionLXII.getConexion();
    }

    public void guardarMateria(MateriaLXII materia) {
        String sql = "INSERT INTO materia(nombre, año, estado) VALUES (?,?,?)";
        PreparedStatement ps = null;

        try {
//            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps = con.prepareStatement(sql);
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAño());
            ps.setBoolean(3, materia.isEstado());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "SE AGREGÓ LA MATERIA CORRECTAMENTE");
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL AGREGAR LA MATERIA" + ex.getMessage());

        }

    }
    

    public MateriaLXII buscarMateria(int id) {
        String sql = "SELECT idMateria, nombre, año, estado FROM materia WHERE idMateria = ? AND estado = 1";
        MateriaLXII materia = null;
        PreparedStatement ps = null;

        try{
            
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                materia = new MateriaLXII();
                materia.setIdMateria(id);
                materia.setNombre(rs.getString("nombre"));
                materia.setAño(rs.getInt("año"));
                materia.setEstado(rs.getBoolean("estado"));
                
                
            }else{
                JOptionPane.showMessageDialog(null, "NO EXISTE LA MATERIA");
                ps.close();
            } 
                
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla materia" + ex.getMessage());        
                    }
            return materia;
        }
        
     
public void modificarMateria (MateriaLXII materia){
        String sql = "UPDATE materia SET idMateria= ? ,nombre = ?, anio = ?, estado = ?  WHERE 1";
        PreparedStatement ps = null;
        
        try{
            ps= con.prepareStatement(sql);
           
            ps.setInt(1, materia.getIdMateria());
            ps.setString(2, materia.getNombre());
            ps.setString(3, materia.getNombre());
            
            ps.setInt(5, materia.getIdMateria());
            int exito = ps.executeUpdate();
            
            if (exito ==1){
                JOptionPane.showMessageDialog(null, "MODIFICADA LA MATERIA EXITOSAMENTE.");
                
            }else {
               JOptionPane.showMessageDialog(null, "LA MATERIA NO EXISTE");
               
            }
        }catch (SQLException ex){
             JOptionPane.showMessageDialog(null, "ERROR ALACCEDER A LA TABLA MATERIA"+ ex.getMessage());
        }
    }
    
    public void eliminarMateria (int id) {
        
    try{
            String sql= "UPDATE materia SET idMateria = ?,nombre = ?, año= ?,estado =? WHERE 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, id);
            int fila = ps.executeUpdate();
            
            if(fila ==1){
               JOptionPane.showMessageDialog(null, "SE ELIMINÓ LA MATERIA.");
               
            }
            ps.close();
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA MATERIA");
        }
    }

       /*
    public List<AlumnoG62> listarAlumnos(){
           List<AlumnoG62> alumnos = new ArrayList<>();
    */
   
   public List<MateriaLXII> listarMaterias(){
           List<MateriaLXII> areas = new ArrayList<>();
           
           try{
               String sql = "SELECT idMateria, nombre, año, estado FROM materia WHERE estado = 1";
               PreparedStatement ps = con.prepareStatement(sql);
               ResultSet rs = ps.executeQuery();
               while (rs.next()){
                   MateriaLXII materia = new MateriaLXII();
                   materia.setIdMateria(rs.getInt("idMateria"));
                   materia.setNombre (rs.getString("nombre"));
                   materia.setAño(rs.getInt("año"));
                   materia.setEstado(rs.getBoolean("estado"));
                   areas.add(materia);
                   
               }
               ps.close();
           }catch (SQLException ex){
               JOptionPane.showMessageDialog(null, "error al acceder a la tabla materia"+ ex.getMessage()) ;
               
        }
           return areas;
        
    }
}
