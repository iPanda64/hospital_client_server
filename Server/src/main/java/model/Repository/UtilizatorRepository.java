package model.Repository;
import model.Factura;
import model.UtilizatorType;
import model.Utilizator;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class UtilizatorRepository {
    private Repository repository;
    public UtilizatorRepository(Repository repository) {
        this.repository = repository;
    }
    public Utilizator findByUsername(String username) {
        String sql="SELECT * FROM utilizator WHERE username = ?";
        PreparedStatement statement=null;
        ResultSet rs=null;
        Utilizator utilizator=null;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            rs = statement.executeQuery();
            if (rs.next()){
                return mapResultSetToUtilizator(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(rs);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return null;
    }
    public void salvare(Utilizator u) {
        String sql = "INSERT INTO utilizator (nume, prenume, email, numar_telefon, data_nasterii, username, parola, tip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, u.getNume());
            statement.setString(2, u.getPrenume());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getTelefon());
            statement.setString(5, u.getDataNastere().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")));
            statement.setString(6, u.getUsername());
            statement.setString(7, u.getParola());
            statement.setString(8, u.getTip().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
    }
    public boolean update(Utilizator u) {
        String sql = "UPDATE utilizator SET nume = ?, prenume = ?, email = ?, numar_telefon = ?, " +
                "data_nasterii = ?, username = ?, parola = ?, tip = ? WHERE id = ?";
        PreparedStatement statement = null;
        boolean success = false;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, u.getNume());
            statement.setString(2, u.getPrenume());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getTelefon());
            statement.setString(5, u.getDataNastere().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")));
            statement.setString(6, u.getUsername());
            statement.setString(7, u.getParola());
            statement.setString(8, u.getTip().name());
            statement.setInt(9, u.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated>0) {
                success=true;
            } else {
                success=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return success;
    }
    public boolean delete(int id) {
        String sql = "DELETE FROM utilizator WHERE id = ?";
        PreparedStatement statement = null;
        boolean success = false;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted>0) {
                success=true;
            } else {
                success=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return success;
    }

    public Utilizator findById(int id) {
        String sql = "SELECT * FROM utilizator WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet rs = null;
        Utilizator utilizator = null;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                return mapResultSetToUtilizator(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(rs);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return null;
    }
    public Utilizator SearchUtilizatorByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM utilizator WHERE username = ? AND parola = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);

            resultSet = statement.executeQuery();

            if (resultSet .next()) {
                return mapResultSetToUtilizator(resultSet );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }

        return null;
    }
    public List<Utilizator> SearchAllUtilizator() {
        List<Utilizator> utilizatori = new ArrayList<>();

        String sql = "SELECT * FROM utilizator";
        Statement statement = null;
        ResultSet rs = null;

        try {

            Connection connection = repository.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                utilizatori.add(mapResultSetToUtilizator(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(rs);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return utilizatori;
    }
    public List<Utilizator> SearchAllPacients() {
        List<Utilizator> pacienti = new ArrayList<>();

        String sql = "SELECT * FROM utilizator WHERE tip = 'pacient'";
        Statement statement = null;
        ResultSet rs = null;

        try {

            Connection connection = repository.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                pacienti.add(mapResultSetToUtilizator(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(rs);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return pacienti;
    }
    private Utilizator mapResultSetToUtilizator(ResultSet rs) throws SQLException {
        return new Utilizator(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("username"),
                rs.getString("parola"),
                rs.getString("email"),
                rs.getString("numar_telefon"),
                java.time.LocalDate.parse(rs.getString("data_nasterii"), java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd")),
                UtilizatorType.valueOf(rs.getString("tip"))
        );
    }

}
