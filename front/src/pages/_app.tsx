import type { AppProps } from 'next/app';
import '../styles/global-style.css';
import 'semantic-ui-css/semantic.min.css';
import '../styles/variables.css';
import '../styles/calender/C_calenderApi.css';
import '../styles/Editor.css';

export default function App({ Component, pageProps }: AppProps) {
  return <Component {...pageProps} />;
}
