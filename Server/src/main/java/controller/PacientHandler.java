package controller;

import model.Factura;
import model.Repository.FacturaRepository;
import model.Request;
import model.Response;

import java.util.List;

public class PacientHandler extends AbstractHandler {
    public PacientHandler(Request request) {
        super(request);
    }
    public Response getResponse() {
        return null;
    }

    public Response createAccountHandler(Request request) {
        return null;
    }

    public Response viewFacturiHandler(Request request) {
        Response response = null;
        try {
            int id = request.getId();
            FacturaRepository fr=new FacturaRepository();
            List<Factura>facturi=fr.getFacturiByPacient(id);
            response=new Response(request,id,facturi,true);
        } catch (Exception e) {
            e.printStackTrace();
            response=new Response(request,-1,null,false);
        }
        return response;
    }

    public Response createProgramareHandler(Request request) {
        return null;
    }

    public Response viewProgramariHandler(Request request) {
        return null;
    }

    public Response viewMedicalHistoryHandler(Request request) {
        return null;
    }

    public Response getConsultatieResults(Request request) {
        return null;
    }

    public Response viewAccount(Request request) {
        return null;
    }
}
