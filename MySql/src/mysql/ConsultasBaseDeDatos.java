/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Israel
 */
class ConsultasBaseDeDatos {

    public static void Consultas(Connection conexion) throws SQLException {

        Scanner leer = new Scanner(System.in);
        boolean salir = false;
        do {
            menu();
            String opcion = leer.nextLine();
            switch (opcion.toLowerCase()) {
                case "01":
                case "1":
                    menu2(conexion);
                    break;
                case "02":
                case "2":
                    menu3(conexion);
                    break;
                case "00":
                case "0":
                case "salir":
                case "q":
                    System.out.println("Sesión finalizada");
                    conexion.close();
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no valida");
                    break;
            }

        } while (!salir);
    }

    private static void menu() {
        System.out.println("01.-Consultas tablas mysql");
        System.out.println("02.-Modificaciones tablas mysql");
        System.out.println("00.-Salir");
    }

    private static void menu2(Connection conexion) throws SQLException {
        boolean salir2 = false;
        Scanner leer2 = new Scanner(System.in);
        do {
            System.out.println("Elige opción");
            System.out.println("01.-Mostrar información sobre la base de datos");
            System.out.println("02.-Mostrar información sobre las tablas");
            System.out.println("03.-Información sobre las columnas de cada tabla");
            System.out.println("04.-Ejecutar sentencias de descripción de datos");
            System.out.println("00.-Volver");
            String opcion = leer2.nextLine();
            switch (opcion.toLowerCase()) {
                case "01":
                case "1":
                    mostrarDatos(conexion);
                    break;
                case "02":
                case "2":
                    mostrarTablas(conexion);
                    break;
                case "03":
                case "3":
                    mostrarColumnas(conexion);
                    break;
                case "04":
                case "4":
                    mostrarDescripcion(conexion);
                case "00":
                case "0":
                case "salir":
                case "q":
                    System.out.println("Sesión finalizada");
                    conexion.close();
                    salir2 = true;
                    break;
                default:
                    System.out.println("Opción no valida");
                    break;
            }
        } while (!salir2);
    }

    private static void menu3(Connection conexion) throws SQLException {
        boolean salir3 = false;
        Scanner leer3 = new Scanner(System.in);
        do {
            System.out.println("01.-Crear tabla departamentos");
            System.out.println("02.-Modificar tabla departamentos");
            System.out.println("03.-Borrar tabla departamentos");
            System.out.println("04.-Crear tabla empleados");
            System.out.println("05.-Modificar tabla empleados");
            System.out.println("06.-Borrar tabla empleados");
            System.out.println("07.-Crear nuevo empleado");
            System.out.println("08.-Modificar empleado");
            System.out.println("09.-Borrar empleado");
            System.out.println("10.-Crear nuevo departamento");
            System.out.println("11.-Modificar departamento");
            System.out.println("12.-Borrar departamento");
            String opcion3 = leer3.nextLine();
            switch (opcion3.toLowerCase()) {
                case "01":
                case "1":
                    crearTablaDepartamento(conexion);
                    break;
                case "02":
                case "2":
                    modificarTablaDepartamento(conexion);
                    break;
                case "03":
                case "3":
                    borrarTablaDepartamento(conexion);
                    break;
                case "04":
                case "4":
                    crearTablaEmpleado(conexion);
                case "05":
                case "5":
                    modificarTablaEmpleado(conexion);
                    break;
                case "06":
                case "6":
                    borrarTablaEmpleado(conexion);
                    break;
                case "07":
                case "7":
                    crearEmpleado(conexion);
                    break;
                case "08":
                case "8":
                    modificarEmpleado(conexion);
                    break;
                case "09":
                case "9":
                    borrarEmpleado(conexion);
                    break;
                case "10":
                    crearDepartamento(conexion);
                    break;
                case "11":
                    modificarDepartamento(conexion);
                    break;
                case "12":
                    borrarDepartamento(conexion);
                    break;
                case "00":
                case "0":
                case "salir":
                case "q":
                    System.out.println("Sesión finalizada");
                    conexion.close();
                    salir3 = true;
                    break;
                default:
                    System.out.println("Opción no valida");
                    break;
            }

        } while (salir3);
    }

    private static void mostrarDatos(Connection conexion) {
        try {
            DatabaseMetaData datos = conexion.getMetaData();
            String nombre = datos.getDatabaseProductName();
            String driver = datos.getDriverName();
            String url = datos.getURL();
            String usuario = datos.getUserName();
            System.out.println("\nINFORMACION BASE DE DATOS");
            System.out.println("Nombre: " + nombre);
            System.out.println("Driver: " + driver);
            System.out.println("URL: " + url);
            System.out.println("Usuario: " + usuario + "\n");

        } catch (Exception e) {
            Logger.getLogger(ConsultasBaseDeDatos.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    private static void mostrarTablas(Connection conexion) {
        DatabaseMetaData datos;
        try {
            datos = conexion.getMetaData();
            ResultSet resul = datos.getTables(null, null, null, null);
            while (resul.next()) {
                String catalogo = resul.getString("TABLE_CAT");
                String esquema = resul.getString("TABLE_SCHEM");
                String tabla = resul.getString("TABLE_NAME");
                String tipo = resul.getString("TABLE_TYPE");
                System.out.println(tipo + ": " + "catalogo: " + catalogo + ",esquema: " + esquema + ",tabla:" + tabla + "\n");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void mostrarColumnas(Connection conexion) {
        DatabaseMetaData datos;
        ResultSet columnas = null;
        try {
            datos = conexion.getMetaData();
            columnas = datos.getColumns(null, null, null, null);
            String name = columnas.getString(3);
            String nombreCol = columnas.getString("COLUMN_NAME"); //getString(4);
            String tipoCol = columnas.getString("TYPE_NAME"); //getString(6);
            String tamCol = columnas.getString("COLUMN_SIZE"); //getString(7);
            String nula = columnas.getString("IS_NULLABLE"); //getString(18);
            System.out.println("Tabla: " + name + " Columna: " + nombreCol
                    + ", Tipo: " + tipoCol + ", Tamaño: " + tamCol + ", ¿Es nula?: "
                    + nula + "\n");
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void mostrarDescripcion(Connection conexion) throws SQLException {
        Statement sentencia = conexion.createStatement();
        ResultSet rs = sentencia.executeQuery("Select * from departamentos");
        ResultSetMetaData rsmd = rs.getMetaData();
        int nCol = rsmd.getColumnCount();
        System.out.printf("%-20s%-15s%-8s%-5s\n", "NOMBRE", "TIPO", "NULA", "ANCHO");
        for (int i = 1; i <= nCol; i++) {
            System.out.printf("%-20s%-15s%-8s%-5s\n",
                    rsmd.getColumnName(i),
                    rsmd.getColumnTypeName(i),
                    rsmd.isNullable(i),
                    rsmd.getColumnDisplaySize(i));
        }
    }

    private static void crearTablaDepartamento(Connection conexion) {
        try {
            String comando = "CREATE TABLE IF NOT EXISTS `departamentos` (\n"
                    + "  `dept_no` tinyint(2) NOT NULL,\n"
                    + "  `dnombre` varchar(15) CHARACTER SET utf8 NOT NULL,\n"
                    + "  `loc` varchar(15) CHARACTER SET utf8 NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(comando);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void modificarTablaDepartamento(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void borrarTablaDepartamento(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void crearTablaEmpleado(Connection conexion) {
        try {
            String comando = "CREATE TABLE IF NOT EXISTS `empleados` (\n"
                    + "  `emp_no` smallint(4) unsigned NOT NULL,\n"
                    + "  `apellido` varchar(10) CHARACTER SET utf8 NOT NULL,\n"
                    + "  `oficio` varchar(10) CHARACTER SET utf8 NOT NULL,\n"
                    + "  `dir` smallint(6) NOT NULL,\n"
                    + "  `fecha_alt` date NOT NULL,\n"
                    + "  `salario` float(6,2) NOT NULL,\n"
                    + "  `comision` float(6,2) NOT NULL,\n"
                    + "  `dept_no` tinyint(2) NOT NULL\n"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;"
                    + "ALTER TABLE `departamentos`\n ADD PRIMARY KEY (`dept_no`);";
            Statement sentencia = conexion.createStatement();
            sentencia.executeUpdate(comando);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasBaseDeDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void modificarTablaEmpleado(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void borrarTablaEmpleado(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void crearEmpleado(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void modificarEmpleado(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void borrarEmpleado(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void crearDepartamento(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void modificarDepartamento(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void borrarDepartamento(Connection conexion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
