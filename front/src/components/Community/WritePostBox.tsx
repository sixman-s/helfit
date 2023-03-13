import React, { useState } from 'react';
import style from '../../styles/Community/C_WritePost.module.css';
import Editor from './C_Community/Editor';
import DropdownC, { Option } from './C_Community/Dropdown';
import Tag from './C_Community/Tag';
import Btn from '../Login/Buttons';

const options: Option[] = [
  { key: 'health', text: '헬스 갤러리', value: 'health' },
  { key: 'pilates', text: '필라테스 갤러리', value: 'pilates' },
  { key: 'crossfit', text: '크로스핏 갤러리', value: 'crossfit' },
  { key: 'oww', text: '오운완 갤러리', value: 'oww' },
  { key: 'diet', text: '식단 갤러리', value: 'diet' }
];

const WritePostBox = () => {
  const [userProfile, setUserProfile] = useState(
    '../assets/Community/UserProfile.svg'
  );
  const [editorInput, setEditorInput] = useState('');
  const [userName, setUserName] = useState('User');
  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  const handleUserProfile = (userProfileData: string) => {
    if (userProfileData) {
      setUserProfile(userProfileData);
    }
  };

  const handleUserName = (userNameData: string) => {
    if (userNameData) {
      setUserName(userNameData);
    }
  };

  const [fileName, setFileName] = useState<string>('No file selected');

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

  const handleEditorInput = (content: string) => {
    setEditorInput(content);
  };

  return (
    <>
      <div className={style.allInput}>
        <div className={style.UserProfile}>
          <img
            src={userProfile}
            className={style.UserPhoto}
            onError={() =>
              setUserProfile('../assets/Community/UserProfile.svg')
            }
          />
          <div className={style.UserName}>{userName}</div>
        </div>
        <div className={style.category}>
          <div className={style.Text}>카테고리</div>
          <div className={style.dropdown}>
            <DropdownC options={options} />
          </div>
        </div>
        <div className={style.tag}>
          <div className={style.Text}>태그</div>
          <div className={style.dropdown}>
            <Tag placeholder='Enter tags' label='Add Tag' />
          </div>
        </div>
        <div className={style.title}>
          <div className={style.Text}>Title</div>
          <div className={style.TitleInput}>
            <input
              type='text'
              placeholder='Write your Title...'
              className={style.TitleInput}
            />
          </div>
        </div>
        <div>
          <div className={style.Text}>사진</div>
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
            <Btn
              className={style.button}
              text='게시글 등록'
              onClick={() => console.log('게시글 작성 버튼을 눌렀습니다.')}
            />
          </div>
        </div>
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
