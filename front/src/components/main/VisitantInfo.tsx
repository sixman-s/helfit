import styled from '../../styles/main/C_userInfo.module.css';
import Link from 'next/link';

type ComponentProps = {
  children: React.ReactNode;
};

const VisitantInfo = ({ children }: ComponentProps) => {
  return (
    <article>
      <header className={styled.header}>
        <h3 className={styled.h3}>
          헬핏이 처음이신가요?
          <br />
          <span className={styled.username}>로그인 후 </span>
          다양한 서비스를 만나보세요.
        </h3>
      </header>
      {children}
    </article>
  );
};

export default VisitantInfo;
