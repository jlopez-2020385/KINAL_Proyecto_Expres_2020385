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
import org.luislopez.bean.EmailProveedor;
import org.luislopez.bean.Proveedores;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuEmailProveedorController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<EmailProveedor> listaEmailProveedor;
    private ObservableList<Proveedores> listaProveedores;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeoperaciones = operaciones.NINGUNO;

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
    
    
    @FXML
    private TextField txtCodigoEmailProveedor;
    @FXML
    private TextField txtEmailProveedor;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private ComboBox cmbCodigoProveedor;
    @FXML
    private TableView tblEmailProveedor;
    @FXML
    private TableColumn colCodigoEmailProveedor;
    @FXML
    private TableColumn colEmailProveedor;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colCodigoProveedor;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoProveedor.setItems(getProveedores());
        // TODO
    }

    /**
     * Método para cargar los datos en la tabla de SceneBuilder
     */  
    
    public void cargarDatos() {
        tblEmailProveedor.setItems(getEmailProveedor());
        colCodigoEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("emailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("descripcion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));

    }


    /**
     * Método para seleccionar elementos de la tabla SceneBuilder
     */    
    
    public void selecionarElementos() {
        txtCodigoEmailProveedor.setText(String.valueOf(((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
        txtEmailProveedor.setText((((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor()));
        txtDescripcion.setText((((EmailProveedor) tblEmailProveedor.getSelectionModel().getSelectedItem()).getDescripcion()));
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor())); 
    }

    
    /**
     * Método para obtener la lista
     * 
     */       
    
    
    public Proveedores buscarProveedor (int codigoProveedor ){
        Proveedores resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                        registro.getString("NITProveedor"),
                        registro.getString("nombresProveedor"),
                        registro.getString("apellidosProveedor"),
                        registro.getString("direccionProveedor"),
                        registro.getString("rasonSocial"),
                        registro.getString("contactoPrincipal"),
                        registro.getString("paginaWeb")
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }      
    
    
    
    public ObservableList<EmailProveedor> getEmailProveedor() {
        ArrayList<EmailProveedor> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarEmailProveedor}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                        
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaEmailProveedor = FXCollections.observableList(lista);
    }    
    
        
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("rasonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                        
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableList(lista);
    }

    /**
     * Método para agregar 
     */      
    
    public void agregar() {
        switch (tipoDeoperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                activarControles();
                tipoDeoperaciones = MenuEmailProveedorController.operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControlers();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                desactivarControles();
                limpiarControlers();                
                tipoDeoperaciones = MenuEmailProveedorController.operaciones.NINGUNO;
                break;
                
        }
    }


    /**
     * Método para guardar 
     */   
    
    
    public void guardar() {
        EmailProveedor registro = new EmailProveedor();
        registro.setEmailProveedor(txtEmailProveedor.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setCodigoProveedor(((EmailProveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());         
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmailProveedor( ?, ?, ?)}");
            procedimiento.setString(1, registro.getEmailProveedor());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.setInt(3, registro.getCodigoProveedor());
            procedimiento.execute();
            listaEmailProveedor.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Método para eliminar 
     */    
    public void eliminar() {
        
        switch(tipoDeoperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControlers();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeoperaciones = MenuEmailProveedorController.operaciones.NINGUNO;                
                
                break;
            default:
                if(tblEmailProveedor.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listaEmailProveedor.remove(tblEmailProveedor.getSelectionModel().getSelectedItem());
                            limpiarControlers();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un cliente para eliminar");
        }
        
    }
    
    
    /**
     * Método para editar 
     */
    
    public void editar(){
        switch(tipoDeoperaciones){
            case NINGUNO:
                if(tblEmailProveedor.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtCodigoEmailProveedor.setEditable(false);
                tipoDeoperaciones = MenuEmailProveedorController.operaciones.ACTUALIZAR;                
                    
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
                limpiarControlers();
                tipoDeoperaciones = MenuEmailProveedorController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
        
    }
    
    /**
     * Método para actualizar 
     */    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmailProveedor(?,?,?,?)}");
            EmailProveedor registro=(EmailProveedor)tblEmailProveedor.getSelectionModel().getSelectedItem();
            
            registro.setEmailProveedor(txtEmailProveedor.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoProveedor(((EmailProveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());         
        
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listaEmailProveedor.add(registro);           
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void reportes(){
        switch(tipoDeoperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControlers();                
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");        
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false); 
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");                 
                tipoDeoperaciones = MenuEmailProveedorController.operaciones.NINGUNO;
                break;  
        }
    }    
          
    
    public void desactivarControles() {
        txtCodigoEmailProveedor.setEditable(true);
        txtEmailProveedor.setEditable(false);
        txtDescripcion.setEditable(false);
        cmbCodigoProveedor.setEditable(false);

    }

    public void activarControles() {
        txtCodigoEmailProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        txtDescripcion.setEditable(true);
        cmbCodigoProveedor.setEditable(true);

    }

    public void limpiarControlers() {
        txtCodigoEmailProveedor.clear();
        txtEmailProveedor.clear();
        txtDescripcion.clear();
        cmbCodigoProveedor.getSelectionModel().getSelectedItem();

    }

    @FXML
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

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}




