<template>
  <div class="w-full max-w-md overflow-y-auto max-h-110 p-6 rounded-xl shadow-md bg-white flex flex-col items-center gap-6">
    <!-- Header -->
    <h2 class="text-lg font-bold text-blue-600">
      {{ signIn ? "Login to your account" : "Create a new account" }}
    </h2>

    <!-- Form -->
    <form @submit.prevent="handleLog" class="w-full flex flex-col gap-4">
      <!-- Only for new account -->
      <input v-model="form.name" v-show="!signIn" :class="inputClass" placeholder="Full Name" />
      <input v-model="form.niu" v-show="!signIn" :class="inputClass" placeholder="National ID" />

      <!-- Only for login -->
      <input v-model="form.num" v-show="signIn" :class="inputClass" placeholder="Account Number" />

      <!-- Common fields -->
      <input v-model="form.tel" :class="inputClass" placeholder="Phone Number" />
      <input v-model="form.pass" type="password" :class="inputClass" placeholder="Password" />

      <!-- Submit button -->
      <button
          type="submit"
          class="w-full py-2 rounded-lg bg-blue-500 text-white font-semibold hover:bg-blue-600 transition"
      >
        {{ signIn ? "Connect" : "Create Account" }}
      </button>
    </form>

    <!-- Feedback messages -->
    <div class="w-full text-center mt-2">
      <p v-if="state.error" class="text-red-500 font-medium">{{ state.error }}</p>
      <p v-else-if="state.logResponse?.nom" class="text-green-600 font-medium">
        ✅ Login successful! Welcome {{ state.logResponse.nom }}
      </p>
    </div>

    <!-- Switch mode -->
    <p class="text-sm text-gray-600 mt-4">
      <span v-if="signIn">
        New here?
        <a @click="signIn=false" class="text-blue-600 font-bold cursor-pointer hover:underline">
          Create an account
        </a>
      </span>
      <span v-else>
        Already have an account?
        <a @click="signIn=true" class="text-blue-600 font-bold cursor-pointer hover:underline">
          Login
        </a>
      </span>
    </p>

    <!-- Close button for mobile -->
    <div
        v-show="isMobile"
        @click="showLog=!showLog"
        class="absolute bottom-4 right-4 border rounded-full p-3 bg-gray-100 hover:bg-gray-300 transition cursor-pointer"
    >
      <i class="fa fa-xmark fa-lg text-gray-700"></i>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, inject, onMounted, reactive, ref} from "vue";
import { vwDetector } from "../Composables/vwDetector.ts";
import { useBank } from "../Composables/useBank.ts";
import { useCompte } from "../Composables/useCompte.ts";

const isMobile = vwDetector();
const signIn = ref(true);
const inputClass =
    "border rounded-lg px-3 py-2 w-full focus:outline-none focus:ring-2 focus:ring-blue-400 transition";

const form = reactive({
  num: "",
  tel: "",
  pass: "",
  niu: "",
  name: ""
});

const showLog = inject("showLog");
const { createCompte } = useBank();
const { log, state } = useCompte();

const resetFields = () => {
  form.tel = "";
  form.pass = "";
  form.num = "";
  if (!signIn.value) {
    form.name = "";
    form.niu = "";
  }
};

const handleLog = async () => {
  if (signIn.value) {
    await log(form.num, form.tel, form.pass);
  } else {
    await createCompte(form.name, form.niu, form.tel, "STUDENT", form.pass);
  }
  resetFields();
};


</script>
