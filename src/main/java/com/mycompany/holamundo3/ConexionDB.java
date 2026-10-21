/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.holamundo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mairon
 */
public class ConexionDB{
    private static final String URL = "jdbc:sqlserver://localhost:1433;" 
                 + "databaseName=AvanzadaII;"
                 + "encrypt=true;" 
                 + "trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "213061";

    private static Connection conexion = null;
    private ConexionDB(){}

    public static Connection getConexion() {
        try{
            if(conexion == null || conexion.isClosed()){
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                   //Establecer la conexión
                   conexion = DriverManager.getConnection(URL,USER,PASSWORD);
                   System.out.println("->Conexion existosa a Sql");
            }
        }catch(ClassNotFoundException e){
            System.err.println("Error no se encontro el Driver de mssql-jdbc" + e.getMessage());
        }catch(SQLException e){
            System.err.println("Error al conectar con SQL" + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion(){
        try{
            if(conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.println("->La conexion con SQL fue cerrada");
            }
        }catch(SQLException e){
            System.err.println("Error al cerrar la conexion" + e.getMessage());
        }
    }
}