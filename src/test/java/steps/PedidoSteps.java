package steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import peppa.hamburgueria.CardapioService;
import peppa.hamburgueria.PedidoService;

public class PedidoSteps {

    private CardapioService cardapio;
    private PedidoService pedidoService;
    private double ultimoTotal;
    private double ultimoTotalComDesconto;
    private int quantidadeInformada;
    private Exception excecaoCapturada;
    private int tempoEstimado;

    @Dado("que o card\u00e1pio cont\u00e9m os itens:")
    public void queOCardapioContemOsItens(DataTable dataTable) {
        cardapio = new CardapioService();
        dataTable.asMaps(String.class, String.class).forEach(row -> {
            String item = row.get("item");
            double preco = Double.parseDouble(row.get("preco"));
            cardapio.cadastrarItem(item, preco);
        });
        pedidoService = new PedidoService(cardapio);
    }

    @Quando("eu fa\u00e7o um pedido de {string} com quantidade {int}")
    public void euFacoUmPedidoDeComQuantidade(String item, Integer quantidade) {
        quantidadeInformada = quantidade;
        try {
            ultimoTotal = pedidoService.calcularTotal(item, quantidade);
            excecaoCapturada = null;
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("eu fa\u00e7o um pedido de {string} com quantidade {int} e desconto de {int} por cento")
    public void euFacoUmPedidoDeComQuantidadeEDescontoDePorCento(String item, Integer quantidade, Integer desconto) {
        quantidadeInformada = quantidade;
        ultimoTotal = pedidoService.calcularTotal(item, quantidade);
        double fatorDesconto = 1 - (desconto / 100.0);
        ultimoTotalComDesconto = Math.round(ultimoTotal * fatorDesconto * 100.0) / 100.0;
    }

    @Quando("eu informo a quantidade total de {int} itens")
    public void euInformoAQuantidadeTotalDeItens(Integer quantidadeTotal) {
        quantidadeInformada = quantidadeTotal;
        tempoEstimado = pedidoService.calcularTempoEstimado(quantidadeTotal);
    }

    @Entao("^o total do pedido deve ser ([0-9]+[\\.,]?[0-9]{0,2})$")
    public void oTotalDoPedidoDeveSer(String totalEsperadoStr) {
        double totalEsperado = parseValor(totalEsperadoStr);
        assertEquals(totalEsperado, ultimoTotal, 0.001);
    }

    @Entao("o sistema deve informar erro de item indispon\u00edvel")
    public void oSistemaDeveInformarErroDeItemIndisponivel() {
        assertNotNull(excecaoCapturada);
        assertEquals("Item indispon\u00edvel no card\u00e1pio", excecaoCapturada.getMessage());
    }

    @Entao("o sistema deve informar erro de quantidade inv\u00e1lida")
    public void oSistemaDeveInformarErroDeQuantidadeInvalida() {
        assertNotNull(excecaoCapturada);
        assertEquals("Quantidade inv\u00e1lida", excecaoCapturada.getMessage());
    }

    @Entao("^o total do pedido com desconto deve ser ([0-9]+[\\.,]?[0-9]{0,2})$")
    public void oTotalDoPedidoComDescontoDeveSer(String totalEsperadoStr) {
        double totalEsperado = parseValor(totalEsperadoStr);
        assertEquals(totalEsperado, ultimoTotalComDesconto, 0.001);
    }

    @Entao("o tempo estimado de preparo deve ser {int} minutos")
    public void oTempoEstimadoDePreparoDeveSer(Integer tempoEsperado) {
        if (tempoEstimado == 0 && pedidoService != null && quantidadeInformada > 0) {
            tempoEstimado = pedidoService.calcularTempoEstimado(quantidadeInformada);
        }
        assertEquals(tempoEsperado.intValue(), tempoEstimado);
    }

    private double parseValor(String valor) {
        return Double.parseDouble(valor.replace(",", "."));
    }
}
