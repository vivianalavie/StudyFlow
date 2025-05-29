"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import { loginUser, LoginDto } from "../lib/api";

export default function LoginForm() {
    const router = useRouter();
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
            await loginUser(form);
            // on success, go to home/dashboard
            router.push("/");
        } catch (err: any) {
            if (err.message === "NOT_FOUND") {
                // no such user or bad creds → go to register
                router.push("/register");
            } else {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            {error && <div className="p-3 bg-red-100 text-red-800 rounded">{error}</div>}
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