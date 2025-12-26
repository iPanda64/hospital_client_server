package model.Repository;
import model.UtilizatorType;
import model.Utilizator;
import java.sql.*;
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
        String sql = "INSERT INTO utilizator (nume, prenume, mail, telefon, data_nasterii, username, parola, tip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, u.getNume());
            statement.setString(2, u.getPrenume());
            statement.setString(3, u.getUsername());
            statement.setString(4, u.getParola());
            statement.setString(5, u.getEmail());
            statement.setString(6, u.getTelefon());
            statement.setDate(7, Date.valueOf(u.getDataNastere()));
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
        String sql = "UPDATE utilizator SET nume = ?, prenume = ?, mail = ?, telefon = ?, " +
                "data_nasterii = ?, username = ?, parola = ?, tip = ? WHERE id = ?";
        PreparedStatement statement = null;
        boolean success = false;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, u.getNume());
            statement.setString(2, u.getPrenume());
            statement.setString(3, u.getUsername());
            statement.setString(4, u.getParola());
            statement.setString(5, u.getEmail());
            statement.setString(6, u.getTelefon());
            statement.setDate(7, Date.valueOf(u.getDataNastere()));
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
    private Utilizator mapResultSetToUtilizator(ResultSet rs) throws SQLException {
        return new Utilizator(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("username"),
                rs.getString("parola"),
                rs.getString("mail"),
                rs.getString("telefon"),
                rs.getDate("data_nasterii").toLocalDate(),
                UtilizatorType.valueOf(rs.getString("tip"))
        );
    }

}
