import styled from '../../styles/main/C_intro.module.css';
import Link from 'next/link';
import { useEffect } from 'react';

const Intro = () => {
  useEffect(() => {
    // 각 li 요소를 가져와서 반복문으로 처리합니다.
    const parts = document.querySelectorAll(`.${styled.part}`);
    console.log(parts);
    parts.forEach((part) => {
      const topPos = part.getBoundingClientRect().top;
      const screenHeight = window.innerHeight;
      // 최상단 위치에 도달했을 때 애니메이션 클래스를 추가합니다.
      if (topPos < screenHeight) {
        part.classList.add(`${styled.scrollIn}`);
      }
    });
    // 스크롤 이벤트 발생 시, 각 li 요소의 위치를 계산하여 애니메이션 클래스를 추가합니다.
    const handleScroll = () => {
      parts.forEach((part) => {
        const topPos = part.getBoundingClientRect().top;
        const screenHeight = window.innerHeight;
        if (topPos < screenHeight) {
          part.classList.add(`${styled.scrollIn}`);
        }
      });
    };
    window.addEventListener('scroll', handleScroll);
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <article className={styled.container}>
      <ul>
        <li className={`${styled.part} `}>
          <h1 className={styled.title}>몸의 변화를 한 눈에!</h1>
          <span className={styled.text}>
            헬핏은 사용자의 신체 정보를 저장하고 관리합니다. <br />
            변화하는 몸무게를 나만의 페이지에서 한 눈에 살펴보세요!
          </span>
          <img
            className={styled.img}
            src='/assets/Main/intro01.svg'
            alt='intro01'
          />
        </li>
        <li className={`${styled.part}`}>
          <h1 className={styled.title}>헬스를 위한 커뮤니티</h1>
          <span className={styled.text}>
            헬핏은 오직 헬스만을 위한 커뮤니티를 제공합니다. <br />
            전국의 다양한 헬스인들과 소통해보세요!
          </span>
          <img
            className={styled.img}
            src='/assets/Main/intro02.svg'
            alt='intro02'
          />
        </li>
        <li className={`${styled.part} ${styled.scrollIn}`}>
          <h1 className={styled.title}>내 손안의 지도</h1>
          <span className={styled.text}>
            헬핏만의 위치기반 서비스를 이용해서 주변의 헬스장을 찾아보세요.
            <br />
            크로스핏, 필라테스 등 카테고리 별로 확인할 수 있어요!
          </span>
          <img
            className={styled.img}
            src='/assets/Main/intro03.svg'
            alt='intro03'
          />
        </li>
        <li className={`${styled.part} ${styled.scrollIn}`}>
          <h1 className={styled.title}>무료로 서비스 이용하기</h1>
          <span className={styled.text}>
            헬핏의 모든 서비스를 무료로 만나보세요. <br />
            Chat GPT가 탑재된 챗봇 서비스도 즐길 수 있어요!
          </span>
          <Link href='/login'>
            <button className={styled.btn}>로그인 후 사용하기</button>
          </Link>
          <img
            className={styled.img}
            src='/assets/Main/intro04.svg'
            alt='intro04'
          />
        </li>
      </ul>
    </article>
  );
};

export default Intro;
