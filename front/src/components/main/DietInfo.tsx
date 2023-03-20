import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';
import NonMembers from './NonMembers';
import axios from 'axios';
import { useEffect, useState } from 'react';
import Image from 'next/image';

const DietInfo = () => {
  const { height, weight } = JSON.parse(localStorage.getItem('UserInfo'));
  const [answer, setAnser] = useState('답변 대기 중입니다.');
  const question = `키 ${height}cm, 몸무게 ${weight}kg, 하루 권장 소비 칼로리 4500kcal 벌크업 식단 알려줘. 하루 운동량은 가벼운 활동량이야. `;
  const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/ai/question`;
  const body = {
    question
  };

  const answerData = (answer: string) => {
    answer.split('').map((str) => {
      if (str === 'g ') {
      }
    });
  };

  useEffect(() => {
    axios.post(url, body).then((res) => {
      setAnser(res.data.body.data.choices[0].message.content);
    });
  }, []);
  return (
    <>
      <article>
        <figure>
          <img src='assets/mainP/questioner_icon.svg' alt='anwer icon' />
          <figcaption>{question}</figcaption>
        </figure>
        <figure>
          <img src='assets/mainP/anser_icon.svg' alt='anwer icon' />
          <figcaption>{answer}</figcaption>
        </figure>
      </article>
    </>
  );
};

export default DietInfo;
