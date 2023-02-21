package com.frank.ventas.control;

import com.frank.ventas.dao.DireccionProveedorDAO;
import com.frank.ventas.dao.ProveedorDAO;
import com.frank.ventas.entidades.DireccionProveedor;
import com.frank.ventas.entidades.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class DireccionProveedorCONTROL {

    private final DireccionProveedorDAO DATOS;
    private final ProveedorDAO DATOSPRO;
    private final DireccionProveedor obj;
    private DefaultTableModel modeloTabla;

    public DireccionProveedorCONTROL() {
        this.DATOS = new DireccionProveedorDAO();
        DATOSPRO = new ProveedorDAO();
        this.obj = new DireccionProveedor();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"RUT", "Proveedor", "Calle", "Numero", "Ciudad", "Comuna"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[6];
        List<DireccionProveedor> lista = DATOS.listar();
        for (DireccionProveedor item : lista) {
            registro[0] = Integer.toString(item.getProveedor().getRut());
            registro[1] = item.getProveedor().getNombre();
            registro[2] = item.getCalle();
            registro[3] = item.getNumero();
            registro[4] = item.getCiudad();
            registro[5] = item.getComuna();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer rut, String calle, String numero, String ciudad, String comuna) {
        if (DATOS.buscarCodigo(rut) != -1) {
            return "Este proveedor ya tiene direccion agregada, maximo una direccion por proveedor";
        } else {
            obj.setProveedor(new Proveedor(rut));
            obj.setNumero(numero);
            obj.setCalle(calle);
            obj.setCiudad(ciudad);
            obj.setComuna(comuna);
        }
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en el Registro";
        }
    }

    public String actualizar(Integer rut, int rutAnterior, String calle, String numero, String ciudad, String comuna) {
        if (rut.equals(rutAnterior)) {
            obj.setProveedor(new Proveedor(rut));
            obj.setNumero(numero);
            obj.setCalle(calle);
            obj.setCiudad(ciudad);
            obj.setComuna(comuna);
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualizacion";
            }
        } else {
            if (DATOS.buscarCodigo(rut) != -1) {
                obj.setProveedor(new Proveedor(rut));
                obj.setNumero(numero);
                obj.setCalle(calle);
                obj.setCiudad(ciudad);
                obj.setComuna(comuna);
                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en la actualizacion";
                }
            } else {
                return "Cliente a actualizar no existe";
            }
        }

    }

    public DefaultComboBoxModel seleccionar() {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Proveedor> lista = new ArrayList<>();
        lista = DATOSPRO.listar();
        for (Proveedor item : lista) {
            items.addElement(new Proveedor(item.getRut(), item.getNombre()));
        }
        return items;
    }
}
