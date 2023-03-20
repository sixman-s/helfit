import { useState } from 'react';

import s from '../../styles/mypage/C_PersonalInfo.module.css';
import ModalPInfo from './modal/ModalPInfo';
import ModalPassword from './modal/ModalPassword';
import ModalContainer from './modal/ModalContainer';
import ModalImage from './modal/ModalImage';
import { useEffect } from 'react';

export interface detailProps {
  info: {
    userId: number;
    birth: number;
    id: string;
    email: string;
    nickname: string;
    profileImageUrl: string;
  };
}

const PersonalInfo = ({ detail }: { detail: detailProps }) => {
  const [modalContents, setModalContents] = useState<JSX.Element>(<></>);
  const [showModal, setShowMoadal] = useState<boolean>(false);

  // console.log('detail : ' + detail);

  const checkImage = detail.profileImageUrl
    ? detail.profileImageUrl
    : '../../../assets/mypage/profile.svg';

  const clickModalP = () => {
    setShowMoadal(true);
    setModalContents(<ModalPInfo detail={detail} />);
  };
  const clickModalPW = () => {
    setShowMoadal(true);
    setModalContents(<ModalPassword />);
  };
  const clickModalImage = () => {
    setShowMoadal(true);
    setModalContents(<ModalImage detail={detail} />);
  };

  return (
    <div className={s.pInfoContainer}>
      <h1 className={s.mypageTitle}>개인 정보</h1>
      <div className={s.topContainer}>
        <div className={s.profileDiv}>
          <img src={checkImage} className={s.profile} />
          <img
            src='../../../assets/mypage/profile_option.svg'
            className={s.profile_option}
            onClick={clickModalImage}
          />
        </div>
        <div className={s.idDiv}>
          <span className={s.welcome}>good day</span>
          <span className={s.id}>{detail.id}</span>
        </div>
        <div className={s.divBtn}>
          <button id={s.infoBtn} className={s.buttonDiv} onClick={clickModalP}>
            닉네임 변경
          </button>
          <button className={s.buttonDiv} onClick={clickModalPW}>
            비밀번호 변경
          </button>
        </div>
      </div>
      <div className={s.bottomContainer}>
        <p className={s.nnContainer}>
          <img src='../../../assets/mypage/nickName.svg' />
          <span>{detail.nickname}</span>
        </p>
        <p className={s.emailContainer}>
          <img src='../../../assets/mypage/email.svg' />
          <span>{detail.email}</span>
        </p>
      </div>
      <ModalContainer
        showModal={showModal}
        exitModal={() => {
          setShowMoadal(false);
        }}
      >
        {modalContents}
      </ModalContainer>
    </div>
  );
};

export default PersonalInfo;
