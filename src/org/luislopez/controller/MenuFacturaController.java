/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.luislopez.bean.Clientes;
import org.luislopez.bean.Compras;
import org.luislopez.bean.Empleado;
import org.luislopez.bean.Factura;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuFacturaController implements Initializable {
    
    
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Factura> listaFactura;  
    private ObservableList <Clientes> listaClientes;  
    private ObservableList <Empleado> listaEmpleado;  
    
        @FXML private Button btnRegresar;
        @FXML private Button btnMenuClientes;
        @FXML private Button btnProgramador; 
        @FXML private Button btnProductos;
        @FXML private Button btnProveedor;
        @FXML private Button btnCompras;    
        @FXML private Button btnCargoEmpleado;   
        @FXML private Button btnProducto;    
        @FXML private Button btnEmpleado;     
        @FXML private Button btnFactura;    
        @FXML private Button btnDetalleFactura;    
        @FXML private Button btnDetalleCompra;    
        @FXML private Button btnFecha;    
        @FXML private Button btnEmailProveedor;     
    
    @FXML private TextField txtNumeroDeFactura;
    @FXML private TextField txtEstado;
    @FXML private TextField txtTotalFactura;
    @FXML private DatePicker txtFechaFactura;
    @FXML private ComboBox cmbCodigoclienteID;
    @FXML private ComboBox cmbCodigoEmpleado;
    @FXML private TableView tblFactura;
    @FXML private TableColumn colNumeroDeFactura;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colTotalFactura;
    @FXML private TableColumn colFechaFactura;
    @FXML private TableColumn colCodigoclienteID;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoclienteID.setItems(getClientes());
        cmbCodigoEmpleado.setItems(getEmpleado());
        
    }
    
    public void cargaDatos(){
    tblFactura.setItems(getFactura());
    colNumeroDeFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroDeFactura"));
    colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
    colTotalFactura.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
    colFechaFactura.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
    colCodigoclienteID.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("clienteID"));
    colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    
    }
    public void selecionarElementos(){
       txtNumeroDeFactura.setText(String.valueOf(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura()));
       txtEstado.setText(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getEstado());
       txtTotalFactura.setText(String.valueOf(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
       txtFechaFactura.setValue(LocalDate.parse(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
       cmbCodigoclienteID.getSelectionModel().select(buscarClientes(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getClienteID())); 
       cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado())); 
       
    }
    
    
    
    public Clientes buscarClientes (int clienteID ){
        Clientes resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarClientes(?)}");
         procedimiento.setInt(1, clienteID);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Clientes(registro.getInt("clienteID"),
                        registro.getString("nombreClientes"),
                        registro.getString("apellidosClientes"),
                        registro.getString("direccionClientes"),
                        registro.getString("NIT"),
                        registro.getString("telefonoClientes"),
                        registro.getString("correoClientes")
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
    
    
    public Empleado buscarEmpleado (int codigoEmpleado ){
        Empleado resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleados(?)}");
         procedimiento.setInt(1, codigoEmpleado);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Empleado(registro.getInt("codigoEmpleado"),
                    registro.getString("nombresEmpleado"),
                    registro.getString("apellidosEmpleado"),
                    registro.getDouble("sueldo"),
                    registro.getString("direccion"),
                    registro.getString("turno"),
                    registro.getInt("codigoCargoEmpleado")  
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }    
    
    
    
    public ObservableList<Factura> getFactura(){
        ArrayList<Factura> lista = new ArrayList<Factura>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Factura (resultado.getInt("numeroDeFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("clienteID"),    
                        resultado.getInt("codigoEmpleado")      
                        
                    
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    
        return listaFactura = FXCollections.observableArrayList(lista);
        
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nombreClientes"),
                        resultado.getString("apellidosClientes"),
                        resultado.getString("direccionClientes"),
                        resultado.getString("NIT"),
                        resultado.getString("telefonoClientes"),
                        resultado.getString("correoClientes")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableList(lista);
    }
    
    
    
    public ObservableList<Empleado> getEmpleado(){
        ArrayList<Empleado> lista = new ArrayList<Empleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleado (resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")            
            ));
        }
    }catch (Exception e){
        e.printStackTrace();
    }    
    return listaEmpleado = FXCollections.observableArrayList(lista);
        
    }
   
    
    
     public void agregar (){
         switch(tipoDeOperacion){
             case NINGUNO:
              activarControles();
             btnAgregar.setText("Guardar");
             btnEliminar.setText("Cancelar");
             btnEditar.setDisable(true);
             btnReporte.setDisable(true);   
             tipoDeOperacion = MenuFacturaController.operaciones.ACTUALIZAR;
             break;
             case ACTUALIZAR:
             guardar ();
             desactivarControles();
             limpiarControles ();
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             tipoDeOperacion = MenuFacturaController.operaciones.NINGUNO;
             cargaDatos();
             break;
         }
     
     
     
     
     }
       
     
     public void guardar (){
         Factura registro = new Factura();
         registro.setEstado(txtEstado.getText());
         registro.setFechaFactura(txtFechaFactura.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
         registro.setClienteID(((Clientes)cmbCodigoclienteID.getSelectionModel().getSelectedItem()).getClienteID());         
         registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());         
         
         try {
             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall ("{CALL sp_AgregarFactura(?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getEstado());     
            procedimiento.setDouble(2, registro.getTotalFactura());
            procedimiento.setString(3, registro.getFechaFactura());
            procedimiento.setInt(4, registro.getClienteID());
            procedimiento.setInt(5, registro.getCodigoEmpleado());
            
            procedimiento.execute();
            
        
            listaFactura.add(registro);
            

            }catch (Exception e){
                e.printStackTrace();
            }
     
    }
     
    public void eliminar() {
        
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = MenuFacturaController.operaciones.NINGUNO;                
                
                break;
            default:
                if(tblFactura.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura());
                            procedimiento.execute();
                            listaFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un cliente para eliminar");
        }     
     
    }
    
    
     public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblFactura.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtNumeroDeFactura.setEditable(false);
                tipoDeOperacion = MenuFacturaController.operaciones.ACTUALIZAR;                
                    
                }else
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un cliente para editar");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperacion = MenuFacturaController.operaciones.NINGUNO;
                cargaDatos();
                break;
        }  
        
    }
     
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarFactura(?,?,?,?,?,?)}");
            Factura registro=(Factura)tblFactura.getSelectionModel().getSelectedItem();
            
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotalFactura.getText()));
            registro.setFechaFactura(txtFechaFactura.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            registro.setClienteID(((Clientes)cmbCodigoclienteID.getSelectionModel().getSelectedItem()).getClienteID());         
            registro.setCodigoEmpleado(((Empleado)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());         
         
            procedimiento.setInt(1, registro.getNumeroDeFactura());     
            procedimiento.setString(2, registro.getEstado());     
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getClienteID());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();

            procedimiento.execute();
                   
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }     
     
    public void reportes(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();                
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");        
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false); 
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");                 
                tipoDeOperacion = MenuFacturaController.operaciones.NINGUNO;
                break;  
        }
    }         
    
    public void desactivarControles(){
        txtNumeroDeFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalFactura.setEditable(false);
        txtFechaFactura.setEditable(false);
        cmbCodigoclienteID.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
        
    
    }
      public void activarControles(){
        txtNumeroDeFactura.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalFactura.setEditable(true);
        txtFechaFactura.setEditable(true);
        cmbCodigoclienteID.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
    
    }
      public void limpiarControles(){
        txtNumeroDeFactura.clear();
        txtEstado.clear();
        txtTotalFactura.clear();
        txtFechaFactura.setValue(null);
        cmbCodigoclienteID.getSelectionModel().getSelectedItem();
        cmbCodigoEmpleado.getSelectionModel().getSelectedItem();
    
    }
        
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
        }if(event.getSource()==btnMenuClientes){
            escenarioPrincipal.menuClientessView();
        }if(event.getSource() == btnProgramador){
            escenarioPrincipal.programadorView(); 
        }if(event.getSource() == btnProductos){
            escenarioPrincipal.menuTipoProductoView();   
        }if(event.getSource() == btnProveedor){
            escenarioPrincipal.menuProveedorView();         
        }if(event.getSource() == btnCompras){
            escenarioPrincipal.menuComprasView();
        }if(event.getSource() == btnCargoEmpleado){
            escenarioPrincipal.menuCargoEmpleadoView();    
        }if(event.getSource() == btnFecha){
            escenarioPrincipal.datePickerView();        
        }if(event.getSource() == btnProducto){
            escenarioPrincipal.menuProductoView();
        }if(event.getSource() == btnEmpleado){
            escenarioPrincipal.menuEmpleadoView();
        }if(event.getSource() == btnFactura){
            escenarioPrincipal.menuFacturaView();   
        }if(event.getSource() == btnDetalleFactura){
            escenarioPrincipal.menuDetalleFacturaView();         
        }if(event.getSource() == btnDetalleCompra){
            escenarioPrincipal.menuDetalleCompraView();   
        }if(event.getSource() == btnEmailProveedor)
            escenarioPrincipal.menuEmailProveedorView();  
    }
    
          public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
