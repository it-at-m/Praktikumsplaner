/**
 * Central catalogue of `data-test` attribute values used to instrument the UI
 * for automated end-to-end tests (e.g. Selenium).
 *
 * See ADR-010 and the "UI Test IDs" guide for the rationale and conventions.
 *
 * Conventions:
 * - kebab-case values,
 * - hierarchical `<area>-<component>-<element>`,
 * - suffix by element type where helpful (`-btn`, `-input`, `-select`, ...).
 *
 * Templates must reference these constants instead of inlining raw strings:
 *
 *   <v-btn :data-test="testIds.nwk.createDialogOpenBtn" />
 *
 * Reusable wrapper components carry a fixed value; this is safe because two
 * instances of the same wrapper are never visible at the same time.
 */
export const testIds = {
  /** Global application shell: app bar and navigation drawer. */
  app: {
    navToggle: "app-nav-toggle",
    homeLogo: "app-home-logo",
    homeTitle: "app-home-title",
  },
  /** Navigation drawer entries. */
  nav: {
    nachwuchskraefte: "nav-nachwuchskraefte",
    meldezeitraum: "nav-meldezeitraum",
    praktikumsplaetze: "nav-praktikumsplaetze",
    zuweisung: "nav-zuweisung",
  },
  /** Main / landing view. */
  main: {
    cardNachwuchskraefte: "main-card-nachwuchskraefte",
    cardMeldezeitraum: "main-card-meldezeitraum",
    cardPraktikumsplaetze: "main-card-praktikumsplaetze",
    cardZuweisung: "main-card-zuweisung",
  },
  /** Global feedback components. */
  feedback: {
    snackbar: "snackbar",
    snackbarCloseBtn: "snackbar-close-btn",
    errorDialog: "error-dialog",
    errorDialogCloseBtn: "error-dialog-close-btn",
  },
  /** Shared/common building blocks. */
  common: {
    pageTitle: "page-title",
    pageTitleBackBtn: "page-title-back-btn",
    dataTable: "data-table",
    dataTableSearch: "data-table-search",
    dataTableGroupBy: "data-table-group-by",
    datePickerField: "date-picker-field",
  },
  /** Generic dialogs (YesNo / TwoChoice / Warning). */
  dialog: {
    yesNoActivatorBtn: "yesno-dialog-activator-btn",
    yesNoNoBtn: "yesno-dialog-no-btn",
    yesNoYesBtn: "yesno-dialog-yes-btn",
    twoChoiceActivatorBtn: "two-choice-dialog-activator-btn",
    twoChoiceOne: "two-choice-dialog-choice-one",
    twoChoiceTwo: "two-choice-dialog-choice-two",
    twoChoiceCloseBtn: "two-choice-dialog-close-btn",
    warningAcceptBtn: "warning-dialog-accept-btn",
    warningRejectBtn: "warning-dialog-reject-btn",
  },
  /** Nachwuchskraefte view and its dialogs/inputs. */
  nwk: {
    createDialogOpenBtn: "nwk-create-dialog-open-btn",
    createDialog: "nwk-create-dialog",
    updateDialogOpenBtn: "nwk-update-dialog-open-btn",
    updateDialog: "nwk-update-dialog",
    cancelBtn: "nwk-cancel-btn",
    acceptBtn: "nwk-accept-btn",
    vornameInput: "nwk-vorname-input",
    nachnameInput: "nwk-nachname-input",
    jahrgangInput: "nwk-jahrgang-input",
    vorlesungstageSelect: "nwk-vorlesungstage-select",
    richtungSelect: "nwk-richtung-select",
    excelImportOpenBtn: "nwk-excel-import-open-btn",
    excelImportDialog: "nwk-excel-import-dialog",
    excelImportFileInput: "nwk-excel-import-file-input",
    excelImportCancelBtn: "nwk-excel-import-cancel-btn",
    excelImportUploadBtn: "nwk-excel-import-upload-btn",
  },
  /** Meldezeitraeume view, list and creation dialog. */
  meldezeitraum: {
    createOpenBtn: "meldezeitraum-create-open-btn",
    createDialog: "meldezeitraum-create-dialog",
    nameInput: "meldezeitraum-name-input",
    startInput: "meldezeitraum-start-input",
    endInput: "meldezeitraum-end-input",
    backBtn: "meldezeitraum-back-btn",
    saveBtn: "meldezeitraum-save-btn",
    listCard: "meldezeitraum-list-card",
    deleteBtn: "meldezeitraum-delete-btn",
  },
  /** Praktikumsstelle (Meldung + overview + edit/delete). */
  praktikumsstelle: {
    editBtn: "praktikumsstelle-edit-btn",
    deleteBtn: "praktikumsstelle-delete-btn",
    editDialog: "praktikumsstelle-edit-dialog",
    cancelBtn: "praktikumsstelle-cancel-btn",
    acceptBtn: "praktikumsstelle-accept-btn",
    saveBtn: "praktikumsstelle-save-btn",
    // form fields (shared between Studium/Ausbildung meldung and edit dialogs)
    dienststelleInput: "praktikumsstelle-dienststelle-input",
    dringlichkeitSelect: "praktikumsstelle-dringlichkeit-select",
    namentlicheAnforderungInput:
      "praktikumsstelle-namentliche-anforderung-input",
    planstelleRadio: "praktikumsstelle-planstelle-radio",
    projektarbeitRadio: "praktikumsstelle-projektarbeit-radio",
    taetigkeitenInput: "praktikumsstelle-taetigkeiten-input",
    studienrichtungSelect: "praktikumsstelle-studienrichtung-select",
    studiensemesterSelect: "praktikumsstelle-studiensemester-select",
    ausbildungsrichtungSelect: "praktikumsstelle-ausbildungsrichtung-select",
    ausbildungsjahrSelect: "praktikumsstelle-ausbildungsjahr-select",
    programmierkenntnisseSelect:
      "praktikumsstelle-programmierkenntnisse-select",
    wuenscheInput: "praktikumsstelle-wuensche-input",
    ausbilderInput: "praktikumsstelle-ausbilder-input",
    ausbilderEmailInput: "praktikumsstelle-ausbilder-email-input",
    fuehrungszeugnisCheckbox: "praktikumsstelle-fuehrungszeugnis-checkbox",
    minderjaehrigRadio: "praktikumsstelle-minderjaehrig-radio",
    meldezeitraumSelect: "praktikumsstelle-meldezeitraum-select",
  },
  /** Zuweisung view and its cards/dialogs. */
  assign: {
    nwkList: "assign-nwk-list",
    nwkCard: "assign-nwk-card",
    praktikumsstellenList: "assign-praktikumsstellen-list",
    praktikumsstelleCard: "assign-praktikumsstelle-card",
    sendMailsBtn: "assign-send-mails-btn",
    exportBtn: "assign-export-btn",
    undeliveredMailsDialog: "assign-undelivered-mails-dialog",
    undeliveredMailsCloseBtn: "assign-undelivered-mails-close-btn",
  },
} as const;

export default testIds;
