import style from '../../styles/Login/C_LoginBox.module.css';

const URL = process.env.NEXT_PUBLIC_URL;

const OAuthBox = () => {
  const handleGoogleLogin = () => {
    window.open(`${URL}/oauth2/authorization/google`);
  };

  const handleNaverLogin = () => {
    window.open(`${URL}/oauth2/authorization/naver`);
  };

  const handleKakaoLogin = () => {
    window.open(`${URL}/oauth2/authorization/kakao`);
  };

  return (
    <>
      <div className={style.OAuthTitle}>
        <div className={style.line}></div>
        <span className={style.OAuthTitleText}>다른 방법으로 로그인</span>
        <div className={style.line}></div>
      </div>
      <div className={style.OAuth2}>
        <button className={style.OAuthBtn} onClick={handleGoogleLogin}>
          <img src='../../assets/LoginP/GoogleOAuth.svg' />
          <span className={style.OAuthText}>Google 계정으로 로그인</span>
        </button>
        <button className={style.OAuthBtn} onClick={handleNaverLogin}>
          <img src='../../assets/LoginP/NaverOAuth.svg' />
          <span className={style.OAuthText}>Naver 계정으로 로그인</span>
        </button>
        <button className={style.OAuthBtn} onClick={handleKakaoLogin}>
          <img src='../../assets/LoginP/KakaoOAuth.svg' />
          <span className={style.OAuthText}>Kakao 계정으로 로그인</span>
        </button>
      </div>
    </>
  );
};

export default OAuthBox;
