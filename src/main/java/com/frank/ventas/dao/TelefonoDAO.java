package com.frank.ventas.dao;

import com.frank.ventas.acceso.Acceso;
import com.frank.ventas.dao.interfaces.metodosDao;
import com.frank.ventas.entidades.Cliente;
import com.frank.ventas.entidades.Telefono;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class TelefonoDAO implements metodosDao<Telefono> {

    private final List<Telefono> lista;
    private final Metodos<Telefono> metodos;
    private final String ruta = "telefonos.txt";
    private boolean resp;
    private Telefono telefono;
    private final ClienteDAO DATOSCLI;

    public TelefonoDAO() {
        lista = new ArrayList<>();
        metodos = new Metodos<>(lista);
        DATOSCLI = new ClienteDAO();
        cargarLista();
    }

    private void cargarLista() {
        Telefono telefono; //1, david
        Cliente cliente;
        int idCliente;
        for (String dato : Acceso.cargarArchivo(ruta)) {
            StringTokenizer st = new StringTokenizer(dato, ",");
            telefono = new Telefono();
            telefono.setId(Integer.parseInt(st.nextToken()));
            telefono.setNumero(st.nextToken());
            idCliente = Integer.parseInt(st.nextToken());

            cliente = DATOSCLI.getObjeto(idCliente);
            telefono.setCliente(cliente);

            metodos.agregarRegistro(telefono);
        }
    }

    public int buscaTelefono(String numero) {
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            if (metodos.obtenerRegistro(i).getNumero().equals(numero)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public List listar() {
        List<Telefono> registros = new ArrayList<>();
        Cliente cliente;
        Telefono telefono;
        int idCliente;
        try {
            for (String dato : Acceso.cargarArchivo(ruta)) {
                StringTokenizer st = new StringTokenizer(dato, ",");
                
                telefono = new Telefono();
                
                telefono.setId(Integer.parseInt(st.nextToken()));
                telefono.setNumero(st.nextToken());
                idCliente = Integer.parseInt(st.nextToken());

                cliente = DATOSCLI.getObjeto(idCliente);
                telefono.setCliente(cliente);

                registros.add(telefono);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al listar Telefono: " + e.getMessage());
        }
        return registros;
    }

    @Override
    public boolean insertar(Telefono obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Telefono(obj.getId(), obj.getNumero(),obj.getCliente());
            metodos.agregarRegistro(obj);
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                telefono = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(telefono.getId()+ "," + telefono.getNumero()+ "," + telefono.getCliente().getRut()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar Telefono: " + e.getMessage());
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

    @Override
    public boolean actualizar(Telefono obj) {
        resp = false;
        PrintWriter pw;
        FileWriter fw;
        try {
            fw = new FileWriter("Archivos/" + ruta);
            pw = new PrintWriter(fw);
            obj = new Telefono(obj.getId(), obj.getNumero(),obj.getCliente());
            int codigo = buscarCodigo(obj.getId());
            if (codigo == -1) {
                metodos.agregarRegistro(obj);
            } else {
                metodos.modificar(codigo, obj);
            }
            for (int i = 0; i < metodos.cantidadRegistro(); i++) {
                telefono = metodos.obtenerRegistro(i);
                pw.println(String.valueOf(telefono.getId()+ "," + telefono.getNumero()+ "," + telefono.getCliente().getRut()));
            }
            pw.close();
            resp = true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar Clientes: " + e.getMessage());
        }
        return resp;
    }

    @Override
    public Telefono getObjeto(int codigo) {
        Telefono telefono = null;
        for (int i = 0; i < metodos.cantidadRegistro(); i++) {
            telefono = metodos.obtenerRegistro(i);
            if (telefono.getId() == codigo) {
                return telefono;
            }
        }
        return telefono;
    }

}
