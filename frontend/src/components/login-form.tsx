// frontend/src/components/login-form.tsx
"use client";

import { useState } from "react";

interface LoginDto {
    email: string;
    password: string;
}

export default function LoginForm() {
    const [form, setForm] = useState<LoginDto>({ email: "", password: "" });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleChange =
        (field: keyof LoginDto) =>
            (e: React.ChangeEvent<HTMLInputElement>) => {
                setForm({ ...form, [field]: e.target.value });
            };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const res = await fetch(
                `${process.env.NEXT_PUBLIC_JAVA_BACKEND_URL}/api/auth/login`,
                {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(form),
                }
            );
            if (!res.ok) {
                const body = await res.json().catch(() => null);
                throw new Error(body?.message || `Login failed (${res.status})`);
            }
            // TODO: handle success (store token, redirect…)
        } catch (err: any) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            {error && (
                <div className="p-3 bg-red-100 text-red-800 rounded">{error}</div>
            )}

            <div className="space-y-2">
                <label htmlFor="email" className="block text-sm font-medium">
                    Email
                </label>
                <input
                    id="email"
                    type="email"
                    value={form.email}
                    onChange={handleChange("email")}
                    required
                    disabled={loading}
                    placeholder="you@example.com"
                    className="w-full p-2 border rounded"
                />
            </div>

            <div className="space-y-2">
                <label htmlFor="password" className="block text-sm font-medium">
                    Password
                </label>
                <input
                    id="password"
                    type="password"
                    value={form.password}
                    onChange={handleChange("password")}
                    required
                    disabled={loading}
                    placeholder="••••••••"
                    className="w-full p-2 border rounded"
                />
            </div>

            <button
                type="submit"
                disabled={loading}
                className="w-full py-2 bg-black text-white rounded disabled:opacity-50"
            >
                {loading ? "Signing in…" : "Sign In"}
            </button>
        </form>
    );
}
