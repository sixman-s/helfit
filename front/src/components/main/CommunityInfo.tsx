import Link from 'next/link';
import layout from '../../styles/main/C_header.module.css';
import styled from '../../styles/main/C_communityInfo.module.css';
import { useState, useEffect } from 'react';
import axios from 'axios';

const CommunityInfo = ({ token }) => {
  const [data, setData] = useState(null);
  const imgUrl =
    'https://blog.kakaocdn.net/dn/3yudy/btqFLtiuxnM/kKhK2caNgPuyvJn6faAon1/img.jpg';
  const title = '은평구 크로스핏으로 오세요^^';
  const context =
    '오전 운동이 빡세긴하지만 기분이 상쾌한 장점이 있는듯!☺️ 운동을 더 잘하고싶네요😆😆 수욜도 득근하세용💪🏻💕';
  useEffect(() => {
    if (token) {
      const headers = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      const url = process.env.NEXT_PUBLIC_URL;
      axios
        .get(`${url}/api/v1/stat/board`, headers)
        .then((res) => {
          console.log(res.data.body.data);
          setData(res.data.body.data);
        })
        .catch((error) => console.log(error));
    }
  }, [token]);
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
