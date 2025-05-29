import "../styles/globals.css";
import type { AppProps } from "next/app";
import Image from "next/image";
import Link from "next/link";

export default function MyApp({ Component, pageProps }: AppProps) {
  return (
      <>
        {/* ——— Header mit Logo ——— */}
        <header className="flex items-center px-6 py-4 bg-white shadow-md">
          <Link href="/">
            <Image
                src="/logo.png"      // deine Datei aus public/logo.png
                alt="StudyFlow Logo"
                width={40}           // Höhe/Breite nach Belieben anpassen
                height={40}
                priority
            />
          </Link>
          <h1 className="ml-3 text-xl font-semibold">StudyFlow</h1>
        </header>

        {/* ——— Seiteninhalt ——— */}
        <main className="p-6">
          <Component {...pageProps} />
        </main>
      </>
  );
}