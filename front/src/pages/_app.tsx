import type { AppProps } from 'next/app';
import { GlobalStyle } from '@/styles/global-style';
import { ThemeProvider } from 'styled-components';
import { theme } from '../styles/theme';

export default function App({ Component, pageProps }: AppProps) {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Component {...pageProps} />
    </ThemeProvider>
  );
}
