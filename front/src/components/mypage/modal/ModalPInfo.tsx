import { PropsWithChildren } from "react";
import { useForm, Controller } from "react-hook-form"

import s from '../../../styles/mypage/M_ModalPInfo.module.css'


interface ModalDefaultType {
    clickModalP: () => void;
  }

const ModalPInfo = ({clickModalP, children}: PropsWithChildren<ModalDefaultType>) => {
    const { control, handleSubmit } = useForm();

    return (
        <div className={s.modalContainer} onClick={clickModalP}>
            {/* {children} */}
            <div className={s.innerContainer} onClick={(e) => e.stopPropagation()}>
                <form>
                    <p id={s.exit}><img src='../../../../assets/mypage/exit.svg' onClick={clickModalP}/></p>
                    <div className={s.imageDiv}>
                        <p id={s.image}>
                            <img src='../../../../assets/mypage/profile.svg' className={s.profile} />
                        </p>
                        <p className={s.imageP}>
                            <button id={s.deleteBtn}>Delete Image</button>
                            <button id={s.findBtn}>Find Image</button>
                        </p>
                    </div>
                    <div className={s.inputDiv}>
                        <label htmlFor="id">User id</label>
                        <input type="text" id="id" name="id" placeholder="your Id"/>

                        <label htmlFor="id">NickName</label>
                        <input type="text" id="nickName" name="nickName" placeholder="your Nickname"/>
                        
                        <label htmlFor="id">User Email</label>
                        <input type="email" id="email" name="email" placeholder="your Email"/>

                        <label htmlFor="id">User BirthDay</label>
                        <input type="text" id="birthDay" name="birthDay" placeholder="your Birthday"/>

                        <p className={s.inputP}>
                        <button id={s.clearBtn}>Clear profile</button>
                        <button id={s.submitBtn}>Submit profile</button>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default ModalPInfo