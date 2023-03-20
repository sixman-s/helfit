import { useEffect, useState } from 'react';
import s from '../../styles/mypage/C_HealthInfo.module.css';
import ModalContainer from './modal/ModalContainer';
import ModalHInfo from './modal/ModalHInfo';

export interface detailProps {
  result: {
    detail: {
      userId: number;
    };
    hDetail: {
      birth: number;
      gender: string;
      height: number;
      weight: number;
    };
    cDetail: {
      calculatorId: number;
      activityLevel: string;
      goal: string;
    };
  };
}

const HealthInfo = ({
  detail,
  hDetail,
  cDetail
}: {
  detail;
  hDetail;
  cDetail: detailProps;
}) => {
  const [showModal, setShowMoadal] = useState(false);
  const [activity, setActivity] = useState(false);
  const [purpose, setPurpose] = useState(false);

  // console.log(cDetail.activityLevel);

  useEffect(() => {
    if (detail && hDetail && cDetail) {
      setActivity(true);
      setPurpose(true);
    }
  }, [detail, hDetail, cDetail]);

  // 계산기 데이터 한글화
  const activityMapper = {
    SEDENTARY: '거의 활동 없음',
    LIGHTLY_ACTIVE: '가벼운 활동',
    MODERATELY_ACTIVE: '적당한 활동',
    VERY_ACTIVE: '강도 높은 활동',
    EXTRA_ACTIVE: '격력한 활동'
  };

  const goalMapper = {
    KEEP: '유지',
    DIET: '다이어트',
    BULK: '증량'
  };

  const activityInfo =
    cDetail !== undefined ? activityMapper[cDetail.activityLevel] : {};
  const goalInfo = cDetail !== undefined ? goalMapper[cDetail.goal] : {};

  // 만 나이 계산기
  const birth = String(hDetail.birth);
  const setBirth =
    birth.slice(0, 4) + '/' + birth.slice(4, 6) + '/' + birth.slice(6, 8);

  let birthDay = new Date(setBirth);
  let today = new Date();
  let years = today.getFullYear() - birthDay.getFullYear();

  birthDay.setFullYear(today.getFullYear());
  if (today < birthDay) years--;

  const chagneGenderName = hDetail.gender === 'MALE' ? '남' : '여';

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
            {hDetail.gender ? chagneGenderName : '정보를 입력해주세요.'}
          </span>
        </p>
        <p className={s.list}>
          <span className={s.question}>나이</span>
          <span className={s.answer}>
            {hDetail.birth ? years : '정보를 입력해주세요.'}
          </span>
        </p>
        <p className={s.list}>
          <span className={s.question}>키</span>
          <span className={s.answer}>
            {hDetail.height ? hDetail.height : '정보를 입력해주세요.'}
          </span>
        </p>
        <p className={s.list}>
          <span className={s.question}>몸무게</span>
          <span className={s.answer}>
            {hDetail.weight ? hDetail.weight : '정보를 입력해주세요.'}
          </span>
        </p>

        {activity ? (
          <p className={s.list}>
            <span className={s.question}>활동 정도</span>
            <span className={s.answer}>{activityInfo}</span>
          </p>
        ) : (
          <></>
        )}
        {purpose ? (
          <p className={s.list}>
            <span className={s.question}>운동 목적</span>
            <span className={s.answer}>{goalInfo}</span>
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
        <ModalHInfo detail={detail} hDetail={hDetail} cDetail={cDetail} />
      </ModalContainer>
    </div>
  );
};

export default HealthInfo;
