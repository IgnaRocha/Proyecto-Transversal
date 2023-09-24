/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto.transversal;

import java.sql.Connection;
import proyecto.transversal.AccesoADatos.ConexionLXII;

/**
 *
 * @author Administrador
 */
public class ProyectoTransversal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Connection con=ConexionLXII.getConexion();
        
    }
    
}
