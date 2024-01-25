package com.ceica.Modelos;

import java.lang.reflect.Method;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class ModeloBase {
    private static final String CONFIG_FILE = "config.properties";
    protected static String URL;
    protected static String USUARIO;
    protected static String PASSWORD;


    static {
        cargarConfiguracion();
    }

    private static void cargarConfiguracion() {
        Properties propiedades = new Properties();
        try (FileInputStream entrada = new FileInputStream(CONFIG_FILE)) {
            propiedades.load(entrada);
            URL = propiedades.getProperty("db.url");
            USUARIO = propiedades.getProperty("db.usuario");
            PASSWORD = propiedades.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método abstracto para obtener el nombre de la tabla
    protected abstract String getNombreTabla();

    // Métodos para CRUD

    protected void insertar(String sql, Object... parametros) {

        sql = "insert into " + getNombreTabla() + " " + sql;
        ejecutarQuery(sql, parametros);
    }

    protected void actualizar(String sql, Object... parametros) {
        sql = "update " + getNombreTabla() + " " + sql;
        ejecutarQuery(sql, parametros);
    }

    protected void borrar(String sql, Object... parametros) {
        sql = "delete from " + getNombreTabla() + " where " + sql;
        ejecutarQuery(sql, parametros);
    }

    // Método genérico para ejecutar consultas SQL
    private void ejecutarQuery(String sql, Object... parametros) {
        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            // Establecer los valores de los parámetros
            for (int i = 0; i < parametros.length; i++) {
                preparedStatement.setObject(i + 1, parametros[i]);
            }

            // Ejecutar la consulta
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract Object createObjectFromResultSet(ResultSet resultSet) throws SQLException;

    protected List<Object> leerTodos() {
        List<Object> resultList = new ArrayList<>();

        String sql = "SELECT * FROM " + getNombreTabla();

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
             PreparedStatement preparedStatement = conexion.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Object obj = createObjectFromResultSet(resultSet);
                resultList.add(obj);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }



}

