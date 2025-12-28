<template>
  <div id="Dashboard" class="size-full overflow-y-auto pb-10 gap-10 flex flex-col p-6 bg-gray-50">
    <div class="flex flex-col text-slate-800 w-full items-start gap-2">
      <p class="text-2xl font-semibold">Welcome!</p>
      <p class="font-light text-sm text-gray-500">Financial overview</p>
    </div>

    <div class="flex flex-col text-slate-800 w-full 2xl:w-1/2 gap-4">
      <p class="text-slate-400 w-full text-right">Brief Overview</p>
      <div v-if="!state" class="text-gray-400 italic">
        No account
      </div>
      <div v-else-if="!state.logResponse" class="text-gray-400 italic">
        Loading account data...
      </div>

      <div v-else class="flex flex-col lg:flex-row gap-4 w-full">
        <div v-for="dash in dashValues" :key="dash.label" class="flex w-full">
          <div class="bg-white w-full text-slate-700 shadow-md rounded-xl flex flex-col gap-5 items-start p-5">
            <div class="flex items-center justify-between w-full">
              <p class="font-medium">{{ dash.label }}</p>
              <span :class="`${dash.color} material-symbols-rounded`">{{ dash.icon }}</span>
            </div>
            <div class="flex flex-col w-full items-start gap-1">
              <span class="font-semibold text-lg">{{ dash.amount }} FCFA</span>
              <p
                  class="text-sm font-light animate-pulse ml-1"
                  :class="dash.color"
                  @click="()=>{
                    if(transactions) {
                      transactions.scrollIntoView({behavior:'smooth'})
                    }
                  }"
              >{{ dash.animate }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="flex flex-col 2xl:w-1/2 text-slate-800 w-full gap-4 h-auto   ">
      <p class="text-slate-400 w-full text-right">Last Transactions</p>
      <div class="flex flex-col w-full gap-2 p-4 h-full max-h-101 overflow-y-auto shadow-md rounded-xl bg-white">
        <div
            v-if="!state.logResponse?.transactions?.length"
            class="text-gray-400 italic"
        >
          No transactions yet
        </div>
        <div
            v-else
            v-for="(transaction, index) in state.logResponse?.transactions"
            :key="index"
            class="rounded-xl p-3 flex justify-between bg-slate-100 hover:bg-slate-200 transition"
        >
          <p class="font-medium">{{ transaction.type }}</p>
          <div class="font-semibold">{{ transaction.amount }} FCFA</div>
        </div>
      </div>
    </div>

    <!-- Quick Actions -->
    <div ref="transactions" class="flex flex-col lg:flex-row lg:flex-wrap 2xl:w-1/2 text-slate-800 w-full gap-6">
      <div
          v-for="values in quickValues"
          :key="values.label"
          class="w-full px-5 py-4 flex flex-col gap-5 bg-white rounded-xl shadow-md"
      >
        <div class="flex flex-col w-full items-start">
          <p class="font-semibold text-lg">{{ values.label }} Funds</p>
          <p class="text-slate-600 text-sm">Add money to your account</p>
        </div>
        <div class="flex flex-col gap-5 w-full p-4 rounded-xl border border-gray-200">
          <fieldset class="w-full flex flex-col gap-3">
            <input
                v-model="values.amount"
                type="number"
                placeholder="0 FCFA"
                class="w-full bg-slate-100 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
            />
            <input
                v-show="values.label === 'Transfer'"
                v-model="values.dest"
                placeholder="Destinator account number"
                class="w-full bg-slate-100 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-green-400"
            />
          </fieldset>
          <button
              @click="async () => {
              await initiateTransaction(state.logResponse?.accountNumber, values.dest, values.amount, values.action);
              values.amount = null;
              values.dest = '';
              await refreshCompte();
            }"
              :class="`${values.color} text-white rounded-lg cursor-pointer py-2 px-4 hover:opacity-90 transition`"
              :aria-label="`Perform ${values.label}`"
          >
            Complete
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, inject, onMounted, type Ref} from "vue";
import { useCompte } from "../Composables/useCompte.ts";
import { useBank } from "../Composables/useBank.ts";

const { state,refreshCompte ,log} = useCompte();
const { initiateTransaction } = useBank();
const transactions=inject<Ref<HTMLElement|null>>("transactions");

const user=(localStorage.getItem("user"));
const obj = user ? JSON.parse(user):null;
onMounted(() => {
  if (obj && obj.accountNumber && obj.telephone && obj.password) {
    log(obj.accountNumber, obj.telephone, obj.password);
    console.log("Auto-login with:", obj);
  } else {
    console.log("No valid user in localStorage");
  }
});

const dashValues = computed(() => [
  {
    label: "Total Balance",
    amount: state.logResponse?.balance ?? "***",
    animate: "Account",
    color: "text-green-600",
    icon: "account_balance_wallet",
  },
  {
    label: "Last Deposit",
    amount: state.logResponse?.status.lastd ?? "***",
    animate: "Deposit",
    color: "text-blue-500",
    icon: "arrow_outward",
  },
  {
    label: "Last Withdraw",
    amount: state.logResponse?.status.lastr ?? "***",
    animate: "Withdraw",
    color: "text-red-400",
    icon: "arrow_outward",
  },
]);

const quickValues: any = computed(() => [
  {
    label: "Transfer",
    color: "bg-green-600",
    icon: "swap_horiz",
    amount: "",
    dest: "",
    action: "TRANSFERT",
  },
  {
    label: "Deposit",
    color: "bg-blue-600",
    icon: "arrow_outward",
    amount: "",
    action: "DEPOT",
  },
  {
    label: "Withdrawal",
    color: "bg-red-600",
    icon: "arrow_outward",
    amount: "",
    action: "RETRAIT",
  },
]);
</script>
