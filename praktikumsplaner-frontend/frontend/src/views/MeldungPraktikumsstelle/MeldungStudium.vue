<template>
    <v-form>
        <h3 style="margin-left: 30px">Praktikumstellen Meldung</h3>
        <v-container
            class="spacing-left"
            style="margin-top: 30px"
        >
            <v-row>
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.studienart"
                        label="Studienart"
                        :items="Studienart"
                        item-value="name"
                        item-text="value"
                        :rules="studiumsRule"
                        :menu-props="customMenuProps"
                        outlined
                        @change="changeVorrZuweisungsZeitraum()"
                    >
                    </v-select>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.studiensemester"
                        label="Studiensemester"
                        :items="Studiensemester"
                        item-value="name"
                        item-text="value"
                        :rules="studiumsRule"
                        :menu-props="customMenuProps"
                        outlined
                        @change="changeVorrZuweisungsZeitraum()"
                    >
                    </v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.dienststelle"
                        label="Konkrete Dienststelle*"
                        :rules="requiredRule"
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.referat"
                        :items="Referat"
                        :menu-props="customMenuProps"
                        item-value="name"
                        item-text="value"
                        label="Referat"
                        outlined
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.programmierkenntnisse"
                        label="Programmierkenntnisse"
                        :items="YesNo"
                        :menu-props="customMenuProps"
                        item-value="name"
                        item-text="value"
                        outlined
                    >
                    </v-select>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.projektarbeit"
                        label="Projektarbeit*"
                        :items="YesNo"
                        :menu-props="customMenuProps"
                        item-value="name"
                        item-text="value"
                        outlined
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.oertlicheAusbilder"
                        label="Name örtliche Ausbilder*in*"
                        :rules="requiredRule"
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.email"
                        label="E-mail örtliche Ausbilderin*"
                        :rules="emailRule"
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="praktikumsstelle.namentlicheAnforderung"
                        label="Namentliche Anforderung spez. gewünschter NWK"
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col>
                    <v-select
                        v-model="praktikumsstelle.dringlichkeit"
                        label="Dringlichkeit"
                        :items="Dringlichkeit"
                        :menu-props="customMenuProps"
                        item-value="name"
                        item-text="value"
                        :rules="requiredRule"
                        outlined
                    ></v-select>
                </v-col>
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-text-field
                        v-model="zeitraum"
                        label="Zeitraum NWK"
                        hint="Wählen Sie Art und Jahrgang des Stuzubis aus"
                        outlined
                        disabled
                        filled
                        background-color="grey"
                    ></v-text-field>
                </v-col>
                <v-col cols="2" />
                <v-col />
                <v-col cols="1" />
            </v-row>
            <v-row>
                <v-col>
                    <v-textarea
                        v-model="praktikumsstelle.taetigkeiten"
                        label="Aufgaben am Praktikumsplatz*"
                        :rules="requiredRule"
                        outlined
                        height="124px"
                    ></v-textarea>
                </v-col>
                <v-col cols="1" />
            </v-row>
        </v-container>
    </v-form>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import Praktikumsstelle from "@/types/Praktikumsstelle";
import { useRules } from "@/composables/rules";
import { useZeitraeume } from "@/composables/voraussichtlicherZuweisungsZeitraum";
import { Referat } from "@/types/Referat";
import { YesNo } from "@/types/YesNo";
import { Dringlichkeit } from "@/types/Dringlichkeit";
import { Studienart } from "@/types/Studienart";
import { Studiensemester } from "@/types/Studiensemester";

const praktikumsstelle = ref<Praktikumsstelle>(
    new Praktikumsstelle("", "", "", "", "")
);
const zeitraeueme = useZeitraeume();
const zeitraum = ref<string>("");
const isStudium = ref<boolean>(false);
const validationRules = useRules();
const studiumsRule = computed(() => {
    return [
        validationRules.notEmptyRuleAndVisible(
            isStudium.value,
            "Darf nicht leer sein!"
        ),
    ];
});
const requiredRule = [validationRules.notEmptyRule("Darf nicht leer sein!")];
const emailRule = [
    validationRules.notEmptyRule("Darf nicht leer sein!"),
    validationRules.emailRule(),
];
const customMenuProps = {
    offsetY: true,
};

function changeVorrZuweisungsZeitraum() {
    zeitraum.value = zeitraeueme.studiumsZeitraum(
        praktikumsstelle.value.studienart,
        praktikumsstelle.value.studiensemester
    );
}
</script>
<style>
.spacing-left {
    margin-left: 30px;
}
</style>