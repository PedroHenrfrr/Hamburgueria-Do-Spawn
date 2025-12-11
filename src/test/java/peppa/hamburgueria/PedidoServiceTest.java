package peppa.hamburgueria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    @Test
    void deveCalcularValorTotalDoPedido() {
        // 1. PREPARAÇÃO
        // Primeiro precisamos de um cardápio com itens
        CardapioService cardapio = new CardapioService();
        cardapio.cadastrarItem("X-Tudo", 20.00);

        // Criamos o serviço de pedido passando o cardápio que criamos
        PedidoService pedidoService = new PedidoService(cardapio);

        // 2. AÇÃO
        // Pedimos 3 X-Tudos (3 * 20.00 = 60.00)
        double total = pedidoService.calcularTotal("X-Tudo", 3);

        // 3. VERIFICAÇÃO
        assertEquals(60.00, total, "O cálculo total (3x20) está errado.");
        assertEquals(60.00, pedidoService.getUltimoTotal(), "O valor não ficou salvo no histórico.");
    }

    @Test
    void deveBloquearPedidoDeItemInexistente() {
        CardapioService cardapio = new CardapioService();
        PedidoService pedidoService = new PedidoService(cardapio);

        // Aqui usamos o assertThrows para garantir que o erro acontece!
        // Estamos dizendo: "Espero que dê um erro ao pedir Pizza"
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.calcularTotal("Pizza", 1);
        }, "Deveria ter bloqueado um item que não existe no cardápio");
    }

    @Test
    void deveCalcularTempoEstimadoDeEntrega() {
        // Não precisamos de cardápio para calcular tempo, podemos passar null ou um vazio
        PedidoService pedidoService = new PedidoService(new CardapioService());

        // Fórmula: 8 + (2 * quantidade)
        // Se eu pedir 5 lanches: 8 + (2 * 5) = 18 minutos
        int tempo = pedidoService.calcularTempoEstimado(5);

        assertEquals(18, tempo, "O cálculo do tempo de entrega está incorreto.");
    }
}