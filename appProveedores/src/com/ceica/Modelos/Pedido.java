package com.ceica.Modelos;

import java.time.LocalDate;

public class Pedido {
    private int id;
    private Proveedor proveedor;
    private Pieza pieza;
    private LocalDate fecha;
    private int cantidad;

    public Pedido() {
    }

    public Pedido(Proveedor proveedor, Pieza pieza) {
        this.proveedor = proveedor;
        this.pieza = pieza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", proveedor=" + proveedor +
                ", pieza=" + pieza +
                ", fecha=" + fecha +
                ", cantidad=" + cantidad +
                '}';
    }
}
