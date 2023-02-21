package com.frank.ventas.presentacion;

import com.frank.ventas.control.VentaCONTROL;
import com.frank.ventas.entidades.Cliente;
import com.frank.ventas.entidades.Producto;
import java.time.LocalDate;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class FormVentas extends javax.swing.JFrame {

    private final VentaCONTROL CONTROL;
    private String accion;
    private DefaultTableModel modeloDetalles;

    public FormVentas() {
        initComponents();
        this.CONTROL = new VentaCONTROL();
        accion = "guardar";
        tabGeneral.setEnabledAt(1, false);
        listar();

        cargarClientes();
        cargarProductos();
        txtFecha.setText(fechaSistema());
        crearDetalles();
        txtDescuento.setText("0.00");
        txtTotal.setText("0.00");

    }

    private void listar() {
        tabla.setModel(CONTROL.listar());
        TableRowSorter orden = new TableRowSorter(tabla.getModel());
        tabla.setRowSorter(orden);
    }

    private void cargarClientes() {
        DefaultComboBoxModel items = CONTROL.seleccionarCliente();
        cboCliente.setModel(items);
    }

    private void cargarProductos() {
        DefaultComboBoxModel items = CONTROL.seleccionarProducto();
        cboProducto.setModel(items);
    }

    private void limpiar() {
        txtId.setText("");
        txtDescuento.setText("0.00");
        txtTotal.setText("0.00");
        txtFecha.setText(fechaSistema());
        crearDetalles();
        accion = "guardar";
    }

    private String fechaSistema() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    private void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Sistema", JOptionPane.ERROR_MESSAGE);
    }

    private void mensajeOK(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    private void crearDetalles() {
        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 3) {
                    return columna == 3;
                }
                if (columna == 4) {
                    return columna == 4;
                }
                return columna == 3;
            }

            @Override
            public Object getValueAt(int row, int col) {
                if (col == 5) {
                    Double cantD;
                    try {
                        cantD = Double.parseDouble((String) getValueAt(row, 3));
                    } catch (NumberFormatException e) {
                        cantD = 1.0;
                    }
                    Double precioD = Double.parseDouble((String) getValueAt(row, 4));
                    if (cantD != null && precioD != null) {
                        return String.format("%.2f", cantD * precioD);
                    } else {
                        return 0;
                    }
                }
                return super.getValueAt(row, col);
            }

            @Override
            public void setValueAt(Object aValue, int row, int col) {
                super.setValueAt(aValue, row, col);
                try {
                    int cantD = Integer.parseInt((String) getValueAt(row, 3));
                    int stockD = Integer.parseInt((String) getValueAt(row, 2));
                    if (cantD > stockD) {
                        super.setValueAt(stockD, row, 4);
                        mensajeError("La Cantidad a vender no puede superar el stock, usted puede cender como"
                                + " maximo: " + stockD);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
                calcularTotales();
                fireTableDataChanged();
            }

        };
        modeloDetalles.setColumnIdentifiers(new Object[]{"Id Producto","Producto", "Stock","Cantidad","Precio","Subtotal"});
        tablaDetalle.setModel(modeloDetalles);
    }

    private void calcularTotales() {
        double total = 0.00;
        int items = modeloDetalles.getRowCount();
        if (items == 0) {
            total = 0.00;
        } else {
            for (int i = 0; i < items; i++) {
                total = total + Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)));
            }
        }
        txtTotal.setText(String.format("%.2f", total));
    }

    public void agregarDetalles(String id, String nombre, String stock, String precio) {
        String idT;
        boolean existe = false;
        for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
            idT = String.valueOf(modeloDetalles.getValueAt(i, 0));
            if (idT.equals(id)) {
                existe = true;
            }
        }
        if (existe) {
            mensajeError("El producto ya se ha agregado");
        } else {
            modeloDetalles.addRow(new Object[]{id, nombre, stock, "1", precio, precio});
            calcularTotales();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabGeneral = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnNuevo = new javax.swing.JButton();
        btnAnularVenta = new javax.swing.JButton();
        btnVerDetalles = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cboProducto = new javax.swing.JComboBox<>();
        btnAgregarProducto = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        btnQuitarProducto = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Venta");

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnAnularVenta.setText("Anular");
        btnAnularVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularVentaActionPerformed(evt);
            }
        });

        btnVerDetalles.setText("Detalles");
        btnVerDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerDetallesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addGap(42, 42, 42)
                .addComponent(btnAnularVenta)
                .addGap(38, 38, 38)
                .addComponent(btnVerDetalles)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnularVenta)
                    .addComponent(btnNuevo)
                    .addComponent(btnVerDetalles))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        tabGeneral.addTab("Listado", jPanel1);

        jLabel1.setText("ID");

        jLabel2.setText("Descuento");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel3.setText("Cliente");

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Seleccionar Producto:");

        cboProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnAgregarProducto.setText("Agregar");
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaDetalle);

        btnQuitarProducto.setText("Quitar");
        btnQuitarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarProductoActionPerformed(evt);
            }
        });

        jLabel5.setText("Total:");

        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        jLabel6.setText("Fecha:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarProducto))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtDescuento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFecha)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(btnQuitarProducto)
                            .addGap(195, 195, 195)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotal))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(btnGuardar)
                .addGap(30, 30, 30)
                .addComponent(btnCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(8, 8, 8)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregarProducto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnQuitarProducto))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(20, 20, 20))
        );

        tabGeneral.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnularVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularVentaActionPerformed
        if (tabla.getSelectedRowCount() == 1) {
            String id = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0));
            String fecha = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 1));
            String descuento = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 2));
            String idCliente = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 3));
            String nombreCliente = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 4));
            String total = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 5));
            String estado = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 6));

            int resultado = JOptionPane.showConfirmDialog(null, "Estas seguro de anular esta venta?", "Sistema", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            switch (resultado) {
                case 0 -> {
                    String msg = CONTROL.anular(Integer.parseInt(id), fecha, Double.parseDouble(descuento),
                            Integer.parseInt(idCliente), Double.parseDouble(total));
                    if (msg.equals("OK")) {
                        mensajeOK("Se anulo la venta correctamente");
                        listar();
                    } else {
                        mensajeError(msg);
                    }
                }
                case 1 -> {

                }
            }
        } else {
            this.mensajeError("Seleccione un registro a Editar");
        }
    }//GEN-LAST:event_btnAnularVentaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        tabGeneral.setEnabledAt(0, false);
        tabGeneral.setEnabledAt(1, true);
        tabGeneral.setSelectedIndex(1);
        this.accion = "guardar";
        btnGuardar.setText("Guardar");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtId.getText().trim().length() == 0 || txtId.getText().trim().length() > 11) {
            this.mensajeError("Debes ingresar un ID y no debe ser mayor a 11 caracteres, es obligatorio");
            txtId.requestFocus();
            return;
        }
        if (txtDescuento.getText().trim().length() == 0 || txtDescuento.getText().trim().length() > 10) {
            this.mensajeError("Debes ingresar un descuento y no debe ser mayor a 10 caracteres, es obligatorio");
            txtDescuento.requestFocus();
            return;
        }
        if (cboCliente.getItemCount() == 0) {
            this.mensajeError("Por favor selecciona un Cliente, es Obligatorio");
        }
        if (modeloDetalles.getRowCount() == 0) {
            this.mensajeError("Por favor agrega productos, es Obligatorio");
        }

        String resp;
        Cliente cliente = (Cliente) cboCliente.getSelectedItem();

        resp = CONTROL.insertar(Integer.parseInt(txtId.getText().trim()), txtFecha.getText(), Double.parseDouble(txtDescuento.getText()),
                cliente.getRut(), Double.parseDouble(txtTotal.getText()), modeloDetalles);
        if (resp.equals("OK")) {
            this.mensajeOK("Insertado correctamente");
            this.limpiar();
            this.listar();

            tabGeneral.setEnabledAt(1, false);
            tabGeneral.setEnabledAt(0, true);
            tabGeneral.setSelectedIndex(0);
        } else {
            this.mensajeError(resp);
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.limpiar();
        tabGeneral.setEnabledAt(1, false);
        tabGeneral.setEnabledAt(0, true);
        tabGeneral.setSelectedIndex(0);
        accion = "guardar";
        btnGuardar.setVisible(true);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void btnQuitarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarProductoActionPerformed
        if (tablaDetalle.getSelectedRowCount() == 1) {
            modeloDetalles.removeRow(tablaDetalle.getSelectedRow());
            calcularTotales();
        } else {
            this.mensajeError("Seleccione un registro para Quitar");
        }
    }//GEN-LAST:event_btnQuitarProductoActionPerformed

    private void btnVerDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerDetallesActionPerformed
        if (tabla.getSelectedRowCount() == 1) {
            String id = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0));
            String fecha = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 1));
            String descuento = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 2));
            String idCliente = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 3));
            String nombreCliente = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 4));
            String total = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 5));
            String estado = String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 6));

            txtId.setText(id);
            txtDescuento.setText(descuento);
            txtFecha.setText(fecha);
            cboCliente.setSelectedItem(new Cliente(Integer.parseInt(idCliente), nombreCliente));
            txtTotal.setText(total);

            modeloDetalles = CONTROL.listarDetalles(Integer.parseInt(id));
            tablaDetalle.setModel(modeloDetalles);
            calcularTotales();

            tabGeneral.setEnabledAt(1, true);
            tabGeneral.setEnabledAt(0, false);
            tabGeneral.setSelectedIndex(1);
            btnGuardar.setVisible(false);
        } else {
            this.mensajeError("Seleccione un registro a Editar");
        }
    }//GEN-LAST:event_btnVerDetallesActionPerformed

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
       Producto p = (Producto) cboProducto.getSelectedItem();
        agregarDetalles(String.valueOf(p.getId()), p.getNombre(), String.valueOf(p.getStock()), String.valueOf(p.getPrecio()));
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnAnularVenta;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnQuitarProducto;
    private javax.swing.JButton btnVerDetalles;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tabla;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
