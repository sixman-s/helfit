import type { AppProps } from 'next/app';
import '../css/global-style.css';
import 'semantic-ui-css/semantic.min.css';
import '../css/variables.css';
import '../css/calendar/C_calendarApi.css';
import '../css/Editor.css';
import Script from 'next/script';

const App = ({ Component, pageProps }: AppProps) => {
  return (
    <>
      <Script
        strategy='beforeInteractive'
        src={`//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.NEXT_PUBLIC_KAKAOMAP_APPKEY}&autoload=false&libraries=services`}
      />
      <Component {...pageProps} />
    </>
  );
};

export default App;
