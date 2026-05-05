import type { Ref } from "vue";

import { Levels } from "@/api/Error";
import {
  defaultResponseHandler,
  getDELETEConfig,
  getGETConfig,
  getPATCHConfig,
  getPOSTConfig,
  getPUTConfig,
} from "@/api/FetchUtils";
import { API_BASE, PRAKTIKUMSSTELLE_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Praktikumsstelle from "@/types/Praktikumsstelle";

export default {
  uploadStudiumsPraktikumsstelle(
    praktikumsstelle: Praktikumsstelle,
    loading: Ref<boolean>
  ): Promise<Praktikumsstelle> {
    loading.value = true;
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium`,
      getPOSTConfig(praktikumsstelle)
    )
      .then((response) => {
        defaultResponseHandler(response);
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Praktikumsstelle erfolgreich gemeldet",
            level: Levels.SUCCESS,
          });
          return response.json();
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  uploadStudiumsPraktikumsstelleWithMeldezeitraum(
    praktikumsstelle: Praktikumsstelle,
    loading: Ref<boolean>
  ): Promise<Praktikumsstelle> {
    loading.value = true;
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium/ausbildungsleitung`,
      getPOSTConfig(praktikumsstelle)
    )
      .then((response) => {
        defaultResponseHandler(response);
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Praktikumsstelle erfolgreich gemeldet",
            level: Levels.SUCCESS,
          });
          return response.json();
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  uploadAusbildungsPraktikumsstelle(
    praktikumsstelle: Praktikumsstelle,
    loading: Ref<boolean>
  ): Promise<Praktikumsstelle> {
    loading.value = true;
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung`,
      getPOSTConfig(praktikumsstelle)
    )
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Praktikumsstelle erfolgreich gemeldet",
            level: Levels.SUCCESS,
          });
          return response.json();
        } else {
          defaultResponseHandler(response);
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  uploadAusbildungsPraktikumsstelleWithMeldezeitraum(
    praktikumsstelle: Praktikumsstelle,
    loading: Ref<boolean>
  ): Promise<Praktikumsstelle> {
    loading.value = true;
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung/ausbildungsleitung`,
      getPOSTConfig(praktikumsstelle)
    )
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Praktikumsstelle erfolgreich gemeldet",
            level: Levels.SUCCESS,
          });
          return response.json();
        } else {
          defaultResponseHandler(response);
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  getAllPraktikumsstellenInSpecificMeldezeitraum(
    meldezeitraum: string
  ): Promise<Praktikumsstelle[]> {
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}?meldezeitraum=${meldezeitraum}`,
      getGETConfig()
    ).then((response) => {
      defaultResponseHandler(response);
      return response.json();
    });
  },
  assignNwk(
    stellenId: string,
    nwkId: string | undefined,
    loading: Ref<boolean>
  ): Promise<Praktikumsstelle> {
    loading.value = true;
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}?nwkId=${nwkId}`,
      getPATCHConfig({})
    )
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Zuweisung erfolgreich",
            level: Levels.SUCCESS,
          });
          return response.json();
        } else {
          defaultResponseHandler(response);
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  unassignNwk(
    stellenId: string,
    loading: Ref<boolean>
  ): Promise<Praktikumsstelle> {
    loading.value = true;
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${stellenId}`,
      getPATCHConfig({})
    )
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Zuweisung aufgehoben",
            level: Levels.SUCCESS,
          });
          return response.json();
        } else {
          defaultResponseHandler(response);
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  deletePraktikumsstelle(
    stelle: Praktikumsstelle,
    loading: Ref<boolean>
  ): Promise<void> {
    if (this.isAusbildungsPraktikumsstelle(stelle)) {
      loading.value = true;
      return fetch(
        `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/ausbildung/${stelle.id}`,
        getDELETEConfig({})
      )
        .then((response) => {
          defaultResponseHandler(response);
          useSnackbarStore().showMessage({
            message: "☑ Praktikumsstelle erfolgreich gelöscht",
            level: Levels.SUCCESS,
          });
        })
        .finally(() => {
          loading.value = false;
        });
    } else if (this.isStudiumsPraktikumsstelle(stelle)) {
      loading.value = true;
      return fetch(
        `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/studium/${stelle.id}`,
        getDELETEConfig({})
      )
        .then((response) => {
          defaultResponseHandler(response);
          useSnackbarStore().showMessage({
            message: "☑ Praktikumsstelle erfolgreich gelöscht",
            level: Levels.SUCCESS,
          });
        })
        .finally(() => {
          loading.value = false;
        });
    } else {
      throw new Error(
        "Praktikumsstelle konnte nicht nach Typ kategorisiert werden."
      );
    }
  },
  updatePraktikumsstelle(
    stelle: Praktikumsstelle,
    loading: Ref<boolean> | undefined
  ): Promise<void> {
    let pathCategory;
    if (this.isAusbildungsPraktikumsstelle(stelle)) {
      pathCategory = "ausbildung";
    } else if (this.isStudiumsPraktikumsstelle(stelle)) {
      pathCategory = "studium";
    } else {
      throw new Error(
        "Praktikumsstelle konnte nicht nach Typ kategorisiert werden."
      );
    }
    if (loading !== undefined) {
      loading.value = true;
    }
    return fetch(
      `${API_BASE}${PRAKTIKUMSSTELLE_BASE}/${pathCategory}/${stelle.id}`,
      getPUTConfig(stelle)
    )
      .then((response) => {
        defaultResponseHandler(response);
        useSnackbarStore().showMessage({
          message: "☑ Praktikumsstelle erfolgreich bearbeitet",
          level: Levels.SUCCESS,
        });
      })
      .finally(() => {
        if (loading !== undefined) {
          loading.value = false;
        }
      });
  },
  isStudiumsPraktikumsstelle(stelle: Praktikumsstelle): boolean {
    return stelle.studiengang !== undefined;
  },
  isAusbildungsPraktikumsstelle(stelle: Praktikumsstelle): boolean {
    return stelle.ausbildungsrichtung !== undefined;
  },
};
