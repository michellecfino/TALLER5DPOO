package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class PedidoTest {
    private Pedido pedido;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(0,"Patricia Fernandez", "Su casita");
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
    void getId() {
    	assertEquals(0,pedido.getIdPedido(), "El ID no corresponde con el pedido.");
    }
    
    @Test
    void testCrearPedido() {
        assertEquals("Patricia Fernandez", pedido.getNombreCliente(), "El nombre del cliente no es el esperado.");
        assertEquals(0, pedido.getIdPedido(), "El ID del pedido no es el esperado.");
    }

    @Test
    void testAgregarProducto() {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        
        //   100*1.19=100+100*0.19 
        assertEquals(15000*1.19, pedido.getPrecioTotalPedido(), "El precio total del pedido no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() {
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
    void testGuardarFactura() {
        File archivo = new File("factura_test.txt");
        
        if (archivo.exists()) {
            archivo.delete();
        }

        try {
            pedido.agregarProducto(producto1);
            pedido.agregarProducto(producto2);
            pedido.guardarFactura(archivo);
            
            assertTrue(archivo.exists(), "El archivo de la factura no fue creado.");

            String contenidoEsperado = pedido.generarTextoFactura();
            String contenidoLeido = new String(Files.readAllBytes(archivo.toPath()));
            assertEquals(contenidoEsperado, contenidoLeido, "El contenido del archivo no es el esperado.");

        } catch (FileNotFoundException e) {
            fail("No se pudo guardar la factura: " + e.getMessage());
        } catch (IOException e) {
            fail("Error al leer el archivo: " + e.getMessage());
        } finally {
            if (archivo.exists()) {
                archivo.delete();
            }
        }
    }

}
