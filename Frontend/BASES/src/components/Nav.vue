<script setup lang="ts">
import { inject, reactive, ref, type Ref, watch } from "vue";
import { vwDetector } from "../Composables/vwDetector.ts";
import Login from "./Login.vue";
import { useCompte } from "../Composables/useCompte.ts";

const isMobile: Ref<boolean> = vwDetector();
const isMobileNav: Ref<boolean> | undefined = inject("isMobileNav");
const showLog = ref(inject("showLog"));
const transactions=inject("transactions");

watch(isMobile, () => {
  if (isMobile) {
    showLog.value = false;
  }
});

const menuItems = reactive([
  { label: "Dashboard", icon: "dashboard", to: "/Dashboard" },
  { label: "Deposit", icon: "arrow_circle_up", to: "/Transaction" },
  { label: "Withdrawal", icon: "arrow_circle_down", to: "/Transaction" },
  { label: "Transfer", icon: "swap_horiz", to: "/Transaction" },
]);

const { state } = useCompte();
</script>

<template>
  <div
      :class="{
      'w-full flex flex-col gap-2 overflow-hidden': true,
      'transition-all duration-500': isMobileNav && isMobile,
      '!h-0': !isMobileNav && isMobile,
      'h-full': !isMobile
    }"
  >
    <!-- Navigation -->
    <nav class="w-full text-slate-700 p-4 bg-gray-50 rounded-lg shadow-sm">
      <ul class="flex flex-col gap-2">
        <ul
            v-for="item in menuItems"
            :key="item.label"
            @click="()=>{
              if(transactions) transactions.scrollIntoView({behavior:'smooth'});
            }"
            class="flex items-center gap-3 p-3 rounded-lg hover:bg-blue-100 transition"
        >
          <span class="material-symbols-rounded text-blue-600">{{ item.icon }}</span>
          <span class="font-medium">{{ item.label }}</span>
        </ul>
      </ul>
    </nav>

    <!-- Footer -->
    <footer
        class="flex flex-col mt-auto gap-2 border-t text-blue-700 w-full border-gray-300 items-center p-2 mb-2 bg-gray-50 rounded-lg shadow-inner"
    >
      <!-- Login modal -->
      <Login
          v-if="showLog"
          :class="{
          'fixed z-20 max-w-[50vw] backdrop-blur-sm bg-blue-100 rounded-lg inset-0': isMobile && showLog
        }"
      />

      <!-- User section -->
      <div
          @click="showLog=!showLog"
          class="w-full flex items-center justify-center gap-4 p-7 cursor-pointer hover:bg-blue-50 rounded-lg transition"
      >
        <i class="far fa-user text-blue-600"></i>
        <div class="flex flex-col items-start text-sm">
          <p v-if="!state.logResponse?.nom" class="font-bold">Sign In</p>
          <p v-if="!state.logResponse?.nom" class="text-xs text-gray-400">Come_n_bank</p>
          <p v-else class="font-bold text-blue-700">{{ state.logResponse?.nom }}</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.material-symbols-outlined {
  font-variation-settings:
      'FILL' 0,
      'wght' 100,
      'GRAD' -25,
      'opsz' 24;
}
</style>
