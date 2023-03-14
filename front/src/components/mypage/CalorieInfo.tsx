import s from '../../styles/mypage/C_CalorieInfo.module.css'

const CalorieInfo = () => {
    return (
    <div>
        <h1 className={s.calorieTitle}>Calorie</h1>
        <div className={s.bottomContainer}>
            <div><p>안정시 대사</p><span>2000</span></div>
            <div><p>안정시 대사</p><span>2000</span></div>
            <div><p>안정시 대사</p><span>2000</span></div>
            <div><p>안정시 대사</p><span>2000</span></div>
        </div>
    </div>
    );
}

export default CalorieInfo