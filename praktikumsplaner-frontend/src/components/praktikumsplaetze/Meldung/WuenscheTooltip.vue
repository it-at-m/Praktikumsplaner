<template>
  <v-tooltip location="right">
    <template #activator="{ props }">
      <v-icon
        class="mt-3"
        color="blue"
        size="large"
        v-bind="props"
        icon="mdi-information"
      />
    </template>
    <span>
      Z.B. zwingende Volljährigkeit, Vorerfahrungen, Verlängerungswünsche
    </span>
  </v-tooltip>
</template>
