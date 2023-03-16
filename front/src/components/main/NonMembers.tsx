import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';
import styled from '../../styles/main/C_infoLayout.module.css';

const NonMembers = () => {
  return (
    <form className={styled.container}>
      <div className={styled.guide}>
        <p className={styled.guideText}>로그인이 필요한 서비스입니다.</p>
        <Link href='/login'>
          <button className={styled.btnText}>login</button>
        </Link>
      </div>
      <div className={styled.guide}>
        <p className={styled.guide}>
          아직 회원이 아니시면
          <Link className={layout.moreBtn} href='/signup'>
            {' 여기'}
          </Link>
          를 클릭해주세요.
        </p>
      </div>
    </form>
  );
};

export default NonMembers;
