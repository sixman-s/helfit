import { useState } from 'react';
import s from '../../styles/mypage/C_HealthInfo.module.css';
import ModalContainer from './modal/ModalContainer';
import ModalHInfo from './modal/ModalHInfo';

export interface detailProps {
  userId: number;
  id: string;
  email: string;
  nickname: string;
  profileImageUrl: string;
  birth: number;
  gender: string;
  height: number;
  weight: number;
}

const HealthInfo = ({ detail }: { detail: detailProps }) => {
  const [showModal, setShowMoadal] = useState(false);
  const [activity, setActivity] = useState();
  const [purpose, setPurpose] = useState();

  const chagneGenderName = detail.gender === 'Male' ? '남' : '여';

  const clickModal = () => {
    setShowMoadal(true);
  };

  return (
    <div className={s.hInfoContainer}>
      <div className={s.titleDiv}>
        <h1 className={s.hInfoTitle}>Health Info</h1>
        <button className={s.button} onClick={clickModal}>
          정보 변경
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
        <p className={s.list}>
          <span className={s.question}>활동 정도</span>
          <span className={s.answer}>
            {activity === undefined ? '값을 입력해주세요.' : activity}
          </span>
        </p>
        <p className={s.list}>
          <span className={s.question}>운동 목적</span>
          <span className={s.answer}>
            {purpose === undefined ? '값을 입력해주세요.' : purpose}
          </span>
        </p>
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
