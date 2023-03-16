import axios from 'axios';
import { useRouter } from 'next/router';
import { useEffect, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';

import s from '../../../styles/mypage/M_ModalPInfo.module.css';

interface detailProps {
  userId: number;
  id: string;
  email: string;
  nickname: string;
  profileImageUrl: string;
  birth: number;
  gender: string;
  height: number;
  weight: number;
}

const ModalPInfo = ({ detail }: { detail: detailProps }) => {
  const { register, handleSubmit } = useForm();
  const router = useRouter();

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));
        console.log(data);
        axios
          .patch(`${url}/api/v1/users`, data, {
            headers: {
              Authorization: `Bearer ${accessToken}`
            }
          })
          .then((data) => {
            {
              console.log(data);
              if (data.status === 200) {
                router.push('/');
              }
            }
          })
          .catch((err) => console.log(err));
      })}
    >
      <div className={s.inputDiv}>
        <label htmlFor='id'>아이디</label>
        <input
          type='text'
          id='id'
          placeholder='your Id'
          readOnly
          value={detail.id}
          className={s.info}
        />

        <label htmlFor='nickName'>닉네임</label>
        <input
          type='text'
          id='nickName'
          className={s.info}
          defaultValue={detail.nickname}
          {...register('nickname')}
        ></input>

        <label htmlFor='email'>이메일</label>
        <input
          type='email'
          id='email'
          className={s.info}
          defaultValue={detail.email}
          {...register('email')}
        />

        <label htmlFor='birthDay'>생년월일</label>
        <input
          type='text'
          id='birthDay'
          className={s.info}
          defaultValue={detail.birth}
          {...register('birth')}
        />
        <div className={s.bottomInput}>
          <div className={s.hnw}>
            <label htmlFor='height'>키</label>
            <input
              type='text'
              id='height'
              className={s.hInfo}
              defaultValue={detail.height}
              {...register('height')}
            ></input>

            <label htmlFor='weight'>몸무게</label>
            <input
              type='text'
              id='weight'
              className={s.hInfo}
              defaultValue={detail.weight}
              {...register('weight')}
            ></input>
          </div>
          <div id={s.genderDiv}>
            <span>성별</span>
            <div className={s.innerGenderDiv}>
              <div>
                <input
                  type='radio'
                  id='man'
                  value='MALE'
                  defaultChecked={detail.gender === 'MALE'}
                  {...register('gender')}
                />
                <label htmlFor='man'>남</label>
              </div>
              <div>
                <input
                  type='radio'
                  id='woman'
                  value='FEMALE'
                  defaultChecked={detail.gender === 'FEMALE'}
                  {...register('gender')}
                />
                <label htmlFor='woman'>여</label>
              </div>
            </div>
          </div>
        </div>

        <p className={s.inputP}>
          <button id={s.clearBtn}>Clear profile</button>
          <button type='submit' id={s.submitBtn}>
            Submit profile
          </button>
        </p>
      </div>
    </form>
  );
};

export default ModalPInfo;
