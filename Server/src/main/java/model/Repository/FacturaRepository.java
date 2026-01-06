package model.Repository;

import model.Factura;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaRepository {
    private Repository repository;
    public FacturaRepository() {
        repository = new Repository();
    }
    public boolean addFactura(Factura factura) {
        String sql = "INSERT INTO factura (id_consultatie, data_emitere, suma) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        boolean success = false;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, factura.getId_consultatie());
            statement.setDate(2, Date.valueOf(factura.getData_emitere()));
            statement.setDouble(3, factura.getSuma());

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
    public boolean updateFactura(Factura factura) {
        String sql = "UPDATE factura SET id_consultatie = ?, data_emitere = ?, suma = ? WHERE id = ?";

        PreparedStatement statement = null;
        boolean success = false;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, factura.getId_consultatie());
            statement.setDate(2, java.sql.Date.valueOf(factura.getData_emitere()));
            statement.setDouble(3, factura.getSuma());

            statement.setInt(4, factura.getId());

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
    public List<Factura> searchFacturaByCost(int cost) {
        List<Factura> listaFacturi = new ArrayList<>();
        String sql = "SELECT * FROM factura WHERE suma = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, cost);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idConsultatie = resultSet.getInt("id_consultatie");

                Date dbDate = resultSet.getDate("data_emitere");
                LocalDate localDate = (dbDate != null) ? dbDate.toLocalDate() : null;

                int suma = resultSet.getInt("suma");
                Factura factura = new Factura(id, idConsultatie, localDate, suma);

                listaFacturi.add(factura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }

        return listaFacturi;
    }
    public List<Factura> getFacturiByPacient(int pacientId) {
        List<Factura> listaFacturi = new ArrayList<>();
        String sql = "SELECT f.* " +
                "FROM factura f " +
                "JOIN consultatie c ON f.id_consultatie = c.id " +
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
                int facturaId=(resultSet.getInt("id"));
                int facturaConsultatieId=(resultSet.getInt("id_consultatie"));

                Date dbDate = resultSet.getDate("data_emitere");
                LocalDate facturaDate=null;
                if(dbDate != null) {
                    facturaDate=(dbDate.toLocalDate());
                }

                int facturaSuma=(resultSet.getInt("suma"));

                Factura factura = new Factura(facturaId,facturaConsultatieId,facturaDate,facturaSuma);
                listaFacturi.add(factura);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement); repository.closeConnection();
        }

        return listaFacturi;
    }
    public Factura findByConsultatieId(int idConsultatie) {
        String sql = "SELECT * FROM factura WHERE id_consultatie = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Factura factura = null;

        try {
            Connection connection = repository.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idConsultatie);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int idConsult = resultSet.getInt("id_consultatie");
                Date data = resultSet.getDate("data_emitere");
                LocalDate localDate = (data != null) ? data.toLocalDate() : null;
                int suma = resultSet.getInt("suma");
                factura = new Factura(id, idConsult, localDate, suma);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            repository.closeResultSet(resultSet);
            repository.closeStatement(statement);
            repository.closeConnection();
        }

        return factura;
    }
}
