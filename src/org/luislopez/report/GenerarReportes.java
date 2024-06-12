/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.luislopez.db.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte,String tiutlo,Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        
        try{
            
            JasperReport ReporteClientes2 =(JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(ReporteClientes2,parametros,Conexion.getInstance().getConexion());
            JasperViewer visor =new JasperViewer(reporteImpreso,false);
            visor.setTitle(tiutlo);
            visor.setVisible(true);
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    
    
}


/*
Interface Map 
HasMap es uno de los objetos que implementa un conjunto de key-value.
Tiene un constructor sin parametros new HashMap () y su suele referirse para agrupa
informacion en un unico objeto


Tiene cierta simulitud con la coleccion de objetos (ArrayList) pero con la diferencia
que estos no tiene orden


Hash hace referencia a una tecnica de organizacion de archivos hashing(abierto-cerrado) en la
se almacena registro en una direccion que es generada por una funcion se aplica a la llava de 


En java el HasMap posee un espacio de memoria y cuando se guarda un objeto 
*/
