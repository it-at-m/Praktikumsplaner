<template>
  <div>
    <GlobalLayout></GlobalLayout>
    <div class="home"> <!-- set this class to allow our footer to look like the 'normal' homepage's footer -->
      <div class="footer">
        <NavLink :item="privacy"/>
        <NavLink :item="imprint"/>
        <div class="muc-footer">
            Made with 💛 in Munich
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.muc-footer {
  margin-top: 30px;
}
</style>

<script>
import GlobalLayout from '@vuepress/core/lib/client/components/GlobalLayout.vue'
import NavLink from '@parent-theme/components/NavLink.vue'

export default {
  components: {
    GlobalLayout,
    NavLink
  },
  computed: {
      privacy() {
          return { id: 'contact', text: 'Contact', link: '/contact/', }
      },
    imprint() {
      return { id: 'imprint', text: 'Imprint', link: '/imprint/', }
    }
  },
}
</script>
