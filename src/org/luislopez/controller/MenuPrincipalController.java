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
import javafx.scene.control.MenuItem;
import org.luislopez.system.Principal; 

/**
 * La clase MenuPrincipalController controla la vista del menú principal de la aplicación Kinal Express.
 */

public class MenuPrincipalController  implements Initializable{
    private Principal escenarioPrincipal;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador; 
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnProveedor;
    @FXML MenuItem btnCompras;    
    @FXML MenuItem btnCargoEmpleado;   
    @FXML MenuItem btnProducto;    
    @FXML MenuItem btnEmpleado;     
    @FXML MenuItem btnFactura;    
    @FXML MenuItem btnDetalleFactura;    
    @FXML MenuItem btnDetalleCompra;    
    @FXML MenuItem btnFecha;    
    @FXML Button btnAdministracion;    
    
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnAdministracion)
            escenarioPrincipal.menuAdministracionView();          
        
    }
    
    
    
    
    
    
    
}
