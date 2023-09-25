/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.transversal;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import proyecto.transversal.AccesoADatos.ConexionLXII;
import proyecto.transversal.Entidades.AlumnoLXII;
import proyecto.transversal.Entidades.InscripcionLXII;
import proyecto.transversal.Entidades.MateriaLXII;
import proyecto.transversal.AccesoADatos.AlumnoDataLXII;
import proyecto.transversal.AccesoADatos.InscripcionDataLXII;
import proyecto.transversal.AccesoADatos.MateriaDataLXII;

/**
 *
 * @author Administrador
 */
public class ProyectoTransversal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Connection con = ConexionLXII.getConexion();

//        InscripcionDataLXII id = new InscripcionDataLXII();
//        
//        AlumnoDataLXII ad = new AlumnoDataLXII();
//        AlumnoLXII alu = new AlumnoLXII();
//
//        AlumnoLXII Diego = new AlumnoLXII(33963258, "Garcia", "Diego", LocalDate.of(1989, 8, 16), true);
//        ad.guardarAlumno(Diego); 
//        
//        for (AlumnoLXII alum: id.obtenerAlumnoPorMateria(5))//andando
//            System.out.println(alum.toString());//andando
        
        
    }
    
}
