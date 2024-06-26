/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import static java.lang.Double.parseDouble;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.luislopez.bean.Compras;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuComprasController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Compras> listaCompras;

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
    private TextField txtNumeroC;
    @FXML
    private DatePicker txtFechaC;
    @FXML
    private TextField txtDescripcionC;
    @FXML
    private TextField txtTotalC;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colNumeroC;
    @FXML
    private TableColumn colFechaC;
    @FXML
    private TableColumn colDescripcionC;
    @FXML
    private TableColumn colTotalC;
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
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        // TODO
    }

    /**
     * Método para cargar los datos en la tabla de SceneBuilder
     */  
    
    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("fechaDocumento"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("descripcion"));
        colTotalC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("totalDocumento"));

    }


    /**
     * Método para seleccionar elementos de la tabla SceneBuilder
     */    
    
    public void selecionarElementos() {
        txtNumeroC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaC.setValue(LocalDate.parse(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDescripcionC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotalC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
    }

    
    /**
     * Método para obtener la lista
     * 
     */       
    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCompras}");
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
                tipoDeoperaciones = operaciones.ACTUALIZAR;
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
                tipoDeoperaciones = operaciones.NINGUNO;
                break;
                
        }
    }


    /**
     * Método para guardar 
     */   
    
    public void guardar() {
        Compras registro = new Compras();
        registro.setFechaDocumento(txtFechaC.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setDescripcion(txtDescripcionC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompras( ?, ?,?)}");
            procedimiento.setString(1, registro.getFechaDocumento());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.setString(3, registro.getTotalDocumento());

            procedimiento.execute();
            listaCompras.add(registro);

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
                tipoDeoperaciones = operaciones.NINGUNO;                
                
                break;
            default:
                if(tblCompras.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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
                if(tblCompras.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtNumeroC.setEditable(false);
                tipoDeoperaciones = operaciones.ACTUALIZAR;                
                    
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
                tipoDeoperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
        
    }
    
    /**
     * Método para actualizar 
     */    
    

    
    public void actualizar(){
        Compras registro = (Compras)tblCompras.getSelectionModel().getSelectedItem();
        registro.setFechaDocumento(txtFechaC.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        registro.setDescripcion(txtDescripcionC.getText());
        registro.setTotalDocumento((txtTotalC.getText()));

        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCompras(?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());


            procedimiento.execute();
             listaCompras.add(registro);
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
                tipoDeoperaciones = operaciones.NINGUNO;
                break;  
        }
    }    
           
    
    
    
    
    public void desactivarControles() {
        txtNumeroC.setEditable(false);
        txtFechaC.setEditable(false);
        txtDescripcionC.setEditable(false);
        txtTotalC.setEditable(false);

    }

    public void activarControles() {
        txtNumeroC.setEditable(true);
        txtFechaC.setEditable(true);
        txtDescripcionC.setEditable(true);
        txtTotalC.setEditable(true);

    }

    public void limpiarControlers() {
        txtNumeroC.clear();
        txtFechaC.setValue(null);
        txtDescripcionC.clear();
        txtTotalC.clear();

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

