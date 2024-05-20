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
import org.luislopez.bean.Compras;
import org.luislopez.bean.DetalleCompra;
import org.luislopez.bean.Productos;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuDetalleCompraController implements Initializable {

    
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <DetalleCompra> listaDetalleCompra;   
    private ObservableList <Productos> listaProductos;
    private ObservableList <Compras> listaCompras;
    
    @FXML private TextField txtCodigoDetalleCompra;
    @FXML private TextField txtCostoUnitario;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox cmbCodigoProducto;
    @FXML private ComboBox cmbNumeroDocumento;
    @FXML private TableView tblDetalleCompra;
    @FXML private TableColumn colCodigoDetalleCompra;
    @FXML private TableColumn colCostoUnitario;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNumeroDocumento;
    @FXML private Button btnRegresar;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoProducto.setItems(getProducto());
        cmbNumeroDocumento.setItems(getCompras());
        
    }
    
    public void cargaDatos(){
    tblDetalleCompra.setItems(getDetalleCompra());
    colCodigoDetalleCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
    colCostoUnitario.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
    colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
    colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoProducto"));
    colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
    
    }
    
    
    public void selecionarElementos(){
       txtCodigoDetalleCompra.setText(String.valueOf(((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
       txtCostoUnitario.setText(String.valueOf(((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getCostoUnitario()));
       txtCantidad.setText(String.valueOf(((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getCantidad()));
       cmbCodigoProducto.getSelectionModel().select(buscarProductos(((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto())); 
       cmbNumeroDocumento.getSelectionModel().select(buscarCompras(((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getNumeroDocumento())); 
       
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
    
    public Compras buscarCompras (int numeroDocumento ){
        Compras resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCompras(?)}");
            procedimiento.setInt(1, numeroDocumento);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Compras(registro.getInt("numeroDocumento"),
                        registro.getString("fechaDocumento"),
                        registro.getString("descripcion"),
                        registro.getString("totalDocumento")
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
        
    
    public ObservableList<DetalleCompra> getDetalleCompra(){
        ArrayList<DetalleCompra> lista = new ArrayList<DetalleCompra>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarDetalleCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new DetalleCompra (resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("codigoProducto"),
                        resultado.getInt("numeroDocumento")          
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    
        return listaDetalleCompra = FXCollections.observableArrayList(lista);
        
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getString("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCompras = FXCollections.observableList(lista);
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
             tipoDeOperacion = MenuDetalleCompraController.operaciones.ACTUALIZAR;
             break;
             case ACTUALIZAR:
             guardar ();
             desactivarControles();
             limpiarControles ();
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             tipoDeOperacion = MenuDetalleCompraController.operaciones.NINGUNO;
             cargaDatos();
             break;
         }
     
     }
       
    
     public void guardar (){
         DetalleCompra registro = new DetalleCompra();
         registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
         registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
         registro.setCodigoProducto(((Productos)cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());         
         registro.setNumeroDocumento(((Compras)cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());         
         
         try {
             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall ("{CALL sp_AgregarDetalleCompra(?, ?, ?, ?)}");
            procedimiento.setDouble(1, registro.getCostoUnitario());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.setInt(3, registro.getCodigoProducto());
            procedimiento.setInt(4, registro.getNumeroDocumento());            
            procedimiento.execute();
            
            listaDetalleCompra.add(registro);
            

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
                tipoDeOperacion = MenuDetalleCompraController.operaciones.NINGUNO;                
                
                break;
            default:
                if(tblDetalleCompra.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarDetalleCompra(?)}");
                            procedimiento.setInt(1, ((DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetalleCompra.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
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
                if(tblDetalleCompra.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtCodigoDetalleCompra.setEditable(false);
                tipoDeOperacion = MenuDetalleCompraController.operaciones.ACTUALIZAR;                
                    
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
                tipoDeOperacion = MenuDetalleCompraController.operaciones.NINGUNO;
                cargaDatos();
                break;
        }  
        
    }
     
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarDetalleCompra(?,?,?,?,?)}");
            DetalleCompra registro=(DetalleCompra)tblDetalleCompra.getSelectionModel().getSelectedItem();
            
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCodigoProducto(((Productos)cmbCodigoProducto.getSelectionModel().getSelectedItem()).getCodigoProducto());         
            registro.setNumeroDocumento(((Compras)cmbNumeroDocumento.getSelectionModel().getSelectedItem()).getNumeroDocumento());         
         
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());     
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());            
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
                tipoDeOperacion = MenuDetalleCompraController.operaciones.NINGUNO;
                break;  
        }
    }         
    
  
    public void desactivarControles(){
        txtCodigoDetalleCompra.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        cmbCodigoProducto.setDisable(true);
        cmbNumeroDocumento.setDisable(true);
        
    
    }
      public void activarControles(){
        txtCodigoDetalleCompra.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        cmbCodigoProducto.setDisable(false);
        cmbNumeroDocumento.setDisable(false);
    
    }
      public void limpiarControles(){
        txtCodigoDetalleCompra.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        cmbCodigoProducto.getSelectionModel().getSelectedItem();
        cmbNumeroDocumento.getSelectionModel().getSelectedItem();
    
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
