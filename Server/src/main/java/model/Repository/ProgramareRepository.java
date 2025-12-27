package model.Repository;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramareRepository {
    private Repository repository;
    public ProgramareRepository() {
        this.repository = new Repository();
    }
    public void salvare(Programare p) {
        String sql = "INSERT INTO programare (id_doctor, id_pacient, data_programarii, status) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, p.getId_doctor());
            statement.setInt(2, p.getId_pacient());
            statement.setDate(3, Date.valueOf(p.getData_programarii()));
            statement.setString(4, p.getStatus().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
    }

    public List<Programare> findAll() {
        List<Programare> programari = new ArrayList<>();
        String sql = "SELECT * FROM programare";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                programari.add(mapResultSetToProgramare(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return programari;
    }

    public List<Programare> findByPacientId(Long id) {
        List<Programare> programari = new ArrayList<>();
        String sql = "SELECT * FROM programare WHERE id_pacient = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                programari.add(mapResultSetToProgramare(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return programari;
    }

    public void updateStatus(Long id, String status) {
        String sql = "UPDATE programare SET status = ? WHERE id = ?";
        PreparedStatement statement = null;
        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM programare WHERE id = ?";
        PreparedStatement statement = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
    }

    public Prescriptie findByConsultatieId(Long consultatieId) {
        return null;
    }

    private Programare mapResultSetToProgramare(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int id_doctor = rs.getInt("id_doctor");
        int id_pacient = rs.getInt("id_pacient");
        LocalDate data = rs.getDate("data_programarii").toLocalDate();
        StatusProgramare status = StatusProgramare.valueOf(rs.getString("status"));

        return new Programare(id, id_doctor, id_pacient, data, status);
    }
}
