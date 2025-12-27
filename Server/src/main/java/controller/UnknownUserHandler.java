package controller;

import model.Repository.Repository;
import model.Repository.UtilizatorRepository;
import model.Request;
import model.Response;
import model.Utilizator;

public class UnknownUserHandler extends AbstractHandler {
    public UnknownUserHandler(Request request) {
        super(request);
    }
    public Response createAccountHandler(Request request) {
        return null;
    }
    public Response loginHandler(Request request) {
        Response response = null;
        String info = (String)request.getPayload();
        int spaceIndex = -1;
        int index = -1;
        for (char c : info.toCharArray()) {
            index++;
            if (c == ' ')
                if (spaceIndex == -1) spaceIndex = index;
                else response = new Response(request, -1, null, false);
        }
        if (response != null) return response;
        String username = info.substring(0, spaceIndex);
        String parola = info.substring(spaceIndex + 1);
        UtilizatorRepository ur = new UtilizatorRepository(new Repository());
        Utilizator u = ur.SearchUtilizatorByUsernameAndPassword(username, parola);
        return new Response<>(request, u.getId(), null  , true);
    }

}
