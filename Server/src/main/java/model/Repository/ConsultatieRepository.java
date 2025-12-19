package model.Repository;

import model.Consultatie;
import model.Factura;
import model.Prescriptie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsultatieRepository {
    private Repository repository;

    public ConsultatieRepository() {
        repository = new Repository();
    }

    public boolean AddConsultatie(Consultatie consultatie) {
        String sql = "INSERT INTO consultatie (id_programare, diagnostic, simptome, cost, data_consultatiei) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        boolean success = false;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, consultatie.getId_programare());
            statement.setString(2, consultatie.getDiagnostic());
            String simptomeStr = String.join(",", consultatie.getSimptome());
            statement.setString(3, simptomeStr);
            statement.setInt(4, consultatie.getCost());
            statement.setDate(5, Date.valueOf(consultatie.getData_consultatiei()));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return success;
    }

    public boolean DeleteConsultatie(int id) {
        String sql = "DELETE FROM consultatie WHERE id = ?";
        PreparedStatement statement = null;
        boolean success = false;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return success;
    }

    public List<Consultatie> SearchConsultatieByPacientId(int pacientId) {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT c.* FROM consultatie c " +
                     "JOIN programare p ON c.id_programare = p.id " +
                     "WHERE p.id_pacient = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, pacientId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                consultatii.add(mapResultSetToConsultatie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return consultatii;
    }

    public List<Consultatie> SearchConsultatieByDoctorId(int doctorId) {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT c.* FROM consultatie c " +
                     "JOIN programare p ON c.id_programare = p.id " +
                     "WHERE p.id_doctor = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, doctorId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                consultatii.add(mapResultSetToConsultatie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return consultatii;
    }

    public boolean UpdateConsultatie(int id, Consultatie newConsultatie) {
        String sql = "UPDATE consultatie SET id_programare = ?, diagnostic = ?, simptome = ?, cost = ?, data_consultatiei = ? WHERE id = ?";
        PreparedStatement statement = null;
        boolean success = false;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, newConsultatie.getId_programare());
            statement.setString(2, newConsultatie.getDiagnostic());
            String simptomeStr = String.join(",", newConsultatie.getSimptome());
            statement.setString(3, simptomeStr);
            statement.setInt(4, newConsultatie.getCost());
            statement.setDate(5, Date.valueOf(newConsultatie.getData_consultatiei()));
            statement.setInt(6, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return success;
    }

    public List<Consultatie> SearchAllConsultatie() {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT * FROM consultatie";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                consultatii.add(mapResultSetToConsultatie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return consultatii;
    }

    public List<Consultatie> SearchConsultatieAfterTime(LocalDate time) {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT * FROM consultatie WHERE data_consultatiei > ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(time));

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                consultatii.add(mapResultSetToConsultatie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return consultatii;
    }

    public List<Consultatie> SearchConsultatieBeforeTime(LocalDate time) {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT * FROM consultatie WHERE data_consultatiei < ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(time));

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                consultatii.add(mapResultSetToConsultatie(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return consultatii;
    }

    public Consultatie SearchPrescriptieOfConsultatie(Prescriptie prescriptie) {
        Consultatie consultatie = null;
        String sql = "SELECT * FROM consultatie WHERE id = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, prescriptie.getId_consultatie());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                consultatie = mapResultSetToConsultatie(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }
        return consultatie;
    }

    public Consultatie SearchConsultatieByFactura(Factura factura) {
        Consultatie consultatie = null;

        String sql = "SELECT * FROM consultatie WHERE id = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, factura.getId_consultatie());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                consultatie = mapResultSetToConsultatie(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }

        return consultatie;
    }

    private Consultatie mapResultSetToConsultatie(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int id_programare = resultSet.getInt("id_programare");
        String diagnostic = resultSet.getString("diagnostic");
        String simptomeStr = resultSet.getString("simptome");
        List<String> simptome = (simptomeStr != null && !simptomeStr.isEmpty()) ? Arrays.asList(simptomeStr.split(",")) : new ArrayList<>();
        int cost = resultSet.getInt("cost");
        Date dbDate = resultSet.getDate("data_consultatiei");
        LocalDate data_consultatiei = (dbDate != null) ? dbDate.toLocalDate() : null;

        return new Consultatie(id, id_programare, diagnostic, simptome, cost, data_consultatiei);
    }
}
