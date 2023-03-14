import s from '../../../styles/mypage/M_ModalPassword.module.css';

const ModalPassword = () => {
  return (
    <form>
      <div className={s.inputDiv}>
        <label htmlFor='password'>현재 비밀번호</label>
        <input
          type='text'
          id='password'
          name='password'
          placeholder='현재 비밀번호를 입력해주세요.'
        />

        <label htmlFor='checkPW'>새 비밀번호</label>
        <input
          type='text'
          id='checkPW'
          name='checkPW'
          placeholder='새 비밀번호를 입력해주세요.'
        />

        <label htmlFor='doubleCheckPW'>새 비밀번호 확인</label>
        <input
          type='text'
          id='doubleCheckPW'
          name='doubleCheckPW'
          placeholder='새 비밀번호를 한번 더 입력해주세요.'
        />

        <p className={s.submitP}>
          <button id={s.submitBtn}>Submit password</button>
        </p>
      </div>
    </form>
  );
};

export default ModalPassword;
