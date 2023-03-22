import axios from 'axios';
import { useEffect, useState } from 'react';
import styled from '../../styles/main/C_dietInfo.module.css';
import { DotPulse } from '@uiball/loaders';
import Link from 'next/link';

const DietInfo = ({ token }) => {
  const userData = JSON.parse(localStorage.getItem('UserInfo'));
  const [info, setInfo] = useState(null);
  const [answer, setAnser] = useState('');

  const setLodingComponent = () => {
    return (
      <span className={styled.loading}>
        <DotPulse size={20} speed={1.3} color='#3361ff' />
      </span>
    );
  };

  const question =
    info !== null
      ? `나는 ${'남성'}이고 하루 운동량은 ${
          info.activityLevel
        }이야. 하루 권장 소비 칼로리 ${userData.result}kcal일 때 ${
          info.goal
        } 식단 나열하고 총 칼로리까지만 알려줘.`
      : '일반적인 다이어트 식단 나열하고 총 칼로리까지만 알려줘';
  const questionView = '오늘의 식단 알려줘.';
  const body = {
    question
  };
  const url = process.env.NEXT_PUBLIC_URL;
  const [dietAnswer] = answer.split('*');

  useEffect(() => {
    if (token) {
      const headers = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      axios
        .get(`${url}/api/v1/calculate/${userData.userId}`, headers)
        .then((res) => {
          setInfo(res.data.body.data);
        })
        .catch((error) => console.log(error));
    }
    axios.post(`${url}/api/v1/ai/question`, body).then((res) => {
      setAnser(res.data.body.data.choices[0].message.content);
    });
  }, []);

  return (
    <>
      <article className={styled.container}>
        <figure className={styled.figure}>
          <img src='assets/mainP/questioner_icon.svg' alt='anwer icon' />
          <p className={`${styled.question} ${styled.title}`}>{questionView}</p>
        </figure>
        <figure className={styled.figure}>
          <img src='assets/mainP/anser_icon.svg' alt='anwer icon' />
          {answer === '' ? (
            setLodingComponent()
          ) : (
            <div className={styled.answer}>
              {info !== null ? (
                <span className={styled.title}>
                  사용자 데이터를 기반으로 맞춤 식단을 알려드립니다.
                </span>
              ) : (
                <span className={styled.title}>
                  {'기본 제공 식단을 알려드립니다.'}
                  <br />
                  <Link className={styled.link} href='/mypage'>
                    여기
                  </Link>
                  를 클릭하여 개인정보를 입력하시면 보다 정확한 식단을
                  제공해드립니다.
                </span>
              )}
              <br />
              <br />
              {dietAnswer.replace(/-/g, '')}
            </div>
          )}
        </figure>
      </article>
    </>
  );
};

export default DietInfo;
