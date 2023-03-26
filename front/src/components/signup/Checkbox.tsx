import { useState, useRef } from 'react';

import style from '../../styles/Signup/C_SignupBox.module.css';

const Checkbox = () => {
  const [showModal, setShowModal] = useState(false);
  const modalRef = useRef<HTMLDivElement>(null);

  const handleModalOpen = () => {
    setShowModal(true);
  };

  return (
    <>
      <div>
        <a className={style.Showmodal} onClick={handleModalOpen}>
          개인정보 수집 및 이용
        </a>
        에 동의합니다.
      </div>
      {showModal && (
        <div className={style.modal} ref={modalRef}>
          <div className={style.modal_content}>
            <h2>개인정보 수집 및 이용 동의</h2>
            <h6 style={{ color: 'red' }}>
              * 필독 * 상기 사항을 모두 읽어주시기 바랍니다.
            </h6>
            <p>
              <br />
              1. 개인정보의 수집항목 및 수집방법
              <br /> HelFit사이트에서는 기본적인 회원 서비스 제공을 위한
              필수정보로 실명인증정보와 가입정보로 구분하여 다음의 정보를
              수집하고 있습니다
              <br /> 필수정보를 입력해주셔야 회원 서비스 이용이 가능합니다.
              <br />
              <br /> 가. 수집하는 개인정보의 항목
              <br />
              가입정보 : 아이디, 비밀번호, 이메일
              <br />
              [컴퓨터에 의해 자동으로 수집되는 정보]
              <br /> 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로
              생성되어 수집될 수 있습니다.
              <br /> - IP주소, 서비스 이용기록, 방문기록 등 <br />
              <br />
              나. 개인정보 수집방법 홈페이지 회원가입을 통한 수집
              <br />
              <br /> 2. 개인정보의 수집/이용 목적 및 보유/이용 기간 <br />
              HelFit사이트에서는 정보주체의 회원 가입일로부터 서비스를 제공하는
              기간 동안에 한하여 HelFit사이트 서비스를 이용하기 위한 최소한의
              개인정보를 보유 및 이용 하게 됩니다.
              <br />
              <br />
              회원가입 등을 통해 개인정보의 수집·이용, 제공 등에 대해 동의하신
              내용은 언제든지 철회하실 수 있습니다.
              <br /> 회원 탈퇴를 요청하거나 수집/이용목적을 달성하거나
              보유/이용기간이 종료한 경우, 사업 폐지 등의 사유발생시 개인 정보를
              지체 없이 파기합니다.
              <br />
              <br /> 개인정보 수집항목 : 아이디, 비밀번호, 이메일, 성별, 키,
              몸무게
              <br />
              개인정보의 수집·이용목적 : 홈페이지 서비스 이용 및 회원관리,
              불량회원의 부정 이용방지, 개인 건강관리 등 <br />
              개인정보의 보유 및 이용기간 : 2년 또는 회원탈퇴시 정보주체는
              개인정보의 수집·이용목적에 대한 동의를 거부할 수 있으며, 동의
              거부시 HelFit사이트에 회원가입이 되지 않으며, HelFit사이트에서
              제공하는 서비스를 이용할 수 없습니다.
              <br />
              <br /> 3. 수집한 개인정보 제3자 제공 HelFit사이트에서는 정보주체의
              동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에
              해당하는 경우에만 개인정보를 제3자에게 제공합니다.
              <br />
              <br />
            </p>
            <button
              className={style.modalBtn}
              onClick={() => setShowModal(false)}
            >
              모든 내용을 확인했습니다.
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default Checkbox;
