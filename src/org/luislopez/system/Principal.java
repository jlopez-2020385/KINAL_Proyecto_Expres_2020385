
package org.luislopez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.luislopez.controller.DatePickerController;
import org.luislopez.controller.MenuAdministracionController;
import org.luislopez.controller.MenuCargoEmpleadoController;
import org.luislopez.controller.MenuClientessController;
import org.luislopez.controller.MenuComprasController;
import org.luislopez.controller.MenuDetalleCompraController;
import org.luislopez.controller.MenuDetalleFacturaController;
import org.luislopez.controller.MenuEmailProveedorController;
import org.luislopez.controller.MenuEmpleadoController;
import org.luislopez.controller.MenuFacturaController;

import org.luislopez.controller.MenuPrincipalController;
import org.luislopez.controller.MenuProductoController;
import org.luislopez.controller.MenuTipoProductoController;
import org.luislopez.controller.MenuProveedorController;
import org.luislopez.controller.ProgramadoController;

/**
 * La clase Principal es la clase principal de la aplicación Kinal Express.
 * Se encarga de manejar la navegación entre las diferentes vistas.
 */

public class Principal extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    
    
    public static void main(String[] args) {
        launch(args);
        
    }    
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        menuPrincipalView();
        escenarioPrincipal.getIcons().add(new Image("/org/luislopez/imagenes/cc.png"));
       // Parent root=FXMLLoader.load(getClass().getResource("/org/luislopez/view/KINALexpres.fxml"));
       // Scene escena=new Scene(root);
       // escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
        
    }
    
    public Initializable cambiarEscena(String fxmlname, int width,int heigth) throws Exception{
        Initializable resultado;
        FXMLLoader loader= new FXMLLoader();
        InputStream file = Principal.class.getResourceAsStream("/org/luislopez/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource("/org/luislopez/view/" + fxmlname));
        
        escena=new Scene ((AnchorPane)loader.load(file),width,heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        
        return resultado;
        
    }
    
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView=(MenuPrincipalController)cambiarEscena("KINALexpres.fxml",1507,841);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void menuClientessView(){
        try{
            MenuClientessController menuClientessView=(MenuClientessController)cambiarEscena("MenuClientes.fxml",1507,841);
            menuClientessView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuAdministracionView(){
        try{
            MenuAdministracionController menuAdministracionController=(MenuAdministracionController)cambiarEscena("MenuAdministracion.fxml",1507,841);
            menuAdministracionController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }    
    
    public void menuTipoProductoView(){
        try{
            MenuTipoProductoController menuTipoProductoController=(MenuTipoProductoController)cambiarEscena("MenuTipoProducto.fxml",1507,841);
            menuTipoProductoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }  

    
    public void menuProveedorView(){
        try{
            MenuProveedorController menuProveedorController=(MenuProveedorController)cambiarEscena("MenuProveedor.fxml",1507,841);
            menuProveedorController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }   
    
    public void menuEmailProveedorView(){
        try{
            MenuEmailProveedorController menuEmailProveedorController=(MenuEmailProveedorController)cambiarEscena("MenuEmailProveedor.fxml",1507,841);
            menuEmailProveedorController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }   
        
    public void menuProductoView(){
        try{
            MenuProductoController menuProductoController=(MenuProductoController)cambiarEscena("MenuProducto.fxml",1507,841);
            menuProductoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }   
        
    
    public void menuComprasView(){
        try{
            MenuComprasController menuComprasController=(MenuComprasController)cambiarEscena("MenuCompras.fxml",1507,841);
            menuComprasController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }       
    
    public void menuCargoEmpleadoView(){
        try{
            MenuCargoEmpleadoController menuCargoEmpleadoController=(MenuCargoEmpleadoController)cambiarEscena("MenuCargoEmpleado.fxml",1507,841);
            menuCargoEmpleadoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }       
    
    
    public void menuFacturaView(){
        try{
            MenuFacturaController menuFacturaController=(MenuFacturaController)cambiarEscena("MenuFactura.fxml",1507,841);
            menuFacturaController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }        
    
    
    public void menuDetalleFacturaView(){
        try{
            MenuDetalleFacturaController menuDetalleFacturaController=(MenuDetalleFacturaController)cambiarEscena("MenuDetalleFactura.fxml",1507,841);
            menuDetalleFacturaController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }       
    
    
    public void menuDetalleCompraView(){
        try{
            MenuDetalleCompraController menuDetalleCompraController=(MenuDetalleCompraController)cambiarEscena("MenuDetalleCompra.fxml",1507,841);
            menuDetalleCompraController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    } 

    
    public void menuEmpleadoView(){
        try{
            MenuEmpleadoController menuEmpleadoController=(MenuEmpleadoController)cambiarEscena("MenuEmpleado.fxml",1507,841);
            menuEmpleadoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }       

    
    public void datePickerView(){
        try{
            DatePickerController datePickerController=(DatePickerController)cambiarEscena("DatePicker.fxml",1507,841);
            datePickerController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }          
    
    
    public void programadorView(){
        try{
            ProgramadoController programadoController=(ProgramadoController)cambiarEscena("Programado.fxml",1507,841);
            programadoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }    
     
}
