import { PropsWithChildren } from 'react';
import s from '../../../styles/mypage/M_ModalContainer.module.css';
import { useEffect } from 'react';

interface ModalDefaultType {
  exitModal: () => void;
  showModal: boolean;
}

const ModalContainer = ({
  exitModal,
  showModal,
  children
}: PropsWithChildren<ModalDefaultType>) => {
  useEffect(() => {}, [children]);

  return showModal ? (
    <div className={s.modalContainer} onClick={exitModal}>
      <div className={s.innerContainer} onClick={(e) => e.stopPropagation()}>
        <p id={s.exit} onClick={exitModal}>
          <img src='../../../../assets/mypage/exit.svg' />
        </p>
        {children}
      </div>
    </div>
  ) : (
    <div className='test'></div>
  );
};

export default ModalContainer;
