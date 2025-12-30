package controller;

import model.Repository.Repository;
import model.Repository.UtilizatorRepository;
import model.Request;
import model.Response;
import model.Utilizator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class AdminHandler extends AbstractHandler {
    public AdminHandler(Request request) {
        super(request);
    }
    public Response getResponse() {
        return null;
    }

    public Response createAccountHandler(Request request) {
        UtilizatorRepository ur =new UtilizatorRepository(new Repository());
        List<Utilizator> utilizatori=null;
        try {
            ur.salvare((Utilizator)request.getPayload());
            utilizatori = ur.SearchAllUtilizator();
        } catch (Exception e) {
            return new Response(request,-1,null,false);
        }
        return new Response(request, request.getId(), utilizatori,true);
    }

    public Response viewAllAccountsHandler(Request request) {
        UtilizatorRepository ur =new UtilizatorRepository(new Repository());
        List<Utilizator> utilizatori;
        try {
            utilizatori = ur.SearchAllUtilizator();
        } catch (Exception e) {
            return new Response(request,-1,null,false);
        }
        return new Response(request, request.getId(), utilizatori,true);
    }

    public Response updateAccountHandler(Request request) {
        UtilizatorRepository ur =new UtilizatorRepository(new Repository());
        List<Utilizator> utilizatori=null;
        Utilizator utilizator=null;
        try {
            utilizator=(Utilizator)request.getPayload();
            ur.update(utilizator);
            utilizatori = ur.SearchAllUtilizator();
        } catch (Exception e) {
            return new Response(request,-1,null,false);
        }
        return new Response(request, request.getId(), utilizatori,true);

    }

    public Response deleteHandler(Request request) {
        Response response = null;//new Response(request,-1,null,false);
        List<Utilizator> utilizatori=null;
        try{
            UtilizatorRepository ur =new UtilizatorRepository(new Repository());
            int id =(Integer)request.getPayload();
            ur.delete(id);
            utilizatori = ur.SearchAllUtilizator();
        }catch (Exception e) {
            e.printStackTrace();
            response = new Response(request,-1,null,false);
        }
        response=new Response(request,request.getId(),utilizatori,true);
        return response;
    }

    public Response viewAccountHandler(Request request) {
        return null;
    }
}
