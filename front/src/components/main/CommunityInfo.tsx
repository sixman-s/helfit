import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';
import styled from '../../styles/main/C_CommunityInfo.module.css';

const CommunityInfo = () => {
  const imgUrl =
    'https://blog.kakaocdn.net/dn/3yudy/btqFLtiuxnM/kKhK2caNgPuyvJn6faAon1/img.jpg';
  const title = '은평구 크로스핏으로 오세요^^';
  const context =
    '오전 운동이 빡세긴하지만 기분이 상쾌한 장점이 있는듯!☺️ 운동을 더 잘하고싶네요😆😆 수욜도 득근하세용💪🏻💕';
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>Today's Member</h2>
        <Link className={layout.moreBtn} href='/community'>
          More
        </Link>
      </header>
      <ul className={styled.contents}>
        <li className={styled.content}>
          <img className={styled.img} src={imgUrl} />

          <label className={styled.label}>
            <span className={styled.title}>{title}</span>
            <p className={styled.context}>{context}</p>
          </label>
        </li>
        <li className={styled.content}>
          <img className={styled.img} src={imgUrl} />

          <label className={styled.label}>
            <span className={styled.title}>{title}</span>
            <p className={styled.context}>{context}</p>
          </label>
        </li>
        <li className={styled.content}>
          <img className={styled.img} src={imgUrl} />

          <label className={styled.label}>
            <span className={styled.title}>{title}</span>
            <p className={styled.context}>{context}</p>
          </label>
        </li>
      </ul>
    </article>
  );
};

export default CommunityInfo;
