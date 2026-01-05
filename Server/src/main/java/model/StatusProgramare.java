package model;

public enum StatusProgramare {
    InAsteptare,
    Aprobata,
    Respinsa;
    @Override
    public String toString() {
        switch (this) {
            case InAsteptare:
                return "in asteptare";
            case Aprobata:
                return "aprobata";
            case Respinsa:
                return "respinsa";
        }
        return "";
    }
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
