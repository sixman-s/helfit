import s from '../../styles/mypage/C_MyList.module.css'

const MyList = () => {
    return (
        <div className={s.myListContainer}>
            <div className={s.topLine}>
                <h1>Questions</h1>
                <span>더보기</span>
            </div>
            <table className={s.table}>
                <tbody>
                    <td className={s.qNumber}>01</td>
                    <td className={s.qContent}>kdkdjsdjidajsai</td>
                    <td className={s.qDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.qNumber}>02</td>
                    <td className={s.qContent}>kdkdjsdjidajsai</td>
                    <td className={s.qDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.qNumber}>03</td>
                    <td className={s.qContent}>kdkdjsdjidajsai</td>
                    <td className={s.qDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.qNumber}>04</td>
                    <td className={s.qContent}>kdkdjsdjidajsai</td>
                    <td className={s.qDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.qNumber}>05</td>
                    <td className={s.qContent}>kdkdjsdjidajsai</td>
                    <td className={s.qDate}>2020/01/01</td>
                </tbody>
            </table>
            <hr />
            <div className={s.topLine}>
                <h1>Answer</h1>
                <span>더보기</span>
            </div>
            <table className={s.table}>
                <tbody>
                    <td className={s.aNumber}>01</td>
                    <td className={s.aContent}>kdkdjsdjidajsai</td>
                    <td className={s.aDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.aNumber}>02</td>
                    <td className={s.aContent}>kdkdjsdjidajsai</td>
                    <td className={s.aDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.aNumber}>03</td>
                    <td className={s.aContent}>kdkdjsdjidajsai</td>
                    <td className={s.aDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.aNumber}>04</td>
                    <td className={s.aContent}>kdkdjsdjidajsai</td>
                    <td className={s.aDate}>2020/01/01</td>
                </tbody>
                <tbody>
                    <td className={s.aNumber}>05</td>
                    <td className={s.aContent}>kdkdjsdjidajsai</td>
                    <td className={s.aDate}>2020/01/01</td>
                </tbody>
            </table>
            <hr />
        </div>
    );
}

export default MyList