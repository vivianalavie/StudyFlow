
import Head from "next/head";
import { RegistrationForm } from "../components/RegistrationForm";

export default function RegisterPage() {
    return (
        <>
            <Head>
                <title>StudyFlow – Registrierung</title>
            </Head>
            <main className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
                <div className="max-w-md w-full bg-white p-8 rounded shadow">
                    <h1 className="text-2xl font-bold mb-4">Neues Konto erstellen</h1>
                    <RegistrationForm />
                </div>
            </main>
        </>
    );
}
