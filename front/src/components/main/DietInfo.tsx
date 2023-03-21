import axios from 'axios';
import { useEffect, useState } from 'react';
import styled from '../../styles/main/C_dietInfo.module.css';
import { DotPulse } from '@uiball/loaders';

const DietInfo = () => {
  const [answer, setAnser] = useState('');
  const setLodingComponent = () => {
    return (
      <span className={styled.loading}>
        <DotPulse size={20} speed={1.3} color='#3361ff' />
      </span>
    );
  };
  const question = `하루 권장 소비 칼로리 2000kcal 벌크업 식단 나열하고 총 칼로리까지만 알려줘. 하루 운동량은 가벼운 활동량이야.`;
  const questionView = '오늘의 식단 알려줘.';
  const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/ai/question`;
  const body = {
    question
  };
  const [dietAnswer] = answer.split('*');

  useEffect(() => {
    axios.post(url, body).then((res) => {
      setAnser(res.data.body.data.choices[0].message.content);
    });
  }, []);
  return (
    <>
      <article className={styled.container}>
        <figure className={styled.figure}>
          <img src='assets/mainP/questioner_icon.svg' alt='anwer icon' />
          <p className={styled.question}>{questionView}</p>
        </figure>
        <figure className={styled.figure}>
          <img src='assets/mainP/anser_icon.svg' alt='anwer icon' />
          {answer === '' ? (
            setLodingComponent()
          ) : (
            <div className={styled.answer}>
              {dietAnswer.replace(/-/g, '')}
              <br />
            </div>
          )}
        </figure>
      </article>
    </>
  );
};

export default DietInfo;
