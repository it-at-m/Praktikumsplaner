import type { Ref } from "vue";

import { Levels } from "@/api/Error";
import {
  defaultResponseHandler,
  getDELETEConfigNoBody,
  getGETConfig,
  getPOSTConfig,
} from "@/api/FetchUtils";
import { API_BASE, MELDEZEITRAUM_BASE } from "@/constants";
import { useSnackbarStore } from "@/stores/snackbar";
import Meldezeitraum from "@/types/Meldezeitraum";

export default {
  create(
    meldezeitraum: Meldezeitraum,
    loading: Ref<boolean>
  ): Promise<Meldezeitraum> {
    loading.value = true;
    return fetch(
      `${API_BASE}${MELDEZEITRAUM_BASE}`,
      getPOSTConfig(meldezeitraum)
    )
      .then((response) => {
        if (response.ok) {
          useSnackbarStore().showMessage({
            message: "☑ Meldezeitraum erfolgreich angelegt",
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
  getCurrentMeldezeitraum(
    loading: Ref<boolean> | undefined
  ): Promise<Meldezeitraum[]> {
    if (loading !== undefined) {
      loading.value = true;
    }
    return fetch(
      `${API_BASE}${MELDEZEITRAUM_BASE}?period=current`,
      getGETConfig()
    )
      .then((response) => {
        defaultResponseHandler(response);
        return response.json();
      })
      .finally(() => {
        if (loading !== undefined) {
          loading.value = false;
        }
      });
  },
  getUpcomingMeldezeitraueme(
    loading: Ref<boolean> | undefined
  ): Promise<Meldezeitraum[]> {
    if (loading !== undefined) {
      loading.value = true;
    }
    return fetch(
      `${API_BASE}${MELDEZEITRAUM_BASE}?period=future`,
      getGETConfig()
    )
      .then((response) => {
        defaultResponseHandler(response);
        return response.json();
      })
      .finally(() => {
        if (loading !== undefined) {
          loading.value = false;
        }
      });
  },
  getPassedMeldezeitraueme(
    loading: Ref<boolean> | undefined
  ): Promise<Meldezeitraum[]> {
    if (loading !== undefined) {
      loading.value = true;
    }
    return fetch(`${API_BASE}${MELDEZEITRAUM_BASE}?period=past`, getGETConfig())
      .then((response) => {
        defaultResponseHandler(response);
        return response.json();
      })
      .finally(() => {
        if (loading !== undefined) {
          loading.value = false;
        }
      });
  },
  getAllMeldezeitraeume(
    loading: Ref<boolean> | undefined
  ): Promise<Meldezeitraum[]> {
    if (loading !== undefined) {
      loading.value = true;
    }
    return fetch(`${API_BASE}${MELDEZEITRAUM_BASE}`, getGETConfig())
      .then((response) => {
        defaultResponseHandler(response);
        return response.json();
      })
      .finally(() => {
        if (loading !== undefined) {
          loading.value = false;
        }
      });
  },
  deleteMeldezeitraumById(
    id: string | undefined,
    loading: Ref<boolean>
  ): Promise<void> {
    loading.value = true;
    return fetch(
      `${API_BASE}${MELDEZEITRAUM_BASE}/${id}`,
      getDELETEConfigNoBody()
    )
      .then((response) => {
        useSnackbarStore().showMessage({
          message: "☑ Löschen erfolgreich.",
          level: Levels.SUCCESS,
        });
        defaultResponseHandler(response);
      })
      .catch((err) => {
        useSnackbarStore().showMessage({
          message: err.message,
          level: Levels.ERROR,
        });
        defaultResponseHandler(err);
      })
      .finally(() => {
        loading.value = false;
      });
  },
};
