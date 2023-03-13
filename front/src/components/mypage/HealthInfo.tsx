import s from '../../styles/mypage/C_HealthInfo.module.css'


const HealthInfo = () => {
    return (
    <div className={s.hInfoContainer}>
        <div className={s.titleDiv}>
            <h1 className={s.hInfoTitle}>Health Info</h1>  
            <button className={s.button}>정보 변경</button>
        </div>
        <div className={s.bottomContainer}>
            <p className={s.list}>
                <span className={s.question}>Gender</span>
                <span className={s.answer}>남</span>
            </p>
            <p className={s.list}>
                <span className={s.question}>Age</span>
                <span className={s.answer}>19</span>
            </p>
            <p className={s.list}>
                <span className={s.question}>Height</span>
                <span className={s.answer}>188</span>
            </p>
            <p className={s.list}>
                <span className={s.question}>Weight</span>
                <span className={s.answer}>78</span>
            </p>
            <p className={s.list}>
                <span className={s.question}>The degree of Activity</span>
                <select className={s.answer}>
                    <option>Light activity</option>
                    <option>Actividad moderada</option>
                    <option>Actividad intensa</option>
                    <option>Veruy intense    activity</option>
                </select>
            </p>
            <p className={s.list}>
                <span className={s.question}>The purpose of exercise</span>
                <select className={s.answer}>
                    <option>Diet</option>
                    <option>Bulkup</option>
                </select>
            </p>
        </div>
    </div>
);
}

export default HealthInfo