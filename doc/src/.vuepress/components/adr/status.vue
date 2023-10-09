<template>
  <div>
    <span
      ><svg height="24px" viewBox="0 0 24 24">
        <path :d="svgPath" :fill="svgFillColor"></path></svg
    ></span>
    <span class="statusText">{{ statusI18nText }}</span>
  </div>
</template>

<script>
import {
  mdiThumbUpOutline,
  mdiThumbDownOutline,
  mdiAlertOutline,
  mdiProgressQuestion,
} from "@mdi/js";

const ACCEPTED = "accepted";
const REJECTED = "rejected";
const PROPOSED = "proposed";
const DEPRECATED = "deprecated";

const statusToColorMap = new Map([
  [ACCEPTED, "green"],
  [REJECTED, "red"],
  [PROPOSED, "gray"],
  [DEPRECATED, "orange"],
]);

const statusToI18NText = new Map([
  [
    "de-DE",
    new Map([
      [ACCEPTED, "angenommen"],
      [REJECTED, "abgelehnt"],
      [PROPOSED, "vorgeschlagen"],
      [DEPRECATED, "veraltet"],
    ]),
  ],
]);

const statusToIconPath = new Map([
  [ACCEPTED, mdiThumbUpOutline],
  [REJECTED, mdiThumbDownOutline],
  [PROPOSED, mdiProgressQuestion],
  [DEPRECATED, mdiAlertOutline],
]);
export default {
  props: {
    status: {
      type: ACCEPTED | REJECTED | PROPOSED | DEPRECATED,
      default: ACCEPTED,
    },
  },
  computed: {
    svgPath: {
      get() {
        return statusToIconPath.get(this.$props.status);
      },
    },
    svgFillColor: {
      get() {
        return statusToColorMap.get(this.$props.status);
      },
    },
    statusI18nText: {
      get() {
        return statusToI18NText.get(this.$lang).get(this.$props.status);
      },
    },
  },
};
</script>

<style scoped>
div {
  display: flex;
}

.statusText {
  margin-left: 4px;
}
</style>
