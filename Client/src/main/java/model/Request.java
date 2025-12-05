package model;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private UtilizatorType utilizatorType;
    private UseCaseType useCaseType;
    private String additionalInfo;
    private int id;
    private Object payload; // Keeping payload for now, as it's used in current client/server logic

    public Request(UtilizatorType utilizatorType, UseCaseType useCaseType, String additionalInfo, int id, Object payload) {
        this.utilizatorType = utilizatorType;
        this.useCaseType = useCaseType;
        this.additionalInfo = additionalInfo;
        this.id = id;
        this.payload = payload;
    }

    // Constructor for simpler requests, if needed
    public Request(UseCaseType useCaseType, Object payload) {
        this(UtilizatorType.Unknown, useCaseType, null, 0, payload);
    }

    public UtilizatorType getUtilizatorType() {
        return utilizatorType;
    }

    public UseCaseType getUseCaseType() {
        return useCaseType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public int getId() {
        return id;
    }

    public Object getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Request{" +
               "utilizatorType=" + utilizatorType +
               ", useCaseType=" + useCaseType +
               ", additionalInfo='" + additionalInfo + "'" +
               ", id=" + id +
               ", payload=" + payload +
               "}";
    }
}
