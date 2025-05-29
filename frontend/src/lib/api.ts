// frontend/src/lib/api.ts

export interface RegisterDto {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export interface LoginDto {
    email: string;
    password: string;
}

const BASE_URL = process.env.NEXT_PUBLIC_JAVA_BACKEND_URL ?? "http://localhost:8080";

export async function registerUser(data: RegisterDto) {
    const res = await fetch(`${BASE_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
    if (!res.ok) {
        const err = await res.text();
        throw new Error(`Register failed (${res.status}): ${err}`);
    }
    return res.json();
}

export async function loginUser(data: LoginDto) {
    const res = await fetch(`${BASE_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
    if (res.status === 401 || res.status === 404) {
        throw new Error("NOT_FOUND");
    }
    if (!res.ok) {
        const err = await res.text();
        throw new Error(`Login failed (${res.status}): ${err}`);
    }
    return res.json();
}
