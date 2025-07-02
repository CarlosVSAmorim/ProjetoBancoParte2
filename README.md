# Sistema de Gerenciamento Bancário em Java

Uma aplicação desktop desenvolvida em Java com interface gráfica (Swing) para simular as operações básicas de um sistema bancário, como gerenciamento de contas, saques, depósitos e transferências. Os dados são persistidos em um banco de dados relacional SQLite.

## Screenshots

<table>
  <tr>
    <td align="center"><strong>Tela Principal</strong></td>
    <td align="center"><strong>Tela de Cadastro/Edição</strong></td>
    <td align="center"><strong>Tela de Operações e Extrato</strong></td>
  </tr>
  <tr>
    <td>
      <!-- SUBSTITUA PELA URL DA SUA IMAGEM -->
      <img src="[https://via.placeholder.com/400x300.png?text=Tela+Principal+com+Lista+de+Contas](https://i.imgur.com/0U4BqK0.png)" alt="Tela Principal com a lista de contas correntes" width="100%">
    </td>
    <td>
      <!-- SUBSTITUA PELA URL DA SUA IMAGEM -->
      <img src="[https://via.placeholder.com/400x300.png?text=Tela+de+Cadastro+de+Conta](https://i.imgur.com/On4zhii.png)" alt="Janela modal para cadastrar uma conta" width="100%">
    </td>
    <td>
      <!-- SUBSTITUA PELA URL DA SUA IMAGEM -->
      <img src="[https://via.placeholder.com/400x300.png?text=Tela+de+Operações+e+Extrato](https://i.imgur.com/sfxaww9.png)" alt="Tela com saldo detalhado, botões de operação e extrato" width="100%">
    </td>
  </tr>
</table>

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

## Como Executar o Projeto

Siga os passos abaixo para compilar e executar a aplicação localmente.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
    cd SEU-REPOSITORIO
    ```
2.  **Baixe o Driver JDBC:**
    - Faça o download do arquivo `.jar` do SQLite JDBC na página de releases oficial.
    - Crie uma pasta chamada `lib` na raiz do projeto e coloque o arquivo `.jar` dentro dela.

3.  **Compile o projeto (via terminal):**
    > Nota: Certifique-se de substituir `sqlite-jdbc-VERSION.jar` pelo nome exato do arquivo que você baixou.

    *No Windows:*
    ```bash
    javac -cp ".;lib\sqlite-jdbc-VERSION.jar" src\*.java src\model\*.java src\view\*.java src\dao\*.java src\util\*.java src\controller\*.java
    ```
4.  **Execute a aplicação (via terminal):**
    > Ao executar pela primeira vez, um arquivo `banco.db` será criado na raiz do projeto.

    *No Windows:*
    ```bash
    java -cp ".;src;lib\sqlite-jdbc-VERSION.jar" Main
    ```

## Estrutura do Projeto

O projeto segue o padrão de arquitetura **Model-View-Controller (MVC)**, com uma camada adicional **DAO (Data Access Object)** para abstrair o acesso ao banco de dados.


/
├── lib/
│ └── sqlite-jdbc-VERSION.jar # Driver de conexão com o banco
├── src/
│ ├── controller/
│ │ └── ContaController.java # Controla a lógica entre View e Model
│ ├── dao/
│ │ ├── ContaDAO.java # Objeto de acesso a dados para Contas
│ │ └── MovimentacaoDAO.java # Objeto de acesso a dados para Movimentações
│ ├── model/
│ │ ├── Conta.java # Classe base abstrata
│ │ └── ... # Demais classes de negócio
│ ├── util/
│ │ └── ConnectionFactory.java # Gerencia a conexão com o banco
│ ├── view/
│ │ ├── TelaPrincipal.java # A interface gráfica principal
│ │ └── ... # Demais componentes da UI
│ └── Main.java # Ponto de entrada da aplicação
└── banco.db # Arquivo do banco de dados (criado na execução)
