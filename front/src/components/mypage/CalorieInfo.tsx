import s from '../../styles/mypage/C_CalorieInfo.module.css';

const CalorieInfo = () => {
  return (
    <div>
      <h1 className={s.calorieTitle}>칼로리</h1>
      <div className={s.bottomContainer}>
        <div>
          <span>2000</span>
        </div>
        <div className={s.kcal}>Kcal</div>
      </div>
    </div>
  );
};

export default CalorieInfo;
