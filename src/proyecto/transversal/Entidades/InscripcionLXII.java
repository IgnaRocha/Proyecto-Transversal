/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.transversal.Entidades;

/**
 *
 * @author Kelly
 */
public class InscripcionLXII {
    private int id_inscripcion;
    private AlumnoLXII alumno;
    private MateriaLXII materia;
    private double nota;   

    public InscripcionLXII() {
    }

    public InscripcionLXII(int id_inscripcion, AlumnoLXII alumno, MateriaLXII materia, double nota) {
        this.id_inscripcion = id_inscripcion;
        this.alumno = alumno;
        this.materia = materia;
        this.nota = nota;
    }

    public InscripcionLXII(AlumnoLXII alumno, MateriaLXII materia, double nota) {
        this.alumno = alumno;
        this.materia = materia;
        this.nota = nota;
    }

    public int getId_inscripcion() {
        return id_inscripcion;
    }

    public void setId_inscripcion(int id_inscripcion) {
        this.id_inscripcion = id_inscripcion;
    }

    public AlumnoLXII getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoLXII alumno) {
        this.alumno = alumno;
    }

    public MateriaLXII getMateria() {
        return materia;
    }

    public void setMateria(MateriaLXII materia) {
        this.materia = materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
    String inscripcion= id_inscripcion + " " + alumno.getApellido()+ "," + alumno.getNombre()+ "," + materia.getNombre();
    return inscripcion;
    }
    
    
}
