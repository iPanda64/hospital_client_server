package model;

public enum UseCaseType {
    Login,
    Chat,
    CreateAccount,
    ViewAccount,
    AdminViewAllAccounts,
    AdminDeleteUser,
    AdminAddUser,
    AdminEditUser,
    PacientViewProgramari,
    PacientCreateProgramare,
    PacientViewHistory,
    PacientViewFacturi,
    PacientGetResults,

    DoctorViewProgramari,
    DoctorViewDatePersonalePacient,

    AsistentViewPacienti,
    AsistentViewProgramari,
    AsistentGestionareProgramari, //pt respingere sau aprobare
    AsistentStergeProgramare,
    AsistentCreareProgramare,
    AsistentViewPrescriptii,
    AsistentGetFacturaData,

}
