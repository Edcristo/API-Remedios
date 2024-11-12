// script.js
document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault();

    const login = document.getElementById("login").value;
    const senha = document.getElementById("senha").value;

    const response = await fetch("/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            login: login,
            senha: senha
        })
    });

    if (response.ok) {
        const data = await response.json();
        const token = data.token; // Armazena o token recebido do backend
        localStorage.setItem("tokenJWT", token); // Salva o token no localStorage

        // Faz uma requisição autenticada ao endpoint /remedio com o token JWT
        const remedioResponse = await fetch("/remedio", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,  // Inclui o token JWT no cabeçalho
                "Content-Type": "application/json"
            }
        });

        if (remedioResponse.ok) {
            const remedioData = await remedioResponse.json();
            console.log("Dados do Remédio:", remedioData);  // Exibe ou manipula os dados do endpoint /remedio
        } else {
            console.error("Falha ao acessar /remedio");
            alert("Acesso negado ao recurso /remedio.");
        }
    } else {
        document.getElementById("error-message").textContent = "Credenciais inválidas. Tente novamente.";
    }
});