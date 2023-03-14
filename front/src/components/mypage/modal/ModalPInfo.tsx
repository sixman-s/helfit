import { useForm, Controller } from 'react-hook-form';

import s from '../../../styles/mypage/M_ModalPInfo.module.css';

const ModalPInfo = () => {
  // const { control, handleSubmit } = useForm();
  const { register, handleSubmit } = useForm();

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));
        console.log(data);
      })}
    >
      <div className={s.imageDiv}>
        <p id={s.image}>
          <img
            src='../../../../assets/mypage/profile.svg'
            className={s.profile}
          />
        </p>
        <p className={s.imageP}>
          <button id={s.deleteBtn}>Delete Image</button>
          <button id={s.findBtn}>Find Image</button>
        </p>
      </div>
      <div className={s.inputDiv}>
        <label htmlFor='id'>아이디</label>
        <input
          type='text'
          id='id'
          placeholder='your Id'
          disabled
          {...register('id')}
        />

        <label htmlFor='nickName'>닉네임</label>
        <input type='text' id='nickName' placeholder='your Nickname' />

        <label htmlFor='email'>이메일</label>
        <input
          type='email'
          id='email'
          placeholder='your Email'
          {...register('email')}
        />

        <label htmlFor='birthDay'>생년월일</label>
        <input type='text' id='birthDay' placeholder='your Birthday' />

        <p className={s.inputP}>
          <button id={s.clearBtn}>Clear profile</button>
          <button id={s.submitBtn}>Submit profile</button>
        </p>
      </div>
    </form>
  );
};

export default ModalPInfo;
