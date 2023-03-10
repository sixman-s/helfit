import login from '../styles/Login/Login.module.css';

const LoginP = () => {
  return (
    <>
      <div className={login.layout}>
        레이아웃
        <div className={login.sidebar}>사이드바</div>
        <div className={login.all_box}>
          모든박스
          <div className={login.svg_box}>svg박스</div>
          <div className={login.login_box}>로그인박스</div>
        </div>
      </div>
    </>
  );
};

export default LoginP;
