package com.frank.ventas.control;

import com.frank.ventas.dao.ClienteDAO;
import com.frank.ventas.dao.DireccionClienteDAO;
import com.frank.ventas.entidades.Cliente;
import com.frank.ventas.entidades.DireccionCliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class DireccionClienteCONTROL {

    private final DireccionClienteDAO DATOS;
    private final ClienteDAO DATOSCLI;
    private final DireccionCliente obj;
    private DefaultTableModel modeloTabla;

    public DireccionClienteCONTROL() {
        this.DATOS = new DireccionClienteDAO();
        DATOSCLI = new ClienteDAO();
        this.obj = new DireccionCliente();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"RUT", "Nombres","Calle","Numero","Ciudad","Comuna"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[6];
        List<DireccionCliente> lista = DATOS.listar();
        for (DireccionCliente item : lista) {
            registro[0] = Integer.toString(item.getCliente().getRut());
            registro[1] = item.getCliente().getNombre();
            registro[2] = item.getCalle();
            registro[3] = item.getNumero();
            registro[4] = item.getCiudad();
            registro[5] = item.getComuna();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer rut, String calle,String numero,String ciudad,String comuna) {
        if (DATOS.buscarCodigo(rut) != -1) {
            return "Este cliente ya tiene direccion agregada";
        } else {
            obj.setCliente(new Cliente(rut));
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

    public String actualizar(Integer rut, String calle,String numero,String ciudad,String comuna) {
        if (DATOS.buscarCodigo(rut) != -1){
            obj.setCliente(new Cliente(rut));
            obj.setNumero(numero);
            obj.setCalle(calle);
            obj.setCiudad(ciudad);
            obj.setComuna(comuna);
            if(DATOS.actualizar(obj)){
                return "OK";
            }else{
                return "Error en la actualizacion";
            }
        }else{
            return "Cliente a actualizar no existe";
        }
    }
    
    public DefaultComboBoxModel seleccionar(){
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Cliente> lista = new ArrayList<>();
        lista = DATOSCLI.listar();
        for(Cliente item: lista){
            items.addElement(new Cliente(item.getRut(), item.getNombre()));
        }
        return items;
    }
}
