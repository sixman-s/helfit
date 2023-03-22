import React, { Dispatch, SetStateAction } from 'react';
import dynamic from 'next/dynamic';
import style from '../../../styles/Community/C_WritePost.module.css';
const ReactQuill = dynamic(() => import('react-quill'), { ssr: false });
import 'react-quill/dist/quill.snow.css';

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
    [{ align: [] }, { color: [] }, { background: [] }], // dropdown with defaults from theme
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
      <div dangerouslySetInnerHTML={{ __html: editorInput }}></div>
    </div>
  );
}

export default Editor;
