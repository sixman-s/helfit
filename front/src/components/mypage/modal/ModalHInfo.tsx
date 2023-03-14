import s from '../../../styles/mypage/M_ModalHInfo.module.css';

const ModalHInfo = () => {
  return (
    <form>
      <div className={s.inputDiv}>
        <label htmlFor='activity'>활동 정도</label>
        <select>
          <option value='' selected disabled hidden>
            선택해주세요.
          </option>
          <option value='Light activity'>가벼운 활동</option>
          <option value='Actividad moderada'>적당한 활동</option>
          <option value='Actividad intensa'>강도 높은 활동</option>
          <option value='Veruy intense activity'>격렬한 활동</option>
        </select>

        <label htmlFor='purpose'>운동 목적</label>
        <select>
          <option value='' selected disabled hidden>
            선택해주세요.
          </option>
          <option value='keep'>유지</option>
          <option value='Diet'>다이어트</option>
          <option value='Bulkup'>증량</option>
        </select>
        <p className={s.submitP}>
          <button id={s.submitBtn}>Submit</button>
        </p>
      </div>
    </form>
  );
};

export default ModalHInfo;
