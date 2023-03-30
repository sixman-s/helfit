import React, { useState, useEffect } from 'react';
import style from '../../styles/Community/C_WritePost.module.css';
import Editor from './C_Community/Editor';
import Tag from './C_Community/Tag';
import Btn from '../loginc/Buttons';
import UserNav from './C_Community/UserNav';
import axios from 'axios';
import { useRouter } from 'next/router';

const URL = process.env.NEXT_PUBLIC_URL;

interface BoardData {
  boardId: number;
  title: string;
  text: string;
  boardImageUrl: string | null;
  tags: {
    tagId: number;
    tagName: string;
  }[];
}

const PatchPostBox = () => {
  const [category, setCategory] = useState('');
  const [tags, setTags] = useState<any[]>([]);
  const [title, setTitle] = useState('');
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [editorInput, setEditorInput] = useState('');
  const [titleError, setTitleError] = useState('');
  const [fetchedData, setFetchedData] = useState<BoardData | null>(null);
  const [fileName, setFileName] = useState<string>('변경 사항이 없습니다.');

  const router = useRouter();
  const { id } = router.query;
  let boardID: number | null = null;
  if (typeof id === 'string') {
    boardID = parseInt(id.split('/').pop() as string);
  }
  const currentPage = router.asPath.split('/')[3];
  let categoryname: string;
  let pageNumber: Number;
  switch (currentPage) {
    case 'health':
      pageNumber = 1;
      categoryname = '헬스 갤러리';
      break;
    case 'crossfit':
      pageNumber = 2;
      categoryname = '크로스핏 갤러리';
      break;
    case 'pilates':
      pageNumber = 4;
      categoryname = '필라테스 갤러리';
      break;
    case 'oww':
      pageNumber = 5;
      categoryname = '오운완 갤러리';
      break;
    case 'diet':
      pageNumber = 6;
      categoryname = '식단 갤러리';
      break;
    default:
      pageNumber = null;
  }

  const escapeMap = {
    '&lt;': '<',
    '&#12296;': '<',
    '&gt;': '>',
    '&#12297;': '>',
    '&amp;': '&',
    '&quot;': '"',
    '&#x27;': "'"
  };
  const pattern = /&(lt|gt|amp|quot|#x27|#12296|#12297);/g;
  const convertToHtml = (text) =>
    text.replace(pattern, (match, entity) => escapeMap[`&${entity};`] || match);

  // 상세페이지 글불러오기
  useEffect(() => {
    axios
      .get(`${URL}/api/v1/board/${pageNumber}/${boardID}`)
      .then((res) => {
        const data = res.data;
        res.data.text = convertToHtml(res.data.text);
        setFetchedData(data);
        setTitle(res.data.title);
        setEditorInput(res.data.text);
        setSelectedFile(res.data.boardImageUrl);
      })
      .catch((err) => console.log(err));
  }, [boardID]);

  useEffect(() => {
    if (title) setTitleError(validateTitle(title));
  }, [title]);

  const validateTitle = (title) => {
    if (title.length === 0) return '제목은 반드시 입력해야 합니다.';
  };

  const handlePostButtonClick = () => {
    const titleError = validateTitle(title);
    const userID = JSON.parse(localStorage.UserInfo).userId;
    if (titleError) {
      setTitleError(titleError);
      return;
    }
    const accessToken = localStorage.accessToken;
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
            .patch(
              `${URL}/api/v1/board/${pageNumber}/${boardID}`,
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
            .then(() => alert('게시글이 성공적으로 수정되었습니다.'))
            .then(() => router.push('/community'))
            .catch((err) => {
              alert('올바른 전송이 아닙니다.');
              console.log(err);
            });
        })
        .catch((err) => {
          alert('올바른 전송이 아닙니다.');
          console.log(err);
        });
    } else {
      axios
        .patch(
          `${URL}/api/v1/board/${pageNumber}/${boardID}`,
          {
            title: title,
            text: editorInput,
            boardTags: tags,
            boardImageUrl: selectedFile
          },
          {
            headers: {
              Authorization: `Bearer ${accessToken}`
            }
          }
        )
        .then(() => alert('게시글이 성공적으로 수정되었습니다.'))
        .then(() => router.push('/community'))
        .catch((err) => {
          alert('올바른 전송이 아닙니다.');
          console.log(err);
        });
    }
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
            <div className={style.Subtext}>카테고리는 변경할 수 없습니다.</div>
          </div>
          <div className={style.PatchCategoty}>
            <div>{categoryname}</div>
          </div>
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
              placeholder={'입력 필요'}
              className={style.TitleInput}
              value={title}
              onChange={handleTitleInputChange}
              onBlur={() => setTitleError(validateTitle(title))}
            />
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
                <p className={style.FileNameOver}>
                  {fileName}
                  {/* {fetchedData.boardImageUrl} */}
                </p>
              </div>
            </div>

            {/* 게시글 등록 버튼 */}

            <Btn
              className={style.button}
              text='게시글 수정'
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

export default PatchPostBox;
