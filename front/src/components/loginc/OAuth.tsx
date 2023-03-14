import style from '../../styles/Login/C_LoginBox.module.css';
import axios from 'axios';

const OAuthBox = () => {
  const handleGoogleLogin = () => {
    axios
      .get(
        'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google'
      )
      .then((res) => {
        console.log(res);
        window.open(
          'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google'
        );
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleNaverLogin = () => {
    axios
      .get(
        'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/naver'
      )
      .then((res) => {
        console.log(res.data);
        window.open(
          'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/naver'
        );
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleKakaoLogin = () => {
    axios
      .get(
        'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/kakao'
      )
      .then((res) => {
        console.log(res.data);
        window.open(
          'http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/kakao'
        );
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <h3 className={style.OAuthText}>Login with</h3>
      <div className={style.OAuth2}>
        <button className={style.OAuthBtn} onClick={handleGoogleLogin}>
          <img src='assets/LoginP/GoogleOAuth.png' height='55px' />
        </button>
        <button className={style.OAuthBtn} onClick={handleNaverLogin}>
          <img src='assets/LoginP/NaverOAuth.png' height='55px' />
        </button>
        <button className={style.OAuthBtn} onClick={handleKakaoLogin}>
          <img src='assets/LoginP/KakaoOAuth.png' height='55px' />
        </button>
      </div>
    </>
  );
};

export default OAuthBox;
