<template>
  <div class="dashboard-container">
    <div v-html = "htmlPage">{{ htmlPage }}</div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import index from '@/api/edu/banner'
export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['name', 'roles'])
  },
  data() {
    return {
      htmlPage: ''
    }
  },
  mounted() {
    this.getHtml()
  },
  methods: {
    getHtml() {
      index.getHtmlInfo(3).then(response => {
        this.htmlPage = response.data.eduHtml.htmlPage
      })
    }
  }
}
</script>
