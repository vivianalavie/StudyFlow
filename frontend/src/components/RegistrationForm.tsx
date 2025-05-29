"use client";

import { useState } from "react";
import { registerUser, RegisterDto } from "../lib/api";

export function RegistrationForm() {
    const [form, setForm] = useState<RegisterDto>({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
    });
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [success, setSuccess] = useState(false);

    const handleChange =
        (field: keyof RegisterDto) =>
            (e: React.ChangeEvent<HTMLInputElement>) => {
                setForm({ ...form, [field]: e.target.value });
            };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        if (form.password !== confirmPassword) {
            setError("Passwords do not match.");
            return;
        }

        setLoading(true);
        try {
            await registerUser(form);
            setSuccess(true);
            setForm({ firstName: "", lastName: "", email: "", password: "" });
            setConfirmPassword("");
        } catch (err: any) {
            setError(err.message || "Registration failed.");
        } finally {
            setLoading(false);
        }
    };

    if (success) {
        return (
            <div className="p-6 bg-green-100 rounded">
                <h2 className="text-green-800">Success!</h2>
                <p>Your account has been created. You can now sign in.</p>
            </div>
        );
    }

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
            {error && (
                <div className="p-3 bg-red-100 text-red-800 rounded">{error}</div>
            )}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <input
                    type="text"
                    placeholder="First Name"
                    value={form.firstName}
                    onChange={handleChange("firstName")}
                    required
                    className="w-full p-2 border rounded"
                />
                <input
                    type="text"
                    placeholder="Last Name"
                    value={form.lastName}
                    onChange={handleChange("lastName")}
                    required
                    className="w-full p-2 border rounded"
                />
            </div>
            <input
                type="email"
                placeholder="Email"
                value={form.email}
                onChange={handleChange("email")}
                required
                className="w-full p-2 border rounded"
            />
            <input
                type="password"
                placeholder="Password"
                value={form.password}
                onChange={handleChange("password")}
                required
                className="w-full p-2 border rounded"
            />
            <input
                type="password"
                placeholder="Confirm Password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="w-full p-2 border rounded"
            />
            <button
                type="submit"
                disabled={loading}
                className="w-full py-2 bg-black text-white rounded disabled:opacity-50"
            >
                {loading ? "Registering..." : "Create Account"}
            </button>
        </form>
    );
}
