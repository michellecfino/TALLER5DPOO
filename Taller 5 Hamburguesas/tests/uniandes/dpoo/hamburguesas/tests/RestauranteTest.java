package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.*;
import uniandes.dpoo.hamburguesas.mundo.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestauranteTest {
    private Restaurante restaurante;
    private ProductoMenu producto1;
    private ProductoMenu producto2;
    private Ingrediente ingrediente1;
    private File ingredientesFile;
    private File menuFile;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante();
        producto1 = new ProductoMenu("hamburguesa", 10000);
        producto2 = new ProductoMenu("papas fritas", 5000);
        ingrediente1 = new Ingrediente("lechuga", 200);

        ingredientesFile = new File("ingredientes.txt");
        menuFile = new File("menu.txt");
        
    }

    @AfterEach
    void tearDown() {
        restaurante = null;
        producto1 = null;
        producto2 = null;
        ingrediente1 = null;

        ingredientesFile.delete();
        menuFile.delete();
    }
    @Test
    void testGetPedidosInicial() {
        ArrayList<Pedido> pedidos = restaurante.getPedidos();
        assertTrue(pedidos.isEmpty(), "Debería ser 0.");
    }
    
    
    @Test
    void testGetMenuBase() {
        ProductoMenu p1 = new ProductoMenu("Hamburguesa", 5000);
        ProductoMenu p2 = new ProductoMenu("mexicana", 3000);

        restaurante.getMenuBase().add(p1);
        restaurante.getMenuBase().add(p2);

        ArrayList<ProductoMenu> menuBase = restaurante.getMenuBase();

        assertEquals(2, menuBase.size(), "El menú base debería contener 2 productos.");
        assertTrue(menuBase.contains(p1), "El menú base debería contener la hamburguesa.");
        assertTrue(menuBase.contains(p2), "El menú base debería contener la mexicana.");
    }
    
    @Test
    void testGetMenuCombos() {
        Restaurante restaurante = new Restaurante();

        ProductoMenu producto1 = new ProductoMenu("corral", 14000);
        ProductoMenu producto2 = new ProductoMenu("papas medianas", 5500);
        ProductoMenu producto3 = new ProductoMenu("gaseosa", 5000);
        
        restaurante.getMenuBase().addAll(Arrays.asList(producto1, producto2, producto3));

        Combo combo = new Combo("combo corral", 0.10, new ArrayList<>(Arrays.asList(producto1, producto2, producto3)));
        restaurante.getMenuCombos().add(combo);

        ArrayList<Combo> menuCombos = restaurante.getMenuCombos();

        assertEquals(1, menuCombos.size(), "El menú de combos debería contener 1 combo.");
    }
    
    @Test
    void testGetIngredientes() {
        Restaurante restaurante = new Restaurante();

        
        Ingrediente ing1 = new Ingrediente("Lechuga", 500);
        Ingrediente ing2 = new Ingrediente("Tomate", 700);
        Ingrediente ing3 = new Ingrediente("Cebolla", 300);

        restaurante.getIngredientes().add(ing1);
        restaurante.getIngredientes().add(ing2);
        restaurante.getIngredientes().add(ing3);

        ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();

        assertEquals(3, ingredientes.size(), "La lista de ingredientes debería contener 3 ingredientes.");
        assertEquals("Lechuga", ingredientes.get(0).getNombre(), "El primer ingrediente debe ser 'Lechuga'.");
        assertEquals("Tomate", ingredientes.get(1).getNombre(), "El segundo ingrediente debe ser 'Tomate'.");
        assertEquals("Cebolla", ingredientes.get(2).getNombre(), "El tercer ingrediente debe ser 'Cebolla'.");
    }
    

    @Test
    void testCerrarYGuardarPedidoExcepcionNoHayPedidoEnCurso() {
        assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
    }

    



    @Test
    void testIniciarPedidoSinPedidoEnCurso() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Patricia", "Su casa");
        assertNotNull(restaurante.getPedidoEnCurso(), "El pedido en curso no debería ser null.");
    }

    @Test
    void testIniciarPedidoConPedidoEnCurso() throws YaHayUnPedidoEnCursoException {
        restaurante.iniciarPedido("Patricia", "Su casa"); 
        Exception exception = assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Juan", "Su oficina");
        });

        String esperado = "Ya existe un pedido en curso, para el cliente Patricia así que no se puede crear un pedido para Juan"; // Ajusta el mensaje esperado si es necesario
        String actual = exception.getMessage();
        
        System.out.println(esperado);
        System.out.println(actual);

        assertTrue(actual.contains(esperado), "Se esperaba un mensaje de error específico.");
    }
   
    @Test
    void testCargarIngredientesYMenuExito() throws Exception {
        File archivoIngredientes = File.createTempFile("ingredientes", ".txt");
        File archivoMenu = File.createTempFile("menu", ".txt");
        File archivoCombos = File.createTempFile("combos", ".txt");

        try (FileWriter writerIngredientes = new FileWriter(archivoIngredientes);
             FileWriter writerMenu = new FileWriter(archivoMenu);
             FileWriter writerCombos = new FileWriter(archivoCombos)) {
             
            writerIngredientes.write("Tomate;5000\nLechuga;3000\nQueso;2000\n");
            writerMenu.write("Hamburguesa;14000\nPapas;5000\nBebida;2000\n");
            writerCombos.write("Combo1;10%;Hamburguesa;Papas\n");
        }

        restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);
        
        assertEquals(3, restaurante.getIngredientes().size());
        assertEquals("Tomate", restaurante.getIngredientes().get(0).getNombre().trim());
        assertEquals("Lechuga", restaurante.getIngredientes().get(1).getNombre().trim());
        assertEquals("Queso", restaurante.getIngredientes().get(2).getNombre().trim());
        
        assertEquals(3, restaurante.getMenuBase().size());
        assertEquals("Hamburguesa", restaurante.getMenuBase().get(0).getNombre().trim());
        assertEquals("Papas", restaurante.getMenuBase().get(1).getNombre().trim());
        assertEquals("Bebida", restaurante.getMenuBase().get(2).getNombre().trim());
        
        // Validar que los combos se carguen correctamente
        assertEquals(1, restaurante.getMenuCombos().size());
        assertEquals("Combo1", restaurante.getMenuCombos().get(0).getNombre());
        
        archivoIngredientes.deleteOnExit();
        archivoMenu.deleteOnExit();
        archivoCombos.deleteOnExit();
    }


    @Test
    void testCargarIngredientesExcepcionRepetido() throws IOException {
        File archivoIngredientes = File.createTempFile("ingredientes", ".txt");
        try (FileWriter writer = new FileWriter(archivoIngredientes)) {
            writer.write("Tomate;5000\nTomate;5000\n");
        }

        assertThrows(IngredienteRepetidoException.class, () -> {
            restaurante.cargarInformacionRestaurante(archivoIngredientes, null, null);
        });

        archivoIngredientes.deleteOnExit();
    }



}
