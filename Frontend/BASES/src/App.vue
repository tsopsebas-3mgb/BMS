<script setup lang="ts">
import  Header  from './components/Header.vue'
import {provide, ref, type Ref} from "vue";
import Nav from "./components/Nav.vue";
import {vwDetector} from "./Composables/vwDetector.ts";
import Dashboard from "./components/Dashboard.vue";
const isMobile = vwDetector()
const isMobileNav:Ref<boolean> = ref(false);
const showLog:Ref<boolean>=ref(false);
const transactions=ref<HTMLElement|null>(null);
provide("transactions",transactions);
provide("showLog",showLog)
provide("isMobileNav", isMobileNav);
</script>

<template>
  <div class="flex flex-col md:flex-row size-full">
  <Header/>
  <div id="router" class="size-full bg-secondary overflow-auto bar">
    <Nav v-if="isMobile"/>
    <Dashboard/>
  </div>
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
