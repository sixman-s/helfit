import style from '../../styles/Login/C_LoginBox.module.css';

const OAuthBox = () => {
  return (
    <>
      <h3 className={style.OAuthText}>Login with</h3>
      <div className={style.OAuth2}>
        <button className={style.OAuthBtn}>
          <img src='assets/LoginP/GoogleOAuth.png' height='55px' />
        </button>
        <button className={style.OAuthBtn}>
          <img src='assets/LoginP/NaverOAuth.png' height='55px' />
        </button>
        <button className={style.OAuthBtn}>
          <img src='assets/LoginP/KakaoOAuth.png' height='55px' />
        </button>
      </div>
    </>
  );
};

export default OAuthBox;
