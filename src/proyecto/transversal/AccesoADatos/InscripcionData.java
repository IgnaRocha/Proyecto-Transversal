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
import proyecto.transversal.Entidades.Alumno;
import proyecto.transversal.Entidades.Inscripcion;
import proyecto.transversal.Entidades.Materia;

/**
 *
 * @author Agente Sosa
 */
public class InscripcionData {
    
    private Connection con= null;
    private AlumnoData aluData;
    private MateriaData matData;

//    public InscripcionDataG62() {
//    }
    
    public InscripcionData(){
        this.con = Conexion.getConexion();
    }
    
    public void  guardarInscripcion(Inscripcion inscripcionG62){
        
        try{
            String sql= "INSERT INTO inscripcion (idAlumno , idMateria, nota) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inscripcionG62.getAlumno().getIdAlumno());
            ps.setInt(2, inscripcionG62.getMateria().getIdMateria());
            ps.setDouble(3, inscripcionG62.getNota());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            
            if (rs.next()){
                inscripcionG62.setIdInscripcion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "NSCRIPCION REGISTRADA");
            }
              ps.close();
            }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA INSCRIPCION"+ ex.getMessage());
            }
            }
    
  
public void actualizarNota(int IdAlumnoG62, int IdMateriaG62, double nota){
    
    String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno= ? AND idMateria = ?, ";
    
     try{
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
         ps.setDouble(1, nota);
         ps.setInt(2, IdAlumnoG62);
         ps.setInt(3, IdMateriaG62);
         int filas = ps.executeUpdate();
         if (filas > 0 ){
         JOptionPane.showMessageDialog(null, "NOTA ACTIUALIZADA");
     }
        ps.close();
     }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA DE INSCRIPCION"+ ex.getMessage());
         
     }
        
             
    }
            
public void borrarInscripcionMateriaAlumno (int IdAlumnoG62, int IdMateriaG62){
    String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria =? ";
        try{
           PreparedStatement ps = con.prepareStatement(sql); 
            ps.setInt(1, IdAlumnoG62);
            ps.setInt(2, IdMateriaG62);
            int filas = ps.executeUpdate();
            if (filas > 0){
                JOptionPane.showMessageDialog(null, "INSCRIPCION BORRADA");
                
            }
            ps.close();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA DE INSCRIPCION"+ ex.getMessage());
        }
    
    
}
    public List <Inscripcion> obtenerInscripciones(){
    ArrayList <Inscripcion> cursadas=new ArrayList<>();
        String sql = "SELECT * FROM inscripcion";
          try{
              PreparedStatement ps= con.prepareStatement (sql);
              ResultSet rs= ps.executeQuery ();
          while (rs.next ()){
            Inscripcion InscripcionG62 = new Inscripcion();
            InscripcionG62.setIdInscripcion (rs.getInt ("idInscripcion"));
            Alumno alu = aluData.buscarAlumno (rs.getInt ("idAlumno"));
            Materia mat = matData.buscarMateria(rs.getInt ("idMateria"));
            InscripcionG62.setAlumno(alu);
            InscripcionG62.setNota(rs.getDouble("nota"));
            InscripcionG62.setMateria(mat);
            cursadas.add (InscripcionG62);
          }
        ps.close();
          }catch (SQLException ex){
              JOptionPane.showMessageDialog (null,"ERROR AL ACCEDER A LA TALBLA INSCRIPCION");
             }
       return cursadas;
    }

public boolean agregarMateria (Materia materiaG62) throws SQLException{
    boolean insert = true;
    String sql = "INSERT INTO materia(nombre, anio, activo VALUES (?, ?, ?)";
    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    ps.setString (1, materiaG62.getNombre());
    ps.setInt (2, materiaG62.getAnio());
    ps.setBoolean (3, materiaG62.isEstado());
    
    ps.executeUpdate();
    
    ResultSet rs = ps.getGeneratedKeys();
    if (rs.next()){
        materiaG62.setIdMateria(rs.getInt(1));
        
    }else{
        insert = false;
        
    }
        return insert;
    
    }
    
   
    
    public List obtenerInscripcionesPorAlumno(int IdAlumno) {
        
    ArrayList <Inscripcion> cursadas=new ArrayList<>();
 String sql = "SELECT * FROM inscripcion WHERE idAlumno= ?";
try{
    PreparedStatement ps= con.prepareStatement (sql);
    ps.setInt (1, IdAlumno);
    ResultSet rs= ps.executeQuery ();
    while (rs.next ()){
            Inscripcion incs=new Inscripcion();
            incs.setIdInscripcion (rs.getInt ("idInscripcion"));
            Alumno alu= aluData.buscarAlumno (rs.getInt ("idAlumno"));
            Materia mat=matData.buscarMateria (rs.getInt ("idMateria"));
            incs.setAlumno(alu);
            incs.setNota(rs.getDouble("nota"));
            cursadas.add (incs);
}
ps.close();
}catch (SQLException ex){
    JOptionPane.showMessageDialog (null,"ERROR AL ACCEDER A LA TALBLA INSCRIPCION");
}
return cursadas;
}
 
    
    public List <Materia> obtenerMateriasCursadas(int id_alumno){
        ArrayList <Materia> materias= new ArrayList<>();
       String sql = "SELECT inscripcion.idMateria, nombre, año, FROM  inscripcion, materia WHERE inscripcion.idMateria= materia.idMateria AND inscripcion.id_alumno = ?;"; 
    
    try{
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id_alumno);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Materia materia= new Materia();
            materia.setIdMateria(rs.getInt("idMateria"));
            materia.setNombre(rs.getString("nombre"));
            materia.setAnio(rs.getInt("anio"));
            materias.add(materia);
        }
        ps.close();
    }catch(SQLException e){
    JOptionPane.showMessageDialog(null,"error al acceder a la tabla inscripcion");
    }
    return materias;   
}
    
    public List <Materia> obtenerMateriasNoCursadas(int id_alumno){
     ArrayList <Materia> materias= new ArrayList<>();
     String sql = "SELECT* FROM materia WHERE estado = 1 AND idMateria NOT IN SELECT idMateria FROM inscripcion WHERE id_alumno = ? ";
     try{
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id_alumno);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Materia materia= new Materia();
            materia.setIdMateria(rs.getInt("idMateria"));
            materia.setNombre(rs.getString("nombre"));
            materia.setAnio(rs.getInt("anio"));
            materias.add(materia);
        }
        ps.close();
    }catch(SQLException e){
    JOptionPane.showMessageDialog(null,"error al acceder a la tabla inscripcion");
    }
     return materias;
    }
    
     public List <Alumno> obtenerAlumnoPorMateria(int idMateria){
     ArrayList <Alumno> alumnosMateria = new ArrayList<>();
     String sql = "SELECT a.id_alumno, dni, nombre, apellido, fechaNacimiento, estado FROM inscripcion i, alumno a WHERE" 
             +"i.id_alumno = a.id_alumno AND idMateria = ? AND a.estado = 1 "; 
        try{     
            PreparedStatement ps = con.prepareCall(sql);
            ps.setInt(1,idMateria);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Alumno alumno = new Alumno();
                
                alumno.setIdAlumno(rs.getInt("id_alumno"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
               alumnosMateria.add(alumno);
               
            }
         ps.close();
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "   error al acceder a la tabla inscripcion");
         }
        return alumnosMateria;
          
        
        }
}