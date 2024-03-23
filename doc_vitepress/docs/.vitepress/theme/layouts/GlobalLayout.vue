<script setup>
import DefaultTheme from "vitepress/theme";
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";

const { Layout } = DefaultTheme;
</script>

<template>
  <Layout>
    <template #layout-bottom>
      <div class="VPHOME">
        <div style="text-align: center">
          <VPLink href="/imprint/">Contact and Imprint</VPLink>
          <div>Made with 💛 in Munich</div>
        </div>
      </div>
    </template>
  </Layout>
</template>
