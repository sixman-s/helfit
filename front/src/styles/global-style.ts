import { createGlobalStyle } from 'styled-components';
import { normalize } from 'styled-normalize';

export const GlobalStyle = createGlobalStyle`
@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css');

${normalize}
  html {
    box-sizing: border-box;
    font-size: 14px;
    min-width: 320px;
  }
  a { cursor: pointer; text-decoration: none; }
  body {
    
  }
`;
