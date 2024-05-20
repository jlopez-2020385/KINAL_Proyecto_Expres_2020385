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
import org.luislopez.bean.CargoEmpleado;
import org.luislopez.bean.Empleado;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuEmpleadoController implements Initializable {
    
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Empleado> listaEmpleado;  
    private ObservableList <CargoEmpleado> listaCargoEmpleado;  
    
    
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombresEmpleado;
    @FXML private TextField txtApellidosEmpleado; 
    @FXML private TextField txtSueldo;
    @FXML private TextField txtDireccion; 
    @FXML private TextField txtTurno;
    @FXML private ComboBox cmbCodigoCargoEmpleado;
    @FXML private TableView tblEmpleado;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNombresEmpleado;
    @FXML private TableColumn colApellidosEmpleado; 
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colDireccion; 
    @FXML private TableColumn colTurno;
    @FXML private TableColumn colCodigoCargoEmpleado;
    @FXML private Button btnRegresar;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargaDatos();
        cmbCodigoCargoEmpleado.setItems(getCargoEmpleado());
    }


    
    public void cargaDatos(){
    tblEmpleado.setItems(getEmpleado());
    colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoEmpleado"));
    colNombresEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombresEmpleado"));
    colApellidosEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidosEmpleado"));    
    colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
    colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
    colTurno.setCellValueFactory(new PropertyValueFactory<Empleado, String>("turno"));
    colCodigoCargoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("codigoCargoEmpleado"));
    
    }
    public void selecionarElementos(){
       txtCodigoEmpleado.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
       txtNombresEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getNombresEmpleado());
       txtApellidosEmpleado.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getApellidosEmpleado());
       txtSueldo.setText(String.valueOf(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getSueldo()));
       txtDireccion.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getDireccion());
       txtTurno.setText(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getTurno());
       cmbCodigoCargoEmpleado.getSelectionModel().select(buscarCargoEmpleado(((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));       
    }
    
    public CargoEmpleado buscarCargoEmpleado (int codigoCargoEmpleado ){
        CargoEmpleado resultado = null;
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargoEmpleado(?)}");
         procedimiento.setInt(1, codigoCargoEmpleado);
         ResultSet registro = procedimiento.executeQuery();
         while (registro.next()){
             resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo")
             
             );
         }
        }catch (Exception e)
        {
            e.printStackTrace();
        }    
    
        return resultado;
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

     public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCargoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                        
                        
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargoEmpleado = FXCollections.observableList(lista);
    }
    
     public void agregar (){
         switch(tipoDeOperacion){
             case NINGUNO:
              activarControles();
             btnAgregar.setText("Guardar");
             btnEliminar.setText("Cancelar");
             btnEditar.setDisable(true);
             btnReporte.setDisable(true);   
             tipoDeOperacion = MenuEmpleadoController.operaciones.ACTUALIZAR;
             break;
             case ACTUALIZAR:
             guardar ();
             desactivarControles();
             limpiarControles ();
             btnAgregar.setText("Agregar");
             btnEliminar.setText("Eliminar");
             btnEditar.setDisable(false);
             btnReporte.setDisable(false);
             tipoDeOperacion = MenuEmpleadoController.operaciones.NINGUNO;
             cargaDatos();
             break;
         }
     
     
     
     
     }
       
     
     public void guardar (){
         Empleado registro = new Empleado();
         registro.setNombresEmpleado(txtNombresEmpleado.getText());
         registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
         registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
         registro.setDireccion(txtDireccion.getText());
         registro.setTurno(txtTurno.getText());         
         registro.setCodigoCargoEmpleado(((CargoEmpleado)cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());         
         try {
             
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall ("{CALL sp_AgregarEmpleado(?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombresEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());        
            procedimiento.setDouble(3, registro.getSueldo());
            procedimiento.setString(4, registro.getDireccion());
            procedimiento.setString(5, registro.getTurno());
            procedimiento.setInt(6, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
        
        listaEmpleado.add(registro);

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
                tipoDeOperacion = MenuEmpleadoController.operaciones.NINGUNO;                
                
                break;
            default:
                if(tblEmpleado.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleado)tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleado.remove(tblEmpleado.getSelectionModel().getSelectedItem());
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
                if(tblEmpleado.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtCodigoEmpleado.setEditable(false);
                tipoDeOperacion = MenuEmpleadoController.operaciones.ACTUALIZAR;                
                    
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
                tipoDeOperacion = MenuEmpleadoController.operaciones.NINGUNO;
                cargaDatos();
                break;
        }  
        
    }
     
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarEmpleados(?,?,?,?,?,?,?)}");
            Empleado registro=(Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
            
            registro.setNombresEmpleado(txtNombresEmpleado.getText());
            registro.setApellidosEmpleado(txtApellidosEmpleado.getText());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDireccion(txtDireccion.getText());
            registro.setTurno(txtTurno.getText());         
            registro.setCodigoCargoEmpleado(((CargoEmpleado)cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());         
            
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());        
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
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
                tipoDeOperacion = MenuEmpleadoController.operaciones.NINGUNO;
                break;  
        }
    }         
    
   
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombresEmpleado.setEditable(false);
        txtApellidosEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTurno.setEditable(false);
        cmbCodigoCargoEmpleado.setDisable(true);
    
    }
      public void activarControles(){
        txtCodigoEmpleado.setEditable(true);
        txtNombresEmpleado.setEditable(true);
        txtApellidosEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTurno.setEditable(true);
        cmbCodigoCargoEmpleado.setDisable(false);
    
    }
      public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNombresEmpleado.clear();
        txtApellidosEmpleado.clear();
        txtSueldo.clear();
        txtDireccion.clear();
        txtTurno.clear();
        cmbCodigoCargoEmpleado.getSelectionModel().getSelectedItem();
    
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
