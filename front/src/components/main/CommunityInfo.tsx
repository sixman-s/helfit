import Link from 'next/link';
import layout from '../../styles/main/C_header.module.css';
import styled from '../../styles/main/C_CommunityInfo.module.css';
import { useEffect, useRef, useState, useLayoutEffect } from 'react';
import axios from 'axios';
import { ConvertToHtml } from './util/ConvertToHtml';

const CommunityInfo = ({ token }) => {
  const [data, setData] = useState([]);
  const elRef = useRef(null);

  useEffect(() => {
    if (token) {
      const url = process.env.NEXT_PUBLIC_URL;
      axios
        .get(`${url}/api/v1/board/hot/5`)
        .then((res) => {
          setData(res.data);
        })
        .catch((error) => console.log(error));
    }
  }, [token]);

  useLayoutEffect(() => {
    if (elRef.current) {
      elRef.current.style.color = '#7e90b3';
      // set other style attributes
    }
  });

  return (
    <ul className={styled.contents}>
      {data &&
        data
          .slice(0, 3)
          .map(({ boardImageUrl, text, title, boardId }, index: number) => (
            <li className={styled.content} key={index}>
              <Link href={`/community/oww/${boardId}`}>
                <img className={styled.img} src={boardImageUrl} />
                <div className={styled.label}>
                  <span className={styled.title}>{title}</span>
                  <p className={styled.context}>{ConvertToHtml(text)}</p>
                </div>
              </Link>
            </li>
          ))}
    </ul>
  );
};

export default CommunityInfo;
