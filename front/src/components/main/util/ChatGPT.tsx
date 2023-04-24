import { useEffect, useRef, useState } from 'react';
import { EventStreamContentType, fetchEventSource } from '@microsoft/fetch-event-source';
import styled from '../../../styles/main/C_chatGPT.module.css';
import axios from 'axios';
import { DotPulse } from '@uiball/loaders';

interface SpeechData {
  type: string;
  content: string;
}

// const LoadingComponent = () => {
//   return (
//     <span className={styled.loading}>
//       <DotPulse size={20} speed={1.3} color='#3361ff' />
//     </span>
//   );
// };

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
  const url = process.env.NEXT_PUBLIC_URL;

  const scrollRef = useRef(null);

  const [input, setInput] = useState('');
  const [speech, setSpeech] = useState([
    {
      type: 'answer',
      content: '안녕하세요. 고객님. 제 이름은 헬쳇이에요. 뭐든지 물어봐주세요.'
    }
    // {
    //   type: 'question',
    //   content: '사용자 질문'
    // }
    // ...
  ]);

  const onSubmit = async () => {
    try {
      setSpeech(prevState => [
        ...prevState,
        {
          type: 'question',
          content: input
        }
      ]);

      setInput('');

      await fetchEventSource(`${url}/api/v1/ai/question`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Cache-Control': 'no-cache',
          Accept: 'text/event-stream'
        },
        body: JSON.stringify({
          question: input
        }),
        async onopen(response) {
          if (response.ok && response.headers.get('content-type') === EventStreamContentType) return;
          else if (response.status >= 400 && response.status < 500 && response.status !== 429) throw new Error();
        },
        onmessage(event) {
          const answer = JSON.parse(event.data);

          setSpeech(prevState => {
            let lastSpeech = prevState[prevState.length - 1];
            let updatedLastSpeech;

            if (lastSpeech.type !== 'answer') {
              updatedLastSpeech = {
                type: 'answer',
                content: ''
              };

              return [...prevState, updatedLastSpeech];
            } else {
              updatedLastSpeech = {
                ...lastSpeech,
                content: answer.choices[0].delta.content ? lastSpeech.content + answer.choices[0].delta.content : lastSpeech.content
              };

              return [...prevState.slice(0, -1), updatedLastSpeech];
            }
          });
        },
        onclose() {
          // console.log('connection closed!');
        }
      });
    } catch (e) {
      console.log(e);
    }
  };

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

  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [speech]);

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
