package com.frank.ventas.control;

import com.frank.ventas.dao.TelefonoDAO;
import com.frank.ventas.entidades.Cliente;
import com.frank.ventas.entidades.Telefono;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class TelefonoControl {

    private final TelefonoDAO DATOS;
    private final Telefono obj;
    private DefaultTableModel modeloTabla;

    public TelefonoControl() {
        this.DATOS = new TelefonoDAO();
        this.obj = new Telefono();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Numero", "RUT", "Nombre"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[4];
        List<Telefono> lista = DATOS.listar();
        for (Telefono item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNumero();
            registro[2] = Integer.toString(item.getCliente().getRut());
            registro[3] = item.getCliente().getNombre();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer id, String numero, Integer rut) {
        if (DATOS.buscarCodigo(id) != -1) {
            return "El ID ingresado ya existe";
        } else if (DATOS.buscaTelefono(numero) != -1) {
            return "El numero de telefono ingresado ya existe";
        } else {
            obj.setId(id);
            obj.setNumero(numero);
            obj.setCliente(new Cliente(rut));
        }
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en el Registro";
        }
    }

    public String actualizar(Integer id, String numero, String numeroAnterior, Integer rut) {
        if (numero.equals(numeroAnterior)) {
            obj.setId(id);
            obj.setNumero(numero);
            obj.setCliente(new Cliente(rut));
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en el Registro";
            }
        } else {
            if (DATOS.buscaTelefono(numero) != -1) {
                return "El numero de telefono ingresado ya existe";
            } else {
                obj.setId(id);
                obj.setNumero(numero);
                obj.setCliente(new Cliente(rut));

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en el Registro";
                }
            }

        }
    }
    
    
}
