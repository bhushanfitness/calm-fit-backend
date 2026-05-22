const BASE_URL = "http://localhost:8080";

export const api = async (url, options = {}) => {
    const response = await fetch(`${BASE_URL}${url}`, {
        credentials: "include",   // ✅ REQUIRED
        headers: {
            "Content-Type": "application/json",
            ...options.headers,
        },
        ...options,
    });

    return response;
};