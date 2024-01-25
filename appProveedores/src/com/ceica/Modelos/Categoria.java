package com.ceica.Modelos;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categoria extends ModeloBase {
    private int id;
    private String nombre;

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Categoria() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id +" -> " + nombre + "\n";
    }

    @Override
    protected String getNombreTabla() {
        return "categorias";
    }

    @Override
    protected Object createObjectFromResultSet(ResultSet resultSet) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getInt("idcategoria"));
        categoria.setNombre(resultSet.getString("categoria"));
        return categoria;
    }



    public static void main(String[] a){
        Categoria categoria=new Categoria();
        List<Categoria> categoriaList=new ArrayList<>();
        List<Object> list=categoria.leerTodos();
        for (Object obj :list){
            categoriaList.add((Categoria) obj);
        }

        System.out.println(categoriaList.toString());
    }
}
