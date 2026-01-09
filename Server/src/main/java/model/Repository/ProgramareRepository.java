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
        String sql = "INSERT INTO programare (id_doctor, id_pacient, data_programare, status) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            System.out.println(p.getStatus().toString());
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

    public List<Programare> findByDoctorId(Long id) {
        List<Programare> programari = new ArrayList<>();
        String sql = "SELECT * FROM programare WHERE id_doctor = ?";
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
    public boolean isSafeToAddConsultatie(int programareId) {
        String sql = "SELECT 1 FROM consultatie WHERE id_programare = ?";

        try (Connection connection = repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, programareId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public Programare findById(Long id) {
        String sql = "SELECT * FROM programare WHERE id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection connection = repository.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToProgramare(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(rs);
            repository.closeStatement(stmt);
            repository.closeConnection();
        }
        return null;
    }
    private Programare mapResultSetToProgramare(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int id_doctor = rs.getInt("id_doctor");
        int id_pacient = rs.getInt("id_pacient");
        LocalDate data = rs.getDate("data_programare").toLocalDate();
        StatusProgramare status = StatusProgramare.fromString(rs.getString("status"));

        return new Programare(id, id_doctor, id_pacient, data, status);
    }

}
