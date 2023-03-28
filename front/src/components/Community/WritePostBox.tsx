import React, { useState, useEffect } from 'react';
import style from '../../styles/Community/C_WritePost.module.css';
import Editor from './C_Community/Editor';
import DropdownC, { Option } from './C_Community/Dropdown';
import Tag from './C_Community/Tag';
import Btn from '../loginc/Buttons';
import UserNav from './C_Community/UserNav';
import axios from 'axios';
import { useRouter } from 'next/router';

const URL = process.env.NEXT_PUBLIC_URL;

const options: Option[] = [
  { key: 'health', text: '헬스 갤러리', value: '1' },
  { key: 'crossfit', text: '크로스핏 갤러리', value: '2' },
  { key: 'pilates', text: '필라테스 갤러리', value: '4' },
  { key: 'oww', text: '오운완 갤러리', value: '5' },
  { key: 'diet', text: '식단 갤러리', value: '6' }
];

const WritePostBox = () => {
  const [category, setCategory] = useState('');
  const [tags, setTags] = useState<any[]>([]);
  const [title, setTitle] = useState('');
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [fileName, setFileName] = useState<string>('No file selected');
  const [editorInput, setEditorInput] = useState('');
  const [titleError, setTitleError] = useState('');

  useEffect(() => {
    if (title) setTitleError(validateTitle(title));
  }, [title]);

  const validateTitle = (title) => {
    if (title.length === 0) return '제목은 반드시 입력해야 합니다.';
  };
  const router = useRouter();

  const handlePostButtonClick = () => {
    const titleError = validateTitle(title);
    const userID = JSON.parse(localStorage.UserInfo).userId;

    if (titleError) {
      setTitleError(titleError);
      return;
    }

    const accessToken = localStorage.accessToken;

    if ((category === '5' || category === '6') && !selectedFile) {
      alert('오운완 갤러리와 식단 갤러리는 사진 업로드가 필수 입니다.');
      return;
    }

    if (selectedFile) {
      const formData = new FormData();
      formData.append('multipartFile', selectedFile);
      return axios
        .post(`${URL}/api/v1/file/upload`, formData, {
          headers: {
            'content-type': 'multipart/form-data',
            Authorization: `Bearer ${accessToken}`
          }
        })
        .then((res) => {
          const boardImageUrl = res.data.body.resource;
          axios
            .post(
              `${URL}/api/v1/board/${category}/${userID}`,
              {
                title: title,
                text: editorInput,
                boardTags: tags,
                boardImageUrl: boardImageUrl
              },
              {
                headers: {
                  Authorization: `Bearer ${accessToken}`
                }
              }
            )
            .then(() => router.push('/community'))
            .catch((err) => {
              alert('올바른 요청이 아닙니다.');
              console.log(err);
            });
        })
        .catch((err) => {
          alert('올바른 요청이 아닙니다.');
          console.log(err);
        });
    }

    axios
      .post(
        `${URL}/api/v1/board/${category}/${userID}`,
        {
          title: title,
          text: editorInput,
          boardTags: tags
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`
          }
        }
      )
      .then(() => alert('게시글 등록이 성공적으로 이루어졌습니다.'))
      .then(() => router.push('/community'))
      .catch((err) => {
        alert('올바른 요청이 아닙니다.');
        console.log(err);
      });
  };

  const handleTagAdd = (newTags: string[]) => {
    setTags(newTags);
  };
  const handleDropdownChange = (event, data) => {
    setCategory(data.value);
  };
  const handleTitleInputChange = (event) => {
    setTitle(event.target.value);
  };
  const handleEditorInput = (text: string) => {
    setEditorInput(text);
  };

  const handleFileInputChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const files = event.target.files;
    if (files && files[0]) {
      setFileName(files[0].name);
      setSelectedFile(files[0]);
    } else {
      setFileName('No file selected');
      setSelectedFile(null);
    }
  };

  return (
    <>
      <div className={style.allInput}>
        <div className={style.UserProfile}>
          <UserNav />
        </div>

        {/* 카테고리 */}

        <div className={style.category}>
          <div className={style.titleMessage}>
            <div className={style.Text}>카테고리</div>
            <div className={style.Subtext}>
              카테고리는 반드시 설정해주어야 합니다.
            </div>
          </div>
          <DropdownC options={options} onChange={handleDropdownChange} />
        </div>

        {/* 테그 */}

        <div className={style.tag}>
          <div className={style.titleMessage}>
            <div className={style.Text}>태그</div>
            <div className={style.Subtext}>
              태그는 최대 5개까지 추가할 수 있습니다.
            </div>
          </div>
          <Tag onTagAdd={handleTagAdd} />
        </div>

        {/* 제목 */}

        <div className={style.title}>
          <div className={style.titleMessage}>
            <div className={style.Text}>제목</div>
            <div
              className={style.Subtext}
              style={{ color: titleError ? 'red' : 'var(--text_5)' }}
            >
              {titleError || '제목은 반드시 입력해야 합니다.'}
            </div>
          </div>
          <div className={style.TitleInput}>
            <input
              type='text'
              placeholder='Write your Title...'
              className={style.TitleInput}
              value={title}
              onChange={handleTitleInputChange}
              onBlur={() => setTitleError(validateTitle(title))}
            />
          </div>
        </div>

        {/* 사진첨부 */}

        <div>
          <div className={style.title}>
            <div className={style.titleMessage}>
              <div className={style.Text}>사진</div>
              <div className={style.Subtext}>
                오운완 갤러리와 식단 갤러리는 사진 선택이 필수입니다.
              </div>
            </div>
          </div>
          <div className={style.line}>
            <div className={style.picture}>
              <input
                type='file'
                accept='image/*'
                id='fileInput'
                onChange={handleFileInputChange}
                style={{ display: 'none' }}
              />
              <label htmlFor='fileInput' className={style.label}>
                사진 선택
              </label>
              <div className={style.FileName}>
                <p className={style.FileNameOver}>{fileName}</p>
              </div>
            </div>

            {/* 게시글 등록 버튼 */}

            <Btn
              className={style.button}
              text='게시글 등록'
              onClick={handlePostButtonClick}
            />
          </div>
        </div>

        {/* 내용 작성 에디터 */}

        <div className={style.editorBox}>
          <Editor
            editorInput={editorInput}
            setEditorInput={handleEditorInput}
          />
        </div>
      </div>
    </>
  );
};

export default WritePostBox;
