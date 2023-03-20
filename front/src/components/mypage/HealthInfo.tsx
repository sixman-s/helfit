import { useEffect, useState } from 'react';
import s from '../../styles/mypage/C_HealthInfo.module.css';
import ModalContainer from './modal/ModalContainer';
import ModalHInfo from './modal/ModalHInfo';

export interface detailProps {
  result: {
    detail: {
      userId: number;
      birth: number;
      gender: string;
      height: number;
      weight: number;
    };
    cDetail: {
      activityLevel: string;
      goal: string;
    };
  };
}

const HealthInfo = ({ detail, cDetail }: { detail; cDetail: detailProps }) => {
  const [showModal, setShowMoadal] = useState(false);
  const [activity, setActivity] = useState(false);
  const [purpose, setPurpose] = useState(false);

  // console.log(detail.gender);
  // console.log(cDetail.goal);

  const birth = String(detail.birth);
  const setBirth =
    birth.slice(0, 4) + '.' + birth.slice(4, 6) + '.' + birth.slice(6, 8);

  useEffect(() => {
    if (cDetail) {
      setActivity(true);
      setPurpose(true);
    }
  }, [detail, cDetail]);

  const chagneGenderName = detail.gender === 'MALE' ? '남' : '여';

  const clickModal = () => {
    setShowMoadal(true);
  };

  return (
    <div className={s.hInfoContainer}>
      <div className={s.titleDiv}>
        <h1 className={s.hInfoTitle}>신체 정보</h1>
        <button className={s.button} onClick={clickModal}>
          칼로리 계산
        </button>
      </div>
      <div className={s.bottomContainer}>
        <p className={s.list}>
          <span className={s.question}>성별</span>
          <span className={s.answer}>
            {detail.gender ? chagneGenderName : '정보를 입력해주세요.'}
          </span>
        </p>
        <p className={s.list}>
          <span className={s.question}>키</span>
          <span className={s.answer}>
            {detail.height ? detail.height : '정보를 입력해주세요.'}
          </span>
        </p>
        <p className={s.list}>
          <span className={s.question}>몸무게</span>
          <span className={s.answer}>
            {detail.weight ? detail.weight : '정보를 입력해주세요.'}
          </span>
        </p>
        <p className={s.bhContainer}>
          <span className={s.question}>생년월일</span>
          <span className={s.answer}>
            {detail.birth ? setBirth : '정보를 입력해주세요.'}
          </span>
        </p>
        {activity ? (
          <p className={s.list}>
            <span className={s.question}>활동 정도</span>
            <span className={s.answer}>{cDetail.activityLevel}</span>
          </p>
        ) : (
          <></>
        )}
        {purpose ? (
          <p className={s.list}>
            <span className={s.question}>운동 목적</span>
            <span className={s.answer}>{cDetail.goal}</span>
          </p>
        ) : (
          <></>
        )}
      </div>
      <ModalContainer
        showModal={showModal}
        exitModal={() => {
          setShowMoadal(false);
        }}
      >
        <ModalHInfo detail={detail} />
      </ModalContainer>
    </div>
  );
};

export default HealthInfo;
