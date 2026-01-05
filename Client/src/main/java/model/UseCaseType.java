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
    DoctorViewFisaMedicala,
    DoctorViewDatePersonalePacient,

    AsistentViewPacienti,
    AsistentViewProgramari,
    AsistentGestionareProgramari, //pt respingere sau aprobare
    AsistentStergeProgramare,
    AsistentCreareProgramare,
    AsistentViewPrescriptii,
    AsistentGetFacturaData,
    AsistentViewDatePersonalePacienti,
    // Add more use cases later
}
