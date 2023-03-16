import React, { useState, useEffect } from 'react';
import style from '../../styles/Community/C_WritePost.module.css';
import Editor from './C_Community/Editor';
import DropdownC, { Option } from './C_Community/Dropdown';
import Tag from './C_Community/Tag';
import Btn from '../loginc/Buttons';
import UserNav from './C_Community/UserNav';
import axios from 'axios';

const options: Option[] = [
  { key: 'health', text: '헬스 갤러리', value: 'health' },
  { key: 'pilates', text: '필라테스 갤러리', value: 'pilates' },
  { key: 'crossfit', text: '크로스핏 갤러리', value: 'crossfit' },
  { key: 'oww', text: '오운완 갤러리', value: 'oww' },
  { key: 'diet', text: '식단 갤러리', value: 'diet' }
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
    if (title.length === 0) return 'title cannot be empty';
  };

  const handlePostButtonClick = () => {
    const titleError = validateTitle(title);

    if (titleError) {
      setTitleError(titleError);
      return;
    }
    console.log({
      category: category,
      tags: tags,
      title: title,
      files: selectedFile,
      content: editorInput
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
  const handleEditorInput = (content: string) => {
    setEditorInput(content);
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
          <div className={style.Text}>카테고리</div>
          <div className={style.dropdown}>
            <DropdownC options={options} onChange={handleDropdownChange} />
          </div>
        </div>

        {/* 테그 */}

        <div className={style.tag}>
          <div className={style.Text}>태그</div>
          <div className={style.dropdown}>
            <Tag onTagAdd={handleTagAdd} />
          </div>
        </div>

        {/* 제목 */}

        <div className={style.title}>
          <div className={style.Text}>Title</div>
          <div className={style.TitleInput}>
            <input
              type='text'
              placeholder='Write your Title...'
              className={style.TitleInput}
              value={title}
              onChange={handleTitleInputChange}
              onBlur={() => setTitleError(validateTitle(title))}
            />
            {titleError && <div className={style.error}>{titleError}</div>}
          </div>
        </div>

        {/* 사진첨부 */}

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
