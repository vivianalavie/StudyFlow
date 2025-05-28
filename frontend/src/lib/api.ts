
export interface RegisterDto {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
}

export interface RegisterResponse {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    createdAt: string;
}

const BASE_URL = process.env.NEXT_PUBLIC_JAVA_BACKEND_URL;

export async function registerUser(data: RegisterDto): Promise<RegisterResponse> {
    const res = await fetch(`${BASE_URL}/api/users/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error(`Register failed: ${res.statusText}`);
    return res.json();
}
