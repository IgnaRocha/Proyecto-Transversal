/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.transversal.AccesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import proyecto.transversal.Entidades.Alumno;

/**
 *
 * @author Agente Sosa
 */
public class AlumnoData {
    
    private Connection con = null;

    public AlumnoData() {
         con = Conexion.getConexion();
                 }
    
    public void guardarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado) VALUES (?,?,?,?,?)";

        try {

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setBoolean(5, alumno.isEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setIdAlumno(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Alumno guardado");
            }
            ps.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "el alumno ya existe");
        }catch (SQLException ex) {
  
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumno");

    }
    }


    
    public Alumno buscarAlumno(int id){
        Alumno alumno = null;
        String sql= "SELECT * FROM alumno WHERE id_alumno = ? AND estado=1";
        PreparedStatement ps = null;
        
        try{
            
            ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                alumno = new Alumno();
                alumno.setIdAlumno(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                
            }else{
                JOptionPane.showMessageDialog(null, "no existe el alumno ");
                ps.close();
            } 
                
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla alumno" + ex.getMessage());        
                    }
            return alumno;
        }
    
    public Alumno buscarAlumnoPorDni(int dni){
        Alumno alumno = null;
        String sql= "SELECT * FROM alumno WHERE dni = ? AND estado=1";
//        PreparedStatement ps = null;
      
        try{
//            PreparedStatement ps = null;
//            ps = con.prepareStatement(sql);
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
            ps.setInt(1,dni);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
                alumno = new Alumno();
                alumno.setDni(rs.getInt("dni"));
                alumno.setIdAlumno(rs.getInt("id_alumno"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                System.out.println(rs.getDate(5));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                
                
            }else{
                JOptionPane.showMessageDialog(null, "no existe el alumno");
                ps.close();
            } 
                
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla alumno" + ex.getMessage());        
                    
            }
        System.out.println(alumno);
            return alumno;
        }
        
        public List<Alumno> listarAlumnos(){
           List<Alumno> alumnos = new ArrayList<>();
           
           try{
               String sql = "SELECT * FROM alumno WHERE estado = 1";
               PreparedStatement ps = con.prepareStatement(sql);
               ResultSet rs = ps.executeQuery();
               while (rs.next()){
                   Alumno alumno = new Alumno();
                   
                   alumno.setIdAlumno(rs.getInt("id_alumno"));
                   alumno.setDni (rs.getInt("dni"));
                   alumno.setApellido(rs.getString("apellido"));
                   alumno.setNombre(rs.getString("nombre"));
                   alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                   alumno.setEstado(rs.getBoolean("estado"));
                   alumnos.add(alumno);
                   System.out.println("______________________");
               }
               ps.close();
           }catch (SQLException ex){
               JOptionPane.showMessageDialog(null, "error al acceder a la tabla alumno"+ ex.getMessage()) ;
               
        }
           return alumnos;
        
    }
    public void modificarAlumno (Alumno alumno){
        String sql = "UPDATE alumno SET dni = ?, apellido = ?, nombre= ?, fechaNacimiento = ? WHERE id_alumno = ?";
        PreparedStatement ps = null;
        
        try{
            ps= con.prepareStatement(sql);
            ps.setInt (1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            ps.setInt(5, alumno.getIdAlumno());
            int exito = ps.executeUpdate();
            
            if (exito ==1){
                JOptionPane.showMessageDialog(null, "MODIFICADO EXITOSAMENTE.");
                
            }else {
               JOptionPane.showMessageDialog(null, "EL ALUMNO NO EXISTE");
               
            }
        }catch (SQLException ex){
             JOptionPane.showMessageDialog(null, "ERROR ALACCEDER A LA TABLA ALUMNO"+ ex.getMessage());
        }
    }
    
    public void eliminiarAlumno (int id){
        
        try{
            String sql= "UPDATE alumno SET estado= 0 WHERE idAlumno = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(2, id);
            int fila = ps.executeUpdate();
            
            if(fila ==1){
               JOptionPane.showMessageDialog(null, "SE ELIMINÓ ALUMNO.");
               
            }
            ps.close();
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA ALUMNO");
        }
    }
    
    
    public void prueba(){
        
    try{
     String sql= "SELECT * FROM dePrueba";
            
            PreparedStatement ps = con.prepareStatement(sql);  
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getDate("fecha"));
            }
           
}catch(SQLException e){
    
}
    }
}
