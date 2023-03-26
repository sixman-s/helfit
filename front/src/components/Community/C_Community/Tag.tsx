import { useState } from 'react';
import style from '../../../styles/Community/C_Tag.module.css';

export interface TagProps {
  onTagAdd: (newTags: string[]) => void;
}

const Tag: React.FC<TagProps> = ({ onTagAdd }) => {
  const [tags, setTags] = useState<string[]>([]);
  const [tag, setTag] = useState<string>('');

  const removeTag = (i: number) => {
    const newTags = [...tags];
    newTags.splice(i, 1);
    setTags(newTags);
    onTagAdd(newTags);
  };

  const addTag = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newTag = e.target.value.trim();
    if (newTag.length > 10 || tags.length >= 5) {
      return;
    }
    setTag(newTag);
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      handleClick();
    }
  };

  const handleClick = () => {
    if (tags.length >= 5) {
      return alert('tag는 최대 5개까지 추가할 수 있습니다.');
    }
    const newTags = [...tags, tag];
    setTags(newTags);
    setTag('');
    onTagAdd(newTags);
  };

  return (
    <div>
      <div className={style.TagContainer}>
        <input
          className={style.InputBox}
          placeholder='Press enter to add tags ...'
          onChange={(e) => addTag(e)}
          onKeyPress={(e) => handleKeyPress(e)}
          value={tag}
        />
        {tags.map((e, i) => (
          <div className={style.Hash} key={i}>
            <div className={style.HashName}>{e}</div>
            <div className={style.HashBtn} onClick={() => removeTag(i)}>
              x
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Tag;
