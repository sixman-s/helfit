import type { AppProps } from 'next/app';
// import { useRouter } from 'next/router';
import '../styles/global-style.css';
import 'semantic-ui-css/semantic.min.css';
import '../styles/variables.css';
import '../styles/calendar/C_calendarApi.css';
import '../styles/Editor.css';
import Script from 'next/script';

const App = ({ Component, pageProps }: AppProps) => {
  // const router = useRouter();
  // const path = (/#!(\/.*)$/.exec(router.asPath) || [])[1];
  // if (path) {
  //   router.replace(path);
  // }

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
