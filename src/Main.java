import javax.swing.SwingUtilities;
import controller.ContaController;
import dao.ContaDAO;
import util.ConnectionFactory;
import view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
        // Inicializa o banco de dados e cria as tabelas se não existirem
        ConnectionFactory.createTables();

        // Inicia a aplicação na thread de eventos da GUI
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal view = new TelaPrincipal();
            ContaDAO dao = new ContaDAO();
            new ContaController(view, dao); // O controller liga a view e o dao
            view.setVisible(true);
        });
    }
}