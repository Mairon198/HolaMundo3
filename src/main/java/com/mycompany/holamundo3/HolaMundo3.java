//Mairon Omar Licona Duarte
//201810010200

//Hice un tercer push.

package com.mycompany.holamundo3;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Scanner;

/**
 *
 * @author Mairon
 */
public class HolaMundo3 {
    
    public static void main(String[] args) throws SQLException {
        System.out.println("==Ejecutando operaciones en tabla Alumnos==");
        
        //Obtener la conexion
        Connection cn = ConexionDB.getConexion();
        if(cn == null){
            System.out.println("No se puede establecer la conexion");
            return;
        }
        
        //Declarar los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;
        
        //Scanner para el menu
        Scanner sc = new Scanner(System.in);
        try{
            int opcion;
            do{
                System.out.println("\n====================================");
                System.out.println("              MENU       ");
                System.out.println("====================================");
                System.out.println("1. Insertar");
                System.out.println("2. Consultar");
                System.out.println("3. Actualizar");
                System.out.println("4. Eliminar");
                System.out.println("5. Salir");
                System.out.println("====================================");
                System.out.print("Seleccione una opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();
                
                switch(opcion){
                    //Parte A Insertar
                    case 1:
                        System.out.println("\n1. Insertando nuevo Alumno...");
                        String psInsert = "INSERT INTO Alumnos(nombre, correo) VALUES (?,?)";
                        psInsertar = cn.prepareStatement(psInsert);
                        System.out.print("Ingrese el nombre del alumno: ");
                        String nombre = sc.nextLine();
                        System.out.print("Ingrese el correo del alumno: ");
                        String correo = sc.nextLine();
                        
                        //Asignaremos valores
                        psInsertar.setString(1, nombre);
                        psInsertar.setString(2, correo);
                        //Ejecutar la insercion
                        int filasAfectadas = psInsertar.executeUpdate();
                        if(filasAfectadas > 0){
                            System.out.println("!Alumno insertado con exito");
                        }break;
                    
                    //Parte B Seleccionar
                    case 2:
                        System.out.println("\n2. Consulta de lista de Alumnos en la base de datos");
                        String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
                        psSeleccionar = cn.prepareStatement(sqlSelect);
                        rs = psSeleccionar.executeQuery();
                        System.out.println("\n=====================================================");
                        System.out.println("  ID     |Nombre               |Correo");
                        System.out.println("=====================================================");
                        while(rs.next()){
                            int id = rs.getInt("id");
                            String nombreAlumno = rs.getString("nombre");
                            String correoAlumno = rs.getString("correo");
                            // Imprimimos la tabla virtual
                            System.out.printf("%-3d | %-20s | %s\n",
                                    id, nombreAlumno, correoAlumno);
                        }
                        System.out.println("=======================================================");
                        rs.close();
                        rs = null;
                        break;
                    
                    //Parte C Actualizar
                    case 3:
                        System.out.println("\n3. Actualizando datos de un Alumno...");
                        String sqlUpdate = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
                        psActualizar = cn.prepareStatement(sqlUpdate);
                        System.out.print("Ingrese el ID del alumno que desea actualizar: ");
                        int idActualizar = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Ingrese el nuevo nombre: ");
                        String nuevoNombre = sc.nextLine();
                        System.out.print("Ingrese el nuevo correo: ");
                        String nuevoCorreo = sc.nextLine();
                        psActualizar.setString(1, nuevoNombre);
                        psActualizar.setString(2, nuevoCorreo);
                        psActualizar.setInt(3, idActualizar);
                        int filasActualizadas = psActualizar.executeUpdate();
                        if(filasActualizadas > 0){
                            System.out.println("!Alumno actualizado con exito");
                        }else{
                            System.out.println("No se encontro un alumno con ese ID.");
                        }
                        break;
                    
                    //Parte D Eliminar
                    case 4:
                        System.out.println("\n4. Eliminando un Alumno...");
                        String sqlDelete = "DELETE FROM Alumnos WHERE id = ?";
                        psEliminar = cn.prepareStatement(sqlDelete);
                        System.out.print("Ingrese el ID del alumno que desea eliminar: ");
                        int idEliminar = sc.nextInt();
                        psEliminar.setInt(1, idEliminar);
                        int filasEliminadas = psEliminar.executeUpdate();
                        if(filasEliminadas > 0){
                            System.out.println("!Alumno eliminado con exito");
                        }else{
                            System.out.println("No se encontro un alumno con ese ID.");
                        }
                        break;
                    
                    //Salir
                    case 5:
                        System.out.println("\nSaliendo del programa...");
                        break;
                    default:
                        System.out.println("\nOpcion no valida. Intente nuevamente.");
                }
            }while(opcion != 5);
        }catch(SQLException e){
            System.err.println("Ocurrio un error con la base de datos: " + e.getMessage());
            
        }finally{
            //Cerrar la conexion de manera obligatoria
            if(rs != null) 
               rs.close();
            if(psInsertar != null) psInsertar.close();
            if(psSeleccionar != null) psSeleccionar.close();
            if(psActualizar != null) psActualizar.close();
            if(psEliminar != null) psEliminar.close();
            
            sc.close();
            ConexionDB.cerrarConexion();
        }
    }
}
