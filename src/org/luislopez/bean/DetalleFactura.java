/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.bean;

/**
 *
 * @author HP
 */
public class DetalleFactura {
    
    private int codigoDetalleFactura;
    private double precioUnitario;
    private int cantidad;
    private int numeroDeFactura; 
    private int codigoProducto; 
        
    public DetalleFactura() {
    }  

    public DetalleFactura(int codigoDetalleFactura, double precioUnitario, int cantidad, int numeroDeFactura, int codigoProducto) {
        this.codigoDetalleFactura = codigoDetalleFactura;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.numeroDeFactura = numeroDeFactura;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoDetalleFactura() {
        return codigoDetalleFactura;
    }

    public void setCodigoDetalleFactura(int codigoDetalleFactura) {
        this.codigoDetalleFactura = codigoDetalleFactura;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(int numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    
    
    
}
