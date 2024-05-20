/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.luislopez.bean.DetalleFactura;
import org.luislopez.bean.Factura;
import org.luislopez.bean.Productos;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuDetalleFacturaController implements Initializable {

    
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <DetalleFactura> listaDetalleFactura;   
    private ObservableList <Factura> listaFactura;
    private ObservableList <Productos> listaProductos;
    
    
    @FXML private TextField txtCodigoDetalleFactura;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbNumeroDeFactura; 
    @FXML private ComboBox cmbCodigoProducto; 
    @FXML private TableView tblDetalleFactura;
    @FXML private TableColumn colCodigoDetalleFactura;
    @FXML private TableColumn colPrecioUnitario;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colNumerodeFactura; 
    @FXML private TableColumn colCodigoProducto; 
    @FXML private Button btnRegresar;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbNumeroDeFactura.setItems(getFactura());
        cmbCodigoProducto.setItems(getProducto());
        
    }
    
    public void cargaDatos(){
    tblDetalleFactura.setItems(getDetalleFactura());
    colCodigoDetalleFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoDetalleFactura"));
    colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Factura, Double>("precioUnitario"));
    colCantidad.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("cantidad"));
    colNumerodeFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroDeFactura"));
    colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Factura, String>("codigoProducto"));
    
    }
    public void selecionarElementos(){
       txtCodigoDetalleFactura.setText(String.valueOf(((DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
       txtPrecioUnitario.setText(String.valueOf(((DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
       txtCantidad.setText(String.valueOf(((DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad()));
       cmbNumeroDeFactura.getSelectionModel().select(buscarFactura(((DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura())); 
       cmbCodigoProducto.getSelectionModel().select(buscarProductos(((DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto())); 
       
    }
    
    
    
    public Factura buscarFactura (int numeroDeFactura ){
        Factura resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarFactura(?)}");
         procedimiento.setInt(1, numeroDeFactura);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Factura(registro.getInt("numeroDeFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("clienteID"),    
                        registro.getInt("codigoEmpleado")      
                        
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
    
    
    public Productos buscarProductos (int codigoProducto ){
        Productos resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
         procedimiento.setInt(1, codigoProducto);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new Productos(registro.getInt("codigoProducto"),
                    registro.getString("descripcionProducto"),
                    registro.getDouble("precioUnitario"),
                    registro.getDouble("precioDocena"),
                    registro.getDouble("precioMayor"),
                    registro.getInt("existencia"),
                    registro.getInt("codigoTipoProducto"),
                    registro.getInt("codigoProveedor") 
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }    
    
    
    
    public ObservableList<DetalleFactura> getDetalleFactura(){
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarDetalleFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleFactura (resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroDeFactura"),
                        resultado.getInt("codigoProducto")          
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    
        return listaDetalleFactura = FXCollections.observableArrayList(lista);
        
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
    
    
    public ObservableList<Productos> getProducto(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_mostrarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos (resultado.getInt("codigoProducto"),
                    resultado.getString("descripcionProducto"),
                    resultado.getDouble("precioUnitario"),
                    resultado.getDouble("precioDocena"),
                    resultado.getDouble("precioMayor"),
                    resultado.getInt("existencia"),
                    resultado.getInt("codigoTipoProducto"),
                    resultado.getInt("codigoProveedor")            
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    return listaProductos = FXCollections.observableArrayList(lista);
        
    }
   
    
    
     public void agregar (){
         switch(tipoDeOperacion){
             case NINGUNO:
              activarControles();
             btnAgregar.setText("Guardar");
             btnEliminar.setText("Cancelar");
             btnEditar.setDisable(true);
             btnReporte.setDisable(true);   
             tipoDeOperacion = MenuDetalleFacturaController.operaciones.ACTUALIZAR;
             break;
             case ACTUALIZAR:
             guardar ();
             desactivarControles();
             limpiarControles ();
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             tipoDeOperacion = MenuDetalleFacturaController.operaciones.NINGUNO;
             cargaDatos();
             break;
         }
     
     }
       
    
     public void guardar (){
         DetalleFactura registro = new DetalleFactura();
         registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
         registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
         registro.setNumeroDeFactura(((Factura)cmbNumeroDeFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura());         
         registro.setCodigoProducto(((Productos)cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());         
         
         try {
             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall ("{CALL sp_AgregarDetalleFactura(?, ?, ?, ?)}");
            procedimiento.setDouble(1, registro.getPrecioUnitario());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getNumeroDeFactura());
            procedimiento.setInt(4, registro.getCodigoProducto());            
            procedimiento.execute();
            
            listaDetalleFactura.add(registro);
            

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
                tipoDeOperacion = MenuDetalleFacturaController.operaciones.NINGUNO;                
                
                break;
            default:
                if(tblDetalleFactura.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarDetalleFactura(?)}");
                            procedimiento.setInt(1, ((DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura());
                            procedimiento.execute();
                            listaFactura.remove(tblDetalleFactura.getSelectionModel().getSelectedItem());
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
                if(tblDetalleFactura.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtCodigoDetalleFactura.setEditable(false);
                tipoDeOperacion = MenuDetalleFacturaController.operaciones.ACTUALIZAR;                
                    
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
                tipoDeOperacion = MenuDetalleFacturaController.operaciones.NINGUNO;
                cargaDatos();
                break;
        }  
        
    }
     
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleFactura(?,?,?,?,?)}");
            DetalleFactura registro=(DetalleFactura)tblDetalleFactura.getSelectionModel().getSelectedItem();
            
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setNumeroDeFactura(((Factura)cmbNumeroDeFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura());         
            registro.setCodigoProducto(((Productos)cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());         
         
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());     
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroDeFactura());
            procedimiento.setInt(5, registro.getCodigoProducto());            
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
                tipoDeOperacion = MenuDetalleFacturaController.operaciones.NINGUNO;
                break;  
        }
    }         
    
    public void desactivarControles(){
        txtCodigoDetalleFactura.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbNumeroDeFactura.setDisable(true);
        cmbCodigoProducto.setDisable(true);
        
    
    }
      public void activarControles(){
        txtCodigoDetalleFactura.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbNumeroDeFactura.setDisable(false);
        cmbCodigoProducto.setDisable(false);
    
    }
      public void limpiarControles(){
        txtCodigoDetalleFactura.clear();
        txtPrecioUnitario.clear();
        txtCantidad.clear();
        cmbNumeroDeFactura.getSelectionModel().getSelectedItem();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
    
    }
        
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
          public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
