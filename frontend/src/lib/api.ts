// frontend/src/lib/api.ts
export interface RegisterDto {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

// Lies dir die BASE_URL aus .env.local ein, mit Fallback
const BASE_URL = process.env.NEXT_PUBLIC_JAVA_BACKEND_URL ?? "http://localhost:8080";

export async function registerUser(data: RegisterDto) {
    const res = await fetch(`${BASE_URL}/api/users/register`, {
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
