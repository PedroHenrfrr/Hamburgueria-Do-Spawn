package peppa.hamburgueria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardapioServiceTest {

    @Test
    void deveCadastrarEConsultarUmHamburguer() {
        
        CardapioService cardapio = new CardapioService();
        String nomeDoLanche = "X-Bacon";
        double precoDoLanche = 25.50;

        cardapio.cadastrarItem(nomeDoLanche, precoDoLanche);

        assertTrue(cardapio.existe(nomeDoLanche), "O X-Bacon deveria existir no cardápio!");
        
        assertEquals(25.50, cardapio.precoDe(nomeDoLanche), "O preço do X-Bacon está errado!");
        
        assertEquals(0.0, cardapio.precoDe("X-Inexistente"), "Item não cadastrado deve custar 0.0");
    }
}