package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PedidoTest {
    private Pedido pedido;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(0, "Patricia Fernandez", "Su casita");
        producto1 = new ProductoMenu("hamburguesa", 10000);
        producto2 = new ProductoMenu("papas fritas", 5000);
    }

    @AfterEach
    void tearDown() {
        pedido = null;
        producto1 = null;
        producto2 = null;
    }

    @Test
    void testCrearPedido() {
        assertEquals("Patricia Fernandez", pedido.getNombreCliente(), "El nombre del cliente no es el esperado.");
        assertEquals(0, pedido.getIdPedido(), "El ID del pedido no es el esperado.");
    }

    @Test
    void testAgregarProducto() throws ProductoRepetidoException, ProductoFaltanteException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);

        assertEquals(15000 * 1.19, pedido.getPrecioTotalPedido(), "El precio total del pedido no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() throws ProductoRepetidoException, ProductoFaltanteException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);

        String esperado = 
            "Cliente: Patricia Fernandez\n" +
            "Dirección: Su casita\n" +
            "----------------\n" +
            "hamburguesa\n            10000\n" +  
            "papas fritas\n            5000\n" +  
            "----------------\n" +
            "Precio Neto:  15000\n" +
            "IVA:          2850\n" +  
            "Precio Total: 17850\n";

        assertEquals(esperado, pedido.generarTextoFactura(), "El texto de la factura no es el esperado.");
    }

    @Test
    void testAgregarProductoRepetido() throws ProductoFaltanteException, ProductoRepetidoException {
        pedido.agregarProducto(producto1);
        
        Exception exception = assertThrows(ProductoRepetidoException.class, () -> {
            pedido.agregarProducto(producto1); 
        });

        String expectedMessage = "hamburguesa";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Se esperaba un mensaje de error específico.");
    }

    @Test
    void testGuardarFactura() throws ProductoRepetidoException, ProductoFaltanteException, IOException {
        File archivo = new File("factura_test.txt");
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        
        pedido.guardarFactura(archivo);
        
        assertTrue(archivo.exists(), "El archivo de la factura no fue creado.");
        
        String esperado = pedido.generarTextoFactura();
        String contenidoLeido = new String(Files.readAllBytes(archivo.toPath()));
        assertEquals(esperado, contenidoLeido, "El contenido del archivo no es el esperado.");
        
        archivo.delete();
    }

    @Test
    void testNoGuardarFacturaConArchivoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            pedido.guardarFactura(null); // Intentar guardar la factura con un archivo nulo
        });

        String expectedMessage = "El archivo no puede ser null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage), "Se esperaba un mensaje de error específico.");
    }
}
