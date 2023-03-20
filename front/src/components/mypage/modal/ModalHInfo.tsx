import axios from 'axios';
import { useForm } from 'react-hook-form';
import { useRouter } from 'next/router';

import s from '../../../styles/mypage/M_ModalHInfo.module.css';

export interface detailProps {
  result: {
    detail: {
      userId: number;
    };
    hDetail: {
      birth: number;
      gender: string;
      height: number;
      weight: number;
    };
    cDetail: {
      calculatorId: number;
      activityLevel: string;
      goal: string;
    };
  };
}

interface HeathForm {
  height: number;
  weight: number;
  birth: number;
  gender: string;
}

const ModalHInfo = ({
  detail,
  hDetail,
  cDetail
}: {
  detail;
  hDetail;
  cDetail: detailProps;
}) => {
  const { register, handleSubmit, formState } = useForm<HeathForm>({
    mode: 'all'
  });
  const router = useRouter();

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  return (
    <form
      onSubmit={handleSubmit(async (data) => {
        await new Promise((r) => setTimeout(r, 1000));

        const { birth, height, weight, gender, activityLevel, goal } = data;

        await axios
          .post(
            `${url}/api/v1/physical`,
            { birth, height, weight, gender },
            {
              headers: {
                Authorization: `Bearer ${accessToken}`
              }
            }
          )
          .then((res) => {
            console.log(`health RES : ${res}`);
          })
          .catch((err) => console.log(err));
        if (cDetail) {
          await axios
            .patch(
              `${url}/api/v1/calculate/${cDetail.calculatorId}`,
              { activityLevel, goal },
              {
                headers: {
                  Authorization: `Bearer ${accessToken}`
                }
              }
            )
            .then((res) => {
              console.log(`calculate RES : ${res}`);
              router.reload();
            })
            .catch((err) => console.log(err));
        } else {
          await axios
            .post(
              `${url}/api/v1/calculate/${detail.userId}`,
              { activityLevel, goal },
              {
                headers: {
                  Authorization: `Bearer ${accessToken}`
                }
              }
            )
            .then((res) => {
              console.log(`calculate RES : ${res}`);
              router.reload();
            })
            .catch((err) => console.log(err));
        }
      })}
    >
      <div className={s.inputDiv}>
        <div className={s.bottomInput}>
          <div className={s.hnw}>
            <label htmlFor='height'>키</label>
            <input
              type='number'
              id='height'
              className={s.hInfo}
              defaultValue={hDetail.height}
              {...register('height', {
                required: true,
                maxLength: {
                  value: 3,
                  message: '세자리 이하이어야 합니다.'
                }
              })}
            ></input>
            <p className={s.error}>{formState.errors.height?.message}</p>

            <label htmlFor='weight'>몸무게</label>
            <input
              type='number'
              id='weight'
              className={s.hInfo}
              defaultValue={hDetail.weight}
              {...register('weight', {
                required: true,
                maxLength: {
                  value: 3,
                  message: '세자리 이하이어야 합니다.'
                }
              })}
            ></input>
            <p className={s.error}>{formState.errors.weight?.message}</p>

            <label htmlFor='birthDay'>생년월일</label>
            <input
              type='number'
              id='birthDay'
              className={s.info}
              defaultValue={hDetail.birth}
              {...register('birth', {
                required: true,
                maxLength: {
                  value: 8,
                  message: '여덟 자리 이하이어야 합니다.'
                }
              })}
            />
            <p className={s.error}>{formState.errors.birth?.message}</p>
          </div>
          <div id={s.genderDiv}>
            <span>성별</span>
            <div className={s.innerGenderDiv}>
              <div>
                <input
                  type='radio'
                  id='man'
                  value='MALE'
                  defaultChecked={hDetail.gender === 'MALE'}
                  {...register('gender')}
                />
                <label htmlFor='man'>남</label>
              </div>
              <div>
                <input
                  type='radio'
                  id='woman'
                  value='FEMALE'
                  defaultChecked={hDetail.gender === 'FEMALE'}
                  {...register('gender')}
                />
                <label htmlFor='woman'>여</label>
              </div>
            </div>
          </div>
        </div>
        <label htmlFor='activity'>활동 정도</label>
        <select {...register('activityLevel')}>
          <option value='' selected disabled hidden>
            선택해주세요.
          </option>
          <option value='SEDENTARY'>거의 활동 없음</option>
          <option value='LIGHTLY_ACTIVE'>가벼운 활동</option>
          <option value='MODERATELY_ACTIVE'>적당한 활동</option>
          <option value='VERY_ACTIVE'>강도 높은 활동</option>
          <option value='EXTRA_ACTIVE'>격렬한 활동</option>
        </select>

        <label htmlFor='purpose'>운동 목적</label>
        <select {...register('goal')}>
          <option value='' selected disabled hidden>
            선택해주세요.
          </option>
          <option value='KEEP'>유지</option>
          <option value='DIET'>다이어트</option>
          <option value='BULK'>증량</option>
        </select>
        <p className={s.submitP}>
          <button type='submit' id={s.submitBtn}>
            저장
          </button>
        </p>
      </div>
    </form>
  );
};

export default ModalHInfo;
