import { PropsWithChildren } from "react";

import s from '../../../styles/mypage/M_ModalPassword.module.css'

interface ModalDefaultType {
    clickModalPW: () => void;
  }
const ModalPassword = ({clickModalPW, children}: PropsWithChildren<ModalDefaultType>) => {
    
    return (
        <div className={s.modalContainer} onClick={clickModalPW}>
            <div className={s.innerContainer} onClick={(e) => e.stopPropagation()}>
                <p id={s.exit}><img src='../../../../assets/mypage/exit.svg' onClick={clickModalPW}/></p>
                <form>
                    <div className={s.inputDiv}>
                        <label htmlFor="password">User password</label>
                        <input type="text" id="password" name="password" placeholder="your password"/>

                        <label htmlFor="checkPW">check password</label>
                        <input type="text" id="checkPW" name="checkPW" placeholder="check password"/>

                        <label htmlFor="doubleCheckPW">double check</label>
                        <input type="text" id="doubleCheckPW" name="doubleCheckPW" placeholder="DoubleCheck password"/>

                        <p className={s.submitP}>
                        <button id={s.submitBtn}>Submit password</button>
                        </p>
                    </div>
                </form>

            </div>
        </div>
    )
}

export default ModalPassword