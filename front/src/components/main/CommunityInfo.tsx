import Link from 'next/link';
import layout from '../../styles/main/C_header.module.css';
import styled from '../../styles/main/C_communityInfo.module.css';
import { useEffect, useState } from 'react';
import axios from 'axios';

const CommunityInfo = ({ token }) => {
  const [data, setData] = useState([]);

  const escapeMap = {
    '&lt;': '<',
    '&#12296;': '<',
    '&gt;': '>',
    '&#12297;': '>',
    '&amp;': '&',
    '&quot;': '"',
    '&#x27;': "'"
  };

  const pattern = /&(lt|gt|amp|quot|#x27|#12296|#12297);/g;

  const convertToHtml = (text) =>
    text.replace(pattern, (match, entity) => escapeMap[`&${entity};`] || match);

  useEffect(() => {
    if (token) {
      const headers = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      const url = process.env.NEXT_PUBLIC_URL;
      axios
        .get(`${url}/api/v1/stat/board/5`, headers)
        .then((res) => {
          setData(res.data.body.data);
        })
        .catch((error) => console.log(error));
    }
  }, [token]);

  return (
    <ul className={styled.contents}>
      {data &&
        data.map(({ boardImageUrl, text, title }, index: number) => (
          <li className={styled.content} key={index}>
            <Link href={`/community/oww/${index + 1}`}>
              <img className={styled.img} src={boardImageUrl} />
              <div className={styled.label}>
                <span className={styled.title}>{title}</span>
                <p className={styled.context}>
                  {convertToHtml(text).replace('<p>', '').replace('</p>', '')}
                </p>
              </div>
            </Link>
          </li>
        ))}
    </ul>
  );
};

export default CommunityInfo;
