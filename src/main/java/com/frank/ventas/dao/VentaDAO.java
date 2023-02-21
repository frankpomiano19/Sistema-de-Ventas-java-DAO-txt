package com.frank.ventas.dao;

import com.frank.ventas.acceso.Acceso;
import com.frank.ventas.dao.interfaces.metodosDao;
import com.frank.ventas.entidades.Cliente;
import com.frank.ventas.entidades.DetalleVenta;
import com.frank.ventas.entidades.Venta;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class VentaDAO implements metodosDao<Venta> {

    private final List<Venta> lista;
    private Metodos<Venta> metodos;
    private final String ruta = "venta.txt";
    private boolean resp;
    private Venta venta;
    private final ClienteDAO DATOS;
    private final DetalleVentaDAO DATOS_DET;

    public VentaDAO() {
        lista = new ArrayList<>();
        metodos = new Metodos<>(lista);
        DATOS = new ClienteDAO();
        DATOS_DET = new DetalleVentaDAO();
        cargarLista();
    }

    private void cargarLista() {
        Venta ven; //1, david
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer(dato, ",");
            ven = new Venta();

            ven.setId(Integer.parseInt(st.nextToken()));
            ven.setFecha(st.nextToken());
            ven.setDescuento(Double.parseDouble(st.nextToken()));

            int idCliente = Integer.parseInt(st.nextToken());
            Cliente cliente = DATOS.getObjeto(idCliente);
            ven.setClienteId(cliente);

            ven.setTotal(Double.parseDouble(st.nextToken()));
            ven.setEstado(Boolean.parseBoolean(st.nextToken()));

            metodos.agregarRegistro(ven);
        }
    }

    @Override
    public List listar() {
        List<Venta> registros = new ArrayList<>();
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer(dato, ",");
                venta = new Venta();
                venta.setId(Integer.parseInt(st.nextToken()));
                venta.setFecha(st.nextToken());
                venta.setDescuento(Double.parseDouble(st.nextToken()));

                int idCliente = Integer.parseInt(st.nextToken());
                Cliente cliente = DATOS.getObjeto(idCliente);
                venta.setClienteId(cliente);

                venta.setTotal(Double.parseDouble(st.nextToken()));
                venta.setEstado(Boolean.parseBoolean(st.nextToken()));
                registros.add(venta);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Clientes: " + e.getMessage());
        }
        return registros;
    }

    @Override
    public boolean insertar(Venta obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Venta(obj.getId(),obj.getFecha(),obj.getDescuento(),obj.getClienteId(),obj.getTotal(),true,obj.getDetalles());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                venta = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(venta.getId()+ "," + venta.getFecha()+ "," +venta.getDescuento()+ "," + 
                        venta.getClienteId().getRut()+ "," +venta.getTotal()+ "," +venta.isEstado()));
            }
            pw.close();
            for(DetalleVenta det : obj.getDetalles()){
                DATOS_DET.insertar(det);
            }
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar Ventas: " + e.getMessage());
        }
        return resp;
    }

    @Override
    public int buscarCodigo(int codigo) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (codigo == metodos.obtenerRegistro(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    public boolean anular(Venta obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Venta(obj.getId(),obj.getFecha(),obj.getDescuento(),obj.getClienteId(),obj.getTotal(),false,obj.getDetalles());
            int codigo = buscarCodigo(obj.getId());
            if (codigo == -1) {
                return false;
            } else {
                metodos.modificar(codigo, obj);
            }
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                venta = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(venta.getId()+ "," + venta.getFecha()+ "," +venta.getDescuento()+ "," + 
                        venta.getClienteId().getRut()+ "," +venta.getTotal()+ "," +venta.isEstado()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al anular Ventas: " + e.getMessage());
        }
        return resp;
    }
    
    @Override
    public boolean actualizar(Venta obj) {
        return false;
    }

    @Override
    public Venta getObjeto(int codigo) {
        Venta vent = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            vent = metodos.obtenerRegistro(i);
            if (vent.getId() == codigo) {
                vent = new Venta(vent.getId(),vent.getFecha(),vent.getDescuento(),vent.getClienteId(),vent.getTotal(),vent.isEstado());
                return vent;
            }
        }
        return vent;
    }

}
