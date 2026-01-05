package controller;

import model.*;
import model.Repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorHandler extends AbstractHandler {
    public DoctorHandler(Request request) {
        super(request);
    }
    public Response viewDatePersonalePacienti(Request request) {
        Response response = null;
        try {
            UtilizatorRepository utilizatorRepository = new UtilizatorRepository(new Repository());
            List<Utilizator> pacienti = utilizatorRepository.SearchAllPacients();
            pacienti.forEach(u -> u.setParola(null));

            response = new Response(request, request.getId(), pacienti, true);
        } catch (Exception e) {
            e.printStackTrace();
            response = new Response(request, -1, null, false);
        }
        return response;
    }

    public Response viewFisaMedicalaPacientHandler(Request request) {
        ConsultatieRepository cr = new ConsultatieRepository();
        PrescriptieRepository pr = new PrescriptieRepository ();
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(new Repository());
        List<Consultatie> istoric;
        try {
            if(request.getAdditionalInfo().equals("pacient")){
                List<Utilizator> utilizators = utilizatorRepository.SearchAllPacients();
                return new Response(request, request.getId(), utilizators, true);
            }
            else if (request.getAdditionalInfo().equals("consultatie_prescriptie")) {
                int idPacient = (Integer) request.getPayload();
                istoric = cr.SearchConsultatieByPacientId(idPacient);

                HashMap<Consultatie, Prescriptie> consultatiePrescriptieHashMap = new HashMap<>();
                istoric.forEach(consultatie -> consultatiePrescriptieHashMap.put(consultatie, pr.SearchPrescriptieOfConsultatie(consultatie.getId())));
                return new Response(request, request.getId(), consultatiePrescriptieHashMap, true);
            }
            else return new Response(request, -1, null, false);

        } catch (Exception e) {
            return new Response(request, -1, null, false);
        }
    }

    public Response viewProgramariHandler(Request request) {
        Response response=null;
        List<Programare>programari=null;
        try {
            ProgramareRepository programareRepository = new ProgramareRepository();
            UtilizatorRepository utilizatorRepository=new UtilizatorRepository(new Repository());
            int id = request.getId();
            programari=programareRepository.findByDoctorId( Long.valueOf(id));
            List<String> result=new ArrayList<>();
            for (Programare programare : programari) {
                int pacient_id = programare.getId_pacient();
                Utilizator utilizator = utilizatorRepository.findById(pacient_id);
                if (utilizator != null) {
                    result.add(utilizator.getNume()+" "+
                            utilizator.getPrenume()+" "+
                            programare.getData_programarii()+" "+
                            programare.getStatus());
                }
            }
            response=new Response(request,id,result,true);
        } catch (Exception e) {
            e.printStackTrace();
            response=new Response(request,-1,null,false);
        }
        return response;
    }

    public Response createPrescriptiePacientiHandler(Request request) {
        return null;
    }

    public Response viewPrescriptiePacientiHandler(Request request) {
        return null;
    }

    public Response viewListaPacientiHandler(Request request) {
        return null;
    }
}
