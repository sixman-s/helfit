import { useState } from 'react';

import s from '../../styles/mypage/C_PersonalInfo.module.css';
import ModalPInfo from './modal/ModalPInfo';
import ModalPassword from './modal/ModalPassword';
import ModalContainer from './modal/ModalContainer';

const PersonalInfo = () => {
  const [modalContents, setModalContents] = useState<JSX.Element>(<></>);
  const [showModal, setShowMoadal] = useState<boolean>(false);

  const clickModalP = () => {
    setShowMoadal(true);
    setModalContents(<ModalPInfo />);
  };
  const clickModalPW = () => {
    setShowMoadal(true);
    setModalContents(<ModalPassword />);
  };

  return (
    <div className={s.pInfoContainer}>
      <h1 className={s.mypageTitle}>Mypage</h1>
      <div className={s.topContainer}>
        <div className={s.profileDiv}>
          <img src='../../../assets/mypage/profile.svg' className={s.profile} />
          <img
            src='../../../assets/mypage/profile_option.svg'
            className={s.profile_option}
            onClick={clickModalP}
          />
        </div>
        <div className={s.idDiv}>
          <span className={s.welcome}>good day</span>
          <span className={s.id}>hfhfhf</span>
        </div>
        <button className={s.buttonDiv} onClick={clickModalPW}>
          비밀번호 변경
        </button>
      </div>
      <div className={s.bottomContainer}>
        <p className={s.nnContainer}>
          <img
            src='../../../assets/mypage/nickName.svg'
            className={s.profile_option}
          />
          <span>eggkim</span>
        </p>
        <p className={s.bhContainer}>
          <img
            src='../../../assets/mypage/birthday.svg'
            className={s.profile_option}
          />
          <span>1990.01.01</span>
        </p>
        <p className={s.emailContainer}>
          <img
            src='../../../assets/mypage/email.svg'
            className={s.profile_option}
          />
          <span>helfit01@gmail.com</span>
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
