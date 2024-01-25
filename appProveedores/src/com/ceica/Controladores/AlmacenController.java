package com.ceica.Controladores;

import com.ceica.Modelos.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el controlador de un almacén.
 */
public class AlmacenController {

    private List<Proveedor> proveedorList;
    private List<Pieza> piezaList;
    private List<Pedido> pedidoList;
    private List<Categoria> categorias;

    public AlmacenController() {

        proveedorList = new ArrayList<>();
        pedidoList = new ArrayList<>();
        piezaList = new ArrayList<>();
        categorias = new ArrayList<>();
        proveedorList = Proveedor.getProveedores();
        Categoria categoria = new Categoria(1, "pequeño");
        categorias.add(categoria);
        categorias.add(new Categoria(2, "mediano"));
        categorias.add(new Categoria(3, "grande"));

    }

    /**
     * Método para añadir un nuevo proveedor al almacén.
     *
     * @param cif       El CIF del proveedor.
     * @param nombre    El nombre del proveedor.
     * @param direccion La dirección del proveedor.
     * @param localidad La localidad del proveedor.
     * @param provincia La provincia del proveedor.
     * @return true si se añadió el proveedor con éxito, false de lo contrario.
     */
    public boolean nuevoProveedor(String cif, String nombre, String direccion, String localidad, String provincia) {
        Proveedor proveedor = new Proveedor(cif, nombre);
        proveedor.setDireccion(direccion);
        proveedor.setLocalidad(localidad);
        proveedor.setProvincia(provincia);
        if (Proveedor.insertar(proveedor)) {
            return proveedorList.add(proveedor);
        } else {
            return false;
        }

    }

    /*
    public boolean borrarProveedor(String cif){
        for (int i = 0; i < proveedorList.size(); i++) {
            if(cif.equals(proveedorList.get(i).getCif())){
                proveedorList.remove(i);
                //proveedorList.remove(proveedorList.get(i));
                return true;
            }
        }
        return false;
    }

     */
    public boolean borrarProveedor(String cif) {
        /*
        return proveedorList.removeIf(proveedor -> cif.equals(proveedor.getCif()));
        */
        if(Proveedor.eliminarProveedor(cif)){
            proveedorList=Proveedor.getProveedores();
            return true;
        }else{
            return false;
        }

    }

    /**
     * @param cif    CIF del proveedor
     * @param nombre Nuevo nombre para el proveedor
     * @return True si puede editar el nombre del proveedor
     */
    public boolean editarNombreProveedor(String cif, String nombre) {
        /*
        for (int i = 0; i < proveedorList.size(); i++) {
            if(cif.equals(proveedorList.get(i).getCif())){
                proveedorList.get(i).setNombre(nombre);
                return true;
            }
        }
        return false;
         */
        /*
        for(Proveedor proveedor : proveedorList){
            if(cif.equals(proveedor.getCif())){
                proveedor.setNombre(nombre);
                return true;
            }
        }
        return false;
         */


        if (Proveedor.editarNombreProveedor(cif, nombre)) {
            proveedorList=Proveedor.getProveedores();
            return true;
           /*
            return proveedorList.stream()
                    .filter(p -> cif.equals(p.getCif()))
                    .findFirst()
                    .map(p -> {
                        p.setNombre(nombre);
                        return true;
                    })
                    .orElse(false);
            */

        } else {
            return false;
        }

    }

    /**
     * @param id     idpieza
     * @param precio nuevo precio
     * @return true si puede editar el precio
     */
    public boolean editarPrecioPieza(int id, Double precio) {
        return piezaList.stream()
                .filter(pieza -> pieza.getId() == id)
                .findFirst()
                .map(pieza -> {
                    pieza.setPrecio(precio);
                    return true;
                })
                .orElse(false);
    }

    /**
     * @param nombre      Nombre de la pieza
     * @param color       Color de la pieza
     * @param precio      Precio de la pieza
     * @param idcategoria idCategoria de la categoría de la pieza
     * @return true si puede crear la pieza
     */
    public boolean nuevaPieza(String nombre, Color color, Double precio, int idcategoria) {
        Pieza pieza = new Pieza(nombre, color.toString(), precio);
        pieza.setCategoria(getCategoriaById(idcategoria));
        piezaList.add(pieza);
        return true;
    }

    /**
     * @param id Id de la categoria
     * @return Categoria del id solicitado
     */
    private Categoria getCategoriaById(int id) {
        return categorias.stream()
                .filter(c -> c.getId() == id).
                findFirst().get();
    }

    /**
     * Método para grabar nuevo pedido. El pedido se graba con la fecha actual
     *
     * @param cif      CIF del proveedor
     * @param idPieza  ID de la pieza
     * @param cantidad Cantidad solicitada
     * @return Información sobre si ha completado el pedido o no
     */
    public String nuevoPedido(String cif, int idPieza, int cantidad) {
        Proveedor proveedor = getProveedorByCIF(cif);
        if (proveedor != null) {
            Pieza pieza = getPiezaByID(idPieza);
            if (pieza != null) {
                Pedido pedido1 = new Pedido(proveedor, pieza);
                pedido1.setCantidad(cantidad);
                pedido1.setFecha(LocalDate.now());
                pedidoList.add(pedido1);
                return "Pedido creado";
            } else {
                return "Error al crear el pedido, Pieza no existe";
            }
        } else {
            return "Error al crear el pedido, Proveedor no existe";
        }
    }

    /**
     * @param id ID de la pieza
     * @return Pieza buscada por ID
     */
    private Pieza getPiezaByID(int id) {
        for (int i = 0; i < piezaList.size(); i++) {
            if (piezaList.get(i).getId() == id) {
                return piezaList.get(i);
            }
        }
        return null;
    }

    /**
     * @param cif CIF del proveedor buscado
     * @return Proveedor buscado
     */
    private Proveedor getProveedorByCIF(String cif) {
        for (Proveedor p : proveedorList) {
            if (cif.equals(p.getCif())) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param idPieza ID de la Pieza
     * @return String Lista de pedidos de la pieza solicitada en formato string
     */
    public String getPedidosByPieza(int idPieza) {
        List<Pedido> pedidosByPieza = new ArrayList<>();
        for (Pedido pedido : pedidoList) {
            if (pedido.getPieza().getId() == idPieza) {
                pedidosByPieza.add(pedido);
            }
        }
        if (pedidosByPieza.size() > 0) {
            return pedidosByPieza.toString();
        } else {
            return "No hay pedidos de esta pieza";
        }
    }

    /**
     * Buscar proveedor por CIF
     *
     * @param cif CIF del proveedor
     * @return String Lista de pedidos del proveedor
     */
    public String getPedidosByProveedor(String cif) {
        return pedidoList.stream()
                .filter(pedido -> cif.equals(pedido.getProveedor().getCif()))
                .toList()
                .stream()
                .findFirst()
                .map(Object::toString)
                .orElse("No hay pedidos de ese proveedor");
    }

    public String verProveedores() {
        return proveedorList.toString();
    }

    @Override
    public String toString() {
        return "AlmacenController{" + "\n" +
                "proveedorList=" + proveedorList + "\n" +
                ", piezaList=" + piezaList + "\n" +
                ", pedidoList="
                + pedidoList + "\n" +
                '}';
    }


    public String categoriasDisponibles() {
        return categorias.toString();
    }

    public boolean categoriaValida(int categoria) {
        for (Categoria cat : categorias) {
            if (cat.getId() == categoria) {
                return true;
            }
        }
        return false;
    }

    public String verPiezas() {
        return piezaList.toString();
    }

    public boolean getProveedorByCif(String cif) {
        return proveedorList.stream()
                .filter(p -> cif.equals(p.getCif()))
                .findFirst()
                .isPresent();
    }
}
