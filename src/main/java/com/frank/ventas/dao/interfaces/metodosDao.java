
package com.frank.ventas.dao.interfaces;

import java.util.List;

public interface metodosDao<T>{
 
    public List<T> listar();
    
    public boolean insertar(T obj);
    
    public boolean actualizar(T obj);
    
    public int buscarCodigo(int codigo);
    
    public T getObjeto(int codigo);
    
}
