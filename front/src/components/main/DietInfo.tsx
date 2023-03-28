import axios from 'axios';
import { useEffect, useState } from 'react';
import styled from '../../styles/main/C_dietInfo.module.css';
import { DotPulse } from '@uiball/loaders';
import Link from 'next/link';

const DietInfo = () => {
  if (typeof window !== 'undefined') {
    const userData = JSON.parse(localStorage.getItem('UserInfo'));
    const [answer, setAnser] = useState('');

    const setLodingComponent = () => {
      return (
        <span className={styled.loading}>
          <DotPulse size={20} speed={1.3} color='#3361ff' />
        </span>
      );
    };

    const questionView =
      userData !== null && userData.gender !== undefined
        ? `하루 운동량이 ${userData.activityLevel}인 ${userData.gender}의 하루 권장 소비 칼로리가 ${userData.result}kcal일 때 ${userData.goal} 식단 알려줘.`
        : '오늘의 식단 알려줘.';

    const question =
      userData !== null && userData.gender !== undefined
        ? `나는 ${userData.gender}이고 하루 운동량은 ${userData.activityLevel}이야. 하루 권장 소비 칼로리 ${userData.result}kcal일 때 ${userData.goal} 식단 나열하고 총 칼로리까지만 알려줘. 답변에 괄호 빼줘.`
        : '일반적인 다이어트 식단 나열하고 총 칼로리까지만 알려줘.';
    const body = {
      question
    };
    const url = process.env.NEXT_PUBLIC_URL;
    const [dietAnswer] = answer.split('*');
    let dietResult = dietAnswer.replace(/-/g, '');
    dietResult = dietResult.replaceAll('&#40;', '(');
    dietResult = dietResult.replaceAll('&#41;', ')');

    useEffect(() => {
      axios.post(`${url}/api/v1/ai/question`, body).then((res) => {
        setAnser(res.data.body.data.choices[0].message.content);
      });
    }, []);

    return (
      <>
        <article className={styled.container}>
          <figure className={styled.figure}>
            <img src='assets/main/questioner_icon.svg' alt='anwer icon' />
            <p className={`${styled.question} ${styled.title}`}>
              {questionView}
            </p>
          </figure>
          <figure className={styled.figure}>
            <img src='assets/main/anser_icon.svg' alt='anwer icon' />
            {answer === '' ? (
              setLodingComponent()
            ) : (
              <div className={styled.answer}>
                {userData !== null && userData.gender !== undefined ? (
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
                {dietResult}
              </div>
            )}
          </figure>
        </article>
      </>
    );
  }
};

export default DietInfo;
