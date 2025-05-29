// frontend/src/pages/register.tsx
import { Geist, Geist_Mono } from "next/font/google"
import { RegistrationForm } from "@/components/RegistrationForm"
import Link from "next/link"

const geistSans = Geist({ variable: "--font-geist-sans", subsets: ["latin"] })
const geistMono = Geist_Mono({ variable: "--font-geist-mono", subsets: ["latin"] })

export default function RegisterPage() {
    return (
        <div
            className={`
        ${geistSans.className} ${geistMono.className}
        font-[family-name:var(--font-geist-sans)]
        bg-[var(--background)] text-[var(--foreground)]
        min-h-screen flex items-center justify-center
      `}
        >
            {/* 2-column grid, centered */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-12 w-full max-w-6xl px-4">

                {/* Left: Registration box */}
                <div className="flex items-center justify-center">
                    <div className="w-full max-w-md bg-white p-8 rounded-lg shadow-lg">
                        <h2 className="text-2xl font-semibold mb-6 text-center">
                            Create Account
                        </h2>
                        <RegistrationForm />
                        <p className="mt-4 text-center text-[var(--foreground)]/70">
                            Already have an account?{" "}
                            <Link href="/" className="text-blue-600 hover:underline">
                                Sign in
                            </Link>
                        </p>
                    </div>
                </div>

                {/* Right: Intro text */}
                <div className="flex flex-col items-center justify-center">
                    <h1 className="text-4xl md:text-5xl font-semibold mb-4 text-center">
                        Start Your Learning Journey
                    </h1>
                    <p className="text-lg md:text-xl text-[var(--foreground)]/70 max-w-lg text-center">
                        Sign up to manage your study flow, set goals, and track progress
                        seamlessly. Join our community of learners today!
                    </p>
                </div>
            </div>
        </div>
    )
}
