# language: pt
@hamburgueria
Funcionalidade: Pedidos na hamburgueria Peppa Lanches
  Para realizar pedidos corretos
  Como cliente
  Eu quero saber se o item pode ser pedido, o valor total e o tempo estimado

  Contexto:
    Dado que o cardápio contém os itens:
      | item         | preco |
      | x-bacon      | 25.00 |
      | x-salada     | 22.00 |
      | batata frita | 12.00 |

  @feliz
  Cenário: Pedido simples de item existente
    Quando eu faço um pedido de "x-bacon" com quantidade 2
    Então o total do pedido deve ser 50.00
    E o tempo estimado de preparo deve ser 12 minutos

  @inexistente
  Cenário: Pedido de item inexistente
    Quando eu faço um pedido de "x-tudo" com quantidade 1
    Então o sistema deve informar erro de item indisponível

  @quantidade
  Cenário: Pedido com quantidade inválida
    Quando eu faço um pedido de "x-bacon" com quantidade 0
    Então o sistema deve informar erro de quantidade inválida

  @desconto
  Cenário: Pedido com desconto de 10 por cento
    Quando eu faço um pedido de "x-salada" com quantidade 2 e desconto de 10 por cento
    Então o total do pedido com desconto deve ser 39.60

  @sla
  Cenário: Calcular tempo estimado de preparo
    Quando eu informo a quantidade total de 5 itens
    Então o tempo estimado de preparo deve ser 18 minutos
