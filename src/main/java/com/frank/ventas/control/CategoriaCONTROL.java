package com.frank.ventas.control;

import com.frank.ventas.dao.CategoriaDAO;
import com.frank.ventas.entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class CategoriaCONTROL {

    private final CategoriaDAO DATOS;
    private final Categoria obj;
    private DefaultTableModel modeloTabla;

    public CategoriaCONTROL() {
        this.DATOS = new CategoriaDAO();
        this.obj = new Categoria();
    }

    public DefaultTableModel listar() {
        String[] titulos = {"ID", "Nombre","Descripcion"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[3];
        List<Categoria> lista = DATOS.listar();
        for (Categoria item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            this.modeloTabla.addRow(registro);
        }
        return this.modeloTabla;
    }

    public String insertar(Integer id, String nombre,String descripcion) {
        if (DATOS.buscarCodigo(id) != -1) {
            return "El ID ingresado ya existe";
        } else if (DATOS.buscaNombre(nombre) != -1) {
            return "El nombre ingresado ya existe";
        } else {
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
        }
        if (DATOS.insertar(obj)) {
            return "OK";
        } else {
            return "Error en el Registro";
        }
    }

    public String actualizar(Integer id, String nombre, String nombreAnterior,String descripcion) {
        if (nombre.equals(nombreAnterior)) {
            obj.setId(id);
            obj.setNombre(nombre);
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en el Registro";
            }
        } else {
            if(DATOS.buscaNombre(nombre) != -1){
                return "El nombre ingresado ya existe";
            }else{
                obj.setId(id);
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);

                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error en el Registro";
                }
            }

        }
    }
    
    public DefaultComboBoxModel seleccionar(){
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Categoria> lista = new ArrayList<>();
        lista = DATOS.listar();
        for(Categoria item: lista){
            items.addElement(new Categoria(item.getId(), item.getNombre(),item.getDescripcion()));
        }
        return items;
    }
}
