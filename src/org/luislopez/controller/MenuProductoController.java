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
import org.luislopez.bean.Productos;
import org.luislopez.bean.Proveedores;
import org.luislopez.bean.TipoProductos;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuProductoController implements Initializable {

    @FXML
    private TableView tvProductos;
    @FXML
    private Button btnRegresar;    
    @FXML
    private Button btnAgregarPro;
    @FXML
    private Button btnEditarPro;
    @FXML
    private Button btnEliminarPro;
    @FXML
    private Button btnReportesPro;
    @FXML
    private TextField txtCodigoProd;
    @FXML
    private TextField txtDescPro;
    @FXML
    private TextField txtPrecioU;
    @FXML
    private TextField txtPrecioD;
    @FXML
    private TextField txtPrecioM;
    @FXML
    private TextField txtImagenPro;
    @FXML
    private TextField txtExistencia;
    @FXML
    private ComboBox cmbCodigoTipoP;
    @FXML
    private ComboBox cmbCodigoP;
    @FXML
    private TableColumn colCodProd;

    @FXML
    private TableColumn colDescProd;

    @FXML
    private TableColumn colPrecioU;

    @FXML
    private TableColumn colPrecioD;

    @FXML
    private TableColumn colPrecioM;

    @FXML
    private TableColumn colImagenP;

    @FXML
    private TableColumn colExistencia;

    @FXML
    private TableColumn colCodTipoProd;

    @FXML
    private TableColumn colCodProv;

    private Principal escenarioPrincipal;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProductos> listaTipoP;

    private enum operador {
        AGREGRAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operador tipoDeOperador = operador.NINGUNO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatos();
        cmbCodigoTipoP.setItems(getTipoP());
        cmbCodigoP.setItems(getProveedores());
        desactivarControles();
    }

    public void cargarDatos() {
        tvProductos.setItems(getProducto());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenP.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("CodigoTipoProducto"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }

    public void seleccionarElemento() {
        txtCodigoProd.setText((((Productos) tvProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtDescPro.setText((((Productos) tvProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto()));
        txtPrecioU.setText(String.valueOf(((Productos) tvProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioD.setText(String.valueOf(((Productos) tvProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioM.setText(String.valueOf(((Productos) tvProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtImagenPro.setText((((Productos) tvProductos.getSelectionModel().getSelectedItem()).getImagenProducto()));
        txtExistencia.setText(String.valueOf(((Productos) tvProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        //cmbCodgioTipoP.set(String.valueOf(((Productos)tvProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        //  cmbCodigoP.setText(String.valueOf(((Productos)tvProductos.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    }

    public ObservableList<Productos> getProducto() {
        ArrayList<Productos> listaP = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_mostrarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaP.add(new Productos(resultado.getString("codigoProducto"),
                        resultado.getString("descripcionProducto"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getDouble("precioDocena"),
                        resultado.getDouble("precioMayor"),
                        resultado.getString("imagenProducto"),
                        resultado.getInt("existencia"),
                        resultado.getInt("CodigoTipoProducto"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(listaP);
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
                        resultado.getString("razonSocial"),
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
                lista.add(new TipoProductos(resultado.getInt("CodigoTipoProducto"),
                        resultado.getString("descripcionProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoP = FXCollections.observableList(lista);
    }

    public void desactivarControles() {
        txtCodigoProd.setEditable(false);
        txtDescPro.setEditable(false);
        txtExistencia.setEditable(false);
        txtImagenPro.setEditable(false);
        txtPrecioD.setEditable(false);
        txtPrecioM.setEditable(false);
        txtPrecioU.setEditable(false);
        cmbCodigoTipoP.setDisable(true);
        cmbCodigoP.setDisable(true);
    }

    public void activarControles() {
        txtCodigoProd.setEditable(true);
        txtDescPro.setEditable(true);
        txtExistencia.setEditable(true);
        txtImagenPro.setEditable(true);
        txtPrecioD.setEditable(true);
        txtPrecioM.setEditable(true);
        txtPrecioU.setEditable(true);
        cmbCodigoTipoP.setDisable(false);
        cmbCodigoP.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoProd.clear();
        txtDescPro.clear();
        txtExistencia.clear();
        txtImagenPro.clear();
        txtPrecioD.clear();
        txtPrecioM.clear();
        txtPrecioU.clear();
        tvProductos.getSelectionModel().getSelectedItem();
        cmbCodigoTipoP.getSelectionModel().getSelectedItem();
        cmbCodigoP.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void agregar() {
        switch (tipoDeOperador) {
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregarPro.setText("Guardar");
                btnEliminarPro.setText("Cancelar");
                btnEditarPro.setDisable(true);
                btnReportesPro.setDisable(true);
                //imgAgregar.setImage(new Image("URL"));
                tipoDeOperador = operador.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                limpiarControles();
                cargarDatos();
                desactivarControles();
                btnAgregarPro.setText("Agregar");
                btnEliminarPro.setText("Eliminar");
                btnEditarPro.setDisable(false);
                btnReportesPro.setDisable(false);
                /*regresar de nuevo a sus imagenes originales
                imgAgregar.setImage(new Image("URL"));*/
                tipoDeOperador = operador.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoProd.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto((((TipoProductos) cmbCodigoTipoP.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        registro.setDescripcionProducto(txtDescPro.getText());
        registro.setPrecioDocena(Double.parseDouble(txtPrecioD.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioM.getText()));
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        registro.setImagenProducto(txtImagenPro.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioU.getText()));
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_agregarProducto(?,?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7,registro.getExistencia() );
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
            
            listaProductos.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void editar() {
        
    }

    @FXML
    private void eliminar() {
    }
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
