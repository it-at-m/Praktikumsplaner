<script setup>
import VPLink from "vitepress/dist/client/theme-default/components/VPLink.vue";
import DefaultTheme from "vitepress/theme";

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
