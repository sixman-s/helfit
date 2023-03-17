import userInfo from '../../styles/main/C_userInfo.module.css';
import layout from '../../styles/main/C_infoLayout.module.css';
import styled from '../../styles/main/C_nonMembers.module.css';
import Link from 'next/link';

const VisitantInfo = () => {
  return (
    <article className={userInfo.container}>
      <header className={userInfo.header}>
        <h3 className={userInfo.h3}>
          헬핏이 처음이신가요?
          <br />
          <span className={userInfo.username}>로그인 후 </span>
          다양한 서비스를 만나보세요.
        </h3>
      </header>
      <form className={`${styled.container} ${styled.userContainer}`}>
        <img className={styled.errIcon} src='/assets/mainP/error_icon.svg' />
        <p className={styled.title}>로그인이 필요한 서비스입니다.</p>
        <Link href='/login'>
          <button className={styled.loginBtn}>Login</button>
        </Link>
        <p className={styled.signupGuide}>
          아직 회원이 아니시면
          <Link className={layout.moreBtn} href='/signup'>
            여기
          </Link>
          를 클릭해주세요.
        </p>
      </form>
    </article>
  );
};

export default VisitantInfo;
