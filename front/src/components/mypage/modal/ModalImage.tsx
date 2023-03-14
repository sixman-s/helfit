import axios from 'axios';
import { useRef } from 'react';
import { useState } from 'react';

import s from '../../../styles/mypage/M_ModalImage.module.css';

const ModalImage = () => {
  const firstImageUrl = '../../../../assets/mypage/profile.svg';

  const [fileURL, setFileURL] = useState<string>('');
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
    URL.revokeObjectURL(fileURL);
    setFileURL('');
    setFile(null);
  };

  const submitHandler = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();

    /** 서버통신 */
    const formData = new FormData();

    if (file) {
      formData.append('file', file[0]);

      try {
        // const response = await axios.post('/api/upload', formData, {
        //   headers: { 'content-type': 'multipart/form-data' }
        // });
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

        <button id={s.submitBtn}>사진 저장</button>
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
