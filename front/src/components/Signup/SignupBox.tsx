import style from '../../styles/Signup/C_SignupBox.module.css';
import OAuthBox from '../Login/OAuth';

const SignupBox = () => {
  return (
    <>
      <div className={style.container}>
        <div className={style.leftbox}>left-box</div>
        <div className={style.rightbox}>
          <img className={style.svg_logo} src={'assets/LoginP/logo.svg'} />
        </div>
        <div className={style.OAuthbox}>
          <OAuthBox />
        </div>
      </div>
    </>
  );
};
export default SignupBox;
