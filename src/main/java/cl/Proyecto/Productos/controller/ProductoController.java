package cl.Proyecto.Productos.controller;

import cl.Proyecto.Productos.dto.ProductoRequestDTO;
import cl.Proyecto.Productos.dto.ProductoResponseDTO;
import cl.Proyecto.Productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/descontar-stock")
    public ResponseEntity<ProductoResponseDTO> descontarStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {
        ProductoResponseDTO actual = productoService.obtenerPorId(id);
        int descontar = (cantidad != null && cantidad > 0) ? cantidad : 0;
        int nuevoStock = Math.max(0, (actual.getStock() != null ? actual.getStock() : 0) - descontar);

        ProductoRequestDTO updateDto = new ProductoRequestDTO();
        updateDto.setNombre(actual.getNombre());
        updateDto.setDescripcion(actual.getDescripcion());
        updateDto.setPrecio(actual.getPrecio());
        updateDto.setStock(nuevoStock);
        updateDto.setCategoria(actual.getCategoria());
        updateDto.setMarca(actual.getMarca());
        updateDto.setModelo(actual.getModelo());
        updateDto.setImagenUrl(actual.getImagenUrl());

        return ResponseEntity.ok(productoService.actualizar(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}