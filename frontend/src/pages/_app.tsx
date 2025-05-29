// frontend/src/pages/_app.tsx
import "../styles/globals.css"
import type { AppProps } from "next/app"
import Image from "next/image"
import Link from "next/link"

export default function MyApp({ Component, pageProps }: AppProps) {
    return (
        <>
            <header className="flex items-center px-12 py-6 bg-white shadow-md">
                <Link href="/" className="flex items-center">
                    <Image
                        src="/logo.png"
                        alt="StudyFlow Logo"
                        width={96}                // 96px wide
                        height={96}               // 96px tall
                        className="h-24 w-auto"   // h-24 = 6rem to match the text
                        priority
                    />
                    <span className="ml-6 text-3xl sm:text-3xl font-semibold">
            StudyFlow
          </span>
                </Link>
            </header>

            <Component {...pageProps} />
        </>
    )
}
