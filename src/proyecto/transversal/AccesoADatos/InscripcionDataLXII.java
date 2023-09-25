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
import proyecto.transversal.Entidades.AlumnoLXII;
import proyecto.transversal.Entidades.InscripcionLXII;
import proyecto.transversal.Entidades.MateriaLXII;

/**
 *
 * @author Agente Sosa
 */
public class InscripcionDataLXII {

    private Connection con = null;
    private AlumnoDataLXII aluData;
    private MateriaDataLXII matData;

//    public InscripcionDataG62() {
//    }
    public InscripcionDataLXII() {
        this.con = ConexionLXII.getConexion();
    }

    public void guardarInscripcion(InscripcionLXII inscripcionG62) {

        try {
            String sql = "INSERT INTO inscripcion (idAlumno , idMateria, nota) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, inscripcionG62.getAlumno().getId_alumno());
            ps.setInt(2, inscripcionG62.getMateria().getIdMateria());
            ps.setDouble(3, inscripcionG62.getNota());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                inscripcionG62.setId_inscripcion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "NSCRIPCION REGISTRADA");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA INSCRIPCION" + ex.getMessage());
        }
    }

    public void actualizarNota(int IdAlumnoLXII, int IdMateriaLXII, double nota) {

        String sql = "UPDATE inscripcion SET nota = ? WHERE idAlumno= ? AND idMateria = ?, ";

        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, nota);
            ps.setInt(2, IdAlumnoLXII);
            ps.setInt(3, IdMateriaLXII);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "NOTA ACTIUALIZADA");
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA DE INSCRIPCION" + ex.getMessage());

        }

    }

    public void borrarInscripcionMateriaAlumno(int IdAlumnoLXII, int IdMateriaLXII) {
        String sql = "DELETE FROM inscripcion WHERE idAlumno = ? AND idMateria =? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, IdAlumnoLXII);
            ps.setInt(2, IdMateriaLXII);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "INSCRIPCION BORRADA");

            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TABLA DE INSCRIPCION" + ex.getMessage());
        }

    }

    public List<InscripcionLXII> obtenerInscripciones() {
        ArrayList<InscripcionLXII> cursadas = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InscripcionLXII InscripcionLXII = new InscripcionLXII();
                InscripcionLXII.setId_inscripcion(rs.getInt("id_inscripcion"));
                AlumnoLXII alu = aluData.buscarAlumno(rs.getInt("id_alumno"));
                MateriaLXII mat = matData.buscarMateria(rs.getInt("idMateria"));
                InscripcionLXII.setAlumno(alu);
                InscripcionLXII.setNota(rs.getDouble("nota"));
                InscripcionLXII.setMateria(mat);
                cursadas.add(InscripcionLXII);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TALBLA INSCRIPCION");
        }
        return cursadas;
    }

//    public boolean agregarMateria(MateriaLXII materiaLXII) throws SQLException {
//        boolean insert = true;
//        String sql = "INSERT INTO materia(nombre, anio, activo VALUES (?, ?, ?)";
//        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        ps.setString(1, materiaLXII.getNombre());
//        ps.setInt(2, materiaLXII.getAnio());
//        ps.setBoolean(3, materiaLXII.isEstado());
//
//        ps.executeUpdate();
//
//        ResultSet rs = ps.getGeneratedKeys();
//        if (rs.next()) {
//            materiaLXII.setIdMateria(rs.getInt(1));
//
//        } else {
//            insert = false;
//
//        }
//        return insert;
//
//    }

    public List obtenerInscripcionesPorAlumno(int Id_alumnoLXII) {

        ArrayList<InscripcionLXII> cursadas = new ArrayList<>();
        String sql = "SELECT * FROM inscripcion WHERE idAlumno= ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Id_alumnoLXII);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InscripcionLXII incs = new InscripcionLXII();
                incs.setId_inscripcion(rs.getInt("idInscripcion"));
                AlumnoLXII alu = aluData.buscarAlumno(rs.getInt("idAlumno"));
                MateriaLXII mat = matData.buscarMateria(rs.getInt("idMateria"));
                incs.setAlumno(alu);
                incs.setNota(rs.getDouble("nota"));
                cursadas.add(incs);
            }
            ps.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR AL ACCEDER A LA TALBLA INSCRIPCION");
        }
        return cursadas;
    }

    public List<MateriaLXII> obtenerMateriasCursadas(int id_alumno) {
        ArrayList<MateriaLXII> materias = new ArrayList<>();
        String sql = "SELECT inscripcion.idMateria, nombre, año, FROM  inscripcion, materia WHERE inscripcion.idMateria= materia.idMateria AND inscripcion.id_alumno = ?;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_alumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MateriaLXII materia = new MateriaLXII();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAño(rs.getInt("año"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla inscripcion");
        }
        return materias;
    }

    public List<MateriaLXII> obtenerMateriasNoCursadas(int id_alumno) {
        ArrayList<MateriaLXII> materias = new ArrayList<>();
        String sql = "SELECT* FROM materia WHERE estado = 1 AND idMateria NOT IN SELECT idMateria FROM inscripcion WHERE id_alumno = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id_alumno);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MateriaLXII materia = new MateriaLXII();
                materia.setIdMateria(rs.getInt("idMateria"));
                materia.setNombre(rs.getString("nombre"));
                materia.setAño(rs.getInt("año"));
                materias.add(materia);
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error al acceder a la tabla inscripcion");
        }
        return materias;
    }

    public List<AlumnoLXII> obtenerAlumnoPorMateria(int idMateria) {
        ArrayList<AlumnoLXII> alumnosMateria = new ArrayList<>();
        String sql = "SELECT a.id_alumno, dni, nombre, apellido, fechaNacimiento, estado FROM inscripcion i, alumno a WHERE"
                + "i.id_alumno = a.id_alumno AND idMateria = ? AND a.estado = 1 ";
        try {
            PreparedStatement ps = con.prepareCall(sql);
            ps.setInt(1, idMateria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AlumnoLXII alumno = new AlumnoLXII();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setDni(rs.getInt("dni"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnosMateria.add(alumno);

            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "   error al acceder a la tabla inscripcion");
        }
        return alumnosMateria;

    }
}
