package cl.Proyecto.Productos.service;

import cl.Proyecto.Productos.dto.ProductoRequestDTO;
import cl.Proyecto.Productos.dto.ProductoResponseDTO;
import cl.Proyecto.Productos.exception.ResourceNotFoundException;
import cl.Proyecto.Productos.model.Producto;
import cl.Proyecto.Productos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return toDTO(producto);
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .categoria(dto.getCategoria())
                .marca(dto.getMarca())
                .modelo(dto.getModelo())
                .imagenUrl(dto.getImagenUrl())
                .activo(true)
                .build();

        Producto guardado = productoRepository.save(producto);
        return toDTO(guardado);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setModelo(dto.getModelo());
        producto.setImagenUrl(dto.getImagenUrl());

        Producto actualizado = productoRepository.save(producto);
        return toDTO(actualizado);
    }

    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        productoRepository.delete(producto);
    }

    private ProductoResponseDTO toDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria());
        dto.setMarca(producto.getMarca());
        dto.setModelo(producto.getModelo());
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setActivo(producto.getActivo());
        return dto;
    }
}