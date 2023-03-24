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

  console.log('detail : ' + JSON.stringify(detail));

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
      <span className={s.mypageTitle}>User</span>
      <div className={s.innerContainer}>
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
            <span className={s.id}>{detail.id}</span>
          </div>
          <div className={s.divBtn}>
            <button className={s.buttonDiv} onClick={clickModalP}>
              닉네임 변경
            </button>
            <button className={s.buttonDiv} onClick={clickModalPW}>
              비밀번호 변경
            </button>
          </div>
        </div>
        <div className={s.bottomContainer}>
          <div className={s.sectionContainer}>
            <img src='../../../assets/mypage/nickName.svg' />
            <span className={s.contentSpan}>{detail.nickname}</span>
          </div>
          <div className={s.sectionContainer}>
            <img src='../../../assets/mypage/email.svg' />
            <span className={s.contentSpan}>{detail.email}</span>
          </div>
        </div>
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
