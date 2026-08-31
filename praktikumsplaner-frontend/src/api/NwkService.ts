import type { Ref } from "vue";

import { Levels } from "@/api/Error";
import {
  defaultResponseHandler,
  getDELETEConfig,
  getGETConfig,
  getPOSTConfig,
  getPUTConfig,
} from "@/api/FetchUtils";
import { API_BASE, NWK_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Nwk from "@/types/Nwk";
import NwkCreate from "@/types/NwkCreate";

export default {
  saveNwk(nwk: NwkCreate, loading: Ref<boolean>): Promise<void> {
    loading.value = true;
    return fetch(`${API_BASE}${NWK_BASE}`, getPOSTConfig(nwk))
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ NWK wurde erfolgreich erstellt.",
            level: Levels.SUCCESS,
          });
        } else {
          defaultResponseHandler(response);
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  uploadExcelFile(excelDatei: File, loading: Ref<boolean>): Promise<void> {
    loading.value = true;
    // File Reader encodes as Base64
    return this.readString(excelDatei).then((base64string: string) => {
      return fetch(
        `${API_BASE}${NWK_BASE}/import`,
        // Base64 String starts after the comma
        getPOSTConfig(base64string.split(",")[1])
      )
        .then((response) => {
          if (response.ok) {
            useSnackbarStore().showMessage({
              message: "☑ Nachwuchskräfte erfolgreich angelegt.",
              level: Levels.SUCCESS,
            });
          } else {
            defaultResponseHandler(response);
          }
        })
        .finally(() => {
          loading.value = false;
        });
    });
  },
  readString(excelDatei: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        if (!(typeof reader.result == "string")) {
          return Promise.reject();
        }
        resolve(reader.result);
      };
      reader.onerror = reject;
      reader.readAsDataURL(excelDatei);
    });
  },
  getAllActiveNwks(): Promise<Nwk[]> {
    return fetch(`${API_BASE}${NWK_BASE}`, getGETConfig()).then((response) => {
      defaultResponseHandler(response);
      return response.json();
    });
  },
  getAllUnassignedNwks(): Promise<Nwk[]> {
    return fetch(
      `${API_BASE}${NWK_BASE}?state=UNASSIGNED`,
      getGETConfig()
    ).then((response) => {
      defaultResponseHandler(response);
      return response.json();
    });
  },
  updateNwk(nwk: Nwk, loading: Ref<boolean>): Promise<void> {
    loading.value = true;
    return fetch(`${API_BASE}${NWK_BASE}`, getPUTConfig(nwk))
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Nachwuchskraft wurde erfolgreich bearbeitet.",
            level: Levels.SUCCESS,
          });
        } else {
          defaultResponseHandler(response);
        }
      })
      .finally(() => {
        loading.value = false;
      });
  },
  deleteNwk(nwkId: string): Promise<void> {
    return fetch(`${API_BASE}${NWK_BASE}/${nwkId}`, getDELETEConfig({})).then(
      (response) => {
        defaultResponseHandler(response);
        useSnackbarStore().showMessage({
          message: "☑ NWK erfolgreich gelöscht",
          level: Levels.SUCCESS,
        });
      }
    );
  },
};
