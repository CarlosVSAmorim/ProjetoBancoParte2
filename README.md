# Sistema de Gerenciamento Bancário em Java

Uma aplicação desktop desenvolvida em Java com interface gráfica (Swing) para simular as operações básicas de um sistema bancário, como gerenciamento de contas, saques, depósitos e transferências. Os dados são persistidos em um banco de dados relacional SQLite.

Este projeto foi desenvolvido como atividade avaliativa para a unidade curricular de Desenvolvimento de Sistemas Orientados a Objetos do curso de Ciência da Computação no Instituto Federal de Santa Catarina (IFSC) - Câmpus Lages.

## Funcionalidades

- **Gerenciamento de Contas (CRUD):**
  - Criar novas contas correntes.
  - Listar todas as contas existentes na tela principal.
  - Editar informações de uma conta (saldo e limite).
  - Excluir contas e todas as suas movimentações associadas.
- **Operações Financeiras:**
  - **Depósito:** Adicionar créditos ao saldo da conta.
  - **Saque:** Debitar valores da conta, utilizando o saldo e o limite (cheque especial).
  - **Transferência:** Mover fundos entre duas contas cadastradas no sistema.
- **Extrato Bancário:**
  - Visualizar o histórico completo de movimentações (créditos e débitos) de uma conta, ordenado por data.
- **Persistência de Dados:**
  - Todas as contas e movimentações são salvas em um banco de dados **SQLite**, garantindo que os dados não sejam perdidos ao fechar a aplicação.
- **Tratamento de Exceções:**
  - Mensagens de feedback claras para o usuário em caso de erros, como saldo insuficiente, dados inválidos ou falhas de comunicação com o banco de dados.

## Tecnologias Utilizadas

- **Linguagem:** Java (JDK 11+)
- **Interface Gráfica:** Java Swing
- **Banco de Dados:** SQLite
- **Driver de Conexão:** SQLite JDBC Driver
- **Padrão de Arquitetura:** MVC (Model-View-Controller)
