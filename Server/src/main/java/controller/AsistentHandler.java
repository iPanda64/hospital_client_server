package controller;

import model.Request;
import model.Response;
import model.Repository.*;
import model.*;
import java.util.List;
public class AsistentHandler extends AbstractHandler {
    public AsistentHandler(Request request) {
        super(request);
    }
    public Response viewDatePersonalePacientiHandler(Request request) {
        UtilizatorRepository ur = new UtilizatorRepository(new Repository());
        try {
            int idPacient = (Integer) request.getPayload();
            Utilizator pacient = ur.findById(idPacient);
            if (pacient != null) {
                return new Response(request, request.getId(), pacient, true);
            } else {
                return new Response(request, request.getId(), null, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(request, -1, null, false);
        }
    }

    public Response viewProgramariPacientiHandler(Request request) {
        ProgramareRepository pr = new ProgramareRepository();
        try {
            List<Programare> programari = pr.findAll();
            return new Response(request, request.getId(), programari, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(request, -1, null, false);
        }
    }

    public Response createProgramarePacientiHandler(Request request) {
        ProgramareRepository pr = new ProgramareRepository();
        try {
            Programare p = (Programare) request.getPayload();
            pr.salvare(p);
            List<Programare> listaNoua = pr.findAll();
            return new Response(request, request.getId(), listaNoua, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response deleteProgramariPacientiHandler(Request request) {
        ProgramareRepository pr = new ProgramareRepository();
        try {
            long idProgramare = (Integer) request.getPayload();
            pr.delete(idProgramare);
            List<Programare> listaNoua = pr.findAll();
            return new Response(request, request.getId(), listaNoua, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response approveProgramariPacientiHandler(Request request) {
        ProgramareRepository pr = new ProgramareRepository();
        try {
            long idProgramare = (Integer) request.getPayload();
            pr.updateStatus(idProgramare, String.valueOf(StatusProgramare.Aprobata));
            List<Programare> listaNoua = pr.findAll();
            return new Response(request, request.getId(), listaNoua, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response viewPrescriptiePacientHandler(Request request) {
        PrescriptieRepository pr = new PrescriptieRepository();
        try {
            long idPacient = (Integer) request.getPayload();
            List<Prescriptie> prescriptii = pr.findByPacientId(idPacient);
            return new Response(request, request.getId(), prescriptii, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response printFacturaConsultatieHandler(Request request) {
        FacturaRepository fr = new FacturaRepository();
        try {
            int idConsultatie = (Integer) request.getPayload();
            Factura f = fr.findByConsultatieId(idConsultatie);
            return new Response(request, request.getId(), f, true);
        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }
    public Response viewListaPacientiHandler(Request request) {
        UtilizatorRepository ur = new UtilizatorRepository(new Repository());
        try {
            List<Utilizator> pacienti = ur.SearchByTip(UtilizatorType.pacient);
            return new Response(request, request.getId(), pacienti, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(request, -1, null, false);
        }
    }
}

