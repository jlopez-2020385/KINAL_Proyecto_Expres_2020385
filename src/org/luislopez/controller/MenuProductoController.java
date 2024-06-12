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
import java.util.HashMap;
import java.util.Map;
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
import org.luislopez.bean.Productos;
import org.luislopez.bean.Proveedores;
import org.luislopez.bean.TipoProductos;
import org.luislopez.db.Conexion;
import org.luislopez.report.GenerarReportes;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuProductoController implements Initializable {

    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Productos> listaProductos;
    private ObservableList <Proveedores> listaProveedores;
    private ObservableList <TipoProductos> listaTipoDeProducto;
    
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
    
    
    @FXML private TextField txtCodigoProd;
    @FXML private TextField txtDescPro;
    @FXML private TextField txtPrecioU;
    @FXML private TextField txtPrecioD;
    @FXML private TextField txtPrecioM;
    @FXML private TextField txtExistencia;
    @FXML private ComboBox cmbCodigoTipoP;
    @FXML private ComboBox cmbCodProv;
    @FXML private TableView tblProductos;
    @FXML private TableColumn colCodProd;
    @FXML private TableColumn colDescProd;
    @FXML private TableColumn colPrecioU;
    @FXML private TableColumn colPrecioD;
    @FXML private TableColumn colPrecioM;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colCodTipoProd;
    @FXML private TableColumn colCodProv;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoTipoP.setItems(getTipoP());
        cmbCodProv.setItems(getProveedores());
    }


    
    public void cargaDatos(){
    tblProductos.setItems(getProducto());
    colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
    colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
    colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
    colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
    colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
    colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
    colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
    colCodProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    
    
    }
    public void selecionarElementos(){
       txtCodigoProd.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
       txtDescPro.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
       txtPrecioU.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
       txtPrecioD.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
       txtPrecioM.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
       txtExistencia.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
       cmbCodigoTipoP.getSelectionModel().select(buscarTipoProducto(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
       cmbCodProv.getSelectionModel().select(buscarProveedor(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
       
    }
    
    public TipoProductos buscarTipoProducto (int codigoTipoProducto ){
        TipoProductos resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoProducto(?)}");
         procedimiento.setInt(1, codigoTipoProducto);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new TipoProductos(registro.getInt("codigoTipoProducto"),
                                            registro.getString("descripcionProducto")
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
    }
    
    public Proveedores buscarProveedor (int codigoProveedor ){
        Proveedores resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
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
    
    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
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
        return listaProveedores = FXCollections.observableList(listaPro);
    }
     public ObservableList<TipoProductos> getTipoP() {
        ArrayList<TipoProductos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProductos(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcionProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoDeProducto = FXCollections.observableList(lista);
    }
    
     public void agregar (){
         switch(tipoDeOperacion){
             case NINGUNO:
              activarControles();
             btnAgregar.setText("Guardar");
             btnEliminar.setText("Cancelar");
             btnEditar.setDisable(true);
             btnReporte.setDisable(true);   
             tipoDeOperacion = operaciones.ACTUALIZAR;
             break;
             case ACTUALIZAR:
             guardar ();
             desactivarControles();
             limpiarControles ();
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             tipoDeOperacion = operaciones.NINGUNO;
             cargaDatos();
             break;
         }
     
     
     
     
     }
     
     
     public void guardar (){
         Productos registro = new Productos();
         registro.setCodigoProveedor(((Proveedores)cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         registro.setCodigoTipoProducto(((TipoProductos)cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
         registro.setDescripcionProducto(txtDescPro.getText());
         try {
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall ("{CALL sp_agregarProducto(?, ?, ?, ?, ?, ?, ?)}");
        procedimiento.setString(1, registro.getDescripcionProducto());
        procedimiento.setDouble(2, registro.getPrecioUnitario());
        procedimiento.setDouble(3, registro.getPrecioDocena());
        procedimiento.setDouble(4, registro.getPrecioMayor());
        procedimiento.setInt(5, registro.getExistencia());
        procedimiento.setInt(6, registro.getCodigoTipoProducto());
        procedimiento.setInt(7, registro.getCodigoProveedor());
        procedimiento.execute();
        
        listaProductos.add(registro);

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
                tipoDeOperacion = operaciones.NINGUNO;                
                
                break;
            default:
                if(tblProductos.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProducto(?)}");
                            procedimiento.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
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
                if(tblProductos.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtCodigoProd.setEditable(false);
                tipoDeOperacion = operaciones.ACTUALIZAR;                
                    
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
                tipoDeOperacion = operaciones.NINGUNO;
                cargaDatos();
                break;
        }  
        
    }
     
     
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoProducto",null);
        GenerarReportes.mostrarReportes("ReporteProductos.jasper", "Reporte de Produsctod", parametros);
        
    }     
     
     
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProducto(?,?,?,?,?,?,?,?)}");
            Productos registro=(Productos)tblProductos.getSelectionModel().getSelectedItem();
            
            registro.setCodigoTipoProducto(((TipoProductos)cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
            registro.setCodigoProveedor(((Proveedores)cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            registro.setDescripcionProducto(txtDescPro.getText());
            registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
            registro.setExistencia(Integer.parseInt(txtExistencia.getText())); 
            
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getCodigoTipoProducto());            
            procedimiento.setInt(8, registro.getCodigoProveedor());

            procedimiento.execute();
                   
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }     
     
    public void reportes(){
        switch(tipoDeOperacion){
            case NINGUNO :
                imprimirReporte();
                break;
                
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();                
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");        
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false); 
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");                 
                tipoDeOperacion = operaciones.NINGUNO;
                break;  
        }
    }         
    
    
    public void desactivarControles(){
        txtCodigoProd.setEditable(false);
        txtDescPro.setEditable(false);
        txtPrecioU.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtExistencia.setEditable(false);
        cmbCodigoTipoP.setDisable(true);
        cmbCodProv.setDisable(true);
    
    }
      public void activarControles(){
        txtCodigoProd.setEditable(true);
        txtDescPro.setEditable(true);
        txtPrecioU.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtExistencia.setEditable(true);
        cmbCodigoTipoP.setDisable(false);
        cmbCodProv.setDisable(false);
    
    }
      public void limpiarControles(){
        txtCodigoProd.clear();
        txtDescPro.clear();
        txtPrecioU.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtExistencia.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
        cmbCodProv.getSelectionModel().getSelectedItem();
    
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
