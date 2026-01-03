package model;

public enum StatusProgramare {
    InAsteptare,
    Aprobata,
    Respinsa;
    public static StatusProgramare fromString(String dbStatus) {
        if (dbStatus == null) return null;

        switch (dbStatus) {
            case "in asteptare":
                return InAsteptare;
            case "aprobata":
                return Aprobata;
            case "respinsa":
                return Respinsa;
            default:
                throw new IllegalArgumentException("Unknown status in DB: " + dbStatus);
        }
    }
}
