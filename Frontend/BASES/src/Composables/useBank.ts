export const useBank = () => {
    // Liste des comptes
    const getComptes = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/banque/comptes", {
                method: "GET",
                headers: { "Accept": "application/json" }
            });
            const data = await response.json();
            return { data, error: null };
        } catch (error) {
            return { data: null, error: `Error ${error}` };
        }
    };

    // Création d’un compte
    const createCompte = async (nom: string, niu: string, tel: string, type: string, pass: string) => {
        try {
            const response = await fetch("http://localhost:8080/api/banque/comptes", {
                method: "POST",
                headers: { "Accept": "application/json", "Content-Type": "application/json" },
                body: JSON.stringify({
                    nom,
                    niu,
                    telephone: tel,
                    type,
                    password: pass
                })
            });
            const data = await response.json();
            return { data, error: null };
        } catch (error) {
            return { data: null, error: `Error ${error}` };
        }
    };

    // Login
    const login = async (num: string, tel: string, pass: string) => {
        try {
            const response = await fetch("http://localhost:8080/api/banque/comptes/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ num, tel, pass })
            });
            const data = await response.json();
            return data;
        } catch (error) {
            return { error: `Error ${error}` };
        }
    };

    // Fetch compte
    const fetchCompte = async (log: boolean, accountNumber: string) => {
        try {
            const response = await fetch(`http://localhost:8080/api/banque/comptes/login/${accountNumber}`, {
                method: "POST",
                headers: { "Accept": "application/json", "Content-Type": "application/json" },
                body: JSON.stringify({
                    "log":log,
                    "accountNumber":accountNumber })
            });
            if (!response.ok) return null;
            const data = await response.json();
            localStorage.setItem("user", JSON.stringify(data));

            return data;
        } catch (error) {
            return null;
        }
    };

    // Transaction
    const initiateTransaction = async (num1: string, num2: string, amount: number, type: string) => {
        try {
            const response = await fetch(`http://localhost:8080/api/banque/comptes/${num1}/transactions/initiate`, {
                method: "POST",
                headers: { "Accept": "application/json", "Content-Type": "application/json" },
                body: JSON.stringify({
                    accountNumber: num1,
                    destinatorAccountNumber: num2,
                    amount,
                    type
                })
            });
            const data = await response.json();
            return { data, error: null };
        } catch (error) {
            return { data: null, error: `Error ${error}` };
        }
    };

    // Transactions d’un compte
    const getTransactions = async (num: string) => {
        try {
            const response = await fetch(`http://localhost:8080/api/banque/comptes/${num}/transactions`, {
                method: "GET",
                headers: { "Accept": "application/json" }
            });
            const data = await response.json();
            return { data, error: null };
        } catch (error) {
            return { data: null, error: `Error ${error}` };
        }
    };

    return {
        getComptes,
        createCompte,
        login,
        fetchCompte,
        initiateTransaction,
        getTransactions
    };
};

