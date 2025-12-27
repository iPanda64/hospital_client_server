package model.Repository;
import model.Prescriptie;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrescriptieRepository {
    private Repository repository;
    public PrescriptieRepository() {
        this.repository = new Repository();
    }
    public void salvare(Prescriptie p) {
        String sql = "INSERT INTO prescriptie (id_consultatie, medicamente, doza_zilnica, durata_zile) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, p.getId_consultatie());
            String medicamenteStr = String.join(",", p.getMedicament());
            statement.setString(2, medicamenteStr);
            statement.setInt(3, p.getDoza_zilnica());
            statement.setInt(4, p.getDurata_tratament_in_zile());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
    }

    public List<Prescriptie> findByPacientId(Long id) {
        List<Prescriptie> prescriptii = new ArrayList<>();
        String sql = "SELECT pr.* FROM prescriptie pr " +
                "JOIN consultatie c ON pr.id_consultatie = c.id " +
                "WHERE c.id_pacient = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                prescriptii.add(mapResultSetToPrescriptie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return prescriptii;
    }

    public Prescriptie findById(Long id) {
        String sql = "SELECT * FROM prescriptie WHERE id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Prescriptie prescriptie = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                prescriptie = mapResultSetToPrescriptie(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return prescriptie;
    }

    private Prescriptie mapResultSetToPrescriptie(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int id_consultatie = rs.getInt("id_consultatie");
        String medicamenteStr = rs.getString("medicamente");
        List<String> medicamente = new ArrayList<>();
        if (medicamenteStr != null && !medicamenteStr.isEmpty()) {
            medicamente = new ArrayList<>(Arrays.asList(medicamenteStr.split(",")));
        }
        int doza = rs.getInt("doza_zilnica");
        int durata = rs.getInt("durata_zile");

        return new Prescriptie(id, id_consultatie, medicamente, doza, durata);
    }
}
