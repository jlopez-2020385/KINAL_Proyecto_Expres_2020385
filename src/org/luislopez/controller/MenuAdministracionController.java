/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuAdministracionController implements Initializable {
        private Principal escenarioPrincipal;
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
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    
    @FXML 
        public void handleButtonAction(ActionEvent event){

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
        
        
        
        

    public void setEscenarioPrincipal(Principal aThis) {
        this.escenarioPrincipal = aThis;
    }
    
}
