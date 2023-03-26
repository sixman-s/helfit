import Link from 'next/link';
import styled from '../../../styles/main/C_noneData.module.css';
const NonData = ({ link, btn }) => {
  return (
    <div className={styled.container}>
      <div className={styled.textaria}>
        <span className={styled.title}>데이터가 없습니다.</span>
        <p className={styled.subtext}>새로운 데이터를 추가해주세요.</p>
      </div>
      <Link href={link} className={styled.link}>
        <button className={styled.btn}>{btn}</button>
      </Link>
    </div>
  );
};

export default NonData;
