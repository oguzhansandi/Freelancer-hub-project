
document.addEventListener("DOMContentLoaded", async function () {
    let token = (localStorage.getItem("jwtToken") || "").trim();
    if (!token.startsWith("Bearer ")) {
        token = `Bearer ${token}`;
    }

    if (!token || token === "Bearer ") {
        redirectToLogin("Token bulunamadı.");
        return;
    }

    try {
        const response = await fetch("/api/token/validate", {
            method: "GET",
            headers: {
                "Authorization": token // ✅ zaten Bearer içeriyor
            }
        });

        if (!response.ok) {
            throw new Error("Token geçersiz.");
        }

    } catch (err) {
        redirectToLogin(err.message);
    }
});

function redirectToLogin(reason) {
    console.warn("Redirect sebebi:", reason);
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("username");
    window.location.href = "/index.html";
}

