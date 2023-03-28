import style from '../../../styles/Community/C_WritePost.module.css';
import React, { Dispatch, SetStateAction } from 'react';
import dynamic from 'next/dynamic';
import 'react-quill/dist/quill.snow.css';

const ReactQuill = dynamic(
  () => import('react-quill').then((mod) => mod.default || mod),
  {
    ssr: false,
    loading: () => <div>Loading...</div> // add a loading component
  }
);
interface EditorProps {
  editorInput?: string;
  setEditorInput: Dispatch<SetStateAction<string>>;
  formValues?: any;
  handleValidation?: () => void;
  errorMsg?: boolean;
  setNewAnswer?: Dispatch<SetStateAction<string>>;
  editorError?: string;
  onBlur?: any;
}

const modules = {
  toolbar: [
    ['bold', 'italic', 'underline', 'strike', 'blockquote', 'code-block'],
    [
      { list: 'ordered' },
      { list: 'bullet' },
      { indent: '-1' },
      { indent: '+1' }
    ],
    [{ align: [] }, { color: [] }, { background: [] }],
    ['clean']
  ]
};

const formats = [
  'header',
  'bold',
  'italic',
  'underline',
  'strike',
  'blockquote',
  'list',
  'bullet',
  'indent',
  'align',
  'color',
  'background'
];

function Editor({
  editorInput,
  setEditorInput,
  formValues,
  handleValidation,
  errorMsg,
  setNewAnswer,
  editorError
}: EditorProps) {
  const handleText = (content: string) => {
    if (setNewAnswer) {
      setNewAnswer(content);
    } else {
      setEditorInput(content);
    }
  };

  const handleBlur = () => {
    if (handleValidation) {
      handleValidation();
    }
  };

  return (
    <div>
      <ReactQuill
        modules={modules}
        formats={formats}
        value={editorInput}
        onChange={handleText}
        onBlur={handleBlur}
        className={style.editor}
      />
    </div>
  );
}

export default Editor;
