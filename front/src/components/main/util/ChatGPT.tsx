import { useEffect, useState } from 'react';
import styled from '../../../styles/main/C_chatGPT.module.css';
import axios from 'axios';
import { DotPulse } from '@uiball/loaders';

const ChatPopup = () => {
  const [input, setInput] = useState(null);
  const [answer, setAnswer] = useState(null);
  const [question, setQuestion] = useState(null);
  const [pending, setPending] = useState(false);
  const url = process.env.NEXT_PUBLIC_URL;

  const setLodingComponent = () => {
    return (
      <span className={styled.loading}>
        <DotPulse size={20} speed={1.3} color='#3361ff' />
      </span>
    );
  };
  console.log(input);
  const onSubmit = () => {
    setQuestion(input);
    axios
      .post(`${url}/api/v1/ai/question`, {
        question: input
      })
      .then((res) => {
        setPending(true);
        setAnswer(res.data.body.data.choices[0].message.content);
      })
      .then(() => {
        setPending(false);
      });
  };

  return (
    <article className={styled.popupContainer}>
      <div className={styled.chatField}>
        <div className={styled.chatView}>
          <div className={styled.answerArea}>
            <img src='assets/mainP/answer_icon.svg' />
            <p className={`${styled.speech} ${styled.answer}`}>
              안녕하세요. 고객님. 제 이름은 헬쳇이에요. 뭐든지 물어봐주세요.
            </p>
          </div>
          {question ? (
            <div className={styled.questionArea}>
              <p className={`${styled.speech} ${styled.question}`}>
                {question}
              </p>
              <img src='assets/mainP/questioner_icon.svg' />
            </div>
          ) : null}
          {answer ? (
            <div className={styled.answerArea}>
              <img src='assets/mainP/answer_icon.svg' />
              <p className={`${styled.speech} ${styled.answer}`}>{answer}</p>
            </div>
          ) : null}
        </div>
      </div>
      <div className={styled.inputArea}>
        <input
          className={styled.textinput}
          type='text'
          placeholder='헬쳇과 대화해보세요.'
          onChange={(e) => setInput(e.target.value)}
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
        src='assets/MainP/chatGPT_icon.svg'
        onClick={() => setClick(!click)}
      />
      {click ? <ChatPopup /> : null}
    </div>
  );
};

export default ChatGPT;
