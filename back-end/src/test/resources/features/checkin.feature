# language: pt
Funcionalidade: Realizar Check-in de Usuário

  Como um participante logado
  Quero fazer check-in em um evento
  Para confirmar minha presença

  Cenário: Realizar check-in com sucesso
    Dado que eu tenho um usuário válido com ID 1
    E eu informo o evento de ID 10
    Quando eu envio a requisição de check-in
    Então o sistema deve retornar o status 200 (OK)
    E a resposta deve conter a inscrição realizada

  Cenário: Tentar realizar check-in com evento vazio (Regra de Negócio/Validação)
    Dado que eu tenho um usuário válido com ID 1
    E eu não informo o ID do evento
    Quando eu envio a requisição de check-in
    Então o sistema deve retornar o status 400 (Bad Request)