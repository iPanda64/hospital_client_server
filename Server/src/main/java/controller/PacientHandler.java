package controller;

import model.Request;
import model.Response;
import model.Repository.*;
import model.*;
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
        try {
            return new Response(request, request.getId(), null, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response createProgramareHandler(Request request) {
        ProgramareRepository pr = new ProgramareRepository();
        List<Programare> programari = null;
        try {
            Programare p = (Programare) request.getPayload();
            pr.salvare(p);
            programari = pr.findByPacientId((long) p.getId());
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
        return new Response(request, request.getId(), programari, true);
    }

    public Response viewProgramariHandler(Request request) {
        ProgramareRepository pr = new ProgramareRepository();
        List<Programare> programari;
        try {
            int idPacient = (Integer) request.getPayload();
            programari = pr.findByPacientId((long) idPacient);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
        return new Response(request, request.getId(), programari, true);
    }

    public Response viewMedicalHistoryHandler(Request request) {
        ConsultatieRepository cr = new ConsultatieRepository();
        List<Consultatie> istoric;
        try {
            int idPacient = (Integer) request.getPayload();
            istoric = cr.SearchConsultatieByPacientId(idPacient);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
        return new Response(request, request.getId(), istoric, true);
    }

    public Response getConsultatieResults(Request request) {
        try {
            return new Response(request, request.getId(), null, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response viewAccount(Request request) {
        return null;
    }
}
