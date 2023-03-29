import { useEffect, useRef, useState } from 'react';
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
    return type === 'question' && content !== '' ? (
      <div className={styled.questionArea} key={index}>
        <p className={`${styled.speech} ${styled.question}`}>{content}</p>
        <img src='../../../assets/main/questioner_icon.svg' />
      </div>
    ) : (
      <div className={styled.answerArea} key={index}>
        <img src='../../../assets/main/answer_icon.svg' />
        <p className={`${styled.speech} ${styled.answer}`}>{content}</p>
      </div>
    );
  });
};

const ChatPopup = () => {
  const scrollRef = useRef(null);

  const [speech, setSpeech] = useState([
    {
      type: 'answer',
      content: '안녕하세요. 고객님. 제 이름은 헬쳇이에요. 뭐든지 물어봐주세요.'
    }
  ]);
  const [input, setInput] = useState('');
  const [answer, setAnswer] = useState(null);
  const url = process.env.NEXT_PUBLIC_URL;

  const onPressEnter = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.nativeEvent.isComposing) {
      // isComposing 이 true 이면
      return; // 조합 중이므로 동작을 막는다.
    }
    if (e.key === 'Enter' && e.shiftKey) {
      // [shift] + [Enter] 치면 걍 리턴
      return;
    } else if (e.key === 'Enter') {
      // [Enter] 치면 메시지 보내기
      onSubmit();
    }
  };

  const onSubmit = () => {
    axios
      .post(`${url}/api/v1/ai/question`, {
        question: input
      })
      .then()
      .then((res) => {
        setAnswer(res.data.body.data.choices[0].message.content);
      })
      .catch((error) => alert('내용을 입력해 주세요.'));
    setSpeech([
      ...speech,
      {
        type: 'question',
        content: input
      }
    ]);
    setInput('');
  };

  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [speech]);

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
      <div className={styled.chatField} ref={scrollRef}>
        <div className={styled.chatView}>
          <SpeechBubble data={speech} />
        </div>
      </div>
      <div className={styled.inputArea}>
        <textarea
          className={styled.textinput}
          placeholder='헬쳇과 대화해보세요.'
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => onPressEnter(e)}
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
        src='../../../../assets/main/chatGPT_icon.svg'
        onClick={() => setClick(!click)}
      />
      {click ? <ChatPopup /> : null}
    </div>
  );
};

export default ChatGPT;
