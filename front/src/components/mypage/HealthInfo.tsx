import { useEffect, useState } from 'react';
import s from '../../styles/mypage/C_HealthInfo.module.css';
import ModalContainer from './modal/ModalContainer';
import ModalHInfo from './modal/ModalHInfo';

export interface detailProps {
  result: {
    detail: {
      userId: number;
      id: string;
      email: string;
      nickname: string;
      profileImageUrl: string;
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

  console.log(detail.gender);
  console.log(cDetail.goal);

  useEffect(() => {
    if (cDetail) {
      setActivity(true);
      setPurpose(true);
    }
  }, [cDetail]);

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
          <span className={s.answer}>{chagneGenderName}</span>
        </p>
        <p className={s.list}>
          <span className={s.question}>키</span>
          <span className={s.answer}>{detail.height}</span>
        </p>
        <p className={s.list}>
          <span className={s.question}>몸무게</span>
          <span className={s.answer}>{detail.weight}</span>
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
