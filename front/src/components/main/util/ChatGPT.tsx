import { useEffect, useState } from 'react';
import styled from '../../../styles/main/C_chatGPT.module.css';
import axios from 'axios';
import { DotPulse } from '@uiball/loaders';

interface SpeechData {
  type: string;
  content: string;
}

const LodingComponent = () => {
  return (
    <span className={styled.loading}>
      <DotPulse size={20} speed={1.3} color='#3361ff' />
    </span>
  );
};

const SpeechBubble = ({ data }) => {
  return data.map(({ type, content }: SpeechData, index: number) => {
    return type === 'question' ? (
      <div className={styled.questionArea} key={index}>
        <p className={`${styled.speech} ${styled.question}`}>{content}</p>
        <img src='../../../assets/mainP/questioner_icon.svg' />
      </div>
    ) : (
      <div className={styled.answerArea} key={index}>
        <img src='../../../assets/mainP/answer_icon.svg' />
        <p className={`${styled.speech} ${styled.answer}`}>{content}</p>
      </div>
    );
  });
};

const ChatPopup = () => {
  const [speech, setSpeech] = useState([
    {
      type: 'answer',
      content: '안녕하세요. 고객님. 제 이름은 헬쳇이에요. 뭐든지 물어봐주세요.'
    }
  ]);
  const [input, setInput] = useState('');
  const [answer, setAnswer] = useState(null);
  const url = process.env.NEXT_PUBLIC_URL;

  const onSubmit = () => {
    setSpeech([
      ...speech,
      {
        type: 'question',
        content: input
      }
    ]);
    setInput('');

    axios
      .post(`${url}/api/v1/ai/question`, {
        question: input
      })
      .then((res) => {
        setAnswer(res.data.body.data.choices[0].message.content);
      });
  };

  useEffect(() => {
    answer === null
      ? null
      : setSpeech([
          ...speech,
          {
            type: 'answer',
            content: answer
          }
        ]);
  }, [answer]);

  return (
    <article className={styled.popupContainer}>
      <div className={styled.chatField}>
        <div className={styled.chatView}>
          <SpeechBubble data={speech} />
        </div>
      </div>
      <div className={styled.inputArea}>
        <input
          className={styled.textinput}
          type='text'
          placeholder='헬쳇과 대화해보세요.'
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => (e.key == 'Enter' ? onSubmit() : null)}
        />
        <button className={styled.submitBtn} onClick={() => onSubmit()}>
          제출
        </button>
      </div>
    </article>
  );
};

const ChatGPT = () => {
  const [click, setClick] = useState(false);
  return (
    <div>
      <img
        className={styled.stikyBtn}
        src='../../../assets/MainP/chatGPT_icon.svg'
        onClick={() => setClick(!click)}
      />
      {click ? <ChatPopup /> : null}
    </div>
  );
};

export default ChatGPT;
