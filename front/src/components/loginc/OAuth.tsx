import style from '../../styles/Login/C_LoginBox.module.css';
import axios from 'axios';

const OAuthBox = () => {
  const handleGoogleLogin = () => {
    window.open(
      'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google'
    );
  };

  const handleNaverLogin = () => {
    window.open(
      'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/naver'
    );
  };

  const handleKakaoLogin = () => {
    window.open(
      'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/kakao'
    );
  };

  return (
    <>
      <h3 className={style.OAuthText}>Login with</h3>
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
