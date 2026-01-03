package controller;

import model.Programare;
import model.Repository.ProgramareRepository;
import model.Repository.Repository;
import model.Repository.UtilizatorRepository;
import model.Request;
import model.Response;
import model.Utilizator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorHandler extends AbstractHandler {
    public DoctorHandler(Request request) {
        super(request);
    }
    public Response viewDatePersonalePacienti(Request request) {
        return null;
    }

    public Response viewFisaMedicalaPacientHandler(Request request) {
        return null;
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
