import axios from 'axios';
import { useRef } from 'react';
import { useState } from 'react';
import { useRouter } from 'next/router';

import s from '../../../styles/mypage/M_ModalImage.module.css';
import { userInfo1 } from '@/pages/mypage';

const ModalImage = (detail: userInfo1['detail']) => {
  const router = useRouter();
  const firstImageUrl = '../../../../assets/mypage/profile.svg';

  const url = process.env.NEXT_PUBLIC_URL;
  const accessToken = localStorage.getItem('accessToken');

  const [fileURL, setFileURL] = useState<string>(detail.profileImageUrl);
  const [file, setFile] = useState<FileList | null>();
  const imgUploadInput = useRef<HTMLInputElement | null>(null);

  const onImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      setFile(event.target.files);

      const newFileURL = URL.createObjectURL(event.target.files[0]);
      setFileURL(newFileURL);
    }
  };

  const onImageRemove = (): void => {
    // URL.revokeObjectURL(fileURL);
    // setFileURL('');
    // setFile(null);

    if (confirm('삭제하시겠습니까?')) {
      try {
        axios
          .delete(`${url}/api/v1/users/profile-image`, {
            headers: {
              Authorization: `Bearer ${accessToken}`
            }
          })
          .then((res) => {
            if (res.status === 200) {
              router.reload();
            }
          });
      } catch (error: any) {
        console.log('이미지업로드 에러 발생');
        throw new Error(error);
      }
    }
  };

  const submitHandler = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    /** 서버통신 */
    const formData = new FormData();

    if (file) {
      formData.append('multipartFile', file[0]);

      try {
        await axios
          .post(`${url}/api/v1/users/profile-image`, formData, {
            headers: {
              'content-type': 'multipart/form-data',
              Authorization: `Bearer ${accessToken}`
            }
          })
          .then((res) => {
            let userInfo = JSON.parse(localStorage.getItem('UserInfo'));

            userInfo['profileImageUrl'] = res.data.body.resource;

            localStorage.setItem('UserInfo', JSON.stringify(userInfo));
            console.log(JSON.parse(localStorage.getItem('UserInfo')));

            router.reload();
          })
          .catch((err) => console.log(err));
      } catch (error: any) {
        console.log('이미지업로드 에러 발생');
        throw new Error(error);
      }
    } else {
      alert('업로드할 이미지가 없습니다');
    }
  };

  return (
    <div className={s.imageDiv}>
      <p id={s.imageP}>
        <img
          id={s.img}
          src={fileURL ? fileURL : firstImageUrl}
          className={s.profile}
        />
      </p>
      <p className={s.imageBtn}>
        <button
          id={s.findBtn}
          onClick={(event) => {
            event.preventDefault();
            if (imgUploadInput.current) {
              imgUploadInput.current.click();
            }
          }}
        >
          사진 찾기
        </button>

        <button id={s.submitBtn} onClick={submitHandler}>
          사진 저장
        </button>
        <button id={s.deleteBtn} onClick={onImageRemove}>
          사진 삭제
        </button>
        <input
          type='file'
          id={s.imageInput}
          accept='image/*'
          required
          ref={imgUploadInput}
          onChange={onImageChange}
        />
      </p>
    </div>
  );
};

export default ModalImage;
