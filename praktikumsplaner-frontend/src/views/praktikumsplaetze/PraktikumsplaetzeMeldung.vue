<template>
  <v-container class="pa-xl-8">
    <page-title
      back-button-url="/praktikumsplaetze"
      page-header-text="Praktikumsplatz melden"
      class="mb-2"
    />
    <div v-if="loadingSite">
      <v-skeleton-loader type="image" />
      <v-spacer />
      <v-skeleton-loader type="image" />
    </div>
    <div v-else>
      <v-form
        v-if="canStellenBeSubmitted()"
        ref="form"
        lazy-validation
      >
        <v-sheet
          border
          rounded
          class="pa-5 mb-3"
        >
          <v-row>
            <v-col>
              <span class="text-h6">Richtung</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="6">
              <v-select
                v-model="praktikumsstelle.richtung"
                :items="richtungOptions"
                item-title="longName"
                item-value="value"
                :hint="selectedArtHint"
                persistent-hint
                label="Richtung"
                :rules="[useRules().notEmptyRule()]"
              >
                <template #item="{ item, props }">
                  <v-list-subheader v-if="item.raw.header">{{
                    item.raw.header
                  }}</v-list-subheader>
                  <v-list-item
                    v-else
                    v-bind="props"
                  />
                </template>
              </v-select>
            </v-col>
          </v-row>
        </v-sheet>
        <v-sheet
          border
          rounded
          class="pa-5 mb-3"
        >
          <v-row>
            <v-col>
              <span class="text-h6">Stellenbeschreibung</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="5">
              <dienststellen-input
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <dringlichkeit-select
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
            <v-col cols="1">
              <dringlichkeit-tooltip />
            </v-col>
            <v-col>
              <namentliche-anforderung-input
                v-model="praktikumsstelle"
                :is-required="false"
              />
            </v-col>
            <v-col cols="1">
              <namentliche-anforderung-tooltip />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <planstelle-radio-group
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <taetigkeiten-input
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
          <v-row v-if="art === 'AUSBILDUNG'">
            <v-col>
              <projektarbeit-radio-group
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
            <v-col cols="1">
              <projektarbeit-tooltip />
            </v-col>
          </v-row>
        </v-sheet>

        <!-- STUDIUM specific -->
        <v-sheet
          v-if="art === 'STUDIUM'"
          border
          rounded
          class="pa-5 mb-3"
        >
          <v-row>
            <v-col>
              <span class="text-h6">Nachwuchskraft (Studium)</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <semester-select
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="6">
              <programmier-kenntnisse-select
                v-model="praktikumsstelle"
                :is-required="true"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="11">
              <wuensche-input
                v-model="praktikumsstelle"
                :is-required="false"
              />
            </v-col>
            <v-col>
              <wuensche-tooltip />
            </v-col>
          </v-row>
        </v-sheet>

        <!-- AUSBILDUNG specific -->
        <v-sheet
          v-if="art === 'AUSBILDUNG'"
          border
          rounded
          class="pa-5 mb-3"
        >
          <v-row>
            <v-col>
              <span class="text-h6">Nachwuchskraft (Ausbildung)</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <ausbildungs-jahr-select
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="6">
              <programmier-kenntnisse-select
                v-model="praktikumsstelle"
                :is-required="false"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <minderjaehrig-moeglich-radio-group
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
            <v-col cols="1">
              <minderjaehrig-moeglich-tooltip />
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="11">
              <wuensche-input
                v-model="praktikumsstelle"
                :is-required="false"
              />
            </v-col>
            <v-col>
              <wuensche-tooltip />
            </v-col>
          </v-row>
        </v-sheet>

        <!-- Ausbilder section in its own card -->
        <v-sheet
          border
          rounded
          class="pa-5 mb-3"
        >
          <v-row>
            <v-col>
              <span class="text-h6">örtliche*r Ausbilder*in</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <ausbilder-input
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
            <v-col cols="1" />
            <v-col>
              <ausbilder-email-input
                v-model="praktikumsstelle"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col>
              <ausbilder-erw-fuehrungszeugnis-checkbox
                v-model="praktikumsstelle"
              />
            </v-col>
          </v-row>
        </v-sheet>

        <v-sheet
          v-if="security.isAusbildungsleitung()"
          border
          rounded
          class="pa-5 mb-3"
        >
          <v-row>
            <v-col>
              <span class="text-h6">Meldezeitraum Auswahl</span>
            </v-col>
          </v-row>
          <v-row>
            <v-col cols="5">
              <meldezeitraum-select
                v-model="praktikumsstelle"
                :meldezeitraueme="meldezeitraeume"
                :is-required="true"
                :required-symbol="requiredFieldSymbol"
              />
            </v-col>
          </v-row>
        </v-sheet>
        <v-row>
          <v-col class="d-flex justify-end">
            <v-btn
              color="primary"
              @click="uploadPraktikumsstelle"
              >speichern</v-btn
            >
          </v-col>
        </v-row>
      </v-form>
      <kein-meldezeitraum-message v-else />
    </div>
    <progress-circular-overlay :loading="loading" />
  </v-container>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";

import MeldezeitraumService from "@/api/MeldezeitraumService";
import MeldungService from "@/api/PraktikumsstellenService";
import PageTitle from "@/components/common/PageTitle.vue";
import ProgressCircularOverlay from "@/components/common/ProgressCircularOverlay.vue";
import AusbildungsJahrSelect from "@/components/praktikumsplaetze/Meldung/AusbildungsJahrSelect.vue";
import DienststellenInput from "@/components/praktikumsplaetze/Meldung/DienststellenInput.vue";
import DringlichkeitSelect from "@/components/praktikumsplaetze/Meldung/DringlichkeitSelect.vue";
import DringlichkeitTooltip from "@/components/praktikumsplaetze/Meldung/DringlichkeitTooltip.vue";
import KeinMeldezeitraumMessage from "@/components/praktikumsplaetze/Meldung/KeinMeldezeitraumMessage.vue";
import MeldezeitraumSelect from "@/components/praktikumsplaetze/Meldung/MeldezeitraumSelect.vue";
import AusbilderInput from "@/components/praktikumsplaetze/Meldung/AusbilderInput.vue";
import AusbilderEmailInput from "@/components/praktikumsplaetze/Meldung/AusbilderEmailInput.vue";
import AusbilderErwFuehrungszeugnisCheckbox from "@/components/praktikumsplaetze/Meldung/AusbilderErwFuehrungszeugnisCheckbox.vue";
import MinderjaehrigMoeglichRadioGroup from "@/components/praktikumsplaetze/Meldung/MinderjaehrigMoeglichRadioGroup.vue";
import MinderjaehrigMoeglichTooltip from "@/components/praktikumsplaetze/Meldung/MinderjaehrigMoeglichTooltip.vue";
import NamentlicheAnforderungInput from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungInput.vue";
import NamentlicheAnforderungTooltip from "@/components/praktikumsplaetze/Meldung/NamentlicheAnforderungTooltip.vue";
import PlanstelleRadioGroup from "@/components/praktikumsplaetze/Meldung/PlanstelleRadioGroup.vue";
import ProgrammierKenntnisseSelect from "@/components/praktikumsplaetze/Meldung/ProgrammierKenntnisseSelect.vue";
import ProjektarbeitRadioGroup from "@/components/praktikumsplaetze/Meldung/ProjektarbeitRadioGroup.vue";
import ProjektarbeitTooltip from "@/components/praktikumsplaetze/Meldung/ProjektarbeitTooltip.vue";
import SemesterSelect from "@/components/praktikumsplaetze/Meldung/StudiensemesterSelect.vue";
import TaetigkeitenInput from "@/components/praktikumsplaetze/Meldung/TaetigkeitenInput.vue";
import WuenscheInput from "@/components/praktikumsplaetze/Meldung/WuenscheInput.vue";
import WuenscheTooltip from "@/components/praktikumsplaetze/Meldung/WuenscheTooltip.vue";
import { useRules } from "@/composables/rules";
import { useSecurity } from "@/composables/security";
import router from "@/plugins/router";
import { useUserStore } from "@/stores/user";
import Meldezeitraum from "@/types/Meldezeitraum";
import Praktikumsstelle from "@/types/Praktikumsstelle";

const requiredFieldSymbol = "*";

const userStore = useUserStore();
const praktikumsstelle = ref<Praktikumsstelle>(
  new Praktikumsstelle(userStore.department)
);
const loadingSite = ref<boolean>(true);
const loading = ref<boolean>(false);
const security = useSecurity();
const form = ref<HTMLFormElement>();
const meldezeitraeume = computed(() => {
  let list: Meldezeitraum[] = [];
  if (currentMeldezeitraum.value) {
    list = [currentMeldezeitraum.value];
  }
  return [
    ...list,
    ...upcomingMeldezeitraeume.value,
    ...passedMeldezeitraeume.value,
  ];
});
const currentMeldezeitraum = ref<Meldezeitraum>();
const upcomingMeldezeitraeume = ref<Meldezeitraum[]>([]);
const passedMeldezeitraeume = ref<Meldezeitraum[]>([]);
const route = router.currentRoute.value;

const richtungOptions = computed(() => [
  { header: "Studium" },
  { value: "BSC", longName: "Bachelor of Science - Informatik" },
  { value: "BWI", longName: "Wirtschaftsinformatik kommunal" },
  { value: "VI", longName: "Diplomverwaltungsinformatiker*in" },
  { value: "LLB", longName: "Bachelor of Laws" },
  { value: "PUMA", longName: "Public Management/Public Sector" },
  { value: "QE3", longName: "Diplomverwaltungswirt*in" },
  { header: "Ausbildung" },
  { value: "FISI", longName: "Fachinformatiker*in für Systemintegration" },
  { value: "QE2", longName: "Verwaltungswirt*in" },
  { value: "KFB", longName: "Kaufleute für Büromanagement" },
  { value: "VFAK", longName: "Verwaltungsfachangestellte*r kommunal" },
]);

const art = computed(() => {
  const value = praktikumsstelle.value.richtung;
  if (!value) return undefined;
  const studium = ["BSC", "BWI", "VI", "LLB", "PUMA", "QE3"];
  return studium.includes(value) ? "STUDIUM" : "AUSBILDUNG";
});

const selectedArtHint = computed(() =>
  art.value === "STUDIUM"
    ? "Art: Studium"
    : art.value === "AUSBILDUNG"
      ? "Art: Ausbildung"
      : ""
);

onMounted(() => {
  MeldezeitraumService.getCurrentMeldezeitraum(loadingSite).then(
    (zeitraueme) => {
      currentMeldezeitraum.value = zeitraueme[0];
    }
  );

  if (security.isAusbildungsleitung()) {
    getUpcomingMeldezeitraeume();
    getPassedMeldezeitraeume();
  }

  if (userStore.username) {
    redirectIfUnauthorized();
  } else {
    watch(
      () => userStore.roles,
      () => {
        redirectIfUnauthorized();
      }
    );
  }
});

function redirectIfUnauthorized() {
  const requiresRoles =
    route.meta.requiresRole != undefined
      ? (route.meta.requiresRole as string[])
      : undefined;
  const security = useSecurity();
  if (requiresRoles !== undefined && !security.checkForAnyRole(requiresRoles)) {
    router.push("/AccessDenied");
  }
}

function canStellenBeSubmitted() {
  return security.isAusbildungsleitung() || currentMeldezeitraum.value;
}

function getUpcomingMeldezeitraeume() {
  MeldezeitraumService.getUpcomingMeldezeitraueme(undefined).then(
    (zeitraeume) => {
      upcomingMeldezeitraeume.value = zeitraeume;
    }
  );
}

function getPassedMeldezeitraeume() {
  MeldezeitraumService.getPassedMeldezeitraueme(undefined).then(
    (zeitraeume) => {
      passedMeldezeitraeume.value = zeitraeume;
    }
  );
}

function resetForm() {
  form.value?.reset();
  router.push("/praktikumsplaetze");
}

function uploadPraktikumsstelle() {
  form.value?.validate().then((validation: { valid: boolean }) => {
    if (!validation.valid) return;
    const isAL = security.isAusbildungsleitung();
    MeldungService.uploadPraktikumsstelle(
      praktikumsstelle.value,
      loading,
      isAL
    ).then(() => {
      resetForm();
    });
  });
}
</script>
