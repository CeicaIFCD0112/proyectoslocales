package com.ceica;

import com.ceica.Controladores.AlmacenController;
import com.ceica.Controladores.LoginController;
import com.ceica.Modelos.Color;

import javax.script.ScriptContext;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String usr,pass;
        AlmacenController almacen=new AlmacenController();
        Scanner leer=new Scanner(System.in);
        System.out.println("Bienvenido a AppAlmacen");
        System.out.println(".... Enter para empezar");
        leer.nextLine();
        do{
            System.out.println("Login de AppAlmacen");
            System.out.print("Introduce Usuario: ");
            usr=leer.nextLine();
            System.out.print("Introduce password");
            pass=leer.nextLine();
            if(LoginController.login(usr,pass)){
                System.out.println("Estoy en AppAlmacen");
                menuPrincipalAlmacen(leer,almacen);

            }else{
                System.out.println("Usuario o Contraseña incorrecta");
            }
        }while(true);
    }

    private static void menuPrincipalAlmacen(Scanner leer, AlmacenController almacen) {
        String op="";
        String menuPrincipal= """
                1. Proveedores
                2. Piezas
                3. Pedidos
                4. Salir
                """;
        do{
            System.out.println(menuPrincipal);
            op=leer.nextLine();
            switch (op){
                case "1":
                    subMenuProveedores(leer,almacen);
                    break;
                case "2":
                    subMenuPiezas(leer,almacen);
                    break;
                case "3":
                    break;
                case "4":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }while(! "4".equals(op));
    }

    private static void subMenuPiezas(Scanner leer, AlmacenController almacen) {
        String op;
        String menuPiezas= """
                1. Nueva pieza
                2. Cambiar precio
                3. Borrar pieza
                4. Ver piezas
                5. Volver al menú anterior
                """;
        do{
            System.out.println(menuPiezas);
            op=leer.nextLine();
            switch (op){
                case "1":
                    nuevaPieza(leer,almacen);
                    break;
                case "2":
                    editarPieza(leer,almacen);
                    break;
                case "3":
                    break;
                case "4":
                    System.out.println(almacen.verPiezas());
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        }while(! "5".equals(op));
    }

    private static void editarPieza(Scanner leer, AlmacenController almacen) {

    }

    private static void nuevaPieza(Scanner leer, AlmacenController almacen) {
        String nombre, colorPieza;
        double precio = 0;
        Color color = null;
        boolean colorValido = false,categoriaValida=false,precioValido=false;
        int categoria;
        System.out.print("Nombre de la pieza: ");
        nombre = leer.nextLine();
        do {
            System.out.print("Precio: ");
            try {
                precio = leer.nextDouble();
                leer.nextLine();
                precioValido=true;
            }catch (Exception e){
                leer.nextLine();
                System.out.println("Formato de precio no válido");
                precioValido=false;
            }
        }while(! precioValido);
        do {
            System.out.println("Color de la pieza (Colores disponibles)");
            System.out.println(Arrays.stream(Color.values()).toList());
            colorPieza = leer.nextLine();
            try {
                color = Color.valueOf(colorPieza.toUpperCase());
                colorValido = true;
            } catch (Exception e) {
                colorValido = false;
            }
        } while (!colorValido);
        do {
            System.out.println(almacen.categoriasDisponibles());
            categoria=leer.nextInt();
            leer.nextLine();
            if(almacen.categoriaValida(categoria)){
                categoriaValida=true;
            }else{
                System.out.println("Categoría no válida");
            }
        }while (!categoriaValida);
        almacen.nuevaPieza(nombre,color,precio,categoria);
    }

    private static void subMenuProveedores(Scanner leer, AlmacenController almacen) {
        String op,cif,nombre,direccion,localidad,provincia;
        String menuProveedores= """
                1. Nuevo proveedor
                2. Editar proveedor
                3. Ver proveedores
                4. Eliminar proveedor
                5. Volver al menú principal
                """;
        do {
            System.out.println(menuProveedores);
            op=leer.nextLine();
            switch (op){
                case "1":
                    System.out.print("CIF: ");
                    cif=leer.nextLine();
                    System.out.print("Nombre: ");
                    nombre= leer.nextLine();
                    System.out.print("Direccion: ");
                    direccion=leer.nextLine();
                    System.out.print("Localidad: ");
                    localidad=leer.nextLine();
                    System.out.print("Provincia: ");
                    provincia=leer.nextLine();
                    almacen.nuevoProveedor(cif,nombre,direccion,localidad,provincia);
                    break;
                case "2":
                    System.out.print("Proveedor a editar, CIF: ");
                    cif=leer.nextLine();
                    if(almacen.getProveedorByCif(cif)){
                        System.out.print("Nuevo Nombre: ");
                        nombre= leer.nextLine();
                        if(almacen.editarNombreProveedor(cif,nombre)){
                            System.out.println("Proveedor Editado");
                        }else {
                            System.out.println("Error editando el proveedor");
                        }
                    }else{
                        System.out.println("No existe ningún proveedor con ese CIF");
                    }

                    break;
                case "3":
                    System.out.println(almacen.verProveedores());
                    break;
                case "4":
                    System.out.print("Proveedor a eliminar, CIF: ");
                    cif=leer.nextLine();
                    if(almacen.borrarProveedor(cif)){
                        System.out.println("Proveedor eliminado...");
                    }else{
                        System.out.println("No se ha podido eliminar el proveedor");
                    }
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal...");
                default:
                    System.out.println("Opción no válida");
            }

        }while(! "5".equals(op));
    }
}