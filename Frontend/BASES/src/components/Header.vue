<template>
  <div
      :class="{'w-full px-3 border-b border-slate-300  h-15 flex items-center justify-between':isMobile,
      'childFull w-full max-w-60 h-full  flex flex-col items-start justify-between border-r-2 border-slate-300 ':!isMobile
  }"
  >
    <div class="flex !py-3 !px-4 justify-start items-center border-b border-b-gray-300 gap-3 ">
      <div class="size-9 p-1 rounded-xl bg-button content-center gap-3">
        <div class="size-full rounded-full bg-opacity content-center">
          <i class="fa-duotone fas fa-coins text-white "></i>
        </div>
      </div>
      <span class="font-bold ">Amphi_Bank</span>
    </div>
    <template v-if="!isMobile">
      <Nav />
    </template>
    <div @click="showLog=!showLog" v-if="isMobile" class="h-8/10 shadow-[0_0_2px_blue]  ml-auto mr-3 flex [&>*]:content-center  rounded-2xl ">
      <p class="px-2 font-bold text-blue-700 rounded-l-2xl">{{state.logResponse?.nom??"SignIn"}}</p>
      <div class="px-2 rounded-r-2xl bg-blue-200 size-full">
        <i class="far flex-1 fa-user "></i>
      </div>

    </div>
    <div v-if="isMobile" class="flex justify-center items-center  ">
      <div @click="isMobileNav=!isMobileNav" class="icon p-2 hov">
        <i :class="['fa',isMobileNav?'fa-xmark':'fa-bars']"></i>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { vwDetector} from "../Composables/vwDetector.ts";
  import Nav from "./Nav.vue";
  import {inject, type Ref} from "vue";
  import {useCompte} from "../Composables/useCompte.ts";
  const isMobile:Ref<boolean> = vwDetector();
  const showLog=inject("showLog");
  const {state}=useCompte();
  const isMobileNav:Ref<boolean> | undefined = inject("isMobileNav");
</script>

<style scoped>

</style>