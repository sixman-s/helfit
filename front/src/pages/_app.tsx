import type { AppProps } from 'next/app';
import '../styles/global-style.css';
import '../styles/variables.css';
import '../styles/calendar/C_calendarApi.css';

export default function App({ Component, pageProps }: AppProps) {
  return <Component {...pageProps} />;
}
