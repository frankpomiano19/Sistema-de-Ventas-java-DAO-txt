package com.frank.ventas.dao;

import com.frank.ventas.acceso.Acceso;
import com.frank.ventas.dao.interfaces.metodosDao;
import com.frank.ventas.entidades.Proveedor;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class ProveedorDAO implements metodosDao<Proveedor> {

    private final List<Proveedor> lista;
    private Metodos<Proveedor> metodos;
    private final String ruta = "proveedor.txt";
    private boolean resp;
    private Proveedor proveedor;

    public ProveedorDAO() {
        lista = new ArrayList<>();
        metodos = new Metodos<>(lista);
        cargarLista();
    }

    private void cargarLista() {
        Proveedor proveedor; //1, david
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer((dato), ",");
            proveedor = new Proveedor(Integer.parseInt(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken());
            metodos.agregarRegistro(proveedor);
        }
    }
    
    public List seleccionar(){
        List<Proveedor> registros = new ArrayList<>();
        try {
            for(String dato: Acceso.cargarArchivo(ruta)){
                StringTokenizer st = new StringTokenizer((dato),",");
                registros.add(new Proveedor(Integer.parseInt(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken()));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en seleccionar proveedor"+e.getMessage());
        }
        return registros;
    }
    
    public int buscaNombre(String nombre) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (metodos.obtenerRegistro(i).getNombre().equals(nombre)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List listar() {
        List<Proveedor> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer(dato, ",");
                proveedor = new Proveedor(Integer.parseInt(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken());
                registros.add(proveedor);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Proveedores: " + e.getMessage());
        }
        return registros;
    }

    @Override
    public boolean insertar(Proveedor obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Proveedor(obj.getRut(), obj.getNombre(),obj.getTelefono(),obj.getPaginaWeb());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                proveedor = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(proveedor.getRut() + "," + proveedor.getNombre()+ "," + proveedor.getTelefono()+ "," + proveedor.getPaginaWeb()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar Proovedor: " + e.getMessage());
        }
        return resp;
    }

    @Override
    public int buscarCodigo(int codigo) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (codigo == metodos.obtenerRegistro(i).getRut()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean actualizar(Proveedor obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Proveedor(obj.getRut(), obj.getNombre(),obj.getTelefono(),obj.getPaginaWeb());
            int codigo = buscarCodigo(obj.getRut());
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificar(codigo, obj);
            }
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                proveedor = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(proveedor.getRut() + "," + proveedor.getNombre()+ "," + proveedor.getTelefono()+ "," + proveedor.getPaginaWeb()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar Proveedores: " + e.getMessage());
        }
        return resp;
    }

    @Override
    public Proveedor getObjeto(int codigo) {
        Proveedor proveedor = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            proveedor = metodos.obtenerRegistro(i);
            if (proveedor.getRut() == codigo) {
                return proveedor;
            }
        }
        return proveedor;
    }

}
