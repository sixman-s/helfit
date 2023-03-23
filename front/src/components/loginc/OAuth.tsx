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
      <h4 className={style.OAuthText}> </h4>
      <div className={style.OAuth2}>
        <button className={style.OAuthBtn} onClick={handleGoogleLogin}>
          <img src='assets/LoginP/GoogleOAuth.png' height='45px' />
        </button>
        <button className={style.OAuthBtn} onClick={handleNaverLogin}>
          <img src='assets/LoginP/NaverOAuth.png' height='45px' />
        </button>
        <button className={style.OAuthBtn} onClick={handleKakaoLogin}>
          <img src='assets/LoginP/KakaoOAuth.png' height='45px' />
        </button>
      </div>
    </>
  );
};

export default OAuthBox;
