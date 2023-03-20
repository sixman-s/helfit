import style from '../../../styles/Community/P_Detail.module.css';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
}

const Modal: React.FC<ModalProps> = ({ isOpen, onClose }) => {
  const handleDeleteButtonClick = () => {
    // delete post logic here
    onClose();
  };

  return isOpen ? (
    <div className={style.ModalOverlay}>
      <div className={style.Modal}>
        <p>정말로 삭제하시겠습니까?</p>
        <button onClick={onClose} className={style.modalBtnN}>
          No
        </button>
        <button onClick={handleDeleteButtonClick} className={style.modalBtnY}>
          Yes
        </button>
      </div>
    </div>
  ) : null;
};

export default Modal;
