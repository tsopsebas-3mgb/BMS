import { reactive } from "vue";
import { useBank } from "./useBank.ts";

const { login, fetchCompte } = useBank();

const state = reactive({
    logResponse: null as any,
    error: null as string | null,
    loading: false
});

export function useCompte() {
    async function log(num: string, tel: string, pass: string) {
        state.loading = true;
        try {
            const result = await login(num, tel, pass);
            console.log("Login result:", result);

            if (!result || !result.log) {
                state.error = "Login échoué : identifiants incorrects";
                state.loading = false;
                return;
            }

            const data = await fetchCompte(result.log, result.accountNumber);
            console.log("Fetched compte:", data);

            if (!data) {
                state.error = "Impossible de récupérer le compte";
                state.loading = false;
                return;
            }

            state.logResponse = data;
            state.error = null;
        } catch (err: any) {
            state.error = `Erreur: ${err.message || err}`;
        } finally {
            state.loading = false;
        }
    }

    async function refreshCompte() {
        if (!state.logResponse?.accountNumber) return;
        const data = await fetchCompte(true, state.logResponse.accountNumber);
        if (data) { state.logResponse = data; } }

    function reset() {
        state.logResponse = null;
        state.error = null;
        state.loading = false;
    }

    return {
        state,
        log,
        refreshCompte,
        reset
    };
}

