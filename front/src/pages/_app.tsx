import type { AppProps } from 'next/app';
import '../styles/global-style.css';
import 'semantic-ui-css/semantic.min.css';
import '../styles/variables.css';
import '../styles/calendar/C_calendarApi.css';
import '../styles/Editor.css';
import Script from 'next/script';

export default function App({ Component, pageProps }: AppProps) {
  return (
    <>
      <Script
        strategy='beforeInteractive'
        src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAOMAP_APPKEY}&autoload=false&libraries=services`}
      />
      <Component {...pageProps} />
    </>
  );
}
