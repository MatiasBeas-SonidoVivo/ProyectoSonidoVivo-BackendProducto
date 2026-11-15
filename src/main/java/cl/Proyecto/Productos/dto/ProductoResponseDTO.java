package cl.Proyecto.Productos.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String categoria;
    private String marca;
    private String modelo;
    private String imagenUrl;
    private Boolean activo;
}