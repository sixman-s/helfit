// styled.d.ts 파일은 theme파일에 들어갈 변수들의 타입을 정하는 부분

import 'styled-components';

declare module 'styled-components' {
  export interface DefaultTheme {
    main: string;
    texts: {
      text_1: string;
      text_2: string;
      text_3: string;
      text_4: string;
      text_5: string;
    };
    lines: {
      line_1: string;
      line_2: string;
    };
    bgs: {
      bg_1: string;
      bg_2: string;
    };
    headers: {
      h1: string;
      h2: string;
      h3: string;
      h4: string;
      h5: string;
      h6: string;
    };
  }
}
