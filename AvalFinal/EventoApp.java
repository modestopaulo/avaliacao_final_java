import java.sql.*;
import java.util.Scanner;

public class EventoApp {

    private static final String URL = "jdbc:mysql://localhost:3306/evento";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexão com o banco de dados estabelecida.");
            Scanner scanner = new Scanner(System.in);

            int opcao;
            do {
                exibirMenu();
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1 -> inserirPessoa(conn, scanner);
                    case 2 -> listarPessoas(conn);
                    case 3 -> deletarPessoa(conn, scanner);
                    case 4 -> inserirOrganizador(conn, scanner);
                    case 5 -> listarOrganizadores(conn);
                    case 6 -> deletarOrganizador(conn, scanner);
                    case 7 -> inserirLocal(conn, scanner);
                    case 8 -> listarLocais(conn);
                    case 9 -> deletarLocal(conn, scanner);
                    case 10 -> inserirEvento(conn, scanner);
                    case 11 -> listarEventos(conn);
                    case 12 -> alterarEvento(conn, scanner);
                    case 13 -> deletarEvento(conn, scanner);
                    case 14 -> enviarNotificacao(scanner);
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } while (opcao != 0);

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private static void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("1 - Inserir Pessoa");
        System.out.println("2 - Listar Pessoas");
        System.out.println("3 - Deletar Pessoas");
        System.out.println("4 - Inserir Organizador");
        System.out.println("5 - Listar Organizadores");
        System.out.println("6 - Deletar Organizadores");
        System.out.println("7 - Inserir Local");
        System.out.println("8 - Listar Locais");
        System.out.println("9 - Deletar Locais");
        System.out.println("10- Inserir Evento");
        System.out.println("11- Listar Eventos");
        System.out.println("12- Editar Evento");
        System.out.println("13- Remover Evento");
        System.out.println("14- Notificar");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void inserirPessoa(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID da Pessoa: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Nome da Pessoa: ");
        String nome = scanner.nextLine();

        String sql = "INSERT INTO Pessoa (id, nome) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, nome);
            stmt.executeUpdate();
            System.out.println("Pessoa inserida com sucesso.");
        }
    }

    private static void listarPessoas(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Pessoa";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nPessoas:");
            while (rs.next()) {
                System.out.printf("ID: %d, Nome: %s%n", rs.getInt("id"), rs.getString("nome"));
            }
        }
    }

    private static void deletarPessoa(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID da pessoa para deletar: ");
        int id = scanner.nextInt();
        
        String sql = "DELETE FROM Pessoa WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
        System.out.println("Pessoa deletada com sucesso!");
        } else {
        System.out.println("Pessoa não encontrado.");
        }
        }
        }

    private static void inserirOrganizador(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID do Organizador (deve ser igual ao ID da Pessoa): ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Email do Organizador: ");
        String email = scanner.nextLine();

        String sql = "INSERT INTO Organizador (id, email) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("Organizador inserido com sucesso.");
        }
    }

    private static void listarOrganizadores(Connection conn) throws SQLException {
        String sql = "SELECT o.id, p.nome, o.email FROM Organizador o JOIN Pessoa p ON o.id = p.id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nOrganizadores:");
            while (rs.next()) {
                System.out.printf("ID: %d, Nome: %s, Email: %s%n", rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
            }
        }
    }

    private static void deletarOrganizador(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID do Organizador para deletar: ");
        int id = scanner.nextInt();
        
        String sql = "DELETE FROM Organizador WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
        System.out.println("Organizador deletado com sucesso!");
        } else {
        System.out.println("Organizador não encontrado.");
        }
        }
        }

    private static void inserirLocal(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID do Local: ");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Descrição do Local: ");
        String descricao = scanner.nextLine();
        System.out.print("Número de Vagas: "); 
        int vagas = scanner.nextInt(); 
    
        String sql = "INSERT INTO Local (id, descricao, vagas) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, descricao);
            stmt.setInt(3, vagas);
            System.out.println("Local inserido com sucesso.");
        }
    }    

    private static void inserirEvento(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID do Evento: ");
        int id = scanner.nextInt();
        System.out.print("ID do Organizador: ");
        int idOrganizador = scanner.nextInt();
        System.out.print("ID do Local: ");
        int idLocal = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Data (YYYY-MM-DD HH:MM:SS): ");
        String data = scanner.nextLine();
        System.out.print("Descrição do Evento: ");
        String descricao = scanner.nextLine();
    
        String verificaLocalSql = "SELECT 1 FROM Local WHERE id = ?";
        try (PreparedStatement stmtVerificaLocal = conn.prepareStatement(verificaLocalSql)) {
            stmtVerificaLocal.setInt(1, idLocal);
            try (ResultSet rs = stmtVerificaLocal.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("ID do Local não encontrado. Insira um Local válido primeiro.");
                    return;
                }
            }
        }
    
        String sql = "INSERT INTO Evento (id, idOrganizador, idLocal, data, descricao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2, idOrganizador);
            stmt.setInt(3, idLocal);
            stmt.setString(4, data);
            stmt.setString(5, descricao);
            stmt.executeUpdate();
            System.out.println("Evento inserido com sucesso.");
        }
    }
    
     private static void alterarEvento(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Digite o ID do evento a ser alterado: ");
        int idEvento = scanner.nextInt();
        scanner.nextLine(); 

        // Verifica se o evento existe
        String sqlVerifica = "SELECT * FROM Evento WHERE id = ?";
        try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica)) {
            stmtVerifica.setInt(1, idEvento);
            try (ResultSet rs = stmtVerifica.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Evento não encontrado.");
                    return;
                }
            }
        }


        System.out.print("Novo ID do Organizador (deixe vazio para não alterar): ");
        int novoIdOrganizador = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Novo ID do Local (deixe vazio para não alterar): ");
        int novoIdLocal = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nova Data (YYYY-MM-DD HH:MM:SS) ou deixe vazio para não alterar: ");
        String novaData = scanner.nextLine();
        System.out.print("Nova Descrição ou deixe vazio para não alterar: ");
        String novaDescricao = scanner.nextLine();

        StringBuilder sqlUpdate = new StringBuilder("UPDATE Evento SET ");
        boolean primeiro = true;

        if (novoIdOrganizador > 0) {
            if (!primeiro) sqlUpdate.append(", ");
            sqlUpdate.append("idOrganizador = ?");
            primeiro = false;
        }

        if (novoIdLocal > 0) {
            if (!primeiro) sqlUpdate.append(", ");
            sqlUpdate.append("idLocal = ?");
            primeiro = false;
        }

        if (!novaData.isEmpty()) {
            if (!primeiro) sqlUpdate.append(", ");
            sqlUpdate.append("data = ?");
            primeiro = false;
        }

        if (!novaDescricao.isEmpty()) {
            if (!primeiro) sqlUpdate.append(", ");
            sqlUpdate.append("descricao = ?");
        }

        sqlUpdate.append(" WHERE id = ?");
        
        try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate.toString())) {
            int paramIndex = 1;

            if (novoIdOrganizador > 0) stmtUpdate.setInt(paramIndex++, novoIdOrganizador);
            if (novoIdLocal > 0) stmtUpdate.setInt(paramIndex++, novoIdLocal);
            if (!novaData.isEmpty()) stmtUpdate.setString(paramIndex++, novaData);
            if (!novaDescricao.isEmpty()) stmtUpdate.setString(paramIndex++, novaDescricao);
            stmtUpdate.setInt(paramIndex, idEvento);

            stmtUpdate.executeUpdate();
            System.out.println("Evento alterado com sucesso.");
        }
    }

    private static void listarEventos(Connection conn) throws SQLException {
        String sql = """
                SELECT e.id, p.nome AS organizador, l.descricao AS local, e.data, e.descricao
                FROM Evento e
                JOIN Organizador o ON e.idOrganizador = o.id
                JOIN Pessoa p ON o.id = p.id
                JOIN Local l ON e.idLocal = l.id
                """;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nEventos:");
            while (rs.next()) {
                System.out.printf("ID: %d, Organizador: %s, Local: %s, Data: %s, Descrição: %s%n",
                        rs.getInt("id"), rs.getString("organizador"),
                        rs.getString("local"), rs.getString("data"),
                        rs.getString("descricao"));
            }
        }
    }

    private static void listarLocais(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Local";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nLocais:");
            while (rs.next()) {
                System.out.printf("ID: %d, Descrição: %s, Vagas: %d%n",
                        rs.getInt("id"), rs.getString("descricao"), rs.getInt("vagas"));
            }
        }
    }

    private static void deletarLocal(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID do local para deletar: ");
        int id = scanner.nextInt();
        
        String sql = "DELETE FROM Local WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
        System.out.println("Local deletado com sucesso!");
        } else {
        System.out.println("Local não encontrado.");
        }
        }
        }

    private static void deletarEvento(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID do Evento para deletar: ");
        int id = scanner.nextInt();
        
        String sql = "DELETE FROM Evento WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
        System.out.println("Evento deletado com sucesso!");
        } else {
        System.out.println("Evento não encontrado.");
        }
        }
        }

    private static void enviarNotificacao(Scanner scanner) {
        System.out.println("Escolha como deseja receber a notificação:");
        System.out.println("1 - Notificação por Email");
        System.out.println("2 - Notificação por Telefone");
        System.out.println("3 - Ambas as opções");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); 
 
        switch (escolha) {
            case 1:
                System.out.print("Digite seu e-mail: ");
                String email = scanner.nextLine();
                System.out.println("Notificação enviada para o e-mail: " + email);
                break;
 
            case 2:
                System.out.print("Digite seu número de telefone: ");
                String telefone = scanner.nextLine();
                System.out.println("Notificação enviada para o telefone: " + telefone);
                break;
 
            case 3:
                System.out.print("Digite seu e-mail: ");
                email = scanner.nextLine();
                System.out.print("Digite seu número de telefone: ");
                telefone = scanner.nextLine();
                System.out.println("Notificação enviada para o e-mail: " + email);
                System.out.println("Notificação enviada para o telefone: " + telefone);
                break;
 
            default:
                System.out.println("Opção de notificação inválida.");
        }
}

}